package com.gftest.myappclient.download;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gftest.myappclient.db.DBTools;
import com.gftest.myappclient.utils.Common;

import android.content.Context;
import android.util.Log;

/**
 * 文件下载
 */
public class FileDownloader2 {
	private static final String TAG = "FileDownloader";
	private Context context;
	/**
	 * 操作数据库的类
	 */
	private DBTools dbTools;

	/**
	 * 已下载文件长度
	 */
	private int downloadSize = 0;

	/**
	 * 原始文件长度
	 */
	private int fileSize = 0;

	/**
	 * 线程数
	 */
	private DownloadThread[] threadArr;

	/**
	 * 本地保存文件
	 */
	private File saveFile;

	/**
	 * 默认下载线程数量
	 */
	private static final int defaultThreadSize = 3;

	/**
	 * 缺省文件下载存放位置
	 */
	private static String defaultDestPath = "";

	/**
	 * 用于缓存各个线程下载的长度
	 * <p>
	 * Key:对应线程id;Value:对应线程已经下载的长度
	 * </p>
	 */
	private Map<Integer, Integer> cacheDownMap = new ConcurrentHashMap<Integer, Integer>();

	/**
	 * 每条线程应该下载的长度
	 */
	private int block;

	/**
	 * 文件下载路径
	 */
	private String downloadUrl;

	/**
	 * 获取线程数
	 */
	public int getThreadSize() {
		return threadArr.length;
	}

	/**
	 * 获取文件大小
	 * 
	 * @return 需要下载文件的总长度
	 */
	public int getFileSize() {
		return fileSize;
	}

	/**
	 * 累加文件已下载的大小
	 * 
	 * @param size
	 *            此刻下载量
	 */
	protected synchronized void append(int size) {
		downloadSize += size;
	}

	/**
	 * 更新指定线程最后下载的位置
	 * 
	 * @param threadId
	 *            线程id
	 * @param pos
	 *            最后下载的位置
	 */
	protected synchronized void update(int threadId, int pos) {
		this.cacheDownMap.put(threadId, pos);
		this.dbTools.update(this.downloadUrl, this.cacheDownMap);
	}

	/**
	 * 构建文件下载器
	 * 
	 * @param downloadUrl
	 *            下载路径
	 * @param fileSaveDir
	 *            文件保存目录
	 * @param threadNum
	 *            下载线程数
	 */
	public FileDownloader2(Context context, String downloadUrl, String destPath, int threadNum) {
		this.context = context;
		this.downloadUrl = downloadUrl;
		dbTools = new DBTools(this.context);
		checkPath(destPath);
		this.threadArr = new DownloadThread[threadNum];

		setRequest(downloadUrl, destPath);
	}

	/**
	 * 检查下载路径是否存在，不存在则创建。。
	 * 
	 * @param destPath
	 * @return 是否存在该目录
	 */
	public boolean checkPath(String destPath) {

		File file = new File(destPath);

		if (!file.exists()) {

			boolean bol = file.mkdirs();

			return bol;
		}
		return true;
	}

	/**
	 * @param context
	 * @param downloadUrl
	 */
	public FileDownloader2(Context context, String downloadUrl) {

		this.context = context;
		this.downloadUrl = downloadUrl;
		this.threadArr = new DownloadThread[defaultThreadSize];
		this.dbTools = new DBTools(this.context);

		dbTools = new DBTools(this.context);
		checkPath(defaultDestPath);
		this.threadArr = new DownloadThread[defaultThreadSize];
		setRequest(downloadUrl, defaultDestPath);
	}

