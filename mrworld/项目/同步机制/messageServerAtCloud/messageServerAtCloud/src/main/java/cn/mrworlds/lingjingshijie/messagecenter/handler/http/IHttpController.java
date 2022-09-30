package cn.mrworlds.lingjingshijie.messagecenter.handler.http;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;

import java.io.FileNotFoundException;

public interface IHttpController {
    /**
     *
     * @param request
     * @param rsp
     * @param ctx
     * @return 是否需要flush
     */
    boolean onHttpRequest(FullHttpRequest request, DefaultFullHttpResponse rsp, ChannelHandlerContext ctx) throws FileNotFoundException, Exception;


}
