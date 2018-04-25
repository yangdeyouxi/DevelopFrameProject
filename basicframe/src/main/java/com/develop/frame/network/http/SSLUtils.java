package com.develop.frame.network.http;

import android.annotation.SuppressLint;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Created by sam on 2017/5/21.
 */

public class SSLUtils {
    public static class SSLParams{
        public SSLSocketFactory sSLSocketFactory;
        public X509TrustManager trustManager;
    }


    public static SSLParams getSslSocketFactory(){
        return getSslSocketFactoryBase(null,null,null);
    }


    /**
     * https单向认证
     * @param trustManager
     * @return
     */
    public static SSLParams getSslSocketFactory(X509TrustManager trustManager){
        return getSslSocketFactoryBase(trustManager,null,null);
    }

    /**
     * https单向认证
     * @param certificates
     * @return
     */

    public static SSLParams getSslSocketFactory(InputStream... certificates){
        return getSslSocketFactoryBase(null,null,null,certificates);
    }

    /**
     * bksFile 和password  ->客户端使用bks证书校验服务端证书
     * certificates 用含有服务端公钥的证书校验服务端证书。
     * https 双向认证
     * @param bksFile
     * @param password
     * @param certificates
     * @return
     */
    public static SSLParams getSslSocketFactory(InputStream bksFile,String password,InputStream... certificates){
        return getSslSocketFactoryBase(null,bksFile,password,certificates);
    }

    /**
     * https 双向认证
     * bksFile 和 password -> 客户端使用bks证书校验服务端证书
     * X509TrustManager ->如果需要自己校验，可以自己实现相关校验，如果不需要自己校验，那么传null即可。
     * @param bksFile
     * @param password
     * @param trustManager
     * @return
     */
    public static SSLParams getSslSocketFactory(InputStream bksFile,String password,X509TrustManager trustManager){
        return getSslSocketFactoryBase(trustManager,bksFile,password);
    }
    public static SSLParams getSslSocketFactoryBase(X509TrustManager trustManager, InputStream bksFile, String password,InputStream... certificates){
        SSLParams sslParams = new SSLParams();

        try{
            TrustManager[] trustManagers = prepareTrustManager(certificates);
            KeyManager[] keyManagers = prepareKeyManager(bksFile,password);
            X509TrustManager manager;

            if (trustManager !=null) {
                manager = trustManager;
            }else if (trustManagers!=null){
                manager = chooseTrustManager(trustManagers);
            }else {
                manager = UnSafeTrustManager;
            }

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagers,new TrustManager[]{trustManager},null);

            sslParams.sSLSocketFactory = sslContext.getSocketFactory();
            sslParams.trustManager = manager;
            return sslParams;
        }catch (NoSuchAlgorithmException e){
            throw new AssertionError(e);
        }catch (KeyManagementException e){
            throw new AssertionError(e);
        }
    }

    /**
     * 为了解决客户端不信任服务器数字证书的问题，网络上大部分的解决方案是让客户端不对证书做任何检查
     * 这是一种有很大安全漏洞的办法
     */
    private static X509TrustManager  UnSafeTrustManager = new X509TrustManager() {


        @SuppressLint("TrustAllX509TrustManager")
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @SuppressLint("TrustAllX509TrustManager")
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    };

    private static TrustManager[] prepareTrustManager(InputStream... certificates){
        if (certificates ==null || certificates.length <=0)
            return null;

        try{
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate:certificates){
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias,certificateFactory.generateCertificate(certificate));
                try{
                    if (certificate!=null){
                        certificate.close();
                    }
                }catch (IOException e){

                }
            }
            TrustManagerFactory trustManagerFactory = null;
            trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            return trustManagers;

        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();

        }catch (CertificateException e){
            e.printStackTrace();
        }catch (KeyStoreException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static KeyManager[] prepareKeyManager(InputStream bksFile, String password){
        try{
            if (bksFile ==null || password ==null){
                return null;
            }
            KeyStore clientKeyStore = KeyStore.getInstance("BKS");
            clientKeyStore.load(bksFile,password.toCharArray());
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(clientKeyStore,password.toCharArray());
            return keyManagerFactory.getKeyManagers();

        }catch (KeyStoreException e){
            e.printStackTrace();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }catch (CertificateException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (Exception e){

        }
        return null;
    }


    private static X509TrustManager chooseTrustManager(TrustManager[] trustManagers){
        for (TrustManager trustManager: trustManagers){
            if (trustManager instanceof X509TrustManager){
                return (X509TrustManager) trustManager;
            }
        }
        return null;
    }


    public static HostnameVerifier UnSafeHostnameVerifier= new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

}
