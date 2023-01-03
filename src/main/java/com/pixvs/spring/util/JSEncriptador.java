package com.pixvs.spring.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;

public class JSEncriptador {


    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";
    private static final String KEY = "the_cake_is_a_lie";

    /**
     * aes padding key
     * @param key
     */
    private static String paddingKey(String key){
        if(key.length() < 32){
            for(int i = key.length(); i < 32; i++){
                key += '0';
            }
        }
        return key;
    }

    /**
     * aes decryption
     * @param encrypt content
     * @return
     * @throws Exception
     */
    public static String aesDecrypt(String encrypt) {
        try {
            String fixedKey = paddingKey(KEY);
            return aesDecrypt(encrypt, fixedKey);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * aes encryption
     * @param content
     * @return
     * @throws Exception
     */
    public static String aesEncrypt(String content) {
        try {
            String fixedKey = paddingKey(KEY);
            return aesEncrypt(content, fixedKey);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Convert byte[] to a string of various radix
     * @param bytes byte[]
     * @param radix can convert the range of hexadecimal, from Character.MIN_RADIX to Character.MAX_RADIX, after the range is changed to decimal
     * @return converted string
     */
    public static String binary(byte[] bytes, int radix){

        return new BigInteger(1, bytes).toString(radix);// where 1 represents a positive number
    }

    /**
     * base 64 encode
     * @param bytes to be encoded byte[]
     * @return encoded base 64 code
     */
    public static String base64Encode(byte[] bytes){
        return Base64.encodeBase64String(bytes);
    }

    /**
     * base 64 decode
     * @param base64Code base 64 code to be decoded
     * @return decoded byte[]
     * @throws Exception
     */
    public static byte[] base64Decode(String base64Code) throws Exception{
        return StringUtils.isEmpty(base64Code) ? null : new BASE64Decoder().decodeBuffer(base64Code);
    }


    /**
     * AES encryption
     * @param content content to be encrypted
     * @param encryptKey encryption key
     * @return Encrypted byte[]
     * @throws Exception
     */
    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));

        return cipher.doFinal(content.getBytes("utf-8"));
    }


    /**
     * AES encryption is base 64 code
     * @param content content to be encrypted
     * @param encryptKey encryption key
     * @return Encrypted base 64 code
     * @throws Exception
     */
    public static String aesEncrypt(String content, String encryptKey) throws Exception {
        return base64Encode(aesEncryptToBytes(content, encryptKey));
    }

    /**
     * AES decryption
     * @param encryptBytes byte[] to be decrypted
     * @param decryptKey decryption key
     * @return Decrypted String
     * @throws Exception
     */
    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);

        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes);
    }


    /**
     * Decrypt base 64 code AES
     * @param encryptStr base 64 code to be decrypted
     * @param decryptKey decryption key
     * @return decrypted string
     * @throws Exception
     */
    public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
        return StringUtils.isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
    }
}
