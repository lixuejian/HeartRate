package com.woliao.test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.woliao.net.ForwardTask;
import com.woliao.net.ThreadPool;

public class TestServer {
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(6788);
			ThreadPool threadPool = ThreadPool.getInstance();
			System.out.println("server启动");
			while(true){
				Socket socket = serverSocket.accept();
				System.out.println("server执行了accept");
				System.out.println("--------------------------------------------------------------------------------");
				System.out.println("socket.toString()为:"+socket.toString());
				System.out.println("--------------------------------------------------------------------------------");
				ForwardTask task = new ForwardTask(socket);
				threadPool.addTask(task);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
