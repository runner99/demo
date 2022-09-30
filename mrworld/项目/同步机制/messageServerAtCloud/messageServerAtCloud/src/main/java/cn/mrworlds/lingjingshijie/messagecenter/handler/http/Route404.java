package cn.mrworlds.lingjingshijie.messagecenter.handler.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.CharsetUtil;

public class Route404 extends RouteBase implements IHttpController {
    private final String msg;

    public Route404(String msg) {
        this.msg = msg;
    }

    @Override
    public boolean onHttpRequest(FullHttpRequest request, DefaultFullHttpResponse rsp, ChannelHandlerContext ctx) {
        rsp.setStatus(HttpResponseStatus.NOT_FOUND);
        rsp.headers().set(HttpHeaders.Names.CONTENT_TYPE,
                "text/html");
        ByteBuf responseBuf = Unpooled.copiedBuffer("\n" +
                "<!DOCTYPE html>\n" +
                "<html>\n" +
                "\n" +
                "<head>\n" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <title>UmspHttpServer</title>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "\n" +
                "  code:40x ,resources not be found '" + msg + "'<img src=\"/images/404.png\"  style=\"position: absolute; left: 50%; top: 50%; margin-left: -285px; margin-top: -190px;\">\n" +
                "\n" +
                "</body>\n" +
                "\n" +
                "</html>", CharsetUtil.UTF_8);

        rsp.content().writeBytes(responseBuf);
        handKeepLive(request,rsp);
        ctx.writeAndFlush(rsp);
        responseBuf.release();
        return true;
    }
}
