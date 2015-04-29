package com.example.listviewdemo.utils;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;

/**
 * AsyncTask执行工具类
 * 
 * http://blog.csdn.net/pipisky2006/article/details/8535454
 * 
 * @author likebamboo
 */
public class TaskUtil {

	/**
	 * 执行异步任务
	 * <p>
	 * android 2.3 及以下使用execute()方法
	 * <p>
	 * android 3.0 及以上使用executeOnExecutor方法
	 * 
	 * @param task
	 * @param params
	 */
	@SuppressLint("NewApi")
	public static <Params, Progress, Result> void execute(AsyncTask<Params, Progress, Result> task, Params... params) {
		if (Build.VERSION.SDK_INT >= 11) {
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
		} else {
			/**
			 * execute()提交的任务，按先后顺序每次只运行一个<br>
			 * 也就是说它是按提交的次序，每次只启动一个线程执行一个任务，完成之后再执行第二个任务，也就是相当于只有一个后台线程在执行所提交的任务
			 * (Executors.newSingleThreadPool())。
			 */
			task.execute(params);
		}
	}
}
