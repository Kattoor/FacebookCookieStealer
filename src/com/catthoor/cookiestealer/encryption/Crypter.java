package com.catthoor.cookiestealer.encryption;

public interface Crypter<T1, T2> {

    T1 encrypt(T2 toEncrypt);
    T2 decrypt(T1 toDecrypt);
}
