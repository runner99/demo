package com.runner99;

import com.fasterxml.uuid.Generators;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Enumeration;
import java.util.UUID;

/**
 * @author weizhenqiang
 * @date 2023/4/14 10:07
 */
public class IdUtils {
//    private static final long SEQUENCE_MASK = 0x3fffL;
//    private static final long TIMESTAMP_OFFSET = 122192928000000000L;
//
//    private final long node;
//    private int clockSequence;
//    private long lastTimestamp;
//
//    public IdUtils() throws SocketException {
//        // 获取 MAC 地址作为节点
//        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
//        byte[] mac = null;
//
//        while (networkInterfaces.hasMoreElements()) {
//            NetworkInterface networkInterface = networkInterfaces.nextElement();
//
//            if (!networkInterface.isLoopback() && networkInterface.getHardwareAddress() != null) {
//                mac = networkInterface.getHardwareAddress();
//                break;
//            }
//        }
//
//        if (mac == null) {
//            throw new RuntimeException("Cannot get MAC address.");
//        }
//
//        // 将 MAC 地址转换为节点
//        ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
//        buffer.putLong(8, macAsLong(mac));
//        buffer.position(0);
//        node = buffer.getLong() & 0x7fffffffffffL;
//
//        lastTimestamp = 0L;
//        clockSequence = (int) (java.lang.Math.random() * 0x4000) & 0x3fff;
//    }
//    private long macAsLong(byte[] mac) {
//        long result = 0;
//        for (int i = 0; i < mac.length; i++) {
//            result <<= 8;
//            result |= (mac[i] & 0xff);
//        }
//        result &= 0xffffffffffffL;
//        result |= 0x0000010000000000L;
//        return result;
//    }
    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
//        for (int i=0;i<10;i++){
            UUID generate = Generators.timeBasedGenerator().generate();
//        }
        long end = System.currentTimeMillis();
        System.out.println(end-begin);
    }

}
