package utils;

import java.util.regex.Pattern;

/**
 * http://beginnersbook.com/2014/08/java-regex-tutorial/
 * @author made
 */
public class RegexMatcher {
    static String regex;
   
    public static boolean testCI(String CI){
        return Pattern.matches("[0-9]{10}", CI);
    }
    
    public static boolean testEmail(String email) {
        return Pattern.matches("^[_A-Za-z0-9-\\\\+]+(\\\\.[_A-Za-z0-9-]+)*\n" +
            "@[A-Za-z0-9-]+(\\\\.[A-Za-z0-9]+)*(\\\\.[A-Za-z]{2,})$;", email.toLowerCase());
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

}
