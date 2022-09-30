package cn.mrworlds.lingjingshijie.messagecenter.handler.room;

import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTP;
import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTPCode;
import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTPCommand;
import cn.mrworlds.lingjingshijie.messagecenter.router.RoomUserChannelRouter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class IOMessageHandler extends RoomHandler {

    @Override
    void handle(ChannelHandlerContext ctx, MRTPCommand msg) {

        msg.operateCode = MRTP.FORWARD_IO_MESSAGE_RSP;

        RoomUserChannelRouter.getInstance().publishMsg(msg.roomID,msg);
    }

    @Override
    void initCmd() {
        this.setCmdCode(MRTPCode.FORWARD_IO_MESSAGE);
    }
}
