package cn.mrworlds.lingjingshijie.messagecenter.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteOrder;

/**
 * 根据协议信息去掉数据包的包头 *
 *
 * <pre>
 * lengthFieldOffset   = 0
 * lengthFieldLength   = 2
 * lengthAdjustment    = 0
 * <b>initialBytesToStrip</b> = <b>2</b> (= the length of the Length field)
 * @author geliang
 *
 */
public class MRTPMessageUnpacker extends LengthFieldBasedFrameDecoder {
    public MRTPMessageUnpacker() {
        // set the length of frame header and length field position in header
        super(ByteOrder.LITTLE_ENDIAN, 10*1024*1024, 0, 4, -4, 0, true);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in)
            throws Exception {
        if (in.readableBytes() < 4) {
            return null;
        }
        return super.decode(ctx, in);
    }

    @Override
    protected ByteBuf extractFrame(ChannelHandlerContext ctx, ByteBuf buffer,
                                   int index, int length) {
        return super.extractFrame(ctx, buffer, index, length);
    }

}
