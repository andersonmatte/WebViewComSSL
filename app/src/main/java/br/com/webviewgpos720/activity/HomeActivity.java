package br.com.webviewgpos720.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import br.com.webviewgpos720.R;

public class HomeActivity extends AppCompatActivity {

    public static final String URL_LOJA_GERTEC = "https://loja.gertec.com.br/";
    private WebView webView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.getSupportActionBar().hide();
        this.webView = (WebView) findViewById(R.id.webview);
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
        this.progressBar.setVisibility(View.GONE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onStart() {
        super.onStart();
        this.loadPagina(URL_LOJA_GERTEC);
    }

    /**
     * Carrega a URL definida na entrada do APP.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetJavaScriptEnabled")
    public void loadPagina(String url) {

        SslErrorHandler handler = null;
        SslError error = null;

        WebSettings webSetting = webView.getSettings();
        webSetting.setBuiltInZoomControls(true);
        webSetting.setJavaScriptEnabled(true);
        //improve WebView Performance
        webSetting.setDomStorageEnabled(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSaveFormData(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        // Web CLient com ignore SSL, Gambis, hahahaha!
        webView.setWebViewClient(new SSLTolerentWebViewClient(webView));
        webView.loadUrl(url);
        // Esconde o botão do zoom
        webView.getSettings().setDisplayZoomControls(false);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Cria a webview para exibição da página da loja.
     */
    public class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("tel:") || url.startsWith("whatsapp:") || url.startsWith("intent://") || url.startsWith("http://")) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                webView.goBack();
                return true;
            }
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }

    }

}