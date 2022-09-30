package cn.mrworlds.lingjingshijie.messagecenter.handler.MRTPHandler;

import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTP;
import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTPCode;
import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTPCommand;
import cn.mrworlds.lingjingshijie.messagecenter.room.moudel.Room;
import cn.mrworlds.lingjingshijie.messagecenter.router.RoomServerRouter;
import cn.mrworlds.lingjingshijie.messagecenter.router.RoomUserChannelRouter;
import io.netty.channel.ChannelHandlerContext;

public class RoomOwnerChangedHandler implements IMRTPHandler {
    @Override
    public boolean handle(ChannelHandlerContext ctx, MRTPCommand msg) {
        if(msg.operateCode == MRTPCode.ROOM_OWNER_CHANGE){

            int roomID = msg.roomID;
            String ownerIDStr =  new String(msg.payload);

            Room room = RoomServerRouter.getInstance().RoomInfo(roomID);

            room.setOwner(ownerIDStr);

            RoomServerRouter.getInstance().changeRoomInfo(room);

            msg.propagation = MRTP.MSG_PROPAGATION_ROOM;

            msg.operateCode = MRTP.ROOM_OWNER_CHANGED;

            RoomUserChannelRouter.getInstance().publishMsg(roomID,msg);
            return true;
        }
        return false;
    }
}
