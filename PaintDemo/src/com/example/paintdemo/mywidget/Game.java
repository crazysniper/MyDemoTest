package com.example.paintdemo.mywidget;

public class Game {

	/** 初始化 九宫格的数据 */
	private final String initStr = "360000000004230800000004200" + "070460003820000014500013020" + "001900000007048300000000045";

	private int[] shuduku = new int[9 * 9];

	public Game() {
		shuduku = fromPuzzleString(initStr);
	}

	/**
	 * 通过传过来的坐标值获取该坐标的具体值（整数）
	 * 
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 * @return
	 */
	private int getTitle(int x, int y) {
		return shuduku[y * 9 + x];
	}

	/**
	 * 把获取到的整数变成String
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public String getTitleString(int x, int y) {
		int v = getTitle(x, y);
		if (0 == v) {
			return "";
		}
		return String.valueOf(v);//
	}

	/**
	 * 把字符串 一个个分离出来，存放至 shudu数组中，通过返回值，赋值给 shuduku数组中
	 * 
	 * @param str
	 * @return
	 */
	public int[] fromPuzzleString(String str) {
		int[] shudu = new int[str.length()];
		for (int i = 0; i < str.length(); i++) {
			// 把获取的单个字符减去 '0' 转成整数，赋给 整形 shudu数组中
			shudu[i] = str.charAt(i) - '0';
		}
		return shudu;
	}
}
