package org.cobrien.chat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client {
	static void usage() {
		System.out.printf("Usage: java Client <port number>");
	}
	
	private Socket sock;
	private BufferedReader in;
	private BufferedWriter out;
	
	public Client(int port) throws IOException {
		this.sock = new Socket(InetAddress.getLocalHost(), port);
		this.in = new BufferedReader(new InputStreamReader(sock.getInputStream(), StandardCharsets.UTF_8));
		this.out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream(), StandardCharsets.UTF_8));
	}
	
	public void close() throws RuntimeException {
		try {
			this.sock.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			usage();
			System.exit(1);
		}
		
		int port = -1;
		try {
			port = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			System.out.printf("Could not parse port number %s\n", args[0]);
			System.exit(1);
		}
		
		Client client;
		try {
			client = new Client(port);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
		while (true) {
			try {
				System.out.print("Enter a message: ");
				System.out.flush();
				String message = null;

				message = reader.readLine();
				
				if (message == null) {
					break;
				}
				
				client.out.write(message);
				String response = client.in.readLine();
				
				System.out.printf("Received: %s", response);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		client.close();
	}
}
