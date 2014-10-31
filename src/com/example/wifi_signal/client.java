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
			// �����ͻ��ˣ��˴���������ipΪ211.87.235.77
//			socket = new Socket("211.87.235.77", 9999);
System.out.println("before new socket");
			socket = new Socket(serverIP, 9999);
System.out.println("after new socket");
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream())), true);// ��ʼ�����
System.out.println("after new printwirter");

			// ��������
			in = new BufferedReader(new InputStreamReader(// ��ʼ������
					socket.getInputStream()));
System.out.println("after new bufferedreader");
		} catch (UnknownHostException e) {
			e.printStackTrace();
			close();
		} catch (Exception e) {
			e.printStackTrace();
			close();
		}
	}

	public String findLocation(String value) {// �����ź�ǿ�ȣ���ȡ��ǰλ��
		if (out != null)
			out.println("0"+value);// ����ǰ��ֵ���ݸ�������
		try {
			result = in.readLine();// �ӷ�������ȡ����
		} catch (IOException e) {
			e.printStackTrace();
			close();
		}
		return result;
	}

	public void send(String mac_value) {// ������ֵ���ݵ�������
		if (out != null)
			out.println("1"+mac_value);// ����ǰ��ֵ���ݸ�������
	}

	public void close()// �رո����˿�
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
