package elements;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author INTEL
 */
public class Usuario {
    private StringProperty username;
    private StringProperty password;
    private StringProperty level;

    public Usuario() {
        
        this.level = new SimpleStringProperty("");
        this.password = new SimpleStringProperty("");
        this.username = new SimpleStringProperty("");
    }
    
    public Usuario(String username, String password, String level) {
        
        this.level = new SimpleStringProperty(level);
        this.password = new SimpleStringProperty(password);
        this.username = new SimpleStringProperty(username);
    }

    public StringProperty getUsername() {
        return username;
    }

    public void setUsername(StringProperty username) {
        this.username = username;
    }

    public StringProperty getPassword() {
        return password;
    }

    public void setPassword(StringProperty password) {
        this.password = password;
    }

    public StringProperty getLevel() {
        return level;
    }

    public void setLevel(StringProperty level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Usuario{" + "username=" + username + ", password=" + password + ", level=" + level + '}';
    }
    
    
    
    
    
}
