package com.example.listviewdemo.xiala.xiala1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.listviewdemo.R;

/**
 * 首先在RefreshableView的构造函数中动态添加了刚刚定义的pull_to_refresh这个布局作为下拉头，
 * 
 * 然后在onLayout方法中将下拉头向上偏移出了屏幕<br>
 * 
 * 再给ListView注册了touch事件。<br>
 * 
 * 之后每当手指在ListView上滑动时，onTouch方法就会执行。<br>
 * 
 * 在onTouch方法中的第一行就调用了setIsAbleToPull方法来判断ListView是否滚动到了最顶部，<br>
 * 
 * 只有滚动到了最顶部才会执行后面的代码，否则就视为正常的ListView滚动，<br>
 * 
 * 不做任何处理。当ListView滚动到了最顶部时，如果手指还在向下拖动，就会改变下拉头的偏移值，<br>
 * 
 * 让下拉头显示出来，下拉的距离设定为手指移动距离的1/2，这样才会有拉力的感觉。<br>
 * 
 * 如果下拉的距离足够大，在松手的时候就会执行刷新操作，如果距离不够大，就仅仅重新隐藏下拉头。<br>
 * 
 * 
 * 具体的刷新操作会在RefreshingTask中进行，
 * 其中在doInBackground方法中回调了PullToRefreshListener接口的onRefresh方法，
 * 
 * 这也是大家在使用RefreshableView时必须要去实现的一个接口，因为具体刷新的逻辑就应该写在onRefresh方法中
 * 
 */
public class MyXiala1 extends LinearLayout implements OnTouchListener {

	/** 下拉状态 */
	private static final int STATUS_PULL_TO_REFRESH = 0;

	/** 释放立即刷新状态 */
	private static final int STATUS_RELEASE_TO_REFRESH = 1;

	/** 正在刷新状态 */
	private static final int STATUS_REFRESHING = 2;

	/** 刷新完成或未刷新状态 */
	private static final int STATUS_REFRESH_FINISHED = 3;

	/** 下来头部回滚的速度 */
	private static final int SCROLL_SPEED = -20;

	/** 一分钟的毫秒值,用于判断上次的更新时间 */
	private static final long ONE_MINTUTE = 60 * 1000;
	/** 一小时的毫秒值,用于判断上次的更新时间 */
	private static final long ONE_HOUR = 60 * ONE_MINTUTE;
	/** 一天的毫秒值,用于判断上次的更新时间 */
	private static final long ONE_DAY = 24 * ONE_HOUR;
	/** 一个月的毫秒值,用于判断上次的更新时间 */
	private static final long ONE_MONTH = 30 * ONE_DAY;
	/** 一年的毫秒值,用于判断上次的更新时间 */
	private static final long ONE_YEAR = 12 * ONE_MONTH;

	/**
	 * 当前处理什么状态，可选值有<br>
	 * STATUS_PULL_TO_REFRESH：下拉状态<br>
	 * STATUS_RELEASE_TO_REFRESH：释放立即刷新状态<br>
	 * STATUS_REFRESHING：正在刷新状态<br>
	 * STATUS_REFRESH_FINISHED： 刷新完成或未刷新状态 <br>
	 */
	private int currentStatus = STATUS_REFRESH_FINISHED;

	/**
	 * 记录上一次的状态是什么，避免进行重复操作
	 */
	private int lastStatus = currentStatus;

	/**
	 * 当前是否可以下来,只有listview滚动到头的时候才允许下拉
	 */
	private boolean ableToPull = false;
	/**
	 * 是否已加载过一次layout，这里onLayout中的初始化只需加载一次<br>
	 * flase:没有加载过，默认值<br>
	 * 
	 */
	private boolean loadOnce = false;

	/**
	 * 为了防止不同界面的下拉刷新在上次更新时间上互相有冲突，使用id来做区分
	 */
	private int mId = -1;

