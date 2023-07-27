package com.zlpay.javabridge;

import com.zlpay.assist.encrypt.sm4.SM4Util;
import com.zlpay.assist.sign.sm2.SM2Cert;
import com.zlpay.assist.sign.sm2.SM2Util;
import org.bouncycastle.util.encoders.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.cert.X509Certificate;

public class Helper {

    public static String sign(String sm2, String password, String signNo, String data) {
        try {
            // 获取私钥
            String privateKey = getPrivateKey(sm2, password);
            return SM2Util.sign(privateKey, signNo, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean verify(String cer, String encrpNo, String sign, String data) {
        try {
            String publicKey = getPublicKey(cer);
            boolean checkResult = SM2Util.verify(publicKey, encrpNo, sign, data);
            System.out.println("验签结果：" + checkResult);
            return checkResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String encrypt(String key, String body) {
        try {
            String jsonData = SM4Util.sm4EcbEncrypt(key, body, "NoPadding");
            System.out.println("加密数据：" + jsonData);
            return jsonData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String decrypt(String sm2, String password, String secret, String data) {
        try {
            String privateKey = getPrivateKey(sm2, password);
            String k = SM2Util.decrypt(privateKey, secret);
            System.out.println("对称秘钥：" + k);
            String backData = SM4Util.sm4EcbDecrypt(k, data);
            System.out.println("返回业务报文：" + backData);
            return backData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    public static String getPublicKey(String cer) {
        String publicKey = "";
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(cer));
            X509Certificate publicCert = SM2Cert.getPublicCert(inputStream);
            publicKey = Base64.toBase64String(publicCert.getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return publicKey;
    }

    public static String getPrivateKey(String sm2, String password) {
        String privateKey = "";
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(sm2));
            byte[] priBytes = SM2Cert.getPrivateCert(inputStream);
            privateKey = SM2Cert.getPrivateKey(password, priBytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return privateKey;
    }


}