	/**
	 * 封装请求
	 */
	public void setRequest(String downUrl, String destPath) {

		URL url = null;
		try {
			url = new URL(downUrl);

			HttpURLConnection conn = Common.getHttpParams(url);
			conn.connect();
			// 打印头部信息
			printResponseHeader(conn);

			// 200==》成功连接...
			if (conn.getResponseCode() == 200) {
				// 获取文件总大小
				this.fileSize = conn.getContentLength();
				if (this.fileSize <= 0)
					throw new RuntimeException("该文件很可能不存在...");

				// 获取当前需要下载文件名称
				String filename = getFileName(conn);
				// 目标保存文件
				this.saveFile = new File(destPath, filename);
				// 获取当前路径下载记录包括已经下载数量。
				Map<Integer, Integer> logdata = dbTools.getData(downUrl);
				// 如果存在下载记录把各条线程已经下载的数据长度放入缓存变量中
				if (logdata.size() > 0) {
					for (Map.Entry<Integer, Integer> entry : logdata.entrySet())
						cacheDownMap.put(entry.getKey(), entry.getValue());
				}
				// 下面计算所有线程已经下载的数据长度
				if (this.cacheDownMap.size() == this.threadArr.length) {
					for (int i = 0; i < this.threadArr.length; i++) {
						this.downloadSize += this.cacheDownMap.get(i + 1);
					}

					print("已经下载数据的长度" + this.downloadSize);
				}

				// 计算分配给每条线程下载的数据长度
				this.block = (this.fileSize % this.threadArr.length) == 0 ? this.fileSize / this.threadArr.length : this.fileSize / this.threadArr.length + 1;
			} else {
				throw new RuntimeException("无响应......");
			}
		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * 获取文件名
	 * 
	 * @param conn
	 *            本次http连接
	 * @return
	 */
	private String getFileName(HttpURLConnection conn) {
		// 获取文件名称
		String filename = this.downloadUrl.substring(this.downloadUrl.lastIndexOf('/') + 1);

		if (filename == null || "".equals(filename.trim())) {// 如果获取不到文件名称
			for (int i = 0;; i++) {
				String mine = conn.getHeaderField(i);

				if (mine == null)
					break;

				if ("content-disposition".equals(conn.getHeaderFieldKey(i).toLowerCase())) {
					Matcher match = Pattern.compile(".*filename=(.*)").matcher(mine.toLowerCase());
					if (match.find())
						return match.group(1);
				}
			}
			// 缺省
			filename = UUID.randomUUID() + ".tmp";// 默认取一个文件名
		}

		return filename;
	}

	/**
	 * 开始下载文件
	 * 
	 * @param listener
	 *            监听下载数量的变化,如果不需要了解实时下载的数量,可以设置为null
	 * @return 已下载文件大小
	 * @throws Exception
	 */
	public int startDownload(DownloadProgressListener listener) throws Exception {
		try {
			RandomAccessFile randOut = new RandomAccessFile(this.saveFile, "rw");
			// 判断原始文件的长度
			if (this.fileSize > 0)
				randOut.setLength(this.fileSize);
			randOut.close();

			URL url = new URL(this.downloadUrl);
			// 缓存数和线程数不等设置原始值
			if (this.cacheDownMap.size() != this.threadArr.length) {
				this.cacheDownMap.clear();

				for (int i = 0; i < this.threadArr.length; i++) {
					this.cacheDownMap.put(i + 1, 0);// 初始化每条线程已经下载的数据长度为0
				}
			}
			// 开启线程进行下载
			for (int i = 0; i < this.threadArr.length; i++) {
				int downLength = this.cacheDownMap.get(i + 1);

				// 这里的第一个条件是判断当前该线程还没有下载完分配数量；第二个是判断当前总的下载量要小于需要下载的文件总大小。
				if (downLength < this.block && this.downloadSize < this.fileSize) {
					this.threadArr[i] = new DownloadThread(this, url, this.saveFile, this.block, this.cacheDownMap.get(i + 1), i + 1);
					// 提高优先级
					this.threadArr[i].setPriority(7);
					this.threadArr[i].start();
				} else {
					// 该线程已经完任务，T掉。
					this.threadArr[i] = null;
				}
			}

			// 将下载数据插入数据库
			this.dbTools.save(this.downloadUrl, this.cacheDownMap);
			boolean notFinish = true;// 下载未完成

			// 在文件被完全下载完之前是个死循环，判断所有线程是否完成下载
			// 如果for循环结束了，外层while循环也就结束。
			while (notFinish) {
				Thread.sleep(900);
				notFinish = false;// 假定全部线程下载完成

				for (int i = 0; i < this.threadArr.length; i++) {
					if (this.threadArr[i] != null && !this.threadArr[i].isFinish()) {// 如果发现线程未完成下载
						notFinish = true;// 设置标志为下载没有完成

						// 获取当前线程完成任务量，如果为-1则重新下载。
						if (this.threadArr[i].getDownLength() == -1) {
							this.threadArr[i] = new DownloadThread(this, url, this.saveFile, this.block, this.cacheDownMap.get(i + 1), i + 1);
							this.threadArr[i].setPriority(7);
							this.threadArr[i].start();
						}
					}
				}

				// 回调，更新UI
				if (listener != null)
					listener.onDownloadSize(this.downloadSize);// 通知目前已经下载完成的数据长度
			}

			// 文件下载完成删除下载记录。
			dbTools.delete(this.downloadUrl);
		} catch (Exception e) {
			print(e.toString());
			throw new Exception("file download fail");
		}
		return this.downloadSize;
	}

	/**
	 * 获取Http响应头字段
	 * 
	 * @param http
	 * @return
	 */
	public static Map<String, String> getHttpResponseHeader(HttpURLConnection http) {
		Map<String, String> header = new LinkedHashMap<String, String>();

		for (int i = 0;; i++) {
			String mine = http.getHeaderField(i);
			if (mine == null)
				break;
			header.put(http.getHeaderFieldKey(i), mine);
		}

		return header;
	}

	/**
	 * 打印Http头字段
	 * 
	 * @param http
	 */
	public static void printResponseHeader(HttpURLConnection http) {
		Map<String, String> header = getHttpResponseHeader(http);

		for (Map.Entry<String, String> entry : header.entrySet()) {
			String key = entry.getKey() != null ? entry.getKey() + ":" : "";
			print(key + entry.getValue());
		}
	}

	/**
	 * 打印日志信息
	 * 
	 * @param msg
	 *            打印的日志内容
	 */
	private static void print(String msg) {
		Log.i(TAG, msg);
	}

	/**
	 * 下载文件监听
	 * 
	 * 主要用于回调。
	 */
	public interface DownloadProgressListener {

		public void onDownloadSize(int size);
	}
}
