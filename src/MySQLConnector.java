import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Somners
 */
public class MySQLConnector implements Connector{
    File file = new File("plugins/config/FiveStarTowns/StandaloneMySQLProps.txt");
    File dir = new File("plugins/config/FiveStarTowns");
    FiveStarTowns plugin;
    
    static String UserName, Password, databaseName, HostName, UsersTable;
//    DataBase is a string jdbc:data://localhost:3306/
    
    /**
     *
     */
    public MySQLConnector(){
        this.plugin = FiveStarTowns.getInstance();
        initialize();
    }
    
    /**
     *
     */
    public void initialize(){
        if(!file.exists()){
            try{
                dir.mkdirs();
                file.createNewFile();
            }
            catch(IOException ex){
                System.out.println("[FiveStarTowns] error creating data.txt");
            }
            
            try{
                FileWriter writer = new FileWriter(file);
                String toFile = "# pound sign is a commented out line. \r\n#these are the configurations for your data database"
                        + "\r\nUsername=\r\nPassword=\r\n#name of the database\r\nDatabase=\r\n#format -->  hostname:port\r\nHostname=localhost:3306";
                writer.write(toFile);
                writer.close();
            }
            catch(IOException ex){
                System.out.println("[FiveStarTowns] error creating values for data.txt");
            }
            
        }
        try{
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        while(line != null){
        if(!line.startsWith("#")){
            String[] split = line.split("=");
            if(split[0].equalsIgnoreCase("Username")){
                UserName = split[1];
            }
            else if(split[0].equalsIgnoreCase("Password")){
                Password = split[1];
            }
            else if(split[0].equalsIgnoreCase("Database")){
                databaseName = split[1];
            }
            else if(split[0].equalsIgnoreCase("Hostname")){
                HostName = split[1];
            }
            
        }
        line = reader.readLine();
        }
        reader.close();
        }
        catch(IOException ex){}
        
        
        
    }
    
    /**
     *
     * @return
     */
    public Connection getConnection(){
        try {
            CanaryConnection canary = null;
            try{
                canary = etc.getConnection();
            }
            catch(SQLException ex){
                System.out.println("[FiveStarTowns] Sql Error: " + ex.toString());
            }
            if(canary != null){
                return canary.getConnection();
            }
            
            try{
                Class.forName("com.mysql.jdbc.Driver");}
            catch(ClassNotFoundException ex){
                System.out.println("[FiveStarTowns] Error finding Driver: " + ex.toString());
            }
            String DataBase = "jdbc:mysql://" + HostName +"/" + databaseName;
            Connection conn = DriverManager.getConnection(DataBase, UserName, Password);
            return conn;
        } catch (SQLException ex) {
            System.out.println("[FiveStarTowns] Error Creating MySQL Connection: " + ex.toString());
        }
        return null;
    }

}
