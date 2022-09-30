package cn.mrworlds.lingjingshijie.messagecenter.handler.http;

import cn.mrworlds.lingjingshijie.messagecenter.handler.HttpRequestHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;

@Slf4j
public class RouteIndex implements IHttpController {


    private static File INDEX;
    private static String DIR_RES = "res/";

    static {
        URL location = HttpRequestHandler.class.getProtectionDomain()
                .getCodeSource().getLocation();
        try {
            String resPath = location.toURI().toString();
            if (resPath.toString().endsWith(".jar")) {
                log.info("resDir:" + resPath);
                resPath = resPath.substring(0, resPath.lastIndexOf("/") + 1);
                log.info("=== java -jar  ===");
                log.info("resDir:" + resPath);
            }
            String path = resPath + DIR_RES + "websocket.html";
            log.info("web file path:" + path);

            path = !path.contains("file:") ? path : path.substring(5);
            INDEX = new File(path);
        } catch (URISyntaxException e) {
            throw new IllegalStateException(
                    "Unable to locate WebsocketChatClient.html", e);
        }

    }

    private static void send100Continue(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
        ctx.writeAndFlush(response);

    }

    @Override
    public boolean onHttpRequest(FullHttpRequest request, DefaultFullHttpResponse rsp, ChannelHandlerContext ctx) {

        ByteBuf buf = request.content();

        String data = buf.toString(CharsetUtil.UTF_8);

        System.out.println("data is :"+data);

        if (HttpHeaders.is100ContinueExpected(request)) {
            send100Continue(ctx); // 3
        }

        try {
            RandomAccessFile file = new RandomAccessFile(INDEX, "r");// 4

            HttpResponse response = new DefaultHttpResponse(
                    request.getProtocolVersion(), HttpResponseStatus.OK);
            response.headers().set(HttpHeaders.Names.CONTENT_TYPE,
                    "text/html; charset=UTF-8");

            boolean keepAlive = HttpHeaders.isKeepAlive(request);

            if (keepAlive) { // 5
                response.headers().set(HttpHeaders.Names.CONTENT_LENGTH,
                        file.length());
                response.headers().set(HttpHeaders.Names.CONNECTION,
                        HttpHeaders.Values.KEEP_ALIVE);
            }
            ctx.write(response); // 6

            if (ctx.pipeline().get(SslHandler.class) == null) { // 7
                ctx.write(new DefaultFileRegion(file.getChannel(), 0, file
                        .length()));
            } else {
                ctx.write(new ChunkedNioFile(file.getChannel()));
            }
            ChannelFuture future = ctx
                    .writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT); // 8
            if (!keepAlive) {
                future.addListener(ChannelFutureListener.CLOSE); // 9
            }

            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
