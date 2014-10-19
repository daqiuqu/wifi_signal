package com.example.wifi_signal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class client {
	Socket socket = null;
	PrintWriter out = null;
	BufferedReader in = null;
	String result = "hello";

	public client(String serverIP) {
		try {
			// 创建客户端，此处服务器的ip为211.87.235.77
//			socket = new Socket("211.87.235.77", 9999);
			socket = new Socket(serverIP, 9999);
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream())), true);// 初始化输出

			// 接收数据
			in = new BufferedReader(new InputStreamReader(// 初始化输入
					socket.getInputStream()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
			close();
		} catch (Exception e) {
			e.printStackTrace();
			close();
		}
	}

	public String findLocation(String value) {// 传递信号强度，获取当前位置
		if (out != null)
			out.println("0"+value);// 将当前的值传递给服务器
		try {
			result = in.readLine();// 从服务器读取数据
		} catch (IOException e) {
			e.printStackTrace();
			close();
		}
		return result;
	}

	public void send(String mac_value) {// 将测试值传递到服务器
		if (out != null)
			out.println("1"+mac_value);// 将当前的值传递给服务器
	}

	private void close()// 关闭各个端口
	{
		try {
			socket.close();
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
