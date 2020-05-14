package com.rvakva.travel.devkit.expend

import android.annotation.SuppressLint
import android.util.Base64
import java.security.*
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午4:54
 */
private const val KEY_AES_ALGORITHM_PADDING = "AES/ECB/PKCS5Padding"

private const val KEY_RSA_ALGORITHM_PADDING = "RSA/ECB/PKCS1Padding"

private const val KEY_AES_ALGORITHM = "AES"

private const val KEY_RSA_ALGORITHM = "RSA"

//公钥
private const val KEY_RSA_PUBLIC_KEY =
    "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqVjbiJNqhwlaTr2d2rREUps4d5v0Z8la61+I/qqlIytYdZISQVNsuC8HWraIy3dmXsopsqd6lRJWWTSoQW1PIs4oNhjh+sHC8pPnEc6ipNO0V2NKLkic2DaH1HLLH6iHiuXrqSHEaC1Sdz9vhuZ0JmERSiSnY/lt+BUP9Bumqgn0YUZYw1NOeH6nfPggNV2vfLJvbwfRir62xtse3Z2du3HdoQDSb3mv0/fgnJnErbzf6NopE8u7W89syqQcJmI616tN6e0yqwMrBUvnl171qksaYw+2zdhu2h3024L79qhd+Y9WwSCdTP+AWRPS16eH+xfq8X0BvP2xK6sO6Q9QFQIDAQAB"

//私钥
private const val KEY_RSA_PRIVATE_KEY =
    "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCpWNuIk2qHCVpOvZ3atERSmzh3m/RnyVrrX4j+qqUjK1h1khJBU2y4LwdatojLd2Zeyimyp3qVElZZNKhBbU8izig2GOH6wcLyk+cRzqKk07RXY0ouSJzYNofUcssfqIeK5eupIcRoLVJ3P2+G5nQmYRFKJKdj+W34FQ/0G6aqCfRhRljDU054fqd8+CA1Xa98sm9vB9GKvrbG2x7dnZ27cd2hANJvea/T9+CcmcStvN/o2ikTy7tbz2zKpBwmYjrXq03p7TKrAysFS+eXXvWqSxpjD7bN2G7aHfTbgvv2qF35j1bBIJ1M/4BZE9LXp4f7F+rxfQG8/bErqw7pD1AVAgMBAAECggEAUCh0ObB1kxeVPII6buQ3FtbMIqmaRWok9BaTdN/3LK9+vp/2rh877e6sz35pP83zkdilZiAqQNeeMblgDyAbfkEQ/ZJHnPcMPOBPVrl1eQmQanDgEwph22HsZ14y0XhnOBLeun72Rc12JGXNd5Ar2GLH2U2CQis1p2Do3UYdXIxzSFB82dgqvtJd1m6xzIXrZ3JIcyUHZGU7MnFT8x9J0MIkE9QFEzgaRGh7yy47t86f1M8qwa/oHuOKOqkRIczdn5+Ntvhe0GMvVknMFcK9+tTOLu0pPwPtE+0lae7g+SVFFLGasN7w66mby526OYjVhogw0qPDBoZHsTDcQ7ob/QKBgQD6DYFrk7wTP3o3J3Q7vcBg/nSNA5GRHBFe1sXYTXUDfJUZV1z4uAABn8Ha2qjMSA7zy0FPBWNUXy9kEV2oAKdhcoeEwJF0VfgKRZ5y/Rwvn9KxSvufPLhEvxwNrrPPuvDvXCuWXNfzQH8hWjvqguSZfadIouBzrlh509G/RIqM2wKBgQCtX/XFg3Xi8Bbcc4zA8mFvfeOzyBE2U+reewiv99+tCTraxITBhLq48+SvN3bs4FubXJA/fzSWKvtpqfTH2nNpi5YrqKTSKBm6lEbVN8syiAzOb8hulFvz7/5ieX9BsIMEzUgHPlmqg6V0M9Yic3wkMa9CVQssgJnszYyiNCyxzwKBgQDyh1joy5DWpnHWvvi43RGTwhmvkC+HLE8Yyn14j6AX8qT2wEqXnne7G4W9zOl9wcJm6dR9XX97HRttFn7dbUzrcldJBjHOQXfkFqRZaXAGLSEA/vZQ+2bKnwYJq1ISAIKh1qccHw8Bi6QC6G9GxRAOY9Z12CyVZxu/hhhhSIyNkwKBgH9CZZuSMDDgvFcateFWyHBW/pGTNBclp8ugkbLghh10HfBAuZV2gLyLwXtfgfrDgFpcqkEBdS8Y46wepV8hGQALBrKLOlhMOP58IjYVmyA/Pu80m/GXEpvOuHG97svf7XvT1qACgzynHwZZmj91d1T150gQNoB28QUhp63UEE5/AoGAWjSka4m8JRhwJbBp/bzhfUE+PonnmTzoVE99BKCOIL9YHNHDHlwF09DXT8/b99MbWLq5zE/RZykcR8YET+0JSOMrhEw+gMOcq88dI1MkEoWo8IgfRzErG3uStj8iPYisijGkbZhaf39AFoS4+a2ylAMaYSTn1uXDkRuG2BqG1do="

