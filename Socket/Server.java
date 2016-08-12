package com.io;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public void start() {

		ServerSocket serverSocket = null;
		Socket clientSocket = null;

		try {
			serverSocket = new ServerSocket(9009);
			clientSocket = serverSocket.accept();
			Reader reader = new InputStreamReader(clientSocket.getInputStream());

			// ��û�б��ر�Ҳû�б�ʶ����

			// read() This method will block until a character is available, an I/O error occurs, or the end of the stream is reached. 
			// �����û�йرգ���ô��������п��ܼ��������ݹ���
			
			int chars;
			StringBuffer stringBuffer = new StringBuffer();
			int index = 0;
			while ((chars = reader.read()) != -1) {
				index++;
				stringBuffer.append((char) chars);
				// http �������� \r\n\r\n ��β
				if (index > 4 && stringBuffer.substring(index - 4, index).equals("\r\n\r\n")) {
					break;
				}

				// ʹ����������� http://127.0.0.1:9009/ ��ӡ���½��
				// GET / HTTP/1.1
				// Host: 127.0.0.1:9009
				// User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64; rv:44.0) Gecko/20100101 Firefox/44.0
				// Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
				// Accept-Language: zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3
				// Accept-Encoding: gzip, deflate
				// Connection: keep-alive
				//
				//
			}

			System.out.println(stringBuffer);

			Writer writer = new OutputStreamWriter(clientSocket.getOutputStream());
			writer.write("���룺ϲ�����г�û���ͺ�ϲ���ܳ�һ������Ҫ��ϲ�����г�����һ��Ʒλ��������������е���ˣ�" 
						+ "��������ʵ������Υ����ȫ�����������й����������ձ������г�����С�ڳ��ͣ�" 
						+ "���ɹ�ģ���������г�������õ�ŷ�ޣ��ݶ�Ҳ�ڱ�С��SUV�����ռ��");
			writer.flush();
			System.out.println("*Server writer close");
			
			
			
			writer.close(); // �ر����Ż������-1,�����Լ�Լ����ʽ ��Ӧ Client 43��
			
			reader.close();
			
		} catch (IOException e) {
			e.printStackTrace();

		} finally {

			if (clientSocket != null) {
				try {
					clientSocket.close();
					System.out.print("*Server close client&");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (serverSocket != null) {
				try {
					serverSocket.close();
					System.out.print("server socket\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
}
