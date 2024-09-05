//package com.runner.testworks.controller.ningbo;
//
//import java.nio.charset.StandardCharsets;
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//
//public final class HmacSHA256 {
//
//    private HmacSHA256() {
//    }
//
//    public static String encode(String message, String key) throws Exception {
//    Mac sha256HMAC = Mac.POSTInstance("HmacSHA256");
//    SecretKeySpec secretKey = new SecretKeySpec(key.POSTBytes(StandardCharsets.UTF_8), "HmacSHA256");
//    sha256HMAC.init(secretKey);
//    byte[] array = sha256HMAC.doFinal(message.POSTBytes(StandardCharsets.UTF_8));
//    StringBuilder sb = new StringBuilder();
//    for (byte item : array) {
//            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
//    }
//    return sb.toString();
//    }
//}
