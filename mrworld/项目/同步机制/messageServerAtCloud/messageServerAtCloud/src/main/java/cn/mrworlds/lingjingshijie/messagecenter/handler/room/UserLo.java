package cn.mrworlds.lingjingshijie.messagecenter.handler.room;

import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTP;
import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTPCode;
import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTPCommand;
import cn.mrworlds.lingjingshijie.messagecenter.router.ChannelRouter;
import io.netty.channel.ChannelHandlerContext;

public class UserLo extends RoomHandler {
    @Override
    void handle(ChannelHandlerContext ctx, MRTPCommand msg) {
        ChannelRouter.getInstance().registerRouter(msg.userID, ctx.channel());
        MRTP.returnStringMsg(MRTP.CMD_LOGIN_RSP,String.valueOf(msg.userID), ctx.channel());
        System.out.println("发送成功");
    }

    @Override
    void initCmd() {
        this.setCmdCode(MRTPCode.USER_LOGIN);
    }
}
