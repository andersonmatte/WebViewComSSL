package br.com.webviewgpos720.activity;

import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class SSLTolerentWebViewClient extends WebViewClient {

    private WebView view;

    public SSLTolerentWebViewClient(WebView webView) {
        this.view = webView;
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        // Ignore SSL certificate errors
        //Possiveis erros, mas eu não quero descobrir agora
//        switch (error.getPrimaryError()) {
//            case SslError.SSL_UNTRUSTED:
//                System.out.println("SslError : The certificate authority is not trusted.");
//                break;
//            case SslError.SSL_EXPIRED:
//                System.out.println("SslError : The certificate has expired.");
//                break;
//            case SslError.SSL_IDMISMATCH:
//                System.out.println("The certificate Hostname mismatch.");
//                break;
//            case SslError.SSL_NOTYETVALID:
//                System.out.println("The certificate is not yet valid.");
//                break;
//        }
        handler.proceed();
    }

}
