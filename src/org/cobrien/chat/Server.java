package org.cobrien.chat;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class Server {
	private ServerSocket sock;

	public Server(int port) throws IOException {
		this.sock = new ServerSocket(port, 0, InetAddress.getLocalHost());
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("usage: java Server <port number>");
		}
		
		int port = -1;
		try {
			port = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			throw new RuntimeException(e);
		}
		
		Server server = null;
		try {
			server = new Server(port);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
