/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaixo;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.naming.NamingException;

/**
 *
 * @author Administrator
 */
public class JavaSQL {
    private final String url; 
    private final String dbName; 
    private final String driver;  
    private final String userName; 
    private final String password; 
    
    public JavaSQL(){
        //Connection information. This can change from host to host.
        this.url = "jdbc:mysql://localhost:3306/rubik"; 
        this.dbName = "kaixo"; 
        this.driver = "com.mysql.jdbc.Driver";  
        this.userName = "root"; 
        this.password = "rubik"; 
    }

    public Connection openConnection() throws NamingException{
        try{
            Class.forName(driver).newInstance(); 
            return DriverManager.getConnection(url,userName,password); 
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("error al conectar ");
        }
        return null;
    }

    public void closeConnection(Connection salida){
        try{
            salida.close();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("error al conectar ");
        }
    } 
    
    //SQL statements here
    
    /* == EXAMPLE ==
    public static void insertData(Connection conn, Record rec) throws SQLException{
        String query = "INSERT INTO record(`name`, `moves`, `duration`)"+" VALUES (?, ?, ?); ";
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString(1, rec.getName().toString());
        preparedStmt.setInt(2, rec.getMoves().intValue());
        preparedStmt.setInt(3, rec.getDuration().intValue());
        
        preparedStmt.execute();        
    }
    */
    
}
