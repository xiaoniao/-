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

			// 流没有被关闭也没有标识结束

			// read() This method will block until a character is available, an I/O error occurs, or the end of the stream is reached. 
			// 如果流没有关闭，那么会假设他有可能继续传数据过来
			
			int chars;
			StringBuffer stringBuffer = new StringBuffer();
			int index = 0;
			while ((chars = reader.read()) != -1) {
				index++;
				stringBuffer.append((char) chars);
				// http 请求是以 \r\n\r\n 结尾
				if (index > 4 && stringBuffer.substring(index - 4, index).equals("\r\n\r\n")) {
					break;
				}

				// 使用浏览器请求 http://127.0.0.1:9009/ 打印如下结果
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
			writer.write("李想：喜欢旅行车没错，就和喜欢跑车一样。非要把喜欢旅行车当成一种品位的区隔，这个就有点过了，" 
						+ "更和它的实用性相违背。全世界来看，中国、美国、日本，旅行车都是小众车型，" 
						+ "不成规模。哪怕旅行车销量最好的欧洲，份额也在被小型SUV大幅抢占。");
			writer.flush();
			System.out.println("*Server writer close");
			
			
			
			writer.close(); // 关闭流才会读出来-1,或者自己约定格式 对应 Client 43行
			
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
