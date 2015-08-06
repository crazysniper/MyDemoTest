package com.gftest.myappclient.ui;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.gftest.myappclient.BaseActivity;
import com.gftest.myappclient.R;

public class MyAnim extends BaseActivity implements OnClickListener {

	private ImageView iv1, iv2, img;
	/** 移动 */
	private Button left_trans, right_trans, up_trans, down_trans;
	/** 透明度 */
	private Button alpha_to0, alpha_to1;
	/** 渐变尺寸收缩 */
	private Button scaleBiger, scaleSmaller;
	/** 旋转 */
	private Button rotate0, rotate1;

	private Button animSet;
	private ImageView iv_set;

	private Animation animation0, animation1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myanim);
		initView();
	}

	private void initView() {
		iv1 = (ImageView) findViewById(R.id.iv1);
		iv2 = (ImageView) findViewById(R.id.iv2);
		img = (ImageView) findViewById(R.id.img);
		iv_set = (ImageView) findViewById(R.id.iv_set);
		left_trans = (Button) findViewById(R.id.left_trans);
		right_trans = (Button) findViewById(R.id.right_trans);
		up_trans = (Button) findViewById(R.id.up_trans);
		down_trans = (Button) findViewById(R.id.down_trans);
		alpha_to0 = (Button) findViewById(R.id.alpha_to0);
		alpha_to1 = (Button) findViewById(R.id.alpha_to1);
		scaleBiger = (Button) findViewById(R.id.scaleBiger);
		scaleSmaller = (Button) findViewById(R.id.scaleSmaller);
		rotate0 = (Button) findViewById(R.id.rotate0);
		rotate1 = (Button) findViewById(R.id.rotate1);
		animSet = (Button) findViewById(R.id.animSet);

		iv1.setOnClickListener(this);
		iv2.setOnClickListener(this);
		left_trans.setOnClickListener(this);
		right_trans.setOnClickListener(this);
		up_trans.setOnClickListener(this);
		down_trans.setOnClickListener(this);
		alpha_to0.setOnClickListener(this);
		alpha_to1.setOnClickListener(this);
		scaleBiger.setOnClickListener(this);
		scaleSmaller.setOnClickListener(this);
		rotate0.setOnClickListener(this);
		rotate1.setOnClickListener(this);
		animSet.setOnClickListener(this);

		animation0 = AnimationUtils.loadAnimation(MyAnim.this, R.anim.animset0);
		animation0.setFillAfter(true);
		animation1 = AnimationUtils.loadAnimation(MyAnim.this, R.anim.animset1);
		animation1.setFillAfter(true);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv1:
			DisplayMetrics displayMetrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
			System.out.println("动画开始");
			System.out.println("iv1.getLeft()=" + iv1.getLeft());// 15
			System.out.println("iv1.getTop()=" + iv1.getTop());// 15
			System.out.println("iv1.getX()=" + iv1.getX());// 15.0
			System.out.println("iv1.getY()=" + iv1.getY());// 15.0
			System.out.println("displayMetrics.widthPixels=" + displayMetrics.widthPixels);// 540
			System.out.println("displayMetrics.heightPixels=" + displayMetrics.heightPixels);// 960
			System.out.println("displayMetrics.xdpi=" + displayMetrics.xdpi);// 258.79245
			System.out.println("displayMetrics.ydpi=" + displayMetrics.ydpi);// 256.67368
			// 这个是以自身为坐标原点的
			// TranslateAnimation translateAnimation = new
			// TranslateAnimation(displayMetrics.widthPixels - 400,
			// displayMetrics.widthPixels - 300, (displayMetrics.heightPixels) /
			// 3, (displayMetrics.heightPixels) / 2);

			TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 1.0f);
			translateAnimation.setDuration(500);
			// iv1.setAnimation(translateAnimation);
			translateAnimation.setFillAfter(false);
			// translateAnimation.startNow();
			iv1.startAnimation(translateAnimation);

			System.out.println("动画之后的");
			System.out.println("iv1.getLeft()=" + iv1.getLeft());// 15
			System.out.println("iv1.getTop()=" + iv1.getTop());// 15
			System.out.println("iv1.getX()=" + iv1.getX());// 15.0
			System.out.println("iv1.getY()=" + iv1.getY());// 15.0
			break;
		case R.id.iv2:
			System.out.println("aaaaaaaaa");
			TranslateAnimation translateAnimation2 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1.0f);
			translateAnimation2.setDuration(600);
			translateAnimation2.setFillAfter(true);
			translateAnimation2.setAnimationListener(new AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {
					System.out.println("动画开始");
				}

				@Override
				public void onAnimationRepeat(Animation animation) {

				}

				@Override
				public void onAnimationEnd(Animation animation) {
					// iv2.setVisibility(View.GONE);
					iv2.clearAnimation();
				}
			});

			iv2.startAnimation(translateAnimation2);
			iv2.getX();

			iv2.getY();
			break;
		case R.id.left_trans:// 左移
			Animation leftAnimation = AnimationUtils.loadAnimation(MyAnim.this, R.anim.trans_left);
			img.startAnimation(leftAnimation);
			break;
		case R.id.right_trans:// 右移
			Animation rightAnimation = AnimationUtils.loadAnimation(MyAnim.this, R.anim.trans_right);
			img.startAnimation(rightAnimation);
			break;
		case R.id.up_trans:// 上移
			Animation upAnimation = AnimationUtils.loadAnimation(MyAnim.this, R.anim.trans_up);
			img.startAnimation(upAnimation);
			break;
		case R.id.down_trans:// 下移
			Animation downAnimation = AnimationUtils.loadAnimation(MyAnim.this, R.anim.trans_down);
			img.startAnimation(downAnimation);
			break;
		case R.id.alpha_to0:// 不透明到透明
			Animation alphaAnimation0 = AnimationUtils.loadAnimation(MyAnim.this, R.anim.alpha_to0);
			img.startAnimation(alphaAnimation0);
			break;
		case R.id.alpha_to1:// 透明到不透明
			Animation alphaAnimation1 = AnimationUtils.loadAnimation(MyAnim.this, R.anim.alpha_to1);
			img.startAnimation(alphaAnimation1);
			break;
		case R.id.scaleBiger:// 变大
			Animation scaleBig = AnimationUtils.loadAnimation(MyAnim.this, R.anim.scale_bigger);
			img.startAnimation(scaleBig);
			break;
		case R.id.scaleSmaller:// 变小
			Animation scaleSmaller = AnimationUtils.loadAnimation(MyAnim.this, R.anim.scale_smaller);
			img.startAnimation(scaleSmaller);
			break;
		case R.id.rotate0:// 顺时针选择90度
			Animation rotateAnimation0 = AnimationUtils.loadAnimation(MyAnim.this, R.anim.rotate_0);
			img.startAnimation(rotateAnimation0);
			break;
		case R.id.rotate1:// 逆时针选择90度
			Animation rotateAnimation1 = AnimationUtils.loadAnimation(MyAnim.this, R.anim.rotate_1);
			img.startAnimation(rotateAnimation1);
			break;
		case R.id.animSet:
			animation0.setAnimationListener(new AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}

				@Override
				public void onAnimationEnd(Animation animation) {
					iv_set.startAnimation(animation1);
				}
			});
			animation1.setAnimationListener(new AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}

				@Override
				public void onAnimationEnd(Animation animation) {
					iv_set.startAnimation(animation0);
				}
			});
			iv_set.startAnimation(animation0);
			break;
		default:
			break;
		}
	}

}
