package cn.mrworlds.lingjingshijie.messagecenter.handler.MRTPHandler;

import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTP;
import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTPCode;
import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTPCommand;
import cn.mrworlds.lingjingshijie.messagecenter.router.ChannelRouter;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserLoginhandler implements IMRTPHandler {
    @Override
    public boolean handle(ChannelHandlerContext ctx, MRTPCommand msg) {
        if(msg.operateCode == MRTPCode.USER_LOGIN){
            Channel oldConnect = ChannelRouter.getInstance().getChannel(msg.userID);
            if (oldConnect != null) {
                oldConnect.close();
                ChannelRouter.getInstance().unRegisterRouter(oldConnect);
            }
            registerRouter(ctx, msg);
            return true;
        }
        return false;
    }

    private void registerRouter(ChannelHandlerContext ctx, MRTPCommand msg) {
        ChannelRouter.getInstance().registerRouter(msg.userID, ctx.channel());

        MRTP.returnStringMsg(MRTP.CMD_LOGIN_RSP,String.valueOf(msg.userID), ctx.channel());
    }

}
