package cn.mrworlds.lingjingshijie.messagecenter.router;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ChannelRouter {
    private Map<Channel, Integer> mChannelMap;  // channel - userID 的 映射关系
    private Map<Integer, Channel> mUserIDMap;   // userID -channel 的映射关系

    private ChannelRouter() {
        mChannelMap = new ConcurrentHashMap();
        mUserIDMap = new ConcurrentHashMap();
    }

    public void registerRouter(int id, Channel channel) {
        mChannelMap.put(channel, id);
        mUserIDMap.put(id, channel);
    }


    private static class SingleHolder {
        final static ChannelRouter holder = new ChannelRouter();
    }

    public static ChannelRouter getInstance() {
        return SingleHolder.holder;
    }

    public void unRegisterRouter(Channel ctx) {
        if (ctx == null) {
            return  ;
        }
        if (mChannelMap.containsKey(ctx)) {
            Integer key = mChannelMap.remove(ctx);
            if (mUserIDMap.containsKey(key)) {
                mUserIDMap.remove(key);

                return ;
            }
        }
    }

    public Channel getChannel(int id) {
        return mUserIDMap.get(id);
    }

    public boolean containsKey(int id) {
        return mUserIDMap.containsKey(id);
    }

    public Integer getID(Channel ctx) {
        final Integer integer = mChannelMap.get(ctx);
        return integer == null ? 0 : integer;
    }

    public Set<Channel> getAllChannel() {
        return mChannelMap.keySet();
    }
}