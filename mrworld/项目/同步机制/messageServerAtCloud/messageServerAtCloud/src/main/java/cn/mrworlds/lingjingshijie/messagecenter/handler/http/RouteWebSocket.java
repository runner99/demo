package cn.mrworlds.lingjingshijie.messagecenter.handler.http;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RouteWebSocket implements IHttpController {


    @Override
    public boolean onHttpRequest(FullHttpRequest request, DefaultFullHttpResponse rsp, ChannelHandlerContext ctx) {
        ByteBuf buf = request.content();

        ctx.channel().writeAndFlush("11");
        return false;
    }
}
