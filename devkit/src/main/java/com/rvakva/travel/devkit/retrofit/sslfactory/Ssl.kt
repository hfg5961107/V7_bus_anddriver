package com.sherloki.devkit.retrofit.sslfactory

import android.content.Context
import java.io.IOException
import java.io.InputStream
import java.security.GeneralSecurityException
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.util.*
import javax.net.ssl.*

class Ssl {

    var trustManager: X509TrustManager? = null
    var sslSocketFactory: SSLSocketFactory? = null

    val defaultManager by lazy {
        arrayOf<TrustManager>(
            object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<X509Certificate>,
                    authType: String
                ) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<X509Certificate>,
                    authType: String
                ) {
//                        String pubKey = EncApi.getInstance().getPubKey();
//
//                        if (!TextUtils.isEmpty(pubKey)) {
//                            if (chain == null) {
//                                throw new IllegalArgumentException("checkServerTrusted:x509Certificate array isnull");
//                            }
//
//                            if (chain.length == 0) {
//                                throw new IllegalArgumentException("checkServerTrusted: X509Certificate is empty");
//                            }
//
//                            RSAPublicKey pubkey = (RSAPublicKey) chain[0].getPublicKey();
//
//                            String encoded = new BigInteger(1, pubkey.getEncoded()).toString(16);
//
//                            final boolean expected = pubKey.equalsIgnoreCase(encoded);
//                            if (!expected) {
//                                throw new CertificateException("checkServerTrusted: Expected public key: "
//                                        + pubKey + ", got public key:" + encoded);
//                            }
//                        }
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            }
        )
    }


    init {
        try {
            trustManager = defaultManager[0] as X509TrustManager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, defaultManager, SecureRandom())
            sslSocketFactory = sslContext.socketFactory
        } catch (e: GeneralSecurityException) {
            throw RuntimeException(e)
        }
    }
}