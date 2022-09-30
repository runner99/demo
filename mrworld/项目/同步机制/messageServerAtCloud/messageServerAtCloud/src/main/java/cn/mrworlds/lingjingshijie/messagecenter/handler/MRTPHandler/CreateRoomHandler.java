package cn.mrworlds.lingjingshijie.messagecenter.handler.MRTPHandler;

import cn.mrworlds.lingjingshijie.messagecenter.Constant.Api;
import cn.mrworlds.lingjingshijie.messagecenter.Constant.ServiceConfig;
import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTP;
import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTPCode;
import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTPCommand;
import cn.mrworlds.lingjingshijie.messagecenter.room.moudel.Room;
import cn.mrworlds.lingjingshijie.messagecenter.router.ChannelRouter;
import cn.mrworlds.lingjingshijie.messagecenter.router.RoomServerRouter;
import cn.mrworlds.lingjingshijie.messagecenter.util.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
/**
 * 处理创建房间请求的逻辑
 * 1.先发送HTTP请求将room持久化到本地，同时将房间信息存放到redis内存
 * 2.在room里开辟多个频道[topic]，房间加入用户后，用户会往这些频道发布相关消息 topic 有 message,admin,hardware,game四个
 * 3.在room中初始化存放消息的队列
 * 4.返回room的详细信息
 */
public class CreateRoomHandler implements IMRTPHandler{
    @Override
    public boolean handle(ChannelHandlerContext ctx, MRTPCommand msg){
        if(msg.operateCode == MRTPCode.CREATE_ROOM){
            String data = new String(msg.payload);

            System.out.println("receive create_room data:"+data);

            String roomSign = null;
            Integer roomID = null;
            try {
                JSONObject json = JSONObject.parseObject(data);

                roomSign = json.getString("token");
                roomID = json.getInteger("roomID");
            }catch (Exception e){
                MRTP.returnStringMsg(MRTP.CREATE_ROOM_RSP_FALSE,"数据错误", ctx.channel());
                return true;
            }

            if(RoomServerRouter.getInstance().existRoom(roomID)){
                return true;
            }


            String responseStr = null;
            int roomPort = RoomServerRouter.getInstance().getOnePort();
            try {

                RoomServerRouter.getInstance().RegesterOne(roomID,roomPort);

                Map<String,Object> map = new HashMap<>();
                map.put("roomToken",roomSign);
                map.put("serverUrl", ServiceConfig.ROOM_ADDRESS_PARADIGM.replace("{roomPort}",String.valueOf(roomPort)));//TODO :将前缀设置成Config放到config文件中
                map.put("userID", ChannelRouter.getInstance().getID(ctx.channel()));

                 String create_room_result = HttpUtil.sendPostForm(Api.getCreate_room_api(), map);
                // {"statusCode":null,"data":{"id":16,"name":"room2","password":"zhoujian","creator":"122","limits":-1,"mode":1,"purpose":null,"description":null,"applyTime":null,"state":0,"startTime":"2021-12-02 11:53:39","endTime":"2021-12-05 11:53:39","template":null,"sign":"u0t0b00g","location":"/roomresource\\u0t0b00g","attendtype":null,"owner":"122","messageurl":"ws://192.168.1.118:20010/ws","initialstatus":null},"info":null,"status":200}

                Room room  = HttpUtil.dataFromResponse(create_room_result, Room.class);
                if(room==null){
                    System.out.println("添加失败");
                    throw new RuntimeException("添加失败");
                }

                try {
                    RoomServerRouter.getInstance().regeterOneRoom(room);
                    MRTP.returnStringMsg(MRTP.CREATE_ROOM_RSP_OK, room.toString(), ctx.channel());
                } catch (Exception e){

                }
                log.info("成功发送成功创建房间消息 ！");
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("e.msg:"+e.getMessage());
                responseStr = "创建失败，请检查房间的各项信息是否符合要求";
                RoomServerRouter.getInstance().releaseRoomPort(roomPort);
                RoomServerRouter.getInstance().UnRegesterOne(roomID);
                RoomServerRouter.getInstance().unregesterOneRoom(roomID);
                //将名称和编号的占位给取消掉
                Map<String,Object> map = new HashMap<>();
                map.put("token",roomSign);
                HttpUtil.sendPostForm(Api.getCancle_occupation_room_api(), map);

                MRTP.returnStringMsg(MRTP.CREATE_ROOM_RSP_FALSE,responseStr, ctx.channel());
                return true;
            }
            return true;
        }

        return false;
    }
}
