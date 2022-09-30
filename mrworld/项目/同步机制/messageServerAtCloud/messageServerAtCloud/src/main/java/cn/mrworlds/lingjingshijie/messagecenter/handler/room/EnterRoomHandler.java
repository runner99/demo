package cn.mrworlds.lingjingshijie.messagecenter.handler.room;

import cn.mrworlds.lingjingshijie.messagecenter.Constant.CacheConstant;
import cn.mrworlds.lingjingshijie.messagecenter.Constant.ServiceConfig;
import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTP;
import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTPCode;
import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTPCommand;
import cn.mrworlds.lingjingshijie.messagecenter.router.RoomUserChannelRouter;
import cn.mrworlds.lingjingshijie.messagecenter.util.Cache;
import cn.mrworlds.lingjingshijie.messagecenter.util.RedisLock;
import cn.mrworlds.lingjingshijie.messagecenter.util.SpringUtils;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EnterRoomHandler extends RoomHandler {

    private Cache cache = SpringUtils.getBean(Cache.class);
    private RedisLock redisLock = SpringUtils.getBean(RedisLock.class);

    @Override
    public void initCmd() {
        this.setCmdCode(MRTPCode.USER_ENTER_ROOM);
    }

    @Override
    //将用户的消息
    public void handle(ChannelHandlerContext ctx, MRTPCommand msg) {
        String data = new String(msg.payload);

        JSONObject json = (JSONObject) JSONObject.parse(data);

        Integer userID = json.getInteger("userID");

        Integer roomID = json.getInteger("roomID");



        //如果在缓存中没有 对应的key，则认为没有通过逻辑中心，就不用初始化各种路由了，返回加入房间失败信息
        Object token = cache.hget(CacheConstant.ROOM_USER_MAP+roomID, userID.toString());

        if(token==null){
            log.info("缓存中没有数据，告知客户端加入失败，重新登陆后尝试");
            MRTP.returnStringMsg(MRTP.USER_ENTER_ROOM_RSP_FALSE,"加入房间异常，请重试一次", ctx.channel());
            return;
        }

        //TODO 加锁，保证同一时刻内多个相同的user_room只有一个被处理
        String key = "MESSAGE_CENTER_ENTER_ROOM_USER_ROOM_ID:"+userID+":"+roomID;
        if(!redisLock.lock(key,key,2)){
           return;
        }


        Integer lastMessageID = json.containsKey("msgID")?json.getInteger("msgID"):0;
        RoomUserChannelRouter.getInstance().regesterOne(userID,roomID,ctx.channel(),lastMessageID);

        JSONObject res = new JSONObject();
        res.put("userName",token);
        res.put("roomID",roomID);
        res.put("userID",userID);

        MRTPCommand command = new MRTPCommand(MRTP.USER_ENTER_ROOM_RSP_OK,res.toJSONString());
        command.propagation = MRTP.MSG_PROPAGATION_ROOM;
        command.userID = userID;

        RoomUserChannelRouter.getInstance().publishMsg(roomID,command);
    }

}
