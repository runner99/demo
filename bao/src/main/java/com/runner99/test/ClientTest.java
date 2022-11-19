package com.runner99.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientTest {
    private Socket client;

    public void getClient(String message) throws IOException {

        //建立服务的连接并进行输出
        client = new Socket("127.0.0.1", 1100);
        OutputStream pt = client.getOutputStream();
        pt.write(message.getBytes());

        //接收服务端的信息
        InputStream input = client.getInputStream();
        byte [] b = new byte[1024];
        int len = input.read(b);
        String data = new String(b, 0,len);
        System.out.println("结果为：" + data);
        client.close();
    }

    public static void main(String[] args) throws IOException {
        ClientTest testClient = new ClientTest();
        //发送的消息
        String message = "2+1";
//        String message = "3-1";
        testClient.getClient(message);
    }
}
