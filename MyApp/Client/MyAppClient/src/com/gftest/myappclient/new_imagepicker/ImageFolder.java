package com.gftest.myappclient.new_imagepicker;

public class ImageFolder {

	/** 图片的文件夹路径 */
	private String dirPath;
	/** 文件夹的名称 */
	private String dirName;
	/** 第一张图片的路径 */
	private String firstImgPath;
	/** 图片的数量 */
	private int count;
	/** 相册id */
	private String id;

	public String getDirPath() {
		return dirPath;
	}

	public void setDirPath(String dirPath) {
		this.dirPath = dirPath;
	}

	public String getDirName() {
		return dirName;
	}

	public void setDirName(String dirName) {
		this.dirName = dirName;
	}

	public String getFirstImgPath() {
		return firstImgPath;
	}

	public void setFirstImgPath(String firstImgPath) {
		this.firstImgPath = firstImgPath;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
