package cn.mrworlds.lingjingshijie.messagecenter.handler.MRTPHandler;

import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTP;
import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTPCode;
import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTPCommand;
import cn.mrworlds.lingjingshijie.messagecenter.router.ChannelRouter;
import cn.mrworlds.lingjingshijie.messagecenter.util.MessageQueueUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
public class MessageQueueHandler implements IMRTPHandler {
    @Override
    public boolean handle(ChannelHandlerContext ctx, MRTPCommand msg) {
        if(msg.operateCode== MRTPCode.LOGIC_RPC_CONNECTION){
            Channel channel = ctx.channel();

            MessageQueueUtil.initCHannel(channel);

            MRTPCommand rsp = new MRTPCommand();
            rsp.operateCode = MRTP.ROOM_SERVER_AVAILABLE_RSP;
            rsp.payload = String.valueOf(true).getBytes();

            log.info("告知所有服务器逻辑中心可用");
            Set<Channel> allChannel = ChannelRouter.getInstance().getAllChannel();

            for (Channel channel1 : allChannel) {
                MRTP.writeAndFlush(channel1,rsp);
            }
            return true;
        }

        return false;
    }
}
