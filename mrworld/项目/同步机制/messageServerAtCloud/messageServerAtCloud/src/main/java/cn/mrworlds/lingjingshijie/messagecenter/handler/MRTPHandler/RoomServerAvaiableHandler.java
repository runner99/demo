package cn.mrworlds.lingjingshijie.messagecenter.handler.MRTPHandler;

import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTP;
import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTPCode;
import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTPCommand;
import cn.mrworlds.lingjingshijie.messagecenter.util.MessageQueueUtil;
import io.netty.channel.ChannelHandlerContext;

public class RoomServerAvaiableHandler implements IMRTPHandler{

    @Override
    public boolean handle(ChannelHandlerContext ctx, MRTPCommand msg) {

        if(msg.operateCode == MRTPCode.ROOM_SERVER_AVAILABLE){

            boolean active = MessageQueueUtil.channel.isActive();

            MRTPCommand rsp = new MRTPCommand();
            rsp.operateCode = MRTP.ROOM_SERVER_AVAILABLE_RSP;
            rsp.payload = String.valueOf(active).getBytes();

            MRTP.writeAndFlush(ctx.channel(),rsp);

            return true;
        }

        return false;
    }
}






