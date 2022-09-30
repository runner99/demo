package cn.mrworlds.lingjingshijie.messagecenter.server;

import cn.mrworlds.lingjingshijie.messagecenter.handler.MRTPCommandDecoder;
import cn.mrworlds.lingjingshijie.messagecenter.handler.MRTPConnectionHandler;
import cn.mrworlds.lingjingshijie.messagecenter.handler.room.RoomMessageDispatcher;
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
public class RoomServer {
    private int port;

    private int state = 0;
    public static final Integer RUNNING = 1;
    public static final Integer CLOSED = 2;
    public RoomServer(int port){
        this.port = port;
    }

    public int getPort(){
        return port;
    }

    private Channel channel;

    NioEventLoopGroup bossGroup = new NioEventLoopGroup();
    NioEventLoopGroup workGroup = new NioEventLoopGroup(24);

    public void start() {

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();

                            pipeline.addLast(new MRTPConnectionHandler())
                                    .addLast(new HttpServerCodec())
                                    .addLast(new HttpObjectAggregator(1024 * 64))
                                    .addLast(new WebSocketServerCompressionHandler())
                                    .addLast(new WebSocketServerProtocolHandler("/ws", null, true))
                                    .addLast(new WebsocketHandler())
                                    .addLast(new MRTPCommandDecoder())
                                    .addLast(new RoomMessageDispatcher());

//                              .addLast(new HttpServerCodec())
//                                    .addLast(new HttpObjectAggregator(1024*64))
//                                    .addLast(new WebSocketServerCompressionHandler())
//                                    .addLast(new WebSocketServerProtocolHandler("/ws",null,true))
//                                    .addLast(new WebsocketHandler())
//                                    .addLast(new MRTPCommandDecoder())
//                                    .addLast(new MessageDispatcher());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 256)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = serverBootstrap.bind(port).sync();

            log.info("房间服务器启动成功，检测端口："+ port);
            state = RUNNING;
            channel =  future.channel();
            channel.closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
            state = 2;
            System.out.println("关闭成功1");
        }
    }

    public void close(){
        if(channel!=null){
            try{
                channel.closeFuture();
            }catch (Exception e){

            }finally {
                bossGroup.shutdownGracefully();
                workGroup.shutdownGracefully();
            }
            log.info("房间服务器关闭成功，检测端口："+ port);
        }
    }

    public boolean hasStarted(){
        return state!=0;
    }
}
