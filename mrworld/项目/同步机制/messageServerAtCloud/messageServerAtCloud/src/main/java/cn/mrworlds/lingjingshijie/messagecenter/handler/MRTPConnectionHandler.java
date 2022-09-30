package cn.mrworlds.lingjingshijie.messagecenter.handler;

import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTP;
import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTPCommand;
import cn.mrworlds.lingjingshijie.messagecenter.router.ChannelRouter;
import cn.mrworlds.lingjingshijie.messagecenter.router.RoomUserChannelRouter;
import cn.mrworlds.lingjingshijie.messagecenter.util.MessageQueueUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
public class MRTPConnectionHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

        log.info("channelActive:" + ctx.channel().remoteAddress());
    }

    //客户端掉线了
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        log.info("channelInActive:" + ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        if(cause.getMessage().equals("远程主机强迫关闭了一个现有的连接。")){
            log.info("channel被远端断开");
            handlerDisconnect(ctx);
            ctx.close();
        }

        log.info(cause.getMessage());
        cause.printStackTrace();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx){

        log.info("handlerRemoved:" + ctx.channel().remoteAddress());
    }


    public static void handlerDisconnect(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        //如果是roomServer过来的连接，将roomServer 不可用消息群发，使得client知晓房间入口已经关闭了
        if(MessageQueueUtil.channel==channel){
            log.info("通知用户逻辑中心挂掉了");
            MRTPCommand mrtpCommand = new MRTPCommand();
            mrtpCommand.operateCode = MRTP.ROOM_SERVER_UNAVAILABLE;
            Set<Channel> allChannel = ChannelRouter.getInstance().getAllChannel();

            for (Channel channel1 : allChannel) {
                MRTP.writeAndFlush(channel1,mrtpCommand);
            }

            MessageQueueUtil.channel = null;
        }

        //如果不是，则认为是用户下线，按照下线情况进行处理处理，设置room里的用户状态，告知用户下线操作
        else {
            RoomUserChannelRouter.getInstance().offLineOneUser(channel);
        }
    }

}
