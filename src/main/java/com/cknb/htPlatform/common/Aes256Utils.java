package com.cknb.htPlatform.common;


import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Aes256Utils {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5PADDING";
    static final Base64.Decoder DECODER = Base64.getDecoder();
    static final Base64.Encoder ENCODER = Base64.getEncoder();
    private static final byte[] IV = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private final String encodedIv;

    private static final String secretKey = "2021.06.03.cknb.hiddentagiqr.com";

    public Aes256Utils() throws Exception {

        this.encodedIv = ENCODER.encodeToString(IV);
    }

    @SneakyThrows
    String encrypt(String plainText) {
        Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
        byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
        char[] byteEncrypted = Hex.encodeHex(encrypted);
        return new String(byteEncrypted).toUpperCase();
    }

    @SneakyThrows
    String decrypt(String cipherText) {
        Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
        byte[] byteCipherText = Hex.decodeHex(cipherText.toCharArray());
        byte[] decrypted = cipher.doFinal(byteCipherText);
        return new String(decrypted);
    }

    private Cipher getCipher(int decryptMode)
            throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        byte[] key = new byte[16];
        int i = 0;

        for (byte b : secretKey.getBytes()) {
            key[i++%16] ^= b;
        }

        cipher.init(decryptMode, new SecretKeySpec(key, "AES"));
        return cipher;
    }
}

