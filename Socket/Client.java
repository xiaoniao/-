package com.io;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;

public class Client {

	public static void main(String[] args) {

		// ������ס���Է���˿ͻ��˸���һ���߳�

		new Thread(new Runnable() {
			public void run() {
				Server server = new Server();
				server.start();
			}
		}).start();

		new Thread(new Runnable() {
			public void run() {
				Client client = new Client();
				client.start();
			}
		}).start();
	}

	public void start() {
		Socket client = null;
		try {
			client = new Socket("127.0.0.1", 9009); // �������� TCP/IP

			Writer writer = new OutputStreamWriter(client.getOutputStream());
			writer.write("���\r\n\r\n");
			writer.flush();

			Reader reader = new InputStreamReader(client.getInputStream());
			StringBuffer stringBuffer = new StringBuffer();
			int chars;
			while ((chars = reader.read()) != -1) {
				stringBuffer.append((char) chars);
			}
			System.out.println(stringBuffer);
			
			reader.close();
			writer.close(); // �ر����Ż������-1,�����Լ�Լ����ʽ ��ӦServer 28��
		} catch (IOException e) {
			e.printStackTrace();
			
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

// ��������Ϊ�˵ȴ�
