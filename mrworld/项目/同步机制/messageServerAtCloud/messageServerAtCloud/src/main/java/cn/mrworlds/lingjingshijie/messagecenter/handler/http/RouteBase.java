package cn.mrworlds.lingjingshijie.messagecenter.handler.http;

import cn.mrworlds.lingjingshijie.messagecenter.util.TextUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.CharsetUtil;

import java.util.HashMap;

public class RouteBase {
    public static boolean returnString(FullHttpRequest request, DefaultFullHttpResponse rsp, ChannelHandlerContext ctx, String msg) {
        rsp.setStatus(HttpResponseStatus.OK);

        rsp.headers().set(HttpHeaders.Names.CONTENT_TYPE,
                "text/html");

        ByteBuf responseBuf = Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8);
        rsp.content().writeBytes(responseBuf);
        handKeepLive(request, rsp);
        ctx.writeAndFlush(rsp);
        responseBuf.release();
        return true;
    }

    public static void handKeepLive(FullHttpRequest request, DefaultFullHttpResponse rsp) {
        boolean keepAlive = HttpHeaders.isKeepAlive(request);
        if (keepAlive) { // 5
            rsp.headers().set(HttpHeaders.Names.CONNECTION,
                    HttpHeaders.Values.KEEP_ALIVE);
        }
        rsp.headers().set(HttpHeaders.Names.CONTENT_LENGTH,
                rsp.content().readableBytes());
    }

    public static boolean return500(FullHttpRequest request, DefaultFullHttpResponse rsp, ChannelHandlerContext ctx, Exception err) {
        new Route500(request.getUri() + " has inner error , case by :" + err).
                onHttpRequest(request, rsp, ctx);
        return true;
    }

    public static boolean return404(FullHttpRequest request, DefaultFullHttpResponse rsp, ChannelHandlerContext ctx) {
        new Route404(request.getUri()).onHttpRequest(request, rsp, ctx);
        return true;
    }

    public static java.util.Map<String, String> paramToMap(String paramStr) {
        java.util.Map<String, String> resMap = new HashMap();
        if (TextUtil.isNotEmpty(paramStr)) {
            String[] params = paramStr.split("&");
            for (int i = 0; i < params.length; i++) {
                String[] param = params[i].split("=");
                if (param.length >= 2) {
                    String key = param[0];
                    String value = param[1];
                    for (int j = 2; j < param.length; j++) {
                        value += "=" + param[j];
                    }
                    resMap.put(key, value);
                }
            }
        }
        return resMap;
    }
}
