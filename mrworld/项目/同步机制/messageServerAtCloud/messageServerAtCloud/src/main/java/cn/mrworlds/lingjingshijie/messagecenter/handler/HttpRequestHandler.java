package cn.mrworlds.lingjingshijie.messagecenter.handler;

import cn.mrworlds.lingjingshijie.messagecenter.handler.http.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.io.FileNotFoundException;
import java.net.URI;
import java.util.Map;
import java.util.TreeMap;

public class HttpRequestHandler extends  SimpleChannelInboundHandler<FullHttpRequest> {

    private static Map<String, IHttpController> routeMap = new TreeMap<>();

    static {
        HttpRequestHandler.addRoute("/ws", new RouteWebSocket());
        HttpRequestHandler.addRoute("/", new RouteIndex());
    }



    public static void addRoute(String uri, IHttpController controller) {
        routeMap.put(uri, controller);
    }

    public HttpRequestHandler() {

    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) {
        System.out.println("11111");
        URI uri = URI.create(request.getUri());
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(
                request.getProtocolVersion(), HttpResponseStatus.OK);
        try {
            response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/html; charset=UTF-8");

            boolean isMatch = false;
            if ((uri.getPath().equals("/") || uri.getPath().equals("/index"))) {
                if (HttpRequestHandler.routeMap.get("/").onHttpRequest(request, response, ctx)) {
                    isMatch = true;
                }
            }

            else {
                for (String s : HttpRequestHandler.routeMap.keySet()) {
                    //精确匹配路径路由器
                    if (uri.getPath().equals(s)) {
                        if (HttpRequestHandler.routeMap.get(s).onHttpRequest(request, response, ctx)) {
                            isMatch = true;
                            System.out.println("匹配成功");
                            break;
                        }
                    }
                }
            }

            if (!isMatch) {
                //try load the static res
                try {
                    new RouteStaticResource(request.getUri()).onHttpRequest(request, response, ctx);
                    isMatch = true;
                } catch (FileNotFoundException e) {
                } catch (Exception e) {
                    throw e;
                }
            }
            if (!isMatch) {

                RouteBase.return404(request, response, ctx);
            }
        } catch (Exception e) {

            RouteBase.return500(request, response, ctx, e);
        }

    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        System.out.println("Client:" + ctx.channel() + "异常");
        ctx.close();
    }

    public static void main(String[] args) {
        String url = "/public/manuals/appliances?id=1&name=jankin#ge";
        URI uri = URI.create(url);
    }
}