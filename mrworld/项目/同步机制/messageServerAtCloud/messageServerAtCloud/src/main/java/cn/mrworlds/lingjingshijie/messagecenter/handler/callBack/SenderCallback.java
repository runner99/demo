package cn.mrworlds.lingjingshijie.messagecenter.handler.callBack;

import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTPCommand;

public interface SenderCallback {

    void callback(MRTPCommand command);
}
