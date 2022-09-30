package cn.mrworlds.lingjingshijie.messagecenter.handler.http;

import cn.mrworlds.lingjingshijie.messagecenter.handler.HttpRequestHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;

@Slf4j
public class RouteStaticResource extends RouteBase implements IHttpController {


    private static String DIR_RES = "res/";
    private static String Path_RES = "";
    private final String msg;

    public RouteStaticResource(String msg) {
        this.msg = msg;
    }

    static {
        URL location = HttpRequestHandler.class.getProtectionDomain()
                .getCodeSource().getLocation();
        try {
            String resPath = location.toURI().toString();
            if (resPath.toString().endsWith(".jar")) {
                resPath = resPath.substring(0, resPath.lastIndexOf("/") + 1);

            }
            Path_RES = resPath + DIR_RES;

            Path_RES = !Path_RES.contains("file:") ? Path_RES : Path_RES.substring(5);

        } catch (URISyntaxException e) {
            throw new IllegalStateException(
                    "Unable to locate WebsocketChatClient.html", e);
        }

    }

    @Override
    public boolean onHttpRequest(FullHttpRequest request, DefaultFullHttpResponse rsp, ChannelHandlerContext ctx) throws Exception {

        String uri = request.getUri();
        String sniff = uri.lastIndexOf(".") > 0 ? uri.substring(uri.lastIndexOf(".") + 1) : uri;

        String fileType = "application/*";
        if ("png".equals(sniff) || "jpg".equals(sniff) ||
                "jpeg".equals(sniff) || "gif".equals(sniff) ||
                "webp".equals(sniff)) {
            fileType = "image/" + sniff;
        } else if ("txt".equals(sniff) || "xml".equals(sniff) ||
                "json".equals(sniff) || "html".equals(sniff) ||
                "js".equals(sniff) || "css".equals(sniff)) {
            fileType = "text/" + sniff;
        }

        RandomAccessFile file = new RandomAccessFile(new File(Path_RES + uri), "r");// 4
        HttpResponse response = new DefaultHttpResponse(
                request.getProtocolVersion(), HttpResponseStatus.OK);
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE,
                fileType);

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
        return false;
    }
}
