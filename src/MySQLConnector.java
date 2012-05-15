import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.io.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.DriverManager;

public class MySQLConnector {
    File file = new File("plugins/StunnerTowns/StandaloneMySQLProps.txt");
    File dir = new File("plugins/StunnerTowns");
    StunnerTowns plugin;
    
    static String UserName, Password, databaseName, HostName, UsersTable;
//    DataBase is a string jdbc:mysql://localhost:3306/
    
    public MySQLConnector(){
        this.plugin = StunnerTowns.getInstance();
    }
    
    public void initialize(){
        if(!file.exists()){
            try{
                dir.mkdirs();
                file.createNewFile();
            }
            catch(IOException ex){
                System.out.println("[Forum Signup] error creating mysql.txt");
            }
            
            try{
                FileWriter writer = new FileWriter(file);
                String toFile = "# pound sign is a commented out line. \r\n#these are the configurations for your mysql database"
                        + "\r\nUsername=\r\nPassword=\r\n#name of the database\r\nDatabase=\r\n#format -->  hostname:port\r\nHostname=localhost:3306";
                writer.write(toFile);
                writer.close();
            }
            catch(IOException ex){
                System.out.println("[Forum Signup] error creating values for mysql.txt");
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
    
    public Connection getConnection() throws SQLException{
        if(plugin.getInstance().getConfig().getMySQL()){
            return etc.getConnection().getConnection();
        }
        
        try{
            Class.forName("com.mysql.jdbc.Driver");}
        catch(ClassNotFoundException ex){}
        String DataBase = "jdbc:mysql://" + HostName +"/" + databaseName;
        Connection conn = DriverManager.getConnection(DataBase, UserName, Password);
        return conn;
        
    }

}
