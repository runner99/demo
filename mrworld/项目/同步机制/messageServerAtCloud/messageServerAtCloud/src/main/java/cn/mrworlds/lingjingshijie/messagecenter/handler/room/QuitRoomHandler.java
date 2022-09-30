package cn.mrworlds.lingjingshijie.messagecenter.handler.room;

import cn.mrworlds.lingjingshijie.messagecenter.Constant.CacheConstant;
import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTP;
import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTPCode;
import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTPCommand;
import cn.mrworlds.lingjingshijie.messagecenter.router.RoomUserChannelRouter;
import cn.mrworlds.lingjingshijie.messagecenter.util.Cache;
import cn.mrworlds.lingjingshijie.messagecenter.util.SpringUtils;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

public class QuitRoomHandler extends RoomHandler {
    public static Cache cache = SpringUtils.getBean(Cache.class);
    @Override
    void handle(ChannelHandlerContext ctx, MRTPCommand msg) {
        Channel channel = ctx.channel();

        RoomUserChannelRouter.getInstance().quitRoom(channel);

        MRTPCommand command = new MRTPCommand();
        command.operateCode = MRTP.QUIT_ROOM_RSP;

        JSONObject json = new JSONObject();
        json.put("userName",cache.hget(CacheConstant.USER_ID_NAME,msg.userID+""));
        json.put("userID",msg.userID);
        command.payload = json.toJSONString().getBytes();
        command.userID = msg.userID;

        //告知房间内其他人用户退出了房间
        RoomUserChannelRouter.getInstance().publishMsg(msg.roomID,command);
    }

    @Override
    void initCmd() {
        super.setCmdCode(MRTPCode.QUIT_ROOM);
    }
}
