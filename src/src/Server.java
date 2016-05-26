package src;
import java.net.*;
import java.io.*;

import javax.swing.JOptionPane;

/**
 * Server for Network Component of Project CSC715
 * @author yutian
 * @since Mar 24, 2016
 */
public class Server {

	public static void main(String[] args) throws Exception {
		Server SERVER = new Server();
		SERVER.run();
	}

	private void run() throws Exception {
		ServerSocket srvsock = new ServerSocket(3306);
		try {
			while (true) {
				Socket sock = srvsock.accept();
				InputStreamReader reader = new InputStreamReader(sock.getInputStream());
				BufferedReader bufReader = new BufferedReader(reader);
				
				String msg = bufReader.readLine();
				JOptionPane.showMessageDialog(null, msg);
				
				if (msg != null) {
					JOptionPane.showMessageDialog(null, "message received");
				}
				
				sock.close();
			}
		} finally {
			srvsock.close();
		}
		
	}

}
