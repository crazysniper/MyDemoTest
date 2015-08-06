package com.gftest.myappclient.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.gftest.myappclient.BaseActivity;
import com.gftest.myappclient.R;
import com.gftest.myappclient.adapter.LoginAccountAdapter;
import com.gftest.myappclient.constants.Constants;
import com.gftest.myappclient.db.AccountDBHelper;
import com.gftest.myappclient.login.LoginInfoUtils;
import com.gftest.myappclient.model.DBLoginEntity;

public class Login extends BaseActivity implements Callback, OnClickListener {

	private PopupWindow mPopupWindow;// 浮动窗口
	private LoginAccountAdapter adapter;// 适配器
	private List<String> usernameList = new ArrayList<String>();// 保存用户名
	private List<String> passwordList = new ArrayList<String>();// 保存密码
	private List<DBLoginEntity> loginEntityList = new ArrayList<DBLoginEntity>();// 保存登陆信息数据对象
	private LinearLayout parent;// 浮动窗口依附布局
	private int pwidth;// 浮动宽口的宽度

	private EditText login_edit_userName;// 账号输入框
	private EditText login_edit_password;// 密码输入框
	private Button down_but;
	private Button login_but;
	private Handler mHandler;// 处理消息更新UI
	private boolean init_flag = false;// 浮动窗口显示标示符

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		this.mHandler = new Handler(this);
	}

	/**
	 * 在此方法中初始化可以获得输入框的宽度，以便于创建同样宽的浮动窗口<br>
	 * 当Activity的当前Window获得或失去焦点时会被回调此方法。当回调了这个方法时表示Activity是完全对用户可见的(只是可见，
	 * 还一片黑呼呼的，有待draw..)。当对话框弹起/消失及Activity新创建及回退等都会调用此方法。
	 * 
	 * 
	 * 如果需要获取某个View的宽高，可以在onWindowFocusChanged()处直接获取即可，
	 * 这比对某个view设置onGlobalLayoutListener的方式来获取方便了许此。
	 * 对于需要读取本地文件记录来判断是否是第一次打开界面去提示文本图片的，也可以在此方法中去读取然后再显示在ui上
	 * 
	 * onResume()方法更多的是指Activity进入了可见的状态，但只是状态，并不是真正的界面构建完成了。
	 */
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		while (!init_flag) {
			// 初始化UI
			initWedget();
			// 初始化浮动窗口
			initPopuWindow();
			init_flag = true;
		}
	}

	/**
	 * 初始化UI控件
	 */
	private void initWedget() {
		// 浮动窗口依附的布局
		this.parent = (LinearLayout) this.findViewById(R.id.username_layout);
		this.login_edit_userName = (EditText) this.findViewById(R.id.login_edit_userName);
		this.login_edit_password = (EditText) this.findViewById(R.id.login_edit_password);
		this.down_but = (Button) this.findViewById(R.id.down_but);
		login_but = (Button) this.findViewById(R.id.login_but_landing);
		// 获取登陆数据
		getData();
		if (usernameList.size() > 0) {
			login_edit_userName.setText(usernameList.get(0));
			login_edit_password.setText(passwordList.get(0));
		}

		// 获取地址输入框的宽度，用于创建浮动窗口的宽度
		pwidth = parent.getWidth();

		down_but.setOnClickListener(this);
		login_but.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.down_but:// 浮动地址下拉框按钮事件
			System.out.println("11111111");
			if (init_flag) {// 显示浮动窗口
				System.out.println("2222");
				mPopupWindow.showAsDropDown(parent, 0, -3);
			}
			break;
		case R.id.login_but_landing:// 登录按钮事件
			// 登录成功将数据保存到SQLite中
			ContentValues values = new ContentValues();
			String userName = login_edit_userName.getText().toString();
			String passWord = login_edit_password.getText().toString();
			values.put(Constants.LOGIN_ACCOUNT_DB_KYE2, userName);
			values.put(Constants.LOGIN_ACCOUNT_DB_KYE3, passWord);
			AccountDBHelper dbHelper = new AccountDBHelper(Login.this);
			// 第一次登陆直接保存
			if (usernameList.size() == 0) {
				dbHelper.insert(values, Constants.LOGIN_ACCOUNT_DB_NAME);
			} else {
				boolean flag = true;// 标示符是否要插入
				// 判断当前用户名是否在数据库存在
				LoginInfoUtils mHandler = LoginInfoUtils.create(Login.this);
				List<DBLoginEntity> info = mHandler.getLoginInfoObj();
				String username = "";
				for (DBLoginEntity dbLoginEntity : info) {
					username = dbLoginEntity.getUserName();
					if (userName.equals(username)) {
						flag = false;
						break;
					}
				}

				if (flag) {
					dbHelper.insert(values, Constants.LOGIN_ACCOUNT_DB_NAME);
				}
			}
			Toast.makeText(Login.this, "登陆成功", Toast.LENGTH_LONG).show();
			break;
		}
	}

	/**
	 * 初始化浮动窗口
	 */
	public void initPopuWindow() {
		// 浮动窗口的布局
		View loginwindow = (View) this.getLayoutInflater().inflate(R.layout.logined_account, null);
		ListView listView = (ListView) loginwindow.findViewById(R.id.list);
		// 初始化适配器
		adapter = new LoginAccountAdapter(Login.this, usernameList, mHandler);
		listView.setAdapter(adapter);
		// 定义一个浮动窗口，并设置
		this.mPopupWindow = new PopupWindow(loginwindow, pwidth, LayoutParams.WRAP_CONTENT, true);
		this.mPopupWindow.setOutsideTouchable(true);
		this.mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
	}

	/**
	 * 获取登录用户名数据
	 */
	private void getData() {
		// 获取数据对象
		LoginInfoUtils mHandler = LoginInfoUtils.create(Login.this);
		loginEntityList = mHandler.getLoginInfoObj();
		for (DBLoginEntity dbLoginEntity : loginEntityList) {
			usernameList.add(dbLoginEntity.getUserName());
			passwordList.add(dbLoginEntity.getPassword());
		}
	}

	/**
	 * 处理浮动窗口传回来的数据
	 * 
	 * @param msg
	 * @return
	 */
	@Override
	public boolean handleMessage(Message msg) {
		Bundle bundle = msg.getData();
		AccountDBHelper dbHelper = new AccountDBHelper(Login.this);
		switch (msg.what) {
		case 1:// 根据返回的id，将数据显示在输入框中
			int sel_id = bundle.getInt("sel_id");
			int _id = loginEntityList.get(sel_id).getID();
			String UserName = loginEntityList.get(sel_id).getUserName();
			String PassWord = loginEntityList.get(sel_id).getPassword();
			// 先删除再插入
			dbHelper.del(_id, Constants.LOGIN_ACCOUNT_DB_NAME);
			login_edit_userName.setText(UserName);
			login_edit_password.setText(PassWord);
			mPopupWindow.dismiss();
			break;
		case 2:// 根据返回的ID，删除数据
			int del_id = bundle.getInt("del_id");
			int id = loginEntityList.get(del_id).getID();
			dbHelper.del(id, Constants.LOGIN_ACCOUNT_DB_NAME);
			usernameList.remove(del_id);
			adapter.notifyDataSetChanged();
			break;
		}
		return false;
	}

}
