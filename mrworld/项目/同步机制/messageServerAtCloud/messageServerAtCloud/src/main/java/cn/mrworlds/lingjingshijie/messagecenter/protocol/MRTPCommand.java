package cn.mrworlds.lingjingshijie.messagecenter.protocol;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;
import org.apache.http.HttpHeaders;

import java.nio.ByteOrder;
import java.util.Objects;



public class MRTPCommand implements Comparable<MRTPCommand> {
    public static final int HEADER_LENGTH = 24;//MRTP命令的信息长度,共六个属性，每个属性都是int占4个字节，共长24
    public int size; //该条命令的总长度
    public int roomID;//命令是从那个房间发出的
    public int userID;//发出命令的用户是谁
    public int propagation;//该条命令的传播级别
    public int channelID;//该条命令针对的是哪一个频道
    public int operateCode ;//操作码
    public int id ;// 消息的ID ，仅在房间服务器上生效
    public int lastID ;// 消息的ID ，仅在房间服务器上生效
    public long gtime;//消息生成的时间戳

    public byte[] payload = new byte[0]; //命令的实际内容

    public MRTPCommand(){}

    public MRTPCommand(int code,String data){
        this.operateCode = code;
        this.payload = data.getBytes();
    }

    public static MRTPCommand readMessage(ByteBuf in) {
        MRTPCommand command = new MRTPCommand();
        in = in.order(ByteOrder.LITTLE_ENDIAN);
        command.size = in.readInt();
        command.roomID = in.readInt();
        command.userID = in.readInt();
        command.propagation = in.readInt();
        command.channelID = in.readInt();
        command.operateCode = in.readInt();

        command.payload = new byte[command.size-HEADER_LENGTH];
        in.readBytes(command.payload);

        return command;
    }

    public ByteBuf toBytBuf() {
        ByteBuf bb = PooledByteBufAllocator.DEFAULT.buffer(HEADER_LENGTH + payload.length);
        bb = bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.writeInt(HEADER_LENGTH + payload.length);
        bb.writeInt(roomID);
        bb.writeInt(userID);
        bb.writeInt(propagation);
        bb.writeInt(channelID);
        bb.writeInt(operateCode);
        if (payload != null && payload.length > 0) {
            bb.writeBytes(payload);
        }
        return bb;
    }


    public String toString(){
        JSONObject json = (JSONObject) JSONObject.toJSON(this);

        return json.toJSONString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MRTPCommand that = (MRTPCommand) o;
        return id == that.id &&
                gtime == that.gtime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gtime);
    }

    @Override
    public int compareTo(MRTPCommand o) {

        return this.id>o.id?1:-1;
    }
}
