package src;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.Date;
/**
 * Create Connections
 * @author yutian
 * @since Mar 24, 2016
 */
public class MySQLAccess {

	private Connection connect = null;
	  private Statement statement = null;
	  private PreparedStatement preparedStatement = null;
	  private ResultSet resultSet = null;

	  public void readDataBase() throws Exception {
	    try {
	      // This will load the MySQL driver, each DB has its own driver
	      Class.forName("com.mysql.jdbc.Driver");
	      // Setup the connection with the DB
	      connect = DriverManager
	          .getConnection("jdbc:mysql://localhost/feedback?"
	              + "user=sqluser&password=sqluserpw&useSSL=true");

	      // Statements allow to issue SQL queries to the database
	      statement = connect.createStatement();
	      // Result set get the result of the SQL query
	      resultSet = statement
	          .executeQuery("select * from feedback.users");
	      writeResultSet(resultSet);

	      // PreparedStatements can use variables and are more efficient
	      preparedStatement = connect
	          .prepareStatement("insert into  feedback.users values (default, ?, ?, ?, ?)");
	      // "myuser, webpage, datum, summery, COMMENTS from feedback.comments");
	      // Parameters start with 1
	      preparedStatement.setString(1, "Test");
	      preparedStatement.setString(2, "TestPassword");
	      preparedStatement.setString(3, "TestEmail");
	      preparedStatement.setDate(4, java.sql.Date.valueOf(java.time.LocalDate.now()));
	      preparedStatement.executeUpdate();

	      preparedStatement = connect
	          .prepareStatement("SELECT * from feedback.users");
	      resultSet = preparedStatement.executeQuery();
	      writeResultSet(resultSet);

	      // Remove again the insert comment
	      preparedStatement = connect
	      .prepareStatement("delete from feedback.users where myuser= ? ; ");
	      preparedStatement.setString(1, "Test");
	      preparedStatement.executeUpdate();
	      
	      resultSet = statement
	      .executeQuery("select * from feedback.users");
	      writeMetaData(resultSet);
	      
	    } catch (SQLException ex) {
	    	// handle any errors
	        System.out.println("SQLException: " + ex.getMessage());
	        System.out.println("SQLState: " + ex.getSQLState());
	        System.out.println("VendorError: " + ex.getErrorCode());
	    } finally {
	      close();
	    }

	  }
	  
	  /**
	   * Insert a new user into the  table users of database feedback
	   * @param _user
	   * @param _password
	   * @param _email
	   * @throws Exception
	   */
	  public void insert(String _user, String _password, String _email) throws Exception {
		  try {
		      // This will load the MySQL driver, each DB has its own driver
		      Class.forName("com.mysql.jdbc.Driver");
		      // Setup the connection with the DB
		      connect = DriverManager
		          .getConnection("jdbc:mysql://localhost/feedback?"
		              + "user=sqluser&password=sqluserpw&useSSL=true");
		      
		      // create a encoder object
		      Base64.Encoder enc = Base64.getEncoder();
		      // call the object to encode the password into array
		      byte[] strenc = enc.encode(_password.getBytes("UTF-8"));
		      // get the new string with format "UTF-8"
		      String enc_pw = new String(strenc, "UTF-8");
		      
		      // PreparedStatements can use variables and are more efficient
		      preparedStatement = connect
		          .prepareStatement("insert into  feedback.users values (default, ?, ?, ?, ?)");
		      // "myuser, webpage, datum, summery, COMMENTS from feedback.comments");
		      // Parameters start with 1
		      preparedStatement.setString(1, _user);
		      preparedStatement.setString(2, enc_pw);
		      preparedStatement.setString(3, _email);
		      preparedStatement.setDate(4, java.sql.Date.valueOf(java.time.LocalDate.now()));
		      preparedStatement.executeUpdate();
		      
		    } catch (SQLException ex) {
		    	// handle any errors
		        System.out.println("SQLException: " + ex.getMessage());
		        System.out.println("SQLState: " + ex.getSQLState());
		        System.out.println("VendorError: " + ex.getErrorCode());
		    } finally {
		      close();
		    }
	  }
	  
	  /**
	   * Delete the given user id
	   * @param _user
	   * @throws Exception
	   */
	  public void delete(String _user) throws Exception {
		  try {
		      // This will load the MySQL driver, each DB has its own driver
		      Class.forName("com.mysql.jdbc.Driver");
		      // Setup the connection with the DB
		      connect = DriverManager
		          .getConnection("jdbc:mysql://localhost/feedback?"
		              + "user=sqluser&password=sqluserpw&useSSL=true");

		      // Remove the specified _user
		      preparedStatement = connect
		      .prepareStatement("delete from feedback.users where myuser= ? ; ");
		      preparedStatement.setString(1, _user);
		      preparedStatement.executeUpdate();	     
		      
		    } catch (SQLException ex) {
		    	// handle any errors
		        System.out.println("SQLException: " + ex.getMessage());
		        System.out.println("SQLState: " + ex.getSQLState());
		        System.out.println("VendorError: " + ex.getErrorCode());
		    } finally {
		      close();
		    }
	  }
	  
