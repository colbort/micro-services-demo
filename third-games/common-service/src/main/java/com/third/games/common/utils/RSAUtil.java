package com.third.games.common.utils;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.security.*;
import java.util.Base64;

@Component
public class RSAUtil {
    private final String ALGORITHM = "RSA";

    /**
     * 生成密钥对
     */
    public KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator gen = KeyPairGenerator.getInstance(ALGORITHM);
        gen.initialize(2048); // 建议 2048
        return gen.generateKeyPair();
    }

    /**
     * 公钥加密
     */
    public String encrypt(String plainText, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encrypted = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    /**
     * 私钥解密
     */
    public String decrypt(String base64Encrypted, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(base64Encrypted));
        return new String(decrypted);
    }

    /**
     * 签名（私钥）
     */
    public String sign(String data, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(data.getBytes());
        return Base64.getEncoder().encodeToString(signature.sign());
    }

    /**
     * 验签（公钥）
     */
    public boolean verify(String data, String base64Signature, PublicKey publicKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(data.getBytes());
        return signature.verify(Base64.getDecoder().decode(base64Signature));
    }
}

