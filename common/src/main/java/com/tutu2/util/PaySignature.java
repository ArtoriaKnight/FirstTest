package com.tutu2.util;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

public class PaySignature {

    private static final String CHARSET = "utf-8";
    private static String AES_KEY_ALGORITHM = "AES/ECB/PKCS5Padding";

    private PaySignature() {
    }


    public static String rsaSign(String content, String privateKey) throws Exception {
        try {
            PrivateKey e = getPrivateKeyFromPKCS8(new ByteArrayInputStream(privateKey.getBytes()));
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initSign(e);
            signature.update(content.getBytes());
            byte[] signed = signature.sign();
            return Base64.encode(signed);
        } catch (InvalidKeySpecException var6) {
            throw new Exception("RSA私钥格式不正确，请检查是否正确配置了PKCS8格式的私钥", var6);
        } catch (Exception var7) {
            throw new Exception("RSAcontent = " + content + "; charset = " + "utf-8", var7);
        }
    }
    public static String rsaSignHex(String content, String privateKey) throws Exception {
        try {
            PrivateKey e = getPrivateKeyFromPKCS8(new ByteArrayInputStream(privateKey.getBytes()));
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initSign(e);
            signature.update(content.getBytes());
            byte[] signed = signature.sign();
            return bin2hex(signed);
        } catch (InvalidKeySpecException var6) {
            throw new Exception("RSA私钥格式不正确，请检查是否正确配置了PKCS8格式的私钥", var6);
        } catch (Exception var7) {
            throw new Exception("RSAcontent = " + content + "; charset = " + "utf-8", var7);
        }
    }

    public static byte[] rsaSign3(String content, String privateKey) throws Exception {
            try {
                PrivateKey e = getPrivateKeyFromPKCS8(new ByteArrayInputStream(privateKey.getBytes()));
                Signature signature = Signature.getInstance("SHA1WithRSA");
                signature.initSign(e);
                signature.update(content.getBytes());
                byte[] signed = signature.sign();
                return signed;
            } catch (InvalidKeySpecException var6) {
                throw new Exception("RSA私钥格式不正确，请检查是否正确配置了PKCS8格式的私钥", var6);
            } catch (Exception var7) {
                throw new Exception("RSAcontent = " + content + "; charset = " + "utf-8", var7);
            }
    }


    private static PrivateKey getPrivateKeyFromPKCS8(InputStream ins) throws Exception {
        if(ins != null) {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = StreamUtil.readText(ins).getBytes();
            encodedKey = Base64.decode(new String(encodedKey,"UTF-8"));
            return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
        } else {
            return null;
        }
    }


    public static boolean rsaCheckContent(String content, String sign, String publicKey) throws Exception {
        try {
            PublicKey e = getPublicKeyFromX509(new ByteArrayInputStream(publicKey.getBytes()));
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initVerify(e);
            signature.update(content.getBytes());
            return signature.verify(Base64.decode(sign));
        } catch (Exception var6) {
            var6.printStackTrace();
            throw new Exception("RSAcontent = " + content + ",sign=" + sign + ",charset = " + "utf-8", var6);
        }
    }

