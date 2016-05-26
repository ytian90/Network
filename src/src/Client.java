package src;
import java.net.*;
import java.awt.*;
import java.io.*;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Base64.Decoder;

import javax.swing.*;
/**
 * Client for Network Component of Project CSC715
 * @author yutian
 * @since Mar 24, 2016
 */
public class Client {

	public static void main(String[] args) throws Exception {
		Client CLIENT = new Client();
		CLIENT.run();
	}

	private void run() throws Exception {
		Socket sock = new Socket("localhost", 3306);
		PrintStream ps = new PrintStream(sock.getOutputStream());
		ps.println("Hello to SERVER from CLIENT");
		login();
		
		InputStreamReader iReader = new InputStreamReader(sock.getInputStream());
		BufferedReader bufReader = new BufferedReader(iReader);
		
		String msg = bufReader.readLine();
		System.out.println(msg);
		
		sock.close();
	}
	
	private void login() throws Exception {
		JTextField xField = new JTextField(10);
		JTextField yField = new JTextField(10);

	    JPanel myPanel = new JPanel();
	    myPanel.add(new JLabel("account:"));
	    myPanel.add(xField);
	    myPanel.add(Box.createHorizontalStrut(15)); // a spacer
	    myPanel.add(new JLabel("password:"));
	    myPanel.add(yField);
        int result = JOptionPane.showConfirmDialog(null, myPanel, 
	               "Please Enter account and password", JOptionPane.OK_CANCEL_OPTION);
        
	    if (result == JOptionPane.OK_OPTION) {
	       System.out.println("account: " + xField.getText());
	       System.out.println("password: " + yField.getText());
	       String account = xField.getText();
	       String password = yField.getText();
	       
	       // encoding byte array into base64
	       Base64.Encoder enc = Base64.getEncoder();
	       byte[] strenc = enc.encode(password.getBytes("UTF-8"));
	       
	       System.out.println("Base64 Encoded Password : " + new String(strenc,"UTF-8"));
	       
	       // decoding byte array into base64
	       Base64.Decoder dec = Base64.getDecoder();
	       byte[] strdec = dec.decode(strenc);
	       
	       System.out.println("Base64 Decoded String : " + new String(strdec,"UTF-8"));
	       
	       
//	       JOptionPane.showMessageDialog(null, "x value: " + xField.getText() + "\ny value: " + yField.getText());
	    }
	}

}
