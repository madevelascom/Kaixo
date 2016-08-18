package utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/*
https://java.net/projects/javamail/pages/Home
*/

/**
 * http://beginnersbook.com/2014/08/java-regex-tutorial/
 * @author made
 */
public class RegexMatcher {
    
    static String regex;
    
    public static boolean testEmail(String email) {
        boolean result = true;
        try{
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        }catch (AddressException ex){
            result = false;
        }
         return result;
    }
    
    public static boolean testDate( String dateString) {
        Date fechaConsulta = new Date();
        Date fechaActual = new Date();
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            fechaConsulta = simpleDateFormat.parse(dateString);

        }
        catch (ParseException ex)
        {
            System.out.println("Exception "+ex);
        }
        
        return fechaConsulta.before(fechaActual);
        
        
    }
    
    public static boolean testCel(String cel){
        return Pattern.matches("[0-9]{10}", cel);
    }
    
    public static boolean testCasa(String casa){
        return Pattern.matches("[0-9]{9}", casa);
    }
    
    public static boolean testUser(String user){
        return Pattern.matches("^(?=.*[a-zA-Z]).{6,}+", user);
    }
    
    public static boolean testPassword(String pass){
        return Pattern.matches("^(?=.*[a-zA-Z]).{6,}", pass);
    }
    
    public static boolean testPaxCISearch(String CI){
        return Pattern.matches("[0-9]{10}", CI);
    }
    
    public static boolean testPaxNomSearch(String nom){
        return Pattern.matches("^\\pL+[\\pL\\pZ\\pP]{1,}$", nom);
    }   
    
    public static boolean testGlucosa(String glucosa){
        return Pattern.matches("\\d+", glucosa);
    }
    
    public static boolean testPeso(String peso){
        return Pattern.matches("\\d+\\.\\d+", peso);
    }

}
