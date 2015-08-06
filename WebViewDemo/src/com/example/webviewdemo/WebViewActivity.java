package com.example.webviewdemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 */
public class WebViewActivity extends Activity implements OnClickListener {

	private WebView webView;
	private ImageView add_new;
	private ProgressBar progressBar;
	private FrameLayout content;
	private TextView refresh, top_title;

	private String url = "http://192.168.0.205:8080/MyApp/index.jsp";
	private AlertDialog.Builder builder;

	private boolean hasLoadAnimation = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		initView();
	}

	@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
	private void initView() {
		content = (FrameLayout) findViewById(R.id.content);
		webView = (WebView) findViewById(R.id.webView);
		add_new = (ImageView) findViewById(R.id.add_new);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		refresh = (TextView) findViewById(R.id.refresh);
		top_title = (TextView) findViewById(R.id.top_title);

		refresh.setOnClickListener(this);
		add_new.setOnClickListener(this);

		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);

		webView.addJavascriptInterface(new JavascriptInterface(), "js");
		webView.loadUrl(url);

		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				// System.out.println("onProgressChanged");
				// System.out.println("newProgress=" + newProgress);
				progressBar.setProgress(newProgress);
				if (newProgress >= 70) {
					view.setVisibility(View.VISIBLE);
				}
				if (newProgress == 100) {
					progressBar.setVisibility(View.GONE);
				}
			}

			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
				if (title.length() > 10) {
					title = title.substring(0, 10) + "..." + "";
				}
				top_title.setText(title);
			}
		});

		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				System.out.println("onPageStarted url=" + url);
				super.onPageStarted(view, url, favicon);
				progressBar.setVisibility(View.VISIBLE);
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				System.out.println("shouldOverrideUrlLoading url=" + url);
				if (url.indexOf("call_tel:") > -1) {
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_CALL);
					intent.setData(Uri.parse("tel:" + url.substring(url.indexOf("call_tel:") + 9)));
					System.out.println("呼叫=" + (url.substring(url.indexOf("call_tel:") + 9)));
					startActivity(intent);
				}
				if (url.indexOf("dail_tel:") > -1) {
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_DIAL);
					intent.setData(Uri.parse("tel:" + url.substring(url.indexOf("dail_tel:") + 9)));
					System.out.println("拨打=" + (url.substring(url.indexOf("dail_tel:") + 9)));
					startActivity(intent);
				}
				return super.shouldOverrideUrlLoading(view, url);
			}

			/**
			 * 先执行onReceivedError，然后执行onPageFinished
			 */
			@Override
			public void onPageFinished(WebView view, String url) {
				System.out.println("onPageFinished");
				super.onPageFinished(view, url);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);
				System.out.println("onReceivedError");
				System.out.println("errorCode=" + errorCode);
				hasLoadAnimation = false;
				if (errorCode < 0) {
					content.setVisibility(View.GONE);
				}
			}
		});
	}

	public class JavascriptInterface {
		public JavascriptInterface() {
		}

		public void openDialog() {
			builder = new AlertDialog.Builder(WebViewActivity.this);
			builder.setTitle("由网页打开android里的对话框");
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					Toast.makeText(WebViewActivity.this, "点击的是确定", Toast.LENGTH_SHORT).show();
				}
			});
			builder.setCancelable(true);
			builder.show();
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.refresh:
			if (!hasLoadAnimation) {
				System.out.println("1111");
				webView.setVisibility(View.GONE);
				content.setVisibility(View.VISIBLE);
				hasLoadAnimation = true;
			}
			webView.reload();
			break;
		case R.id.add_new:
			webView.loadUrl("javascript:msg()");
			break;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		webView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		webView.onPause();
	}

	@Override
	public void onBackPressed() {
		if (webView.canGoBack()) {
			webView.goBack();
		} else {
			WebViewActivity.this.finish();
		}
	}
}