	/**
	 * 下拉头的高度
	 */
	private int hideHeaderHeight;
	/**
	 * 手指按下时的屏幕纵坐标
	 */
	private float yDown;
	/**
	 * 在被判定为滚动之前用户手指可以移动的最大值
	 */
	private int touchSlop;
	/**
	 * 下拉头的布局参数
	 */
	private MarginLayoutParams headerLayoutParams;

	private PullToRefreshListener pullToRefreshListener;

	private SharedPreferences sharedPreferences;
	private static final String last_update_time_key = "last_update_time_key_";

	private View headerView;
	private Context context;
	private LayoutInflater layoutInflater;
	private ListView listView;
	private ProgressBar xiala1_progressbar;
	private ImageView xiala1_arrow;
	private TextView xiala1_description, xiala1_updated_time;

	public void setPullToRefreshListener(PullToRefreshListener listener, int id) {
		this.pullToRefreshListener = listener;
		this.mId = id;
	}

	public MyXiala1(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();
	}

	private void initView() {
		layoutInflater = LayoutInflater.from(context);
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

		headerView = layoutInflater.inflate(R.layout.myxiala1_header, null, true);
		xiala1_arrow = (ImageView) headerView.findViewById(R.id.xiala1_arrow);
		xiala1_progressbar = (ProgressBar) headerView.findViewById(R.id.xiala1_progressbar);
		xiala1_description = (TextView) headerView.findViewById(R.id.xiala1_description);
		xiala1_updated_time = (TextView) headerView.findViewById(R.id.xiala1_updated_time);
		setOrientation(VERTICAL);

		touchSlop = ViewConfiguration.get(context).getScaledDoubleTapSlop();

		refreshUpdatedAtValue();
		addView(headerView, 0);
	}

	/**
	 * 进行一些关键性的初始化操作，比如：将下拉头向上偏移进行隐藏，给ListView注册touch事件。
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		System.out.println("onLayout  changed=" + changed + "__loadOnce=" + loadOnce);
		if (changed && !loadOnce) {
			hideHeaderHeight = -headerView.getHeight();
			System.out.println("头部高度=" + hideHeaderHeight);
			headerLayoutParams = (MarginLayoutParams) headerView.getLayoutParams();
			headerLayoutParams.topMargin = hideHeaderHeight;
			listView = (ListView) getChildAt(1);
			listView.setOnTouchListener(this);
			loadOnce = true;
		}
	}

	/**
	 * 当ListView被触摸时调用，其中处理了各种下拉刷新的具体逻辑。
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		setIsAbleToPull(event);
		if (ableToPull) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				yDown = event.getRawY();// 相对屏幕的坐标;而getX相对容器的坐标
				break;
			case MotionEvent.ACTION_MOVE:
				float yMove = event.getRawY();
				int distance = (int) (yMove - yDown);
				// 如果手指是下滑状态，并且下拉头是完全隐藏的，就屏蔽下拉事件
				if (distance <= 0 && headerLayoutParams.topMargin <= hideHeaderHeight) {
					return false;
				}
				if (distance < touchSlop) {
					return false;
				}
				if (currentStatus != STATUS_REFRESHING) {
					if (headerLayoutParams.topMargin > 0) {
						currentStatus = STATUS_RELEASE_TO_REFRESH;
					} else {
						currentStatus = STATUS_PULL_TO_REFRESH;
					}
					// 通过偏移下拉头的topMargin值，来实现下拉效果
					headerLayoutParams.topMargin = (distance / 2) + hideHeaderHeight;
					headerView.setLayoutParams(headerLayoutParams);
				}
				break;
			case MotionEvent.ACTION_UP:
			default:
				if (currentStatus == STATUS_RELEASE_TO_REFRESH) {
					// 松手时如果是释放立即刷新状态，就去调用正在刷新的任务
					new RefreshingTask().execute();
				} else if (currentStatus == STATUS_PULL_TO_REFRESH) {
					// 松手时如果是下拉状态，就去调用隐藏下拉头的任务
					new HideHeaderTask().execute();
				}
				break;
			}
			// 时刻记得更新下来头的信息
			if (currentStatus == STATUS_PULL_TO_REFRESH || currentStatus == STATUS_RELEASE_TO_REFRESH) {
				updateHeaderView();
				// 当前正处在下拉或者释放状态，要让listview失去焦点，否则被点击的那一项会一直处于选中状态
				listView.setPressed(false);
				listView.setFocusable(false);
				listView.setFocusableInTouchMode(false);
				lastStatus = currentStatus;
				// 当前正处于下拉或释放状态，通过返回true屏蔽listview的滚动事件
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据当前ListView的滚动状态来设定 {@link #ableToPull}
	 * 的值，每次都需要在onTouch中第一个执行，这样可以判断出当前应该是滚动ListView，还是应该进行下拉。
	 * 
	 * @param event
	 */
	private void setIsAbleToPull(MotionEvent event) {
		View firstChild = listView.getChildAt(0);
		if (firstChild != null) {
			int firstVisiblePos = listView.getFirstVisiblePosition();
			if (firstVisiblePos == 0 && firstChild.getTop() == 0) {
				if (!ableToPull) {
					yDown = event.getRawY();
				}
				// 如果首个元素的上边缘，距离父布局值为0，就说明ListView滚动到了最顶部，此时应该允许下拉刷新
				ableToPull = true;
			} else {
				if (headerLayoutParams.topMargin != hideHeaderHeight) {
					headerLayoutParams.topMargin = hideHeaderHeight;
					headerView.setLayoutParams(headerLayoutParams);
				}
				ableToPull = false;
			}

		} else {
			// 如果listview中没有元素，也允许下来刷新
			ableToPull = true;
		}
	}

