package src;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
/**
 * a capitalization server
 * @author yutian
 * @since Mar 24, 2016
 */
import java.nio.file.Paths;

/**
 * A server program which accepts requests from clients to
 * capitalize strings.  When clients connect, a new thread is
 * started to handle an interactive dialog in which the client
 * sends in a string and the server thread sends back the
 * capitalized version of the string.
 *
 * The program is runs in an infinite loop, so shutdown in platform
 * dependent.  If you ran it from a console window with the "java"
 * interpreter, Ctrl+C generally will shut it down.
 */
public class CapitalizeServer {
	
	private final static String mongodb_filename = "mongo.txt";
	private final static String mysql_filename = "mysql.txt";

	/**
     * Application method to run the server runs in an infinite loop
     * listening on port 9898.  When a connection is requested, it
     * spawns a new thread to do the servicing and immediately returns
     * to listening.  The server keeps a unique client number for each
     * client that connects just to show interesting logging
     * messages.  It is certainly not necessary to do this.
     */
    public static void main(String[] args) throws Exception {
        System.out.println("The capitalization server is running.");
        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(3304);
        try {
            while (true) {
                new Capitalizer(listener.accept(), clientNumber++).start();
            }
        } finally {
            listener.close();
        }
    }

    /**
     * A private thread to handle capitalization requests on a particular
     * socket.  The client terminates the dialogue by sending a single line
     * containing only a period.
     */
    private static class Capitalizer extends Thread {
        private Socket socket;
        private int clientNumber;

        public Capitalizer(Socket socket, int clientNumber) {
            this.socket = socket;
            this.clientNumber = clientNumber;
            log("New connection with client# " + clientNumber + " at " + socket);
        }

        /**
         * Services this thread's client by first sending the
         * client a welcome message then repeatedly reading strings
         * and sending back the capitalized version of the string.
         */
        public void run() {
            try {

                // Decorate the streams so we can send characters
                // and not just bytes.  Ensure output is flushed
                // after every newline.
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                PrintWriter out1 = new PrintWriter(socket.getOutputStream(), true);
                
                // Send a welcome message to the client.
                out.println("Hello, you are client #" + clientNumber + ".");
                out.println("Enter a line with only a period to quit\n");
                
                
                

                // Get messages from the client, line by line; return them
                // capitalized
                while (true) {
                    String input = in.readLine();
                    
                    if (input == null || input.equals(".")) {
                    	break;
                    }
                    
                    // save the input into the sql.txt file
                    writeToFile(input);
//                    out.println(input.toUpperCase());
                    
                 // Read the MongoDB instruction from file mongo.txt
                    String monStr = readFromFile();
//                    out.println(input.toUpperCase());
                    out.println(monStr.toUpperCase());
                    
                    
//                    if (input == null || input.equals(".")) {
//                        break;
//                    }
//                    out.println(input.toUpperCase());
                }
            } catch (IOException e) {
                log("Error handling client# " + clientNumber + ": " + e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    log("Couldn't close a socket, what's going on?");
                }
                log("Connection with client# " + clientNumber + " closed");
            }
        }
        
        private void writeToFile(String text) throws IOException {
        	BufferedWriter output = null;
            try {
                File file = new File(mysql_filename);
                output = new BufferedWriter(new FileWriter(file));
                output.write(text);
            } catch ( IOException e ) {
                e.printStackTrace();
            } finally {
                if ( output != null ) output.close();
            }
        }
        
        private String readFromFile() throws IOException {
        	
        	BufferedReader br = null;
            try {
            	br = new BufferedReader(new FileReader(mongodb_filename));
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();
                
                while (line != null) {
                	sb.append(line);
                	sb.append(System.lineSeparator());
                	line = br.readLine();
                }
                
                String monStr = sb.toString();
                return monStr;
            } catch ( IOException e ) {
                e.printStackTrace();
            } finally {
                if ( br != null ) br.close();
            }
        	return null;
        	
        }

        /**
         * Logs a simple message.  In this case we just write the
         * message to the server applications standard output.
         */
        private void log(String message) {
            System.out.println(message);
        }
        
    }

}
