package utils;

/* Este codigo no es mio
 * Fue obtenido de:
 * http://stackoverflow.com/questions/6592010/encrypt-and-decrypt-a-password-in-java
 * Al ingresar, pedire user y pass por lo que es necesario esto.*/


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CryptMD5 {
    
	  private static MessageDigest md;

	   public static String cryptWithMD5(String pass){
	    try {
	        md = MessageDigest.getInstance("MD5");
	        byte[] passBytes = pass.getBytes();
	        md.reset();
	        byte[] digested = md.digest(passBytes);
	        StringBuffer sb = new StringBuffer();
	        for(int i=0;i<digested.length;i++){
	            sb.append(Integer.toHexString(0xff & digested[i]));
	        }
	        return sb.toString();
	    } catch (NoSuchAlgorithmException ex) {
	        Logger.getLogger(CryptMD5.class.getName()).log(Level.SEVERE, null, ex);
	    }
	        return null;


	   }
}
