package lukakrivacevic.weatherapp.ui.fragments

import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_web_view.*
import lukakrivacevic.weatherapp.R

class WebViewFragment : Fragment(R.layout.fragment_web_view) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        webView.apply {
            webViewClient = WebViewClient()
            loadUrl("http://www.google.com")
            canGoBack()
            setOnKeyListener ( object : View.OnKeyListener {
                override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                    if (keyCode == KeyEvent.KEYCODE_BACK
                        && event?.getAction() == MotionEvent.ACTION_UP
                        && webView.canGoBack()) {
                        webView.goBack();
                        return true;
                    }
                    return false;
                }

            })

        }

    }
}