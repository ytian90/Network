package src;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Base64;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * a capitalize client
 * @author yutian
 * @since Mar 24, 2016
 */

/**
 * A simple Swing-based client for the capitalization server.
 * It has a main frame window with a text field for entering
 * strings and a textarea to see the results of capitalizing
 * them.
 */
public class CapitalizeClient {

	private BufferedReader in;
    private PrintWriter out;
    private JFrame frame = new JFrame("Capitalize Client");
    private JTextField dataField = new JTextField(40);
    private JTextArea messageArea = new JTextArea(8, 60);

    /**
     * Constructs the client by laying out the GUI and registering a
     * listener with the textfield so that pressing Enter in the
     * listener sends the textfield contents to the server.
     */
    public CapitalizeClient() {

        // Layout GUI
        messageArea.setEditable(false);
        frame.getContentPane().add(dataField, "North");
        frame.getContentPane().add(new JScrollPane(messageArea), "Center");

        // Add Listeners
        dataField.addActionListener(new ActionListener() {
            /**
             * Responds to pressing the enter key in the textfield
             * by sending the contents of the text field to the
             * server and displaying the response from the server
             * in the text area.  If the response is "." we exit
             * the whole application, which closes all sockets,
             * streams and windows.
             */
            public void actionPerformed(ActionEvent e) {
                out.println(dataField.getText());
                   String response;
                try {
                    response = in.readLine();
                    if (response == null || response.equals("")) {
                          System.exit(0);
                      }
                } catch (IOException ex) {
                       response = "Error: " + ex;
                }
                messageArea.append(response + "\n");
                dataField.selectAll();
            }
        });
    }

    /**
     * Implements the connection logic by prompting the end user for
     * the server's IP address, connecting, setting up streams, and
     * consuming the welcome messages from the server.  The Capitalizer
     * protocol says that the server sends three lines of text to the
     * client immediately after establishing a connection.
     * @throws Exception 
     */
    public void connectToServer() throws Exception {

        // Get the server address from a dialog box.
        if (login()) {
        	// Make connection and initialize streams
            Socket socket = new Socket("localhost", 3304);
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Consume the initial welcoming messages from the server
            for (int i = 0; i < 3; i++) {
                messageArea.append(in.readLine() + "\n");
            }
        }
    }
    
    /**
     * Login Method to prompt user to enter account and password,
     * check the inputs to the records in database.
     * @throws Exception
     */
    private boolean login() throws Exception {
    	
    	String ip = "";
		String account = "";
        String password = "";
        String email = "";
        
		JTextField xField = new JTextField(10);
		JTextField yField = new JTextField(10);
		JTextField zField = new JTextField(20);
		JTextField qField = new JTextField(20);

	    JPanel myPanel = new JPanel();
	    myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
	    myPanel.add(new JLabel("IP Address:"));
	    myPanel.add(xField);
	    myPanel.add(new JLabel("Account:"));
	    myPanel.add(yField);
	    myPanel.add(new JLabel("Password:"));
	    myPanel.add(zField);
	    myPanel.add(new JLabel("Email:"));
	    myPanel.add(qField);
        int result = JOptionPane.showConfirmDialog(null, myPanel, 
	               "Please Enter IP address of server, account, password and email", JOptionPane.OK_CANCEL_OPTION);
        
	    if (result == JOptionPane.OK_OPTION) {
	    	MySQLAccess dbc = new MySQLAccess();
	    	ip = xField.getText();
	        account = yField.getText();
	        password = zField.getText();
	        email = qField.getText();
	    	
	        // already exist account
	    	if (dbc.verifyAccount(account)) {
	    		// if right password
	    		if (dbc.verifyPassword(account, password)) {
	    			JOptionPane.showMessageDialog(null, "account and password are right, login successfully!");
	    			return true;
	    		} else {
	    			JOptionPane.showMessageDialog(null, "password is wrong, please retry.");
	    			return false;
	    		}
	    	} else {
	    		int createAcc_op = JOptionPane.showConfirmDialog(null, myPanel, 
	 	               "Would you like to create a new account?", JOptionPane.OK_CANCEL_OPTION);
	    		if (createAcc_op == JOptionPane.OK_OPTION) {
	    			dbc.insert(account, password, email);
	    			JOptionPane.showMessageDialog(null, "new account has been created!");
	    			return true;
	    		}
	    	}
	    }
	    
	    return false;
	}
    

    /**
     * Runs the client application.
     */
    public static void main(String[] args) throws Exception {
        CapitalizeClient client = new CapitalizeClient();
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.pack();
        client.frame.setVisible(true);
        client.connectToServer();
    }

}
