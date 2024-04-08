package com.photo.editor.square.splash.view.view.bean;

import android.util.Base64;
import com.bumptech.glide.load.Key;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

/* loaded from: classes2.dex */
public class AESEncryptionDecryption {
    public static final String PublurKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAto4URLiN/OkTVcD1XNF8\nZ7Guj0TP6ZhsWb3qr9ycv6ZG4ZUI/781SmzKqJLYlb2xAXJj+6wqkiqwLWE/VYlI\n+42G3o466iZt2KCfu/ce+OAeJYbAcFUzkZFGnH+VorZ63YMWx6SHIpJHKYsFdCcg\nwcbPlXkOL37/f9VrzcD1DJAMPAFn7kMRWbWKqCzQskJzi3KyJmcnydbuP8bvXs4/\nYSKqTg0kH12aWZbeNVTGhNli7raOONeN4LTYcgaihER1Rkp0a1gb86bfoco2c3IA\njPp8D9NMeR8t7IRoy70Fhn9H2oJZ3o6RSMCiaSNokK+XYyxTUbdZfxwdy8l9P2CB\n1QIDAQAB";

    public static String encrypt(String str, String str2) throws Exception {
        byte[] decode = Base64.decode(str2, 0);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(1, (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decode)));
        return Base64.encodeToString(cipher.doFinal(str.getBytes(Charset.forName(Key.STRING_CHARSET_NAME))), 0);
    }

    public static String decrypt(String str, String str2) throws Exception {
        byte[] decode = Base64.decode(str.getBytes(Charset.forName(Key.STRING_CHARSET_NAME)), 0);
        byte[] decode2 = Base64.decode(str2, 0);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(2, (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decode2)));
        return new String(cipher.doFinal(decode));
    }
}
