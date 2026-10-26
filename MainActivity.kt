package com.anbutourtravels.app

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import android.app.Activity

class MainActivity : Activity() {
    private lateinit var webView: WebView
    private var filePathCallback: ValueCallback<Array<Uri>>? = null
    private val filePickerRequest = 4102
    private val siteUrl = "https://anbucab.netlify.app/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        webView = WebView(this)
        setContentView(webView)

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.allowFileAccess = false
        webView.settings.javaScriptCanOpenWindowsAutomatically = false
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                val uri = request.url
                val scheme = uri.scheme ?: return false
                if (scheme == "tel" || scheme == "mailto" || scheme == "sms") {
                    runCatching { startActivity(Intent(Intent.ACTION_VIEW, uri)) }
                    return true
                }
                if (scheme == "https" && uri.host != "anbucab.netlify.app") {
                    runCatching { startActivity(Intent(Intent.ACTION_VIEW, uri)) }
                    return true
                }
                if (scheme == "whatsapp" || uri.host == "wa.me" || uri.host == "api.whatsapp.com") {
                    runCatching { startActivity(Intent(Intent.ACTION_VIEW, uri)) }
                    return true
                }
                return false
            }
        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                this@MainActivity.filePathCallback?.onReceiveValue(null)
                this@MainActivity.filePathCallback = filePathCallback
                val intent = fileChooserParams?.createIntent()
                    ?: Intent(Intent.ACTION_GET_CONTENT).apply { type = "image/*"; addCategory(Intent.CATEGORY_OPENABLE) }
                return try {
                    startActivityForResult(intent, filePickerRequest)
                    true
                } catch (e: Exception) {
                    this@MainActivity.filePathCallback = null
                    Toast.makeText(this@MainActivity, "Unable to open photo picker", Toast.LENGTH_SHORT).show()
                    false
                }
            }
        }

        if (savedInstanceState == null) webView.loadUrl(siteUrl)
        else webView.restoreState(savedInstanceState)
    }

    @Deprecated("Deprecated in Android API, retained for broad device compatibility")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == filePickerRequest) {
            val result = if (resultCode == Activity.RESULT_OK) {
                WebChromeClient.FileChooserParams.parseResult(resultCode, data)
            } else null
            filePathCallback?.onReceiveValue(result)
            filePathCallback = null
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        webView.saveState(outState)
        super.onSaveInstanceState(outState)
    }

    @Deprecated("Deprecated in Android API, retained for broad device compatibility")
    override fun onBackPressed() {
        if (::webView.isInitialized && webView.canGoBack()) webView.goBack()
        else super.onBackPressed()
    }
}
