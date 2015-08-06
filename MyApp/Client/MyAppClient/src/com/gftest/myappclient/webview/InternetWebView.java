package com.gftest.myappclient.webview;

import android.annotation.SuppressLint;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gftest.myappclient.BaseActivity;
import com.gftest.myappclient.R;

public class InternetWebView extends BaseActivity implements OnClickListener {

	private WebView webView;
	private ProgressBar progressBar;
	private TextView refresh, top_title, add_new;

	private String url = "http://192.168.0.205:8080/MyApp/index.jsp";
	// private String url = "http://www.baidu.com";
	// private String url = "http://www.google.com";
	private AlertDialog.Builder builder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_internetwebview);
		initView();
	}

	@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
	private void initView() {
		webView = (WebView) findViewById(R.id.webView);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		refresh = (TextView) findViewById(R.id.refresh);
		top_title = (TextView) findViewById(R.id.top_title);
		add_new = (TextView) findViewById(R.id.add_new);

		add_new.setOnClickListener(this);
		refresh.setOnClickListener(this);

		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);

		// 打开页面，自适应屏幕：
		webSettings.setUseWideViewPort(true);// 设置此属性，可以任意比例缩放
		webSettings.setLoadWithOverviewMode(true);

		// 使页面支持缩放
		webSettings.setJavaScriptEnabled(true);
		webSettings.setBuiltInZoomControls(true);
		webSettings.setSupportZoom(true);

		// 网页中所有图片会被加载，默认为true
		webSettings.setBlockNetworkImage(true);// 把图片加载放到最后加载渲染

		webView.addJavascriptInterface(new JavascriptInterface(), "js");
		webView.loadUrl(url);

		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				System.out.println("onProgressChanged");
				System.out.println("newProgress=" + newProgress);
				progressBar.setProgress(newProgress);
				if (newProgress >= 80) {
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
				if (url.indexOf("send_nocontent_tel:") > -1) {
					Uri smsToUri = Uri.parse("smsto:10086");
					Intent mIntent = new Intent(android.content.Intent.ACTION_SENDTO, smsToUri);
					mIntent.putExtra("sms_body", "The SMS text");
					startActivity(mIntent);
				}
				if (url.indexOf("send_content_tel:") > -1) {
					Uri smsToUri = Uri.parse("smsto:");
					Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
					intent.putExtra("sms_body", "带有文本，不带号码");
					startActivity(intent);
				}
				if (url.indexOf("send_content_num_tel:") > -1) {
					Uri smsToUri = Uri.parse("smsto:10086");
					Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
					intent.putExtra("sms_body", "带有文本和号码");
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
				if (errorCode < 0) {
					view.setVisibility(View.GONE);
				}
			}
		});
	}

	public class JavascriptInterface {
		public JavascriptInterface() {
		}

		public void openDialog() {
			builder = new AlertDialog.Builder(InternetWebView.this);
			builder.setTitle("由网页打开android里的对话框");
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					Toast.makeText(InternetWebView.this, "点击的是确定", Toast.LENGTH_SHORT).show();
				}
			});
			builder.setCancelable(true);
			builder.show();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.refresh:
			webView.reload();
			break;
		case R.id.add_new:
			String aaString = "android 传给网页的数据1";
			String bbString = "android 传给网页的数据2";
			webView.loadUrl("javascript:msg2('" + aaString + "','" + bbString + "')");
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
			InternetWebView.this.finish();
		}
	}
}
