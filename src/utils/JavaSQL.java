/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import elements.Consulta;
import elements.Distribuidor;
import elements.Medicina;
import elements.Paciente;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        // driver on win this.driver = "com.mysql.jdbc.Driver"; 
        // url on made's win:  "jdbc:mysql://localhost:3306/rubik"; 
        this.url = "jdbc:mariadb://localhost:3306/kaixo"; 
        this.dbName = "Kaixo"; 
        this.driver = "org.mariadb.jdbc.Driver";  
        this.userName = "root"; 
        this.password = "rubik"; 
        
        /*
        this.url = "jdbc:mariadb://localhost:3306/kaixo"; 
        this.dbName = "Kaixo"; 
        this.driver = "org.mariadb.jdbc.Driver";  
        this.userName = "root"; 
        this.password = "rubik"; 
        */
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
    
    public static ObservableList<Medicina> loadMedicinas(Connection conn) throws SQLException{
        ObservableList<Medicina> medData = FXCollections.observableArrayList();
        Statement  stmt = conn.createStatement();
        String sql = "SELECT nombre, concentracion, presentacion FROM medicinas;";
        ResultSet rs = stmt.executeQuery(sql);
        
        if(rs.next()){
            do{                      
            Medicina med = new Medicina(rs.getString("nombre"),rs.getString("concentracion") , 
                    rs.getString("presentacion"));
            medData.add(med);
            }while(rs.next());
        }
        
        return medData;
    }
    
    public static ObservableList<Distribuidor> loadDistribuidor(Connection conn) throws SQLException{
        ObservableList<Distribuidor> distData = FXCollections.observableArrayList();
        Statement  stmt = conn.createStatement();
        String sql = "SELECT nombre, direccion, telefono FROM distribuidores;";
        ResultSet rs = stmt.executeQuery(sql);
        
        if(rs.next()){
            do{                      
            Distribuidor dist = new Distribuidor(rs.getString("nombre"),rs.getString("direccion") , 
                    rs.getString("telefono"));
            distData.add(dist);
            }while(rs.next());
        }
        return distData;
    }
    
    public static boolean loginSession(Connection conn, String user, String pass) throws SQLException{
        Statement  stmt = conn.createStatement();
        String sql = "SELECT username, password FROM users WHERE username = '"
                +user+"' AND password = '"+pass+"';";
        ResultSet rs = stmt.executeQuery(sql);
        
        return rs.next();
    }
    
    public static int userLevel (Connection conn, String user) throws SQLException{
        Statement  stmt = conn.createStatement();
        String sql = "SELECT level FROM users WHERE username = '"+user+"';";
        ResultSet rs = stmt.executeQuery(sql);
        
        int level = 0;
        if(rs.next()){
            level = rs.getInt("level");
        }
        
        return level;
    }
    
    public static String errorMsg(Connection conn, int id) throws SQLException{
        Statement stmt = conn.createStatement();
        String result = "";
        String sql = "SELECT error FROM errores WHERE id = "+String.valueOf(id)+";";
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()){
            result = rs.getString("error");
        }
        return result;
    }
    
    public static Paciente searchPaxCI(Connection conn, String pax) throws SQLException{
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM paciente WHERE CI = '"+pax+"';";
        ResultSet rs = stmt.executeQuery(sql);
        
        if (rs.next()){
            Paciente paciente = new Paciente (rs.getString("CI"), rs.getString("nombres"),
            rs.getString("apellidos"), rs.getString("fechanacimiento"), rs.getString("tiposangre"),
            rs.getString("numcelular"), rs.getString("numcasa"), rs.getString("dircasa"), rs.getString("email"));
            return paciente;
        }else{
            Paciente paciente = new Paciente();
            return paciente;
        }
    }
    
    public static Paciente searchPaxNom(Connection conn, String pax) throws SQLException{
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM paciente WHERE CI IN "
                + "(SELECT CI FROM paciente WHERE CONCAT(nombres, ' ', apellidos)='"+pax+"');";
        ResultSet rs = stmt.executeQuery(sql);
        
        if (rs.next()){
            Paciente paciente = new Paciente (rs.getString("CI"), rs.getString("nombres"),
            rs.getString("apellidos"), rs.getString("fechanacimiento"), rs.getString("tiposangre"),
            rs.getString("numcelular"), rs.getString("numcasa"), rs.getString("dircasa"), rs.getString("email"));
            return paciente;
        }else{
            Paciente paciente = new Paciente();
            return paciente;
        }
    }
    
    public static void insertNewPax(Connection conn, Paciente pax) throws SQLException{ 
       Statement stmt = conn.createStatement();
        String sql = "INSERT INTO `paciente` VALUES ('"+pax.getCI().getValue()+"',"
                + " '"+pax.getNombres().getValue()+"',"
                + " '"+pax.getApellidos().getValue()+"', "
                + " '"+pax.getNacimiento().getValue()+"', "
                + " '"+pax.getSangre().getValue()+"',"
                + " '"+pax.getCelular().getValue()+"',"
                + " '"+pax.getCasa().getValue()+"',"
                + " '"+pax.getDireccion().getValue()+"',"
                + " '"+pax.getEmail().getValue()+"') ";
        stmt.executeQuery(sql);
    }
    
    public static String selecPaxNameConcat(Connection conn, String id) throws SQLException{
        Statement stmt = conn.createStatement();
        String rslt = "";
        String sql = "SELECT CONCAT(paciente.nombres, ' ', paciente.apellidos) "
                + "AS 'paciente' FROM paciente WHERE CI = '"+id+"';";
        
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()){
            rslt = rs.getString("paciente");
        }
        return rslt;
        
    }
    
    public static void updatePax(Connection conn, Paciente pax)throws SQLException{
        Statement stmt = conn.createStatement();
        String sql = "UPDATE `paciente` SET   "
                + "nombres = '"+pax.getNombres().getValue()+"', "
                + "apellidos = '"+pax.getApellidos().getValue()+"', "
                + "fechanacimiento = '"+pax.getNacimiento().getValue()+"', "
                + "tiposangre = '"+pax.getSangre().getValue()+"', "
                + "numcelular = '"+pax.getCelular().getValue()+"', "
                + "numcasa = '"+pax.getCasa().getValue()+"', "
                + "dircasa = '"+pax.getDireccion().getValue()+"', "
                + "email = '"+pax.getEmail().getValue()+"' "
                + "WHERE CI = '"+pax.getCI().getValue()+"';";
        stmt.executeQuery(sql);
    }
    public static boolean existsPax(Connection conn, String CI) throws SQLException{
        Statement  stmt = conn.createStatement();
        String sql = "SELECT * FROM paciente WHERE CI = '"+CI+"';";
        ResultSet rs = stmt.executeQuery(sql);
        
        return rs.next();
    }
    
    public static void insertConsulta(Connection conn, Consulta con) throws SQLException{
        Statement  stmt = conn.createStatement();
        String sql = "INSERT INTO consultas (`fecha`, `paciente`, `estado`) VALUES "
                + "('"+con.getFecha().getValue()+"', "
                + " '"+con.getPaciente().getValue()+"',"
                + " '"+con.getEstado().getValue()+"');";
        stmt.executeQuery(sql);
    }
    
    public static ObservableList<Consulta> loadConsultasHoy(Connection conn) throws SQLException{
        ObservableList<Consulta> distConHoy = FXCollections.observableArrayList();
        Statement  stmt = conn.createStatement();
        
        /*SELECT TIME(consultas.fecha) as 'hora_con', consultas.fecha, 
        CONCAT(paciente.nombres, ' ', paciente.apellidos) as 'paciente', 
        consultas.estado FROM consultas JOIN paciente 
        ON consultas.paciente = paciente.CI  
        WHERE DATE(consultas.fecha) = DATE(NOW()) ;*/
        
        String sql = "SELECT fecha, paciente, estado FROM consultas "
                + "WHERE DATE(consultas.fecha) = DATE(NOW());";
        
        ResultSet rs = stmt.executeQuery(sql);
        
        if(rs.next()){
            do{                      
            Consulta con = new Consulta(rs.getString("fecha"),rs.getString("paciente") , 
                    rs.getString("estado"));
            distConHoy.add(con);
            }while(rs.next());
        }
        
        return distConHoy;
    }
    
    public static boolean existsCons(Connection conn, String date) throws SQLException{
        Statement  stmt = conn.createStatement();
        String sql = "SELECT * FROM consultas WHERE fecha = '"+date+"';";
        ResultSet rs = stmt.executeQuery(sql);
        
        return rs.next();
    }
   
    public static boolean medExists(Connection conn, Medicina med) throws SQLException{
        Statement  stmt = conn.createStatement();
        String sql = "SELECT * FROM medicinas WHERE "
                + "nombre = '"+med.getNombre().getValue()+"' AND "
                + "concentracion = '"+med.getConcentracion().getValue()+"' AND "
                + "presentacion = '"+med.getPresentacion().getValue()+"';";
        ResultSet rs = stmt.executeQuery(sql);
        
        return rs.next();
    }
    
    public static void insertNewMed(Connection conn, Medicina med) throws SQLException{
        Statement  stmt = conn.createStatement();
        String sql = "INSERT INTO medicinas  (`nombre`, `concentracion`, `presentacion`) VALUES "
                + "('"+med.getNombre().getValue()+"', "
                + " '"+med.getConcentracion().getValue()+"',"
                + " '"+med.getPresentacion().getValue()+"');";
        stmt.executeQuery(sql);
    }
    
       
    public static void deleteMed(Connection conn, Medicina med) throws SQLException{
        Statement  stmt = conn.createStatement();
        String sql = "DELETE FROM medicinas WHERE "
                + "nombre = '"+med.getNombre().getValue()+"' AND "
                + "concentracion = '"+med.getConcentracion().getValue()+"' AND "
                + "presentacion = '"+med.getPresentacion().getValue()+"';";
        stmt.executeQuery(sql);
    }
 
    public static void updateMed(Connection conn, Medicina med, Medicina prev) throws SQLException{
        Statement  stmt = conn.createStatement();
        String sql = "UPDATE medicinas SET "
                + "nombre = '"+med.getNombre().getValue()+"', "
                + "concentracion = '"+med.getConcentracion().getValue()+"', "
                + "presentacion = '"+med.getPresentacion().getValue()+"' "
                + "WHERE "
                + "nombre = '"+prev.getNombre().getValue()+"' AND "
                + "concentracion = '"+prev.getConcentracion().getValue()+"' AND "
                + "presentacion = '"+prev.getPresentacion().getValue()+"';";
        stmt.executeQuery(sql);
    }
   
    public static boolean distExists(Connection conn, Distribuidor dist) throws SQLException{
        Statement  stmt = conn.createStatement();
        String sql = "SELECT * FROM distribuidores WHERE "
                + "nombre = '"+dist.getNombre().getValue()+"' AND "
                + "direccion  = '"+dist.getDireccion().getValue()+"' AND "
                + "telefono  = '"+dist.getTelefono().getValue()+"';";
        ResultSet rs = stmt.executeQuery(sql);
        
        return rs.next();
    }
    
    public static void insertNewDist(Connection conn, Distribuidor dist) throws SQLException{
        Statement  stmt = conn.createStatement();
        String sql = "INSERT INTO distribuidores (`nombre`, `direccion`, `telefono`)  VALUES "
                + "('"+dist.getNombre().getValue()+"', "
                + " '"+dist.getDireccion().getValue()+"',"
                + " '"+dist.getTelefono().getValue()+"');";
        stmt.executeQuery(sql);
    }
    
       
    public static void deleteDist(Connection conn, Distribuidor dist) throws SQLException{
        Statement  stmt = conn.createStatement();
        String sql = "DELETE FROM distribuidores WHERE "
                + "nombre = '"+dist.getNombre().getValue()+"' AND "
                + "direccion  = '"+dist.getDireccion().getValue()+"' AND "
                + "telefono  = '"+dist.getTelefono().getValue()+"';";
        stmt.executeQuery(sql);
    }
 
    public static void updateDist(Connection conn, Distribuidor dist, Distribuidor prev) throws SQLException{
        Statement  stmt = conn.createStatement();
        String sql = "UPDATE distribuidores SET "
                + "nombre = '"+dist.getNombre().getValue()+"', "
                + "direccion  = '"+dist.getDireccion().getValue()+"', "
                + "telefono  = '"+dist.getTelefono().getValue()+"' "
                + "WHERE "
                + "nombre = '"+prev.getNombre().getValue()+"' AND "
                + "direccion = '"+prev.getDireccion().getValue()+"' AND "
                + "telefono = '"+prev.getTelefono().getValue()+"';";
       stmt.executeQuery(sql);
    }
}
