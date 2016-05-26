package src;
import java.net.*;
import java.io.*;

import javax.swing.JOptionPane;
/**
 * 
 * @author yutian
 * @since Mar 24, 2016
 */
public class DateClient {

	public static void main(String[] args) throws Exception {
		String serverAddress = JOptionPane.showInputDialog(
	            "Enter IP Address of a machine that is\n" +
	            "running the date service on port 9090:");
		Socket s = new Socket(serverAddress, 9090);
        BufferedReader input =
            new BufferedReader(new InputStreamReader(s.getInputStream()));
        String answer = input.readLine();
        JOptionPane.showMessageDialog(null, answer);
        System.exit(0);
	}

}