private const val KEY_AES_KEY = "63c9fsCnl5SCBG5U"

fun generateRsaKeyPair(): Pair<String, String> {
    val keyPairGenerator = KeyPairGenerator.getInstance(KEY_RSA_ALGORITHM)
    val keypair = keyPairGenerator.genKeyPair()
    val private = String(Base64.encode(keypair.private.encoded, Base64.DEFAULT))
    val public = String(Base64.encode(keypair.public.encoded, Base64.DEFAULT))
    return Pair(private, public)
}

@SuppressLint("GetInstance")
fun String.toAesEncrypt(key: String = KEY_AES_KEY): String {
    val cipher = Cipher.getInstance(KEY_AES_ALGORITHM_PADDING)
    val keySpec = SecretKeySpec(key.toByteArray(),
        KEY_AES_ALGORITHM
    )
    cipher.init(Cipher.ENCRYPT_MODE, keySpec)
    val cipherByteArray = cipher.doFinal(this.toByteArray())
    return String(Base64.encode(cipherByteArray, Base64.NO_WRAP))
}

@SuppressLint("GetInstance")
fun String.toAesDecrypt(key: String = KEY_AES_KEY): String {
    val keySpec = SecretKeySpec(key.toByteArray(),
        KEY_AES_ALGORITHM
    )
    val cipher = Cipher.getInstance(KEY_AES_ALGORITHM_PADDING)
    cipher.init(Cipher.DECRYPT_MODE, keySpec)
    val base64DecodeArray = Base64.decode(this, Base64.NO_WRAP)
    return String(cipher.doFinal(base64DecodeArray))
}

//公钥加密
fun String.toRsaEncrypt(publicKey: String = KEY_RSA_PUBLIC_KEY): String {
    val byteArray = Base64.decode(publicKey, Base64.NO_WRAP)
    val keyFactory = KeyFactory.getInstance(KEY_RSA_ALGORITHM)
    val keySpec = X509EncodedKeySpec(byteArray)
    val pubKey = keyFactory.generatePublic(keySpec) as PublicKey
    val cipher = Cipher.getInstance(KEY_RSA_ALGORITHM_PADDING)
    cipher.init(Cipher.ENCRYPT_MODE, pubKey)
    val cipherByteArray = cipher.doFinal(this.toByteArray())
    return String(Base64.encode(cipherByteArray, Base64.NO_WRAP))
}

// 私钥解密
fun String.toRsaDecrypt(privateKey: String = KEY_RSA_PRIVATE_KEY): String {
    val base64Buffer = Base64.decode(privateKey, Base64.NO_WRAP)
    val keySpec = PKCS8EncodedKeySpec(base64Buffer)
    val keyFactory = KeyFactory.getInstance(KEY_RSA_ALGORITHM)
    val priKey = keyFactory.generatePrivate(keySpec) as PrivateKey
    val cipher = Cipher.getInstance(KEY_RSA_ALGORITHM_PADDING)
    cipher.init(Cipher.DECRYPT_MODE, priKey)
    val rawByteArray = Base64.decode(this.toByteArray(), Base64.NO_WRAP)
    return String(cipher.doFinal(rawByteArray))
}

fun String.toSha256(): String {
    val digest = MessageDigest.getInstance("SHA-256")
    return digest.digest(this.toByteArray()).toHex()
}

fun ByteArray.toHex() =
    with(StringBuilder()) {
        this@toHex.forEach {
            val hex = it.toInt() and (0xFF)
            val hexStr = Integer.toHexString(hex)
            if (hexStr.length == 1) {
                append("0").append(hexStr)
            } else {
                append(hexStr)
            }
        }
        this.toString()
    }


fun String.toBase64(flags: Int = Base64.NO_WRAP) = String(Base64.encode(this.toByteArray(), flags))