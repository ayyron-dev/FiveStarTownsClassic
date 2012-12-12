
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Somners
 */
public class SQLiteConnection implements Connector {
    
    private MyClassLoader loader = null;
    
    /**
     *
     */
    public SQLiteConnection(){
//        loader = Thread.currentThread().getContextClassLoader()
        try {
            loader = (MyClassLoader) FiveStarTowns.getInstance().getClass().getClassLoader();
            loader.addURL(new File("sqlite-jdbc.jar").toURI().toURL());
            Class<?> clazz = loader.loadClass("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SQLiteConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(FiveStarTowns.class.getName()).log(Level.SEVERE, null, ex);
        }
        File database = new File("plugins/config/FiveStarTowns/sqlite-database.db");
        if(!database.exists()){
            try {
                database.createNewFile();
            } catch (IOException ex) {
                System.out.println("[FiveStarTowns] Error creating sqlite-database.db: " + ex.toString());;
            }
        }
    }
    
    /**
     *
     * @return
     */
    @Override
    public Connection getConnection(){
        
        try{
            Class.forName("org.sqlite.JDBC", true, loader);}
        catch(ClassNotFoundException ex){
            System.out.println("[FiveStarTowns] Error finding Driver: " + ex.toString());
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:plugins/config/FiveStarTowns/sqlite-database.db");
        } catch (SQLException ex) {
            System.out.println("[FiveStarTowns] Error creating data connection: " + ex.toString());
        }
        return conn;
        
    }
}
