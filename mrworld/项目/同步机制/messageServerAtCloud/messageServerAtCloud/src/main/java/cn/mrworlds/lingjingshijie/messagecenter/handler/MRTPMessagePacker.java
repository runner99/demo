package cn.mrworlds.lingjingshijie.messagecenter.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldPrepender;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteOrder;
import java.util.List;

@Slf4j
public class MRTPMessagePacker extends LengthFieldPrepender {

    //(ByteOrder byteOrder, int lengthFieldLength, int lengthAdjustment, boolean lengthIncludesLengthFieldLength)
    public MRTPMessagePacker() {
        super(ByteOrder.LITTLE_ENDIAN, 4, 0, true);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out)
            throws Exception {

        super.encode(ctx, msg, out);
    }

}
