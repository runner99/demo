package cn.mrworlds.lingjingshijie.messagecenter.handler;

import cn.mrworlds.lingjingshijie.messagecenter.handler.MRTPHandler.*;
import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTPCommand;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.LinkedList;
import java.util.List;

public class MessageDispatcher extends SimpleChannelInboundHandler<MRTPCommand> {

    public List<IMRTPHandler> handlerList ;

    public MessageDispatcher(){
        handlerList = new LinkedList<>();
        handlerList.add(new UserLoginhandler()); // 处理用户登录事件
        handlerList.add(new CreateRoomHandler());// 处理房间创建事件

        handlerList.add(new RoomOwnerChangedHandler()); // 处理切换房主事件
        handlerList.add(new RoomStateChangedHandler()); // 处理开启同步事件和光比同步时间
        handlerList.add(new MessageQueueHandler());     // 处理房间模块连接信息
        handlerList.add(new RoomServerAvaiableHandler()); // 处理查询服务器是否可用
        handlerList.add(new DestoryRoomHandler());      // 处理房间销毁请求
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MRTPCommand mrtpCommand) throws Exception {
        for (IMRTPHandler handler : handlerList) {
            if(handler.handle(ctx,mrtpCommand)){
                break;
            }
        }
    }
}