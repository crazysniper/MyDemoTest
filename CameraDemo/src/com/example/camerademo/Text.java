package com.example.camerademo;

public class Text {

	/**
	 * 连续2次以上调用Camera，会报java.lang.RuntimeException: Fail to connect to camera
	 * service
	 * 
	 * 因为没有对camera进行释放，或者说系统有自动释放功能但是还没有被释放
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 通过Camera控制摄像头拍照的步骤：
	 * 
	 * 1.调用Camera的open()方法打开相机。
	 * 
	 * 2.调用Camera的setParameters()方法获取拍照参数。该方法返回一个Camera.Parameters对象。
	 * 
	 * 3.调用Camera.Paramers对象方法设置拍照参数
	 * 
	 * 4.调用Camera的setParameters，并将Camera.Paramers作为参数传入，这样即可对相机的拍照参数进行控制
	 * 
	 * 5.调用Camera的startPreview()方法开始预览取景,在预览取景之前需要调用Camera的setPreViewDisplay(
	 * SurfaceHolder holder)方法设置使用哪个SurfaceView来显示取景图片。
	 * 
	 * 6.调用Camera的takePicture()方法进行拍照.
	 * 
	 * 7.结束程序时，调用Camera的StopPriview()结束取景预览，并调用release()方法释放资源。
	 * 
	 * 
	 * 
	 * 
	 * 切换前后摄像头
	 */
}
