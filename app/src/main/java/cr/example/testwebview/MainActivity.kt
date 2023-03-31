package cr.example.testwebview

import android.annotation.SuppressLint
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import cr.example.testwebview.databinding.ActivityMainBinding
import cr.example.testwebview.util.changeBackgroundColor
import cr.example.testwebview.util.getBackgroundColor
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets


class MainActivity : AppCompatActivity() {

    private val startUrl = "https:\\www.google.dex" // not existing

    private lateinit var binding: ActivityMainBinding

    private class ExtendedWebViewClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest
        ): Boolean {
            val uri: Uri = request.url
            view?.loadUrl(uri.toString())
            return true
        }

    }

    @Throws(IOException::class)
    private fun getString(inputStream: InputStream): String {
        val inputStreamReader = InputStreamReader(inputStream, StandardCharsets.UTF_8)
        val br = BufferedReader(inputStreamReader)
        var line: String?
        val sb = StringBuilder()
        while (br.readLine().also { line = it } != null) {
            sb.append(line)
        }
        return sb.toString()
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.webView.run {
          settings.javaScriptEnabled=true
          settings.domStorageEnabled=true

          webViewClient = ExtendedWebViewClient()

          clearCache(true)
        }

        binding.buttonLoad.setOnClickListener {
            binding.webView.loadUrl(binding.url.text.toString())
        }

        binding.buttonLoadAssetHtml.setOnClickListener {

            val file : InputStream
            val assetName = "imprint.html"
            try {
                file = resources.assets.open(assetName)
                val versionName = "0.1"
                val r = Regex("\\{APP_VERSION\\}") // OK !
//                val r = Regex("\\{APP_VERSION}") // CRASH !
                val sHtml = getString(file)
                val imprint = sHtml.replace( r, versionName )
                binding.webView.loadDataWithBaseURL(
                    "nil://nil.nil", imprint, "text/html", "UTF-8", null
                )

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        binding.url.setText(startUrl)
        binding.webView.loadUrl(binding.url.text.toString())

        binding.buttonChangeColor.setOnClickListener {

            if(it.getBackgroundColor()==Color.RED)
              it.changeBackgroundColor(Color.BLACK)
            else
              it.changeBackgroundColor(Color.RED)

        }

    }


}