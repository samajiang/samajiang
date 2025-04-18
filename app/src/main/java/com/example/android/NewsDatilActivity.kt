package com.example.android

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class NewsDatilActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_datil)
        val toobar: Toolbar = findViewById(R.id.newstoolbar)
        val webview:WebView = findViewById(R.id.newsweb)
        var weburl:String? = intent.getStringExtra("newsurl")
        var title = intent.getStringExtra("newstitle")

        if (weburl != null) {
            toobar.title = title
            webview.loadUrl(weburl)
        }


    }
}