    private static PublicKey getPublicKeyFromX509(InputStream ins) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        StringWriter writer = new StringWriter();
        StreamUtil.io(new InputStreamReader(ins), writer);
        byte[] encodedKey = writer.toString().getBytes();
        encodedKey = Base64.decode(new String(encodedKey,"UTF-8"));
        return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
    }







    public static Map<String, Key> genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(2048);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        HashMap<String,Key> keyMap = new HashMap<String,Key>(2);
        keyMap.put("PUBLIC_KEY", publicKey);
        keyMap.put("PRIVATE_KEY", privateKey);
        return keyMap;
    }


    public static String getPrivateKey(Map<String, Key> keyMap)
            throws Exception {
        Key key = keyMap.get("PRIVATE_KEY");
        return Base64.encode(key.getEncoded());
    }


    public static String getPublicKey(Map<String, Key> keyMap)
            throws Exception {
        Key key = keyMap.get("PUBLIC_KEY");
        return Base64.encode(key.getEncoded());
    }

    /**AES解密*/
    public static String AESDecrypt(String sSrc,String sKey) throws Exception{
        SecretKeySpec skeySpec = new SecretKeySpec(sKey.getBytes("ASCII"), "AES");
        Cipher cipher = Cipher.getInstance(AES_KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] encrypted1 = hex2bin(sSrc);
        byte[] original = cipher.doFinal(encrypted1);
        return new String(original,CHARSET);
    }

    /**AES加密*/
    public static String AESEncrypt(String sSrc,String sKey) throws Exception{
        SecretKeySpec skeySpec = new SecretKeySpec(sKey.getBytes("ASCII"), "AES");
        Cipher cipher = Cipher.getInstance(AES_KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes(CHARSET));
        return bin2hex(encrypted);
    }

    /**二进制转十六进制*/
    public static String bin2hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**十六进制转二进制*/
    public static byte[] hex2bin(String src) {
        int l = src.length() / 2;
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            ret[i] = Integer.valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();
        }
        return ret;
    }

    public static String getSha1(String str){
        if(str==null||str.length()==0){
            return null;
        }
        char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9',
                'a','b','c','d','e','f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes(CHARSET));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j*2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final String getMessageDigest(byte[] paramArrayOfByte) {
        char[] arrayOfChar1= {'0','1','2','3','4','5','6','7','8','9',
                'a','b','c','d','e','f'};
        try {
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            localMessageDigest.update(paramArrayOfByte);
            byte[] arrayOfByte = localMessageDigest.digest();
            int i = arrayOfByte.length;
            char[] arrayOfChar2 = new char[i * 2];
            int j = 0;
            int k = 0;
            while (true) {
                if (j >= i) {
                    return new String(arrayOfChar2);
                }
                int m = arrayOfByte[j];
                int n = k + 1;
                arrayOfChar2[k] = arrayOfChar1[(0xF & m >>> 4)];
                k = n + 1;
                arrayOfChar2[n] = arrayOfChar1[(m & 0xF)];
                j++;
            }
        } catch (Exception localException) {
        }
        return null;
    }

    public static String getSignContent(Map<String, ?> sortedParams) {
        StringBuilder content = new StringBuilder();
        ArrayList<String> keys = new ArrayList<String>(sortedParams.keySet());
        Collections.sort(keys);
        int index = 0;

        for (String key : keys) {
            String value = String.valueOf(sortedParams.get(key));
            if (StringUtil.areNotEmpty(key, value)) {
                content.append(index == 0 ? "" : "&").append(key).append("=").append(value);
                ++index;
            }
        }

        return content.toString();
    }

    /**
     * 获取一定长度的随机字符串
     * @param length 指定字符串长度
     * @return 一定长度的字符串
     */
    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static boolean rsaCheckContentHex(String content, String sign, String publicKey) throws Exception {
        try {
            PublicKey e = getPublicKeyFromX509(new ByteArrayInputStream(publicKey.getBytes()));
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initVerify(e);
            signature.update(content.getBytes());
            return signature.verify(hex2bin(sign));
        } catch (Exception var6) {
            var6.printStackTrace();
            throw new Exception("RSAcontent = " + content + ",sign=" + sign + ",charset = " + "utf-8", var6);
        }
    }

    public static byte[] encryptByPublicKey(byte[] bytes, String publicKey) {
        return new byte[0];
    }

    public static char getRandomOneString() {
        String base = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
            return base.charAt(random.nextInt(base.length()));
    }
    
    public static void main(String[] args) {
    	String str = PaySignature.getMessageDigest((10002601 + "123456").getBytes());
    	System.out.println(str);
	}
}
