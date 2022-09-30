package cn.mrworlds.lingjingshijie.messagecenter.handler;

import cn.mrworlds.lingjingshijie.messagecenter.handler.MRTPHandler.*;
import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTPCode;
import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTPCommand;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

@Slf4j
public class MRTPCommandDispatcher extends SimpleChannelInboundHandler<MRTPCommand> {
    ArrayList<IMRTPHandler> handleList = new ArrayList<IMRTPHandler>();

    private static MRTPCommandDispatcher instance = new MRTPCommandDispatcher();
    public static MRTPCommandDispatcher getInstance(){
        return instance;
    };

    public MRTPCommandDispatcher(){
        handleList.add(new UserLoginhandler());
        handleList.add(new CreateRoomHandler());
        handleList.add(new RoomOwnerChangedHandler());
        handleList.add(new RoomStateChangedHandler());
        handleList.add(new MessageQueueHandler());
        handleList.add(new RoomServerAvaiableHandler());
        handleList.add(new DestoryRoomHandler());
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, MRTPCommand msg){
        if (msg.operateCode!= MRTPCode.HEART_BEAT){
            System.out.println("dispatcher msg:"+ msg);
        }
        for (IMRTPHandler iMRTPHandler : handleList) {
            if(iMRTPHandler.handle(ctx,msg)){
                return;
            }
        }
        log.warn("No handler to handle the msg:"+msg.toString());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }
}
