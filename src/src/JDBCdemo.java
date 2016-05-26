package src;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;


/**
 * Demo to connect Java to MySQL
 * @author yutian
 * @since Mar 24, 2016
 */
public class JDBCdemo {

	public static void main(String[] args) throws Exception {
//		MySQLAccess dao = new MySQLAccess();
//	    dao.insert("larry", "123", "abc@gmail.com");
//	    dao.update("larry", "456", "a@gmail.com");
//	    dao.delete("");
//		System.out.println(dao.verifyPassword("larry", "123"));
//		System.out.println(dao.verifyPassword("larry", "1234"));
//		System.out.println(dao.verifyAccount("admin"));
//		System.out.println(dao.verifyAccount("admi"));
		
//		String password = "abc123";
//		Base64.Encoder enc = Base64.getEncoder();
//        byte[] strenc = enc.encode(password.getBytes("UTF-8"));
//        String enc_pw = new String(strenc, "UTF-8");
//        dao.insert("larry", enc_pw, "abc@gmail.com");
        
		String text = "Hello world";
		String mysql_filename = "mysql.txt";
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

}
