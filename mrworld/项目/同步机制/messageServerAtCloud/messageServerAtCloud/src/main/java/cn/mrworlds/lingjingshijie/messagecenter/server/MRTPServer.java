package cn.mrworlds.lingjingshijie.messagecenter.server;

import cn.mrworlds.lingjingshijie.messagecenter.handler.*;
import cn.mrworlds.lingjingshijie.messagecenter.handler.websocket.WebsocketHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MRTPServer {

    private int port;

    public MRTPServer(int port){
        this.port = port;
    }

    public void start() {
        //启动两个线程池，分别用于处理连接事件和读写事件
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup(24);

        try {
            //创建服务器脚手架，封装了服务器的启动流程
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            // 指定boosGroup 负责处理连接事件，workGroup处理读写事件
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class) // 服务端使用NioServerSocketChannel
                    .childHandler(new ChannelInitializer() {  // 接收到读写请求后，需要对数据进行如何处理
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();

                            pipeline.addLast(new MRTPConnectionHandler())  // 先进行连接处理
                                    //TODO 加入心跳机制
                                    .addLast(new HttpServerCodec())       // 把数据按照Http协议解码并拿出数据  a
                                    .addLast(new HttpObjectAggregator(1024*64)) // 将数据a 聚合得到数据b
                                    .addLast(new WebSocketServerCompressionHandler())              // 使用 websocket 协议 将 b 数据解码得到 c
                                    .addLast(new WebSocketServerProtocolHandler("/ws",null,true))
                                    .addLast(new WebsocketHandler())
                                    .addLast(new MRTPCommandDecoder())                             // 把c转为MRTPCommand 实例
                                    .addLast(new MessageDispatcher());                             // 根据MRTPCommand来判断走哪些业务操作

                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 256)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = serverBootstrap.bind(port).sync();

            log.info("启动成功");
            future.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
