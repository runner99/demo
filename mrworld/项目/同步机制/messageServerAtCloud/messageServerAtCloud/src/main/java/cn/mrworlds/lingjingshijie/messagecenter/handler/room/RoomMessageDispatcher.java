package cn.mrworlds.lingjingshijie.messagecenter.handler.room;

import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTPCommand;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Slf4j
public class RoomMessageDispatcher extends SimpleChannelInboundHandler<MRTPCommand> {

    private HashMap<Integer,RoomHandler> handlerMap ;

    public RoomMessageDispatcher(){
        handlerMap = new HashMap<>();
        this.addOneHandler(new EnterRoomHandler());
        this.addOneHandler(new IOMessageHandler());
        this.addOneHandler(new QuitRoomHandler());
    }
    public void addOneHandler(RoomHandler handler){
        int cmdCode = handler.getCmdCode();
        if(!handlerMap.containsKey(cmdCode)){
            handlerMap.put(cmdCode,handler);
        }
    }

    @Override
    //    protected void channelRead0(ChannelHandlerContext ctx, MRTPCommand mrtpCommand) throws Exception {
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MRTPCommand mrtpCommand) throws Exception {
       log.info("receive info client at room:["+ mrtpCommand.operateCode+"]"+mrtpCommand.roomID+"["+ new String(mrtpCommand.payload)+"]") ;

        int cmd = mrtpCommand.operateCode;

        RoomHandler roomHandler = handlerMap.get(cmd);

        if(roomHandler!=null){
            roomHandler.handle(channelHandlerContext,mrtpCommand);
        }
    }
}
