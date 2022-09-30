package cn.mrworlds.lingjingshijie.messagecenter.handler.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

public class WebsocketHandler extends SimpleChannelInboundHandler<WebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, WebSocketFrame msg) throws Exception {
        if(msg instanceof TextWebSocketFrame){

            channelHandlerContext.fireChannelRead(msg);

            return;
        }

        if(msg instanceof PingWebSocketFrame){

        }
    }
}
