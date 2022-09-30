package cn.mrworlds.lingjingshijie.messagecenter.handler.MRTPHandler;

import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTPCommand;
import io.netty.channel.ChannelHandlerContext;

public interface IMRTPHandler {
    boolean handle(ChannelHandlerContext ctx, MRTPCommand msg) ;
}