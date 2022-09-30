package cn.mrworlds.lingjingshijie.messagecenter.handler.room;

import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTPCommand;
import io.netty.channel.ChannelHandlerContext;

public abstract class RoomHandler {
    private int cmdCode;

    abstract void handle(ChannelHandlerContext ctx, MRTPCommand msg) ;

    abstract void initCmd() ;

    public int getCmdCode(){
        return cmdCode;
    }

    public RoomHandler(){
        this.initCmd();
    }

    public  void setCmdCode(int code) {
        this.cmdCode = code;
    }
}
