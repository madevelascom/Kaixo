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
import elements.Receta;
import elements.Valoracion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        this.password = "antrax95"; 
        
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

   
    //SQL statements here
    
    public static ObservableList<Medicina> loadMedicinas(Connection conn) throws SQLException{
        ObservableList<Medicina> medData = FXCollections.observableArrayList();
        String query = "{CALL loadMedicinas()}";
        
        CallableStatement stmt = conn.prepareCall(query);
        ResultSet rs = stmt.executeQuery();
        
        if(rs.next()){
            do{                      
            Medicina med = new Medicina(rs.getString("nombre"),rs.getString("concentracion") , 
                    rs.getString("presentacion"));
            medData.add(med);
            }while(rs.next());
        }
        
        return medData;
    }
    
    public static ObservableList<String> loadDistribuidorNames(Connection conn, 
        ObservableList<String> options) throws SQLException{
        String sql = "SELECT nombre FROM distribuidores;";
        Statement  stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        
        if(rs.next()){
            do{                      
            options.add(rs.getString("nombre"));
            }while(rs.next());
        }        
        return options;
    }
    
    
    public static ObservableList<Distribuidor> loadDistribuidor(Connection conn) throws SQLException{
        ObservableList<Distribuidor> distData = FXCollections.observableArrayList();
        String query = "{CALL loadDistribuidor()}";
        
        CallableStatement stmt = conn.prepareCall(query);
        ResultSet rs = stmt.executeQuery();
        
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
        String query = "{CALL loginSession(?,?)}";
        CallableStatement  stmt = conn.prepareCall(query);
        stmt.setString("user1", user); 
        stmt.setString("pass", pass);

        ResultSet rs = stmt.executeQuery();
        
        return rs.next();
    }
    
    public static int userLevel (Connection conn, String user) throws SQLException{
        String query = "{CALL userLevel(?)}";
        CallableStatement  stmt = conn.prepareCall(query);
        stmt.setString("user1", user); 

        ResultSet rs = stmt.executeQuery();
        
        int level = 0;
        if(rs.next()){
            level = rs.getInt("level");
        }
        
        return level;
    }
    
    public static String errorMsg(Connection conn, int id) throws SQLException{
        String result = "";
        String query = "{CALL errorMsg(?)}";
        CallableStatement  stmt = conn.prepareCall(query);
        stmt.setInt("id", id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()){
            result = rs.getString("error");
        }
        return result;
    }
    
    public static Paciente searchPaxCI(Connection conn, String pax) throws SQLException{
        String query = "{CALL searchPaxCI(?)}";
        CallableStatement  stmt = conn.prepareCall(query);
        stmt.setString("pax", pax);
        ResultSet rs = stmt.executeQuery();
        
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
        String query = "{CALL searchPaxNom(?)}";
        CallableStatement  stmt = conn.prepareCall(query);
        stmt.setString("pax", pax);
        ResultSet rs = stmt.executeQuery();
        
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
        String query = "{CALL insertNewPax(?,?,?,?,?,?,?,?,?)}";
        CallableStatement  stmt = conn.prepareCall(query);
        stmt.setString("CI", pax.getCI().getValue());
        stmt.setString("apellidos", pax.getApellidos().getValue());
        stmt.setString("nombres", pax.getNombres().getValue());

        stmt.setString("fechanacimiento", pax.getNacimiento().getValue());
        stmt.setString("tiposangre", pax.getSangre().getValue());
        stmt.setString("numcelular", pax.getCelular().getValue());
        stmt.setString("numcasa", pax.getCasa().getValue());
        stmt.setString("dircasa", pax.getDireccion().getValue());
        stmt.setString("email", pax.getEmail().getValue());
        stmt.executeQuery();
    }
    
    public static String selecPaxNameConcat(Connection conn, String id) throws SQLException{
        String rslt = "";
        String query = "{CALL selectPaxNameConcat(?)}";
        CallableStatement  stmt = conn.prepareCall(query);
        stmt.setString("id", id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()){
            rslt = rs.getString("paciente");
        }
        return rslt;
        
    }
    
    public static String selecPaxName(Connection conn, String id) throws SQLException{
        String rslt = "";
        String query = "{CALL selectPaxName(?)}";
        CallableStatement  stmt = conn.prepareCall(query);
        stmt.setString("id", id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()){
            rslt = rs.getString("nombres");
        }
        return rslt;
        
    }
    
    public static String selecPaxLastName(Connection conn, String id) throws SQLException{
        String rslt = "";
        String query = "{CALL selectPaxLastName(?)}";
        CallableStatement  stmt = conn.prepareCall(query);
        stmt.setString("id", id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()){
            rslt = rs.getString("apellidos");
        }
        return rslt;
        
    }
    
    public static void updatePax(Connection conn, Paciente pax)throws SQLException{
        String query = "{CALL updatePax(?,?,?,?,?,?,?,?,?)}";
        CallableStatement  stmt = conn.prepareCall(query);
        stmt.setString("CI", pax.getCI().getValue());
        stmt.setString("nombres", pax.getNombres().getValue());
        stmt.setString("apellidos", pax.getApellidos().getValue());
        stmt.setString("fechanacimiento", pax.getNacimiento().getValue());
        stmt.setString("tiposangre", pax.getSangre().getValue());
        stmt.setString("numcelular", pax.getCelular().getValue());
        stmt.setString("numcasa", pax.getCasa().getValue());
        stmt.setString("dircasa", pax.getDireccion().getValue());
        stmt.setString("email", pax.getEmail().getValue());
        stmt.executeQuery();
    }
    public static boolean existsPax(Connection conn, String CI) throws SQLException{
        String query = "{CALL existsPax(?)}";
        CallableStatement  stmt = conn.prepareCall(query);
        stmt.setString("CI", CI);
        ResultSet rs = stmt.executeQuery();
        
        return rs.next();
    }
    
    public static void insertConsulta(Connection conn, Consulta con) throws SQLException{
        String query = "{CALL insertConsulta(?,?,?)}";
        CallableStatement  stmt = conn.prepareCall(query);
        stmt.setString("fecha", con.getFecha().getValue());
        stmt.setString("patient", con.getPaciente().getValue());
        stmt.setString("estado", con.getEstado().getValue());

        stmt.executeQuery();
    }
    
    public static void updateConsulta(Connection conn, Consulta nuevo, Consulta old ) throws SQLException{
        String query = "{CALL updateConsulta(?,?,?,?)}";
        CallableStatement  stmt = conn.prepareCall(query);
        stmt.setString("nuevafecha", nuevo.getFecha().getValue());
        stmt.setString("nuevoestado", nuevo.getEstado().getValue());
        stmt.setString("viejopaciente", old.getPaciente().getValue());
        stmt.setString("viejafecha", old.getFecha().getValue());
                 
        
        stmt.executeQuery();
    }
    
    public static ObservableList<Consulta> loadConsultasHoy(Connection conn) throws SQLException{
        ObservableList<Consulta> distConHoy = FXCollections.observableArrayList();
        String query = "{CALL loadConsultasHoy()}";
        CallableStatement  stmt = conn.prepareCall(query);
        
        /*SELECT TIME(consultas.fecha) as 'hora_con', consultas.fecha, 
        CONCAT(paciente.nombres, ' ', paciente.apellidos) as 'paciente', 
        consultas.estado FROM consultas JOIN paciente 
        ON consultas.paciente = paciente.CI  
        WHERE DATE(consultas.fecha) = DATE(NOW()) ;*/
        
        
        ResultSet rs = stmt.executeQuery();
        
        if(rs.next()){
            do{                      
            Consulta con = new Consulta(rs.getString("fecha"),rs.getString("paciente") , 
                    rs.getString("estado"));
            distConHoy.add(con);
            }while(rs.next());
        }
        
        return distConHoy;
    }
    
    
    
    public static ObservableList<Consulta> loadConsultasPasadas(Connection conn , String CI) throws SQLException{
        ObservableList<Consulta> conPas = FXCollections.observableArrayList();
        String query = "{CALL loadConsultasPasadas(?)}";
        CallableStatement  stmt = conn.prepareCall(query);
        
        stmt.setString("CI", CI);
        

        
        ResultSet rs = stmt.executeQuery();
        
        if(rs.next()){
            do{                      
            Consulta con = new Consulta(rs.getString("fecha"),rs.getString("paciente") , 
                    rs.getString("estado"));
            conPas.add(con);
            }while(rs.next());
        }
        
        return conPas;
    }
    
    public static void updateEstado( Connection conn, Consulta con, String estado) throws SQLException{
        String query = "{CALL updateEstado(?,?,?)}";
        CallableStatement  stmt = conn.prepareCall(query);
        
        stmt.setString("paciente", con.getPaciente().getValue());
        stmt.setString("fecha", con.getFecha().getValue());
        stmt.setString("estado", estado);
        
      
        
        stmt.executeQuery();
    }
    
    
    
    public static boolean existsPaxName( Connection conn, String nombres, String apellidos) throws SQLException{
        String query = "{CALL existsPaxName(?,?)}";
        CallableStatement  stmt = conn.prepareCall(query);
        stmt.setString("nombres", nombres);
        stmt.setString("apellidos", apellidos);

        ResultSet rs = stmt.executeQuery();
        
        return rs.next();
    }
    
    public static String getCIByName (Connection conn, String nombres, String apellidos) throws SQLException{
        String query = "{CALL getCIByName(?,?)}";
        CallableStatement  stmt = conn.prepareCall(query);
        stmt.setString("nombres", nombres);
        stmt.setString("apellidos", apellidos);
        String CI = "";

        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()){
            CI = rs.getString("CI");
        }
        
        return CI;
    }
    
    public static void updateDialog(Connection conn, Consulta con, String Dialog) throws SQLException{
        String query = "{CALL updateDialog(?,?,?)}";
        CallableStatement  stmt = conn.prepareCall(query);
        
        stmt.setString("paciente", con.getPaciente().getValue());
        stmt.setString("fecha", con.getFecha().getValue());
        stmt.setString("diagnostico", Dialog);
        
     
        
        stmt.executeQuery();
    }
    
    public static boolean existsCons(Connection conn, String date) throws SQLException{
        String query = "{CALL existsCons(?)}";
        CallableStatement  stmt = conn.prepareCall(query);
        stmt.setString("fecha", date);
        
        ResultSet rs = stmt.executeQuery();
        
        return rs.next();
    }
   
    public static boolean medExists(Connection conn, Medicina med) throws SQLException{
        String query = "{CALL medExists(?,?,?)}";
        CallableStatement  stmt = conn.prepareCall(query);
        stmt.setString("nombre", med.getNombre().getValue());
        stmt.setString("concentracion", med.getConcentracion().getValue());
        stmt.setString("presentacion", med.getPresentacion().getValue());

        ResultSet rs = stmt.executeQuery();
        
        return rs.next();
    }
    
    public static void insertNewMed(Connection conn, Medicina med, List<String> dists) throws SQLException{
        String query = "{CALL insertNewMed(?,?,?)}";
        CallableStatement  stmt = conn.prepareCall(query);
        stmt.setString("nombre", med.getNombre().getValue());
        stmt.setString("concentracion", med.getConcentracion().getValue());
        stmt.setString("presentacion", med.getPresentacion().getValue());
        stmt.executeQuery();
   
        for (String x : dists){
            Statement  stmt_rep = conn.createStatement();
            String sql = "INSERT INTO medicina_dist (`id_medicina`, `id_distribuidor`) "
                    + "SELECT medicinas.id, distribuidores.id FROM medicinas, distribuidores "
                    + "WHERE medicinas.nombre = '"+med.getNombre().getValue()+"' AND "
                    + "medicinas.concentracion = '"+med.getConcentracion().getValue()+"' AND "
                    + "medicinas.presentacion = '"+med.getPresentacion().getValue()+"' AND "
                    + "distribuidores.nombre = '"+x+"';";      
            stmt_rep.executeQuery(sql);
        }     
    }
    
    public static List<String> loadMedDist(Connection conn, Medicina med) throws SQLException{
        List<String> result = new ArrayList<>();
        String sql = "SELECT distribuidores.nombre FROM distribuidores WHERE distribuidores.id IN "
                + "(SELECT medicina_dist.id_distribuidor FROM medicina_dist "
                + "WHERE medicina_dist.id_medicina =  (SELECT medicinas.id FROM medicinas "
                + "WHERE medicinas.nombre = '"+med.getNombre().getValue()+"' AND "
                + "medicinas.concentracion = '"+med.getConcentracion().getValue()+"' AND "
                + "medicinas.presentacion = '"+med.getPresentacion().getValue()+"'));";
        Statement  stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        
        if(rs.next()){
            do{
                result.add(rs.getString("distribuidores.nombre"));
            }while(rs.next());
        }
        return result;
    }
       
    public static void deleteMed(Connection conn, Medicina med) throws SQLException{
        String query = "{CALL deleteMed(?,?,?)}";
        CallableStatement  stmt = conn.prepareCall(query);
        stmt.setString("nombre", med.getNombre().getValue());
        stmt.setString("concentracion", med.getConcentracion().getValue());
        stmt.setString("presentacion", med.getPresentacion().getValue());
        stmt.executeQuery();
    }
 
    public static void updateMed(Connection conn, Medicina med, Medicina prev, List<String> result) throws SQLException{
        String query = "{CALL updateMed(?,?,?,?,?,?)}";
        CallableStatement  stmt = conn.prepareCall(query);
        stmt.setString("nombre_a", prev.getNombre().getValue());
        stmt.setString("concentracion_a", prev.getConcentracion().getValue());
        stmt.setString("presentacion_a", prev.getPresentacion().getValue());
        stmt.setString("nombre_n", med.getNombre().getValue());
        stmt.setString("concentracion_n", med.getConcentracion().getValue());
        stmt.setString("presentacion_n", med.getPresentacion().getValue());

        stmt.executeQuery();
        
        String sql = "DELETE FROM medicina_dist WHERE id_medicina = ( "
                + "SELECT medicinas.id FROM medicinas WHERE "
                + "medicinas.nombre = '"+prev.getNombre().getValue()+"' AND "
                + "medicinas.concentracion = '"+prev.getConcentracion().getValue()+"' AND "
                + "medicinas.presentacion = '"+prev.getPresentacion().getValue()+"');";
        Statement  stmt_1 = conn.createStatement();
        stmt_1.executeQuery(sql);
        
        for (String x : result){
            Statement  stmt_rep = conn.createStatement();
            String sql_insert = "INSERT INTO medicina_dist (`id_medicina`, `id_distribuidor`) "
                    + "SELECT medicinas.id, distribuidores.id FROM medicinas, distribuidores "
                    + "WHERE medicinas.nombre = '"+med.getNombre().getValue()+"' AND "
                    + "medicinas.concentracion = '"+med.getConcentracion().getValue()+"' AND "
                    + "medicinas.presentacion = '"+med.getPresentacion().getValue()+"' AND "
                    + "distribuidores.nombre = '"+x+"';";     
            stmt_rep.executeQuery(sql_insert);
        } 
        
    }
   
    public static boolean distExists(Connection conn, Distribuidor dist) throws SQLException{
        String query = "{CALL distExists(?,?,?)}";
        CallableStatement  stmt = conn.prepareCall(query);
        stmt.setString("nombre", dist.getNombre().getValue());
        stmt.setString("direccion", dist.getDireccion().getValue());
        stmt.setString("telefono", dist.getTelefono().getValue());

        ResultSet rs = stmt.executeQuery();
        
        return rs.next();
    }
    
    public static void insertNewDist(Connection conn, Distribuidor dist) throws SQLException{
        String query = "{CALL insertNewDist(?,?,?)}";
        CallableStatement  stmt = conn.prepareCall(query);
        stmt.setString("nombre", dist.getNombre().getValue());
        stmt.setString("direccion", dist.getDireccion().getValue());
        stmt.setString("telefono", dist.getTelefono().getValue());
        stmt.executeQuery();
    }
    
       
    public static void deleteDist(Connection conn, Distribuidor dist) throws SQLException{
        String query = "{CALL deleteDist(?,?,?)}";
        CallableStatement  stmt = conn.prepareCall(query);
        stmt.setString("nombre", dist.getNombre().getValue());
        stmt.setString("direccion", dist.getDireccion().getValue());
        stmt.setString("telefono", dist.getTelefono().getValue());
        stmt.executeQuery();
    }
 
    public static void updateDist(Connection conn, Distribuidor dist, Distribuidor prev) throws SQLException{
        String query = "{CALL updateDist(?,?,?,?,?,?)}";
        CallableStatement  stmt = conn.prepareCall(query);
        stmt.setString("nombre_a", prev.getNombre().getValue());
        stmt.setString("direccion_a", prev.getDireccion().getValue());
        stmt.setString("telefono_a", prev.getTelefono().getValue());
        stmt.setString("nombre_n", dist.getNombre().getValue());
        stmt.setString("direccion_n", dist.getDireccion().getValue());
        stmt.setString("telefono_n", dist.getTelefono().getValue());

       stmt.executeQuery();
    }
    
    public static void insertValoracion(Connection conn, Valoracion val, Consulta con)throws SQLException{
        String query = "{CALL insertValoracion(?,?,?,?)}";
        CallableStatement  stmt = conn.prepareCall(query);
        stmt.setString("presion", val.getPresion().getValue());
        stmt.setInt("glucosa", val.getGlucosa().getValue());
        stmt.setDouble("peso", val.getPeso().getValue());
        stmt.setString("fecha", con.getFecha().getValue());
        
        stmt.executeQuery();

        
    }
    
    public static ObservableList<String> loadMedicinasNameCon(Connection conn) throws SQLException{
        String query = "{CALL loadMedicinasNameCon()}";
        CallableStatement  stmt = conn.prepareCall(query);
        ResultSet rs = stmt.executeQuery();
        ObservableList<String> medData = FXCollections.observableArrayList();
        
        if(rs.next()){
            do{                      
            medData.add(rs.getString("nombre"));
            }while(rs.next());
        }
        
        return medData;
    }
    
     public static ObservableList<Receta> loadMedicinasFrecuencies(Connection conn, Consulta cons) {
        String query = "{CALL loadMedicinasFrecuencies(?,?)}";
        ObservableList<Receta> recData = FXCollections.observableArrayList();
        
        CallableStatement  stmt;
        try {
            stmt = conn.prepareCall(query);

            stmt.setString("paciente", cons.getPaciente().getValue());
            stmt.setString("fecha", cons.getFecha().getValue());
            ResultSet rs = stmt.executeQuery();


            if(rs.next()){
                do{                      
                recData.add(new Receta(rs.getString("id_consulta"), rs.getString("id_medicina"), rs.getString("frecuencia")
                , rs.getString("nombre"), rs.getString("concentracion")));
                }while(rs.next());
            }
        } catch (SQLException ex) {
            Logger.getLogger(JavaSQL.class.getName()).log(Level.SEVERE, null, ex);
        }

        return recData;
    }
    

}
