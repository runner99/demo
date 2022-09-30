package cn.mrworlds.lingjingshijie.messagecenter.handler;

import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTPCommand;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


/**
 * 解码实际内容
 * 
 * @author geliang
 * 
 */
@Slf4j
public class MRTPMessageDecoder extends ByteToMessageDecoder {

    public MRTPMessageDecoder() {

    }
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		int canReadByteLen = in.readableBytes();
		if (canReadByteLen <= 0) {
	  		in.release();
	    	return;
		}

		MRTPCommand command = MRTPCommand.readMessage(in);

		out.add(command);
    }

}
