package com.orangehrm.pageFactory.util;

import  java.sql.Connection;		
import  java.sql.Statement;		
import  java.sql.ResultSet;		
import  java.sql.DriverManager;
import java.sql.PreparedStatement;
import  java.sql.SQLException;		

public class mySqlUtil {
    	public static void  main(String[] args) throws  ClassNotFoundException, SQLException {													
				//Connection UR(L Syntax: "jdbc:mysql://ipaddress:portnumber/db_name"		
    		insertrow();    
    		updaterow();
    		selectData();
    		deleteRow();
    		System.out.println("after deleting");
    		selectData();
    	}
    	public  static void selectData() throws SQLException,ClassNotFoundException
    	{
    		//Query to Execute		
			String query = "select *  from employee;";	
			Connection con =open_connection();
			
	  		//Create Statement Object		
     	   Statement stmt = con.createStatement();					
    
    			// Execute the SQL Query. Store results in ResultSet		
      		ResultSet rs= stmt.executeQuery(query);							
      
      		// While Loop to iterate through all data and print results		
				while (rs.next()){
			        		String myName = rs.getString(1);								        
                         String myAge = rs.getString(2);					                               
                         System. out.println(myName+"  "+myAge);		
                 }		
   			 // closing DB Connection		
				close_connection(con);			
		}
 	
    	public  static void close_connection(Connection c) throws SQLException {
    		c.close();
    	}
		public static Connection open_connection()throws SQLException,ClassNotFoundException{
    		String dbUrl = "jdbc:mysql://localhost/emp";					
//                String dbUrl = "jdbc:mysql://192.168.1.6:3307/emp";		
				//Database Username		
				String username = "root";	
                
				//Database Password		
		
				String password = "Welcome@123";				

				
		        
         	    //Load mysql jdbc driver		
           	    Class.forName("com.mysql.jdbc.Driver");			
           
           		//Create Connection to DB		
			return DriverManager.getConnection(dbUrl,username,password);
		}
		
        
    	public static void  insertrow() throws  ClassNotFoundException, SQLException {													
			//Connection URL Syntax: "jdbc:mysql://ipaddress:portnumber/db_name"		
            String dbUrl = "jdbc:mysql://localhost/emp";					
//            String dbUrl = "jdbc:mysql://192.168.1.6:3307/emp";		
			//Database Username		
			String username = "root";	
            
			//Database Password		
			String password = "Welcome@123";				
	   	    Class.forName("com.mysql.jdbc.Driver");			
	        
       		//Create Connection to DB		
        	Connection con = DriverManager.getConnection(dbUrl,username,password);
    
			//Query to Execute		
			String query = "insert into employee (name, age) values ('Akshat12',2);";	
            
     	    //Load mysql jdbc driver		
      
      		//Create Statement Object		
    	   Statement stmt = con.prepareStatement(query)	;			
   
   			// Execute the SQL Query. Store results in ResultSet		
     		stmt.execute(query);							
     
     		// While Loop to iterate through all data and print results		
			
  			 // closing DB Connection		
  			con.close();			
	}
    	
    	public static void  deleteRow() throws  ClassNotFoundException, SQLException {													
			//Connection URL Syntax: "jdbc:mysql://ipaddress:portnumber/db_name"		
            String dbUrl = "jdbc:mysql://localhost/emp";					
//            String dbUrl = "jdbc:mysql://192.168.1.6:3307/emp";		
			//Database Username		
			String username = "root";	
            
			//Database Password		
			String password = "Welcome@123";				
	   	    Class.forName("com.mysql.jdbc.Driver");			
	        
       		//Create Connection to DB		
        	Connection con = DriverManager.getConnection(dbUrl,username,password);
    
			//Query to Execute		
			String query = "delete  from employee where name ='Akshat12';";	
            
     	    //Load mysql jdbc driver		
      
      		//Create Statement Object

			PreparedStatement preparedStmt = con.prepareStatement(query);
    	    
    	      // execute the preparedstatement
    	      preparedStmt.execute();
   			
     		// While Loop to iterate through all data and print results		
			
  			 // closing DB Connection		
  			con.close();			
	}
    	
public static void updaterow() {
 try
	    {
	
	        String dbUrl = "jdbc:mysql://localhost/emp";					
	        String username = "root";	
          
			//Database Password		
			String password = "Welcome@123";				
	   	    Class.forName("com.mysql.jdbc.Driver");			
	        
     		//Create Connection to DB		
      	Connection con = DriverManager.getConnection(dbUrl,username,password);
  
		 
	   
	      // create the java mysql update preparedstatement
	      String query = "update employee set name = 'Aayush' where age =3";
	      PreparedStatement preparedStmt = con.prepareStatement(query);
	     
	      // execute the java preparedstatement
	      preparedStmt.executeUpdate();
	      
	      con.close();
	    }
	    catch (Exception e)
	    {
	      System.err.println("Got an exception! ");
	      System.err.println(e.getMessage());
	    }
	  }

    	
}
