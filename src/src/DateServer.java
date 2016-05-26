package src;
import java.net.*;
import java.io.*;
import java.util.Date;
/**
 * 
 * @author yutian
 * @since Mar 24, 2016
 */
public class DateServer {

	public static void main(String[] args) throws Exception {
		 ServerSocket listener = new ServerSocket(9090);
	        try {
	            while (true) {
	                Socket socket = listener.accept();
	                try {
	                    PrintWriter out =
	                        new PrintWriter(socket.getOutputStream(), true);
	                    out.println(new Date().toString());
	                } finally {
	                    socket.close();
	                }
	            }
	        }
	        finally {
	            listener.close();
	        }
	}

}