	  /**
	   * Update the password and email for the user
	   * @param _user
	   * @param _password
	   * @param _email
	   * @throws Exception
	   */
	  public void update(String _user, String _password, String _email) throws Exception {
		  try {
		      // This will load the MySQL driver, each DB has its own driver
		      Class.forName("com.mysql.jdbc.Driver");
		      // Setup the connection with the DB
		      connect = DriverManager
		          .getConnection("jdbc:mysql://localhost/feedback?"
		              + "user=sqluser&password=sqluserpw&useSSL=true");
		      
		      // encrypt the password
		      Base64.Encoder enc = Base64.getEncoder();
		      byte[] strenc = enc.encode(_password.getBytes("UTF-8"));
		      String enc_pw = new String(strenc, "UTF-8");
		      
		   // PreparedStatements can use variables and are more efficient
		      preparedStatement = connect
		          .prepareStatement("update feedback.users set password = ?, email = ? where myuser = ?");
		      // "myuser, webpage, datum, summery, COMMENTS from feedback.comments");
		      // Parameters start with 1
		      preparedStatement.setString(1, enc_pw);
		      preparedStatement.setString(2, _email);
		      preparedStatement.setString(3, _user);
		      preparedStatement.executeUpdate();
		      
		      
		    } catch (SQLException ex) {
		    	// handle any errors
		        System.out.println("SQLException: " + ex.getMessage());
		        System.out.println("SQLState: " + ex.getSQLState());
		        System.out.println("VendorError: " + ex.getErrorCode());
		    } finally {
		      close();
		    }
	  }
	  
	  public boolean verifyPassword(String _user, String _password) throws Exception {
		  try {
		      // This will load the MySQL driver, each DB has its own driver
		      Class.forName("com.mysql.jdbc.Driver");
		      // Setup the connection with the DB
		      connect = DriverManager
		          .getConnection("jdbc:mysql://localhost/feedback?"
		              + "user=sqluser&password=sqluserpw&useSSL=true");
		      
		      
		   // PreparedStatements can use variables and are more efficient
		      preparedStatement = connect
		          .prepareStatement("select password from feedback.users where myuser = ?");
		      // "myuser, webpage, datum, summery, COMMENTS from feedback.comments");
		      // Parameters start with 1
		      preparedStatement.setString(1, _user);
		      
		      resultSet = preparedStatement.executeQuery();
		      while (resultSet.next()) {
		    	  String password = resultSet.getString("password");
		    	  
		    	  // decrypt the password from database 
		    	  Base64.Decoder dec = Base64.getDecoder();
			      byte[] strdec = dec.decode(password.getBytes());
			      String dec_ps =  new String(strdec,"UTF-8");
		    	  
		    	  if (dec_ps.equals(_password)) {
			    	  return true;
			      } else {
			    	  return false;
			      }
		      }
	      
		    } catch (SQLException ex) {
		    	// handle any errors
		        System.out.println("SQLException: " + ex.getMessage());
		        System.out.println("SQLState: " + ex.getSQLState());
		        System.out.println("VendorError: " + ex.getErrorCode());
		    } finally {
		      close();
		      
		    }
		  return false;

	  }
	  
	  public boolean verifyAccount(String _user) throws Exception {
		  try {
		      // This will load the MySQL driver, each DB has its own driver
		      Class.forName("com.mysql.jdbc.Driver");
		      // Setup the connection with the DB
		      connect = DriverManager
		          .getConnection("jdbc:mysql://localhost/feedback?"
		              + "user=sqluser&password=sqluserpw&useSSL=true");
		      
		      
		   // PreparedStatements can use variables and are more efficient
		      preparedStatement = connect
		          .prepareStatement("select myuser from users where myuser = ?");
		      // "myuser, webpage, datum, summery, COMMENTS from feedback.comments");
		      // Parameters start with 1
		      preparedStatement.setString(1, _user);
		      
		      resultSet = preparedStatement.executeQuery();
		      while (resultSet.next()) {
		    	  String user = resultSet.getString("myuser");
		    	  
		    	  if (user.toUpperCase().equals(_user.toUpperCase())) {
			    	  return true;
			      } else {
			    	  return false;
			      }
		      }
	      
		    } catch (SQLException ex) {
		    	// handle any errors
		        System.out.println("SQLException: " + ex.getMessage());
		        System.out.println("SQLState: " + ex.getSQLState());
		        System.out.println("VendorError: " + ex.getErrorCode());
		    } finally {
		      close();
		      
		    }
		  return false;

	  }
	  
	  
	  private void writeMetaData(ResultSet resultSet) throws SQLException {
	    //   Now get some metadata from the database
	    // Result set get the result of the SQL query
	    
	    System.out.println("The columns in the table are: ");
	    
	    System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
	    for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
	      System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
	    }
	  }

	  private void writeResultSet(ResultSet resultSet) throws SQLException {
	    // ResultSet is initially before the first data set
	    while (resultSet.next()) {
	      // It is possible to get the columns via name
	      // also possible to get the columns via the column number
	      // which starts at 1
	      // e.g. resultSet.getSTring(2);
	      String user = resultSet.getString("myuser");
	      String password = resultSet.getString("password");
	      String email = resultSet.getString("email");
	      Date date = resultSet.getDate("datum");
	      
	      System.out.println("User: " + user);
	      System.out.println("Password: " + password);
	      System.out.println("Email: " + email);
	      System.out.println("Date: " + date);

	    }
	  }

	  // You need to close the resultSet
	  private void close() {
	    try {
	      if (resultSet != null) {
	        resultSet.close();
	      }

	      if (statement != null) {
	        statement.close();
	      }

	      if (connect != null) {
	        connect.close();
	      }
	    } catch (Exception e) {

	    }
	  }


}
