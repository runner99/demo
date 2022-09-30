package cn.mrworlds.lingjingshijie.messagecenter.handler.MRTPHandler;

import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTP;
import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTPCode;
import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTPCommand;
import cn.mrworlds.lingjingshijie.messagecenter.room.moudel.Room;
import cn.mrworlds.lingjingshijie.messagecenter.router.RoomServerRouter;
import cn.mrworlds.lingjingshijie.messagecenter.router.RoomUserChannelRouter;
import io.netty.channel.ChannelHandlerContext;

/**
 * 处理房间状态变化的handler
 */
public class RoomStateChangedHandler   implements IMRTPHandler {

    @Override
    public boolean handle(ChannelHandlerContext ctx, MRTPCommand msg) {
        if(msg.operateCode == MRTPCode.ROOM_STATE_CHANGE){

            int roomID = msg.roomID;
            String stateStr =  new String(msg.payload);

            Integer roomState = Integer.parseInt(stateStr);

            Room room = RoomServerRouter.getInstance().RoomInfo(roomID);

            room.setState(roomState);

            RoomServerRouter.getInstance().changeRoomInfo(room);

            msg.propagation = MRTP.MSG_PROPAGATION_ROOM;

            msg.operateCode = MRTP.ROOM_STATE_CHANGED;

            RoomUserChannelRouter.getInstance().publishMsg(roomID,msg);
            return true;
        }
        return false;
    }
}