	/**
	 * 当所有的刷新逻辑完成后，记录调用一下，否则listview将一直处于正在刷新状态
	 */
	public void finishRefreshing() {
		currentStatus = STATUS_REFRESH_FINISHED;
		sharedPreferences.edit().putLong(last_update_time_key + mId, System.currentTimeMillis()).commit();
		new HideHeaderTask().execute();
	}

	/**
	 * 更新下拉头中的信息。
	 */
	private void updateHeaderView() {
		if (lastStatus == currentStatus) {
			if (currentStatus == STATUS_PULL_TO_REFRESH) {
				xiala1_description.setText(getResources().getString(R.string.pull_to_refresh));
				xiala1_arrow.setVisibility(View.VISIBLE);
				xiala1_progressbar.setVisibility(View.GONE);
				rotateArrow();
			} else if (currentStatus == STATUS_RELEASE_TO_REFRESH) {
				xiala1_description.setText(getResources().getString(R.string.release_to_refresh));
				xiala1_arrow.setVisibility(View.VISIBLE);
				xiala1_progressbar.setVisibility(View.GONE);
				rotateArrow();
			} else if (currentStatus == STATUS_REFRESHING) {
				xiala1_description.setText(getResources().getString(R.string.refreshing));
				xiala1_progressbar.setVisibility(View.VISIBLE);
				xiala1_arrow.clearAnimation();
				xiala1_arrow.setVisibility(View.GONE);
			}
			refreshUpdatedAtValue();
		}
	}

	/**
	 * 根据当前的状态来旋转箭头
	 */
	private void rotateArrow() {
		float pivotX = xiala1_arrow.getWidth() / 2f;
		float pivotY = xiala1_arrow.getHeight() / 2f;
		float fromDegrees = 0f;
		float toDegrees = 0f;
		if (currentStatus == STATUS_PULL_TO_REFRESH) {
			fromDegrees = 180f;
			toDegrees = 360f;
		} else if (currentStatus == STATUS_RELEASE_TO_REFRESH) {
			fromDegrees = 0f;
			toDegrees = 180f;
		}
		RotateAnimation rotateAnimation = new RotateAnimation(fromDegrees, toDegrees, pivotX, pivotY);
		rotateAnimation.setDuration(100);
		rotateAnimation.setFillAfter(true);
		rotateAnimation.setRepeatCount(Animation.INFINITE);// 无限循环
		xiala1_arrow.startAnimation(rotateAnimation);
	}

