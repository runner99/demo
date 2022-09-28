package com.runner;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class RSAutil {

    public static Map<String,String> keyMap = null;
    public static final String PRIVATE_KEY = "privateKey";
    public static final String PUBLIC_KEY= "publicKey";
    static{
        keyMap = new HashMap<String,String>();
        keyMap.put(PRIVATE_KEY,"MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAJD1mEpqnNEVnSnUCof9kovZOgLpj1zvTIVDNzMKhhY1SNmkULtU7kT9aKGgWhMuOctFn/4I4RE4Q43pyEB5sfuE10aJHo2XDE835kuEhpYhHFoPnR4ugv8Q3/tghJmaludtXCW5417bEfD/j+IHFEZii2y/5+zWB28CzsPs2r93AgMBAAECgYEAhwqUdfb94fZNtFLaALgkVm3mkiH9MWmokpj8imsqYPQAyug9YVcMpW6hk7hrUWUF6C0TkDe7XCxbGpCIwi/csng9yHht462KX2ID49RmbsyHIDm1SYPhXTCzUe/Ob1PQz0rXwqokytRoQ1PSN9x618dZY7OdCt4YEUL30C1jc/kCQQDSwz0nM8w6aZ4ehOVCuac4IYoWkicidy4NoAd04cpisBfKTJBS0AgZWg3X1/cwX+lu+++qk5aIcEL7RlSanEFTAkEAsBKqrlFNAIwDAmFanacqVJH7dtD2E+eM266J05/2N5YoIiHDPeS2RAwTujfVLKBLP1dfV4BhExSgnRZPrN3QzQJBAKZ53PX4Lhr9jg3hPw3Dg1gM9fv6GbtZlCDjBMDIEIp/OWtrqpwfAia0QTb6uZ+WQtaNXbPIvCCtO9sqpG0ze00CQQCpeX4yBW1RrnKjEuPuxV14Q39A0Udax1CMj0Z4nQqYXH7Kw6ay53cMrnc6kc0thCiJkmbhyV8mx38FVNlh8autAkA2A1PQEQXuz459bBWocBbVP1iNtAa6JsFvvlOxkC/w9Z8aHmnCGMg20G3yflE0Re7vsaK2dP1w9KtBvxUlqOcm");
        keyMap.put(PUBLIC_KEY,"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCQ9ZhKapzRFZ0p1AqH/ZKL2ToC6Y9c70yFQzczCoYWNUjZpFC7VO5E/WihoFoTLjnLRZ/+COEROEON6chAebH7hNdGiR6NlwxPN+ZLhIaWIRxaD50eLoL/EN/7YISZmpbnbVwlueNe2xHw/4/iBxRGYotsv+fs1gdvAs7D7Nq/dwIDAQAB");
    }

    public static void main(String[] args) {
        String content = "解开了";
        String en = RSAEncrypt(content);
        System.out.println("加密后："+en);
        String s = RSADecrypt(en);
        System.out.println(s);
        String deResult=RSADecrypt("FXW3XD81ezz9HLuPSUOFbu4mpApwRtlNvUrMnfUSrwNuFDvmm9CA9m8zVUUY1UYNCAyVfkj9a859V0zBwf9iyO8AaribHeP668JuXLf9pkObfhzLwDyS60ne1CMMJtZ/LsU/JpgEEaACZwaVJDPkiaUiuI5EcVcrWG8qwX4Yi/M=");
        System.out.println("解密后："+deResult);
    }


    /**
     * 使用公钥加密
     * @param content
     * @param
     * @return
     */
    public static String RSAEncrypt(String content){
        String publicKey = keyMap.get(PUBLIC_KEY);
        try {
            Cipher cipher=Cipher.getInstance("RSA");
            byte[] pub_bytes= Base64.getDecoder().decode(publicKey);
            X509EncodedKeySpec keySpec=new X509EncodedKeySpec(pub_bytes);
            KeyFactory keyFactory=KeyFactory.getInstance("RSA");
            PublicKey publicKey1=keyFactory.generatePublic(keySpec);
            cipher.init(Cipher.ENCRYPT_MODE,publicKey1);
            byte[] bytes_result = cipher.doFinal(content.getBytes("utf-8"));
            return Base64.getEncoder().encodeToString(bytes_result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String RSADecrypt(String content){

        String privateKey = keyMap.get(PRIVATE_KEY);
        try {
            byte[] pri_bytes=Base64.getDecoder().decode(privateKey);
            PKCS8EncodedKeySpec keySpec=new PKCS8EncodedKeySpec(pri_bytes);
            KeyFactory keyFactory=KeyFactory.getInstance("RSA");
            PrivateKey privateKey1 = keyFactory.generatePrivate(keySpec);
            Cipher cipher=Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE,privateKey1);
            byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(content));
            String result= new String(bytes,"utf-8");

            return  result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
