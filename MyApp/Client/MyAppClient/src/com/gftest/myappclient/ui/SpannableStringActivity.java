package com.gftest.myappclient.ui;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gftest.myappclient.BaseActivity;
import com.gftest.myappclient.R;

public class SpannableStringActivity extends BaseActivity implements OnClickListener {
	TextView mTextView = null;
	SpannableString msp = null;
	private EditText tv;
	private Button underline_btn;
	private Button strike_btn;
	private Button style_btn;
	private Button font_btn;
	private Button color_btn1;
	private Button color_btn2;
	private Button url_btn;
	private Button image_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spannablestring);
		initView();
	}

	private void initView() {
		mTextView = (TextView) findViewById(R.id.myTextView);
		tv = (EditText) this.findViewById(R.id.tv);
		underline_btn = (Button) this.findViewById(R.id.underline_btn);
		strike_btn = (Button) this.findViewById(R.id.strike_btn);
		style_btn = (Button) this.findViewById(R.id.style_btn);
		font_btn = (Button) this.findViewById(R.id.font_btn);
		color_btn1 = (Button) this.findViewById(R.id.color_btn1);
		color_btn2 = (Button) this.findViewById(R.id.color_btn2);
		url_btn = (Button) this.findViewById(R.id.url_btn);
		image_btn = (Button) this.findViewById(R.id.image_btn);

		underline_btn.setOnClickListener(this);
		strike_btn.setOnClickListener(this);
		style_btn.setOnClickListener(this);
		font_btn.setOnClickListener(this);
		color_btn1.setOnClickListener(this);
		color_btn2.setOnClickListener(this);
		url_btn.setOnClickListener(this);
		image_btn.setOnClickListener(this);

		// 创建一个 SpannableString对象
		msp = new SpannableString("字体测试字体大小一半两倍前景色背景色正常粗体斜体粗斜体下划线删除线x1x2电话邮件网站短信彩信地图X轴综合/bot");

		// 设置字体(default,default-bold,monospace,serif,sans-serif)
		msp.setSpan(new TypefaceSpan("monospace"), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);// Spanned.SPAN_EXCLUSIVE_EXCLUSIVE(前后都不包括)
		msp.setSpan(new TypefaceSpan("serif"), 2, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		// 设置字体大小（绝对值,单位：像素）
		msp.setSpan(new AbsoluteSizeSpan(20), 4, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		msp.setSpan(new AbsoluteSizeSpan(20, true), 6, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 第二个参数boolean
																								// dip，如果为true，表示前面的字体大小单位为dip，否则为像素，同上。

		// 设置字体大小（相对值,单位：像素） 参数表示为默认字体大小的多少倍
		msp.setSpan(new RelativeSizeSpan(0.5f), 8, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 0.5f表示默认字体大小的一半
		msp.setSpan(new RelativeSizeSpan(2.0f), 10, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 2.0f表示默认字体大小的两倍

		// 设置字体前景色
		msp.setSpan(new ForegroundColorSpan(Color.MAGENTA), 12, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为洋红色

		// 设置字体背景色
		msp.setSpan(new BackgroundColorSpan(Color.CYAN), 15, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置背景色为青色

		// 设置字体样式正常，粗体，斜体，粗斜体
		msp.setSpan(new StyleSpan(android.graphics.Typeface.NORMAL), 18, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 正常
		msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 20, 22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 粗体
		msp.setSpan(new StyleSpan(android.graphics.Typeface.ITALIC), 22, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 斜体
		msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC), 24, 27, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 粗斜体

		// 设置下划线
		msp.setSpan(new UnderlineSpan(), 27, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		// 设置删除线
		msp.setSpan(new StrikethroughSpan(), 30, 33, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		// 设置上下标
		msp.setSpan(new SubscriptSpan(), 34, 35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 下标
		msp.setSpan(new SuperscriptSpan(), 36, 37, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 上标

		// 超级链接（需要添加setMovementMethod方法附加响应）
		msp.setSpan(new URLSpan("tel:4155551212"), 37, 39, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 电话
		msp.setSpan(new URLSpan("mailto:webmaster@google.com"), 39, 41, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 邮件
		msp.setSpan(new URLSpan("http://www.baidu.com"), 41, 43, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 网络
		msp.setSpan(new URLSpan("sms:4155551212"), 43, 45, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 短信
																								// 使用sms:或者smsto:
		msp.setSpan(new URLSpan("mms:4155551212"), 45, 47, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 彩信
																								// 使用mms:或者mmsto:
		msp.setSpan(new URLSpan("geo:38.899533,-77.036476"), 47, 49, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 地图

		// 设置字体大小（相对值,单位：像素） 参数表示为默认字体宽度的多少倍
		msp.setSpan(new ScaleXSpan(2.0f), 49, 51, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 2.0f表示默认字体宽度的两倍，即X轴方向放大为默认字体的两倍，而高度不变

		// 设置字体（依次包括字体名称，字体大小，字体样式，字体颜色，链接颜色）
		// ColorStateList csllink = null;
		// ColorStateList csl = null;
		// XmlResourceParser xppcolor = getResources().getXml(R.color1.color1);
		// try {
		// csl = ColorStateList.createFromXml(getResources(), xppcolor);
		// } catch (XmlPullParserException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		//
		// XmlResourceParser xpplinkcolor =
		// getResources().getXml(R.xml.linkcolor);
		// try {
		// csllink = ColorStateList.createFromXml(getResources(), xpplinkcolor);
		// } catch (XmlPullParserException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// msp.setSpan(new TextAppearanceSpan("monospace",
		// android.graphics.Typeface.BOLD_ITALIC, 30, csl, csllink), 51, 53,
		// Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		// 设置项目符号
		msp.setSpan(new BulletSpan(android.text.style.BulletSpan.STANDARD_GAP_WIDTH, Color.GREEN), 0, msp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 第一个参数表示项目符号占用的宽度，第二个参数为项目符号的颜色

		// 设置图片
		Drawable drawable = getResources().getDrawable(R.drawable.ic_launcher);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		msp.setSpan(new ImageSpan(drawable), 53, 57, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		mTextView.setText(msp);
		mTextView.setMovementMethod(LinkMovementMethod.getInstance());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.image_btn:// 图片
			addImageSpan();
			break;
		case R.id.url_btn:// 超链接
			addUrlSpan();
			break;
		case R.id.color_btn1:// 文字颜色
			addForeColorSpan();
			break;
		case R.id.color_btn2:// 文字背景颜色
			addBackColorSpan();
			break;
		case R.id.font_btn:// 字体大小
			addFontSpan();
			break;
		case R.id.style_btn:// 粗体，斜体
			addStyleSpan();
			break;
		case R.id.strike_btn:// 删除线
			addStrikeSpan();
			break;
		case R.id.underline_btn:// 下划线
			addUnderLineSpan();
			break;
		}
	}

	/**
	 * 图片
	 */
	private void addImageSpan() {
		SpannableString spanString = new SpannableString(" ");
		Drawable d = getResources().getDrawable(R.drawable.ic_launcher);// moto
																		// xt910载入的drawable-hdpi下的图片
		System.out.println("d.getIntrinsicWidth()=" + d.getIntrinsicWidth());
		System.out.println("d.getIntrinsicHeight()=" + d.getIntrinsicHeight());
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		System.out.println("bitmap.getWidth()=" + bitmap.getWidth());
		System.out.println("bitmap.getHeight()=" + bitmap.getHeight());

		d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
		ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
		spanString.setSpan(span, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		tv.append(spanString);
	}

	/**
	 * 超链接
	 */
	private void addUrlSpan() {
		SpannableString spanString = new SpannableString("超链接");
		URLSpan span = new URLSpan("tel:0123456789");
		spanString.setSpan(span, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		tv.append(spanString);
	}

	/**
	 * 文字颜色
	 */
	private void addForeColorSpan() {
		SpannableString spanString = new SpannableString("颜色1");
		ForegroundColorSpan span = new ForegroundColorSpan(Color.GREEN);
		spanString.setSpan(span, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		tv.append(spanString);
	}

	/**
	 * 文字背景颜色
	 */
	private void addBackColorSpan() {
		SpannableString spanString = new SpannableString("颜色2");
		BackgroundColorSpan span = new BackgroundColorSpan(Color.YELLOW);
		spanString.setSpan(span, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		tv.append(spanString);
	}

	/**
	 * 字体大小
	 */
	private void addFontSpan() {
		SpannableString spanString = new SpannableString("36号字体");
		AbsoluteSizeSpan span = new AbsoluteSizeSpan(36);
		spanString.setSpan(span, 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		tv.append(spanString);
	}

	/**
	 * 粗体，斜体
	 */
	private void addStyleSpan() {
		SpannableString spanString = new SpannableString("BIBI");
		StyleSpan span = new StyleSpan(Typeface.BOLD_ITALIC);
		spanString.setSpan(span, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		tv.append(spanString);
		SpannableString spanString2 = new SpannableString("加粗");
		StyleSpan span2 = new StyleSpan(Typeface.BOLD_ITALIC);
		spanString.setSpan(span2, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		tv.append(spanString2);
	}

	/**
	 * 删除线
	 */
	private void addStrikeSpan() {
		SpannableString spanString = new SpannableString("删除线");
		StrikethroughSpan span = new StrikethroughSpan();
		spanString.setSpan(span, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		tv.append(spanString);
	}

	/**
	 * 下划线
	 */
	private void addUnderLineSpan() {
		SpannableString spanString = new SpannableString("下划线");
		UnderlineSpan span = new UnderlineSpan();
		spanString.setSpan(span, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		tv.append(spanString);
	}

}