	/**
	 * 文字描述
	 * 
	 * @param lastUpdateTime
	 */
	public void refreshUpdatedAtValue() {
		System.out.println("refreshUpdatedAtValue");
		long currentTime = System.currentTimeMillis();
		long lastUpdateTime = sharedPreferences.getLong(last_update_time_key + mId, currentTime);
		long timePassed = currentTime - lastUpdateTime;
		String updateTimeValue = "";
		System.out.println("timePassed=" + timePassed);
		if (timePassed < ONE_MINTUTE) {
			updateTimeValue = getResources().getString(R.string.updated_just_now);
		} else {
			if (timePassed < ONE_HOUR) {
				updateTimeValue = timePassed / ONE_MINTUTE + "分钟";
			} else if (timePassed < ONE_DAY) {
				updateTimeValue = timePassed / ONE_HOUR + "小时";
			} else if (timePassed < ONE_MONTH) {
				updateTimeValue = timePassed / ONE_DAY + "天";
			} else if (timePassed < ONE_YEAR) {
				updateTimeValue = timePassed / ONE_MONTH + "个月";
			} else {
				updateTimeValue = timePassed / ONE_YEAR + "年";
			}
			updateTimeValue = String.format(getResources().getString(R.string.last_updated_time), updateTimeValue);
		}
		xiala1_updated_time.setText(updateTimeValue);
	}

	/**
	 * 正在刷新的任务，在此任务中会去回调注册进来的下拉刷新监听器。
	 * 
	 * @author Gao
	 * 
	 */
	class RefreshingTask extends AsyncTask<Void, Integer, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			int topMargin = headerLayoutParams.topMargin;
			while (true) {
				topMargin = topMargin + SCROLL_SPEED;
				if (topMargin <= 0) {
					topMargin = 0;
					break;
				}
				publishProgress(topMargin);
				sleep(10);
			}
			currentStatus = STATUS_REFRESHING;
			publishProgress(0);
			if (pullToRefreshListener != null) {
				pullToRefreshListener.onRefresh();
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... topMargin) {
			// super.onProgressUpdate(values);
			updateHeaderView();
			headerLayoutParams.topMargin = topMargin[0];
			headerView.setLayoutParams(headerLayoutParams);
		}

	}

	/**
	 * 隐藏下拉头的任务，当未进行下拉刷新或下拉刷新完成后，此任务将会使下拉头重新隐藏。
	 * 
	 * @author Gao
	 * 
	 */
	class HideHeaderTask extends AsyncTask<Void, Integer, Integer> {

		@Override
		protected Integer doInBackground(Void... params) {
			int topMargin = headerLayoutParams.topMargin;
			while (true) {
				topMargin = topMargin + SCROLL_SPEED;
				if (topMargin <= hideHeaderHeight) {
					topMargin = hideHeaderHeight;
					break;
				}
				publishProgress(topMargin);
				sleep(10);
			}
			return topMargin;
		}

		@Override
		protected void onProgressUpdate(Integer... topMargin) {
			// super.onProgressUpdate(values);
			headerLayoutParams.topMargin = topMargin[0];
			headerView.setLayoutParams(headerLayoutParams);
		}

		@Override
		protected void onPostExecute(Integer topMargin) {
			// super.onPostExecute(result);
			currentStatus = STATUS_REFRESH_FINISHED;
			headerLayoutParams.topMargin = topMargin;
			headerView.setLayoutParams(headerLayoutParams);
		}

	}

	/**
	 * 使当前线程睡眠指定的毫秒数。
	 * 
	 * @param time
	 *            指定当前线程睡眠多久，以毫秒为单位
	 */
	private void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 下拉刷新的监听器，使用下拉刷新的地方应该注册此监听器来获取刷新回调。
	 * 
	 * @author Gao
	 * 
	 */
	public interface PullToRefreshListener {
		/**
		 * 刷新时会去回调此方法，在方法内编写具体的刷新逻辑。注意此方法是在子线程中调用的， 你可以不必另开线程来进行耗时操作。
		 */
		public void onRefresh();

		public void onLoadMore();
	}

}
