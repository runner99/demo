package cn.mrworlds.lingjingshijie.messagecenter.handler.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.Map;

public class RouteScript implements IHttpController {
    @Override
    public boolean onHttpRequest(FullHttpRequest request, DefaultFullHttpResponse rsp, ChannelHandlerContext ctx) {
        try {
           // Map<String, String> map = Http.query2map(request.getUri());
//            ScriptEngine.loadScriptFileByUrl(map.get("url"));
            return RouteBase.returnString(request, rsp, ctx, "load script success " );
        } catch (Exception e) {
            return RouteBase.return500(request, rsp, ctx, e);
        }
    }


}
