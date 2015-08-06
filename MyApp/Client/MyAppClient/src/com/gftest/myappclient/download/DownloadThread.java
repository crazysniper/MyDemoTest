package com.gftest.myappclient.download;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import com.gftest.myappclient.utils.Common;

import android.util.Log;

/**
 * 用于下载文件的线程。 该线程有被分配的工作量：block
 * 
 */
public class DownloadThread extends Thread {
	private static final String TAG = "DownloadThread";
	/**
	 * 目标文件
	 */
	private File saveFile;
	/**
	 * 文件下载路径
	 */
	private URL downUrl;
	/**
	 * 当前线程需要下载的文件大小
	 */
	private int block;

	/**
	 * 线程身份识别id
	 */
	private int threadId = -1;
	/**
	 * 之前已经下载的位置
	 */
	private int downLength;
	/**
	 * 判断当前线程是否结束
	 */
	private boolean isFinished = false;
	/**
	 * 文件下载器
	 */
	private FileDownloader2 downloader;

	/**
	 * @param downloader
	 *            下载器
	 * @param downUrl
	 *            下载的网络路径
	 * @param saveFile
	 *            保存的目标文件
	 * @param block
	 *            下载任务量
	 * @param downLength
	 *            已经下载的大小
	 * @param threadId
	 *            线程id
	 */
	public DownloadThread(FileDownloader2 downloader, URL downUrl, File saveFile, int block, int downLength, int threadId) {
		this.downUrl = downUrl;
		this.saveFile = saveFile;
		this.block = block;
		this.downloader = downloader;
		this.threadId = threadId;
		this.downLength = downLength;
	}

	@Override
	public void run() {
		if (downLength < block) {// 未下载完成
			try {
				// 使用Get方式下载
				HttpURLConnection http = Common.getHttpParams(downUrl);

				// 开始下载位置
				int startPosition = block * (threadId - 1) + downLength;
				// 结束下载位置
				int endPosition = block * threadId - 1;
				// 设置获取实体数据的范围
				http.setRequestProperty("Range", "bytes=" + startPosition + "-" + endPosition);
				// 获取文件输入流
				InputStream inStream = http.getInputStream();
				byte[] buffer = new byte[1024];
				int offset = 0;
				print("Thread " + this.threadId + " start download from position " + endPosition);

				RandomAccessFile threadfile = new RandomAccessFile(this.saveFile, "rwd");
				threadfile.seek(endPosition);

				while ((offset = inStream.read(buffer, 0, 1024)) != -1) {
					threadfile.write(buffer, 0, offset);
					// 下载量累加
					downLength += offset;
					// 更新下载数据库
					downloader.update(this.threadId, downLength);
					downloader.append(offset);
				}

				threadfile.close();
				inStream.close();
				print("Thread " + this.threadId + " download finish");
				// 设置下载完成标志位。
				this.isFinished = true;
			} catch (Exception e) {
				this.downLength = -1;
				print("Thread " + this.threadId + ":" + e);
			}
		}
	}

	/**
	 * 打印日志信息
	 * 
	 * @param msg
	 */
	private static void print(String msg) {
		Log.i(TAG, msg);
	}

	/**
	 * 下载是否完成
	 * 
	 * @return
	 */
	public boolean isFinish() {
		return isFinished;
	}

	/**
	 * 已经下载的内容大小
	 * 
	 * @return 如果返回值为-1,代表下载失败
	 */
	public long getDownLength() {
		return downLength;
	}
}
