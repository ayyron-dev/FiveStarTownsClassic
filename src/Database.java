import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Somners
 */
public class Database {
    
    private Logger log=Logger.getLogger("Minecraft");
    
    /**
     *
     * @param key
     * @param table
     * @param field
     * @return
     */
    public boolean keyExists(String key, String table, String field){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean exists = false;
        try{
                conn = FiveStarTowns.getConnection();
        }catch(Exception SQLE){
                log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception setting Connection ", SQLE);
        }
        if(conn != null){ //Dont wanna try executing if the connection isnt set
                try{
                        
                        ps = conn.prepareStatement("SELECT * FROM "+table+" WHERE "+field+" = ?"); //Prepares a Statement
                        ps.setString(1, key); //Sets the ? to the String "Key"
                        rs = ps.executeQuery(); //Executes a ResultSet
                        if (rs.next()){exists = true; } //ResultSet was returned so key exists
                } catch (SQLException ex) {
                        log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception in keyExsists ", ex);
                }finally{
                        try{//Close Connections
                                if(rs != null ){ rs.close(); }
                                if(ps != null ){ ps.close(); }
                                if(conn != null ){ conn.close(); }
                        }catch(SQLException SQLE){
                                log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception closing connection ", SQLE);
                        }
                }
                if(exists){return true;}
                else{return false;}
        }
        return false;

}
    
    /**
     *
     * @param table
     * @param field
     * @param key
     */
    public void delete(String table, String field, String key){
        Connection conn = null;
			PreparedStatement ps = null;
			try{
				conn = FiveStarTowns.getConnection();
			}catch(Exception SQLE){
				log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception setting Connection ", SQLE);
			}
			if(conn != null){
				try{
					ps = conn.prepareStatement("DELETE FROM "+table+" WHERE "+field+"=?"); //For multiple values use Table (Value1, Value2, Value3) VALUES(?,?,?)  then ps.setString(1, val); ps.setString(2, val); ps.setString(3, val);
					ps.setString(1, key);
					ps.executeUpdate(); //Execute InsertChunk
				} catch (SQLException SQLE) { //May be thrown for cases where Key Already Exists
					log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception in Insert ", SQLE);
				}finally{
					try{
						if(ps != null ){ ps.close(); }
						if(conn != null ){ conn.close(); }
					}catch(SQLException SQLE){
						log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception closing connection ", SQLE);
					}
				}
			}
    }
    
    /**
     *
     * @param coords
     * @param strang
     */
    public void InsertChunk(String coords, String strang){
			Connection conn = null;
			PreparedStatement ps = null;
			try{
				conn = FiveStarTowns.getConnection();
			}catch(Exception SQLE){
				log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception setting Connection ", SQLE);
			}
			if(conn != null){
				try{
					ps = conn.prepareStatement("INSERT INTO chunks (coords, town, owner) VALUES(?,?,?)"); //For multiple values use Table (Value1, Value2, Value3) VALUES(?,?,?)  then ps.setString(1, val); ps.setString(2, val); ps.setString(3, val);
					ps.setString(1, coords);
                                        ps.setString(2, strang);
                                        ps.setString(3, "none");
					ps.executeUpdate(); //Execute InsertChunk
				} catch (SQLException SQLE) { //May be thrown for cases where Key Already Exists
					log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception in Insert ", SQLE);
				}finally{
					try{
						if(ps != null ){ ps.close(); }
						if(conn != null ){ conn.close(); }
					}catch(SQLException SQLE){
						log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception closing connection ", SQLE);
					}
				}
			}
		}
    

        
            /**
     *
     * @param name
     * @param town
     */
    public void insertTownPlayer(String name, String town){
			Connection conn = null;
			PreparedStatement ps = null;
			try{
				conn = FiveStarTowns.getConnection();
			}catch(Exception SQLE){
				log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception setting Connection ", SQLE);
			}
			if(conn != null){
				try{
					ps = conn.prepareStatement("INSERT INTO townsusers (username, town) VALUES(?,?)"); //For multiple values use Table (Value1, Value2, Value3) VALUES(?,?,?)  then ps.setString(1, val); ps.setString(2, val); ps.setString(3, val);
					ps.setString(1, name);
                                        ps.setString(2, town);
					ps.executeUpdate(); //Execute InsertChunk
				} catch (SQLException SQLE) { //May be thrown for cases where Key Already Exists
					log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception in Insert ", SQLE);
				}finally{
					try{
						if(ps != null ){ ps.close(); }
						if(conn != null ){ conn.close(); }
					}catch(SQLException SQLE){
						log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception closing connection ", SQLE);
					}
				}
			}
		}
    
    /**
     *
     * @param key
     * @param field
     * @param table
     * @param updateKey
     * @param updateField
     */
    public void updateStringEntry(String key, String field, String table, String updateKey, String updateField){
			Connection conn = null;
			PreparedStatement ps = null;
			try{
				conn = FiveStarTowns.getConnection();
			}catch(Exception SQLE){
				log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception setting Connection ", SQLE);
			}
			if(conn != null){
				try{
					ps = conn.prepareStatement("UPDATE "+table+" SET "+updateField+" = ? WHERE "+field+" = ? LIMIT 1");
					ps.setString(1, updateKey);
                                        ps.setString(2, key);
					ps.executeUpdate();
				}catch (SQLException SQLE) {
					log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception in Update ", SQLE);
				}finally{
					try{
						if(ps != null){ ps.close(); }
						if(conn != null){ conn.close(); }
					}catch(SQLException SQLE){
						log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception closing connection ", SQLE);
					}
				}
			}
		}
    
    /**
     *
     * @param key
     * @param field
     * @param table
     * @param updateKey
     * @param updateField
     */
    public void updateIntEntry(String key, String field, String table, int updateKey, String updateField){
			Connection conn = null;
			PreparedStatement ps = null;
			try{
				conn = FiveStarTowns.getConnection();
			}catch(Exception SQLE){
				log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception setting Connection ", SQLE);
			}
			if(conn != null){
				try{
					ps = conn.prepareStatement("UPDATE "+table+" SET "+updateField+" = ? WHERE "+field+" = ? LIMIT 1");
					ps.setInt(1, updateKey);
                                        ps.setString(2, key);
					ps.executeUpdate();
				}catch (SQLException SQLE) {
					log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception in Update ", SQLE);
				}finally{
					try{
						if(ps != null){ ps.close(); }
						if(conn != null){ conn.close(); }
					}catch(SQLException SQLE){
						log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception closing connection ", SQLE);
					}
				}
			}
		}    
    
    /**
     *
     * @param key
     * @param field
     * @param table
     * @param updateKey
     * @param updateField
     */
    public void updateDoubleEntry(String key, String field, String table, double updateKey, String updateField){
			Connection conn = null;
			PreparedStatement ps = null;
			try{
				conn = FiveStarTowns.getConnection();
			}catch(Exception SQLE){
				log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception setting Connection ", SQLE);
			}
			if(conn != null){
				try{
					ps = conn.prepareStatement("UPDATE "+table+" SET "+updateField+" = ? WHERE "+field+" = ? LIMIT 1");
					ps.setDouble(1, updateKey);
                                        ps.setString(2, key);
					ps.executeUpdate();
				}catch (SQLException SQLE) {
					log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception in Update ", SQLE);
				}finally{
					try{
						if(ps != null){ ps.close(); }
						if(conn != null){ conn.close(); }
					}catch(SQLException SQLE){
						log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception closing connection ", SQLE);
					}
				}
			}
		} 
    
    
    /**
     *
     * @param key
     * @param table
     * @param fieldtoget
     * @param field
     * @return
     */
    public String getStringValue(String key, String table, String fieldtoget, String field){ //Can be anything like int double long String float etc....
			String ValueGet = "";
			Connection conn = null;
			PreparedStatement ps = null;
                        ResultSet rs = null;
    		try{
				conn = FiveStarTowns.getConnection();
			}catch(Exception SQLE){
				log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception setting Connection ", SQLE);
			}
                        if(conn != null){ //Dont wanna try executing if the connection isnt set
			try{
				ps = conn.prepareStatement("SELECT "+fieldtoget+" FROM "+table+" WHERE "+field+" = ?");
				ps.setString(1, key);
    			rs = ps.executeQuery(); //Execute Query
    			if (rs.next()){
    				ValueGet = rs.getString(fieldtoget); //Gets the String Value
    			}
			}catch (SQLException SQLE){
				log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception in getValue ", SQLE);
			}finally{
				try{ //Connection Closing
					if(rs != null){ rs.close(); }
					if(ps != null){ ps.close(); }
					if(conn != null){ conn.close(); }
				}catch(SQLException SQLE){
					log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception closing connection ", SQLE);
				}
}
			return ValueGet;
}   
                        return null;
    
    
}
    
        /**
     *
     * @param key
     * @param table
     * @param fieldtoget
     * @param field
     * @return
     */
    public Integer getIntValue(String key, String table, String fieldtoget, String field){ //Can be anything like int double long String float etc....
			int ValueGet = -1;
			Connection conn = null;
			PreparedStatement ps = null;
                        ResultSet rs = null;
    		try{
				conn = FiveStarTowns.getConnection();
			}catch(Exception SQLE){
				log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception setting Connection ", SQLE);
			}
                        if(conn != null){ //Dont wanna try executing if the connection isnt set
			try{
				ps = conn.prepareStatement("SELECT "+fieldtoget+" FROM "+table+" WHERE "+field+" = ?");
				ps.setString(1, key);
    			rs = ps.executeQuery(); //Execute Query
    			if (rs.next()){
    				ValueGet = rs.getInt(fieldtoget); //Gets the String Value
    			}
			}catch (SQLException SQLE){
				log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception in getValue ", SQLE);
			}finally{
				try{ //Connection Closing
					if(rs != null){ rs.close(); }
					if(ps != null){ ps.close(); }
					if(conn != null){ conn.close(); }
				}catch(SQLException SQLE){
					log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception closing connection ", SQLE);
				}
    }
                        return ValueGet;
    }   
                        return null;


    }
        
    /**
     *
     * @param key
     * @param table
     * @param fieldtoget
     * @param field
     * @return
     */
    public Double getDoubleValue(String key, String table, String fieldtoget, String field){ //Can be anything like int double long String float etc....
			double ValueGet = -1;
			Connection conn = null;
			PreparedStatement ps = null;
                        ResultSet rs = null;
    		try{
				conn = FiveStarTowns.getConnection();
			}catch(Exception SQLE){
				log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception setting Connection ", SQLE);
			}
                        if(conn != null){ //Dont wanna try executing if the connection isnt set
			try{
				ps = conn.prepareStatement("SELECT "+fieldtoget+" FROM "+table+" WHERE "+field+" = ?");
				ps.setString(1, key);
    			rs = ps.executeQuery(); //Execute Query
    			if (rs.next()){
    				ValueGet = rs.getDouble(fieldtoget); //Gets the String Value
    			}
			}catch (SQLException SQLE){
				log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception in getValue ", SQLE);
			}finally{
				try{ //Connection Closing
					if(rs != null){ rs.close(); }
					if(ps != null){ ps.close(); }
					if(conn != null){ conn.close(); }
				}catch(SQLException SQLE){
					log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception closing connection ", SQLE);
				}
    }
                        return ValueGet;
    }   
                        return null;


    }
        
    /**
     *
     * @param town
     */
    public void removeChunk(String town){
    			String ValueGet = "";
			Connection conn = null;
			PreparedStatement ps = null;
                        ResultSet rs = null;
    		try{
				conn = FiveStarTowns.getConnection();
			}catch(Exception SQLE){
				log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception setting Connection ", SQLE);
			}
                        if(conn != null){ //Dont wanna try executing if the connection isnt set
			try{
				ps = conn.prepareStatement("SELECT * FROM chunks WHERE town = ?");
				ps.setString(1, town);
                                rs = ps.executeQuery(); //Execute Query
                        
    			while (rs.next()){
    				rs.updateString("town", "Wilderness");
    			}
			}catch (SQLException SQLE){
				log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception in getValue ", SQLE);
			}finally{
				try{ //Connection Closing
					if(rs != null){ rs.close(); }
					if(ps != null){ ps.close(); }
					if(conn != null){ conn.close(); }
				}catch(SQLException SQLE){
					log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception closing connection ", SQLE);
				}
}
                        }
    
    
    
}

    /**
     *
     */
    public void createChunkTable(){
		String mysqltable = ("CREATE TABLE IF NOT EXISTS `chunks` (`ID` INT(255) NOT NULL AUTO_INCREMENT, `coords` varchar(50) NOT NULL, `town` varchar(50) NOT NULL, `owner` varchar(50) NOT NULL, PRIMARY KEY (`ID`))");
		String sqlitetable = ("CREATE TABLE IF NOT EXISTS `chunks` (`ID` integer primary key AUTOINCREMENT, `coords` text, `town` text, `owner` text)");
		Connection conn = null;
		Statement st = null;
		try{
			conn = FiveStarTowns.getConnection();
		}catch(Exception SQLE){
			log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception setting Connection ", SQLE);
		}
		if(conn != null){ //Dont wanna try executing if the connection isnt set
			try{
				st = conn.createStatement(); //Creates Database Statement
				st.executeUpdate(FiveStarTowns.getConfig().useMySQL() ? mysqltable : sqlitetable); //Creates Table
			}catch (SQLException SQLE) {
				log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception creating table ", SQLE);
			}finally{
				try{
					if(st != null){ st.close(); } //Remember to close the connection
					if(conn != null ){ conn.close(); } //Remember to close the connection
				}catch(SQLException SQLE){
					log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception closing connection ", SQLE);
				}
			}

    }
      
}
    
    /**
     *
     */
    public void createExpTable(){
		String mysqltable = ("CREATE TABLE IF NOT EXISTS `experience` (`ID` INT(255) NOT NULL AUTO_INCREMENT, `clan` varchar(50) NOT NULL, `xp` INT(50) NOT NULL, PRIMARY KEY (`ID`))");
		String sqlitetable = ("CREATE TABLE IF NOT EXISTS experience (ID integer primary key AUTOINCREMENT, clan text NOT NULL, xp integer NOT NULL)");
		
                Connection conn = null;
		Statement st = null;
		try{
			conn = FiveStarTowns.getConnection();
		}catch(Exception SQLE){
			log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception setting Connection ", SQLE);
		}
		if(conn != null){ //Dont wanna try executing if the connection isnt set
			try{
				st = conn.createStatement(); //Creates Database Statement
				st.executeUpdate(FiveStarTowns.getConfig().useMySQL() ? mysqltable : sqlitetable); //Creates Table
			}catch (SQLException SQLE) {
				log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception creating table ", SQLE);
			}finally{
				try{
					if(st != null ){ st.close(); } //Remember to close the connection
					if(conn != null ){ conn.close(); } //Remember to close the connection
				}catch(SQLException SQLE){
					log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception closing connection ", SQLE);
				}
			}

    }
      
}

    
            /**
     *
     * @param name
     * @param owner
     * @param assistant
     */
    public void insertTown(String name, String owner, String assistant){
			Connection conn = null;
			PreparedStatement ps = null;
			try{
				conn = FiveStarTowns.getConnection();
			}catch(Exception SQLE){
				log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception setting Connection ", SQLE);
			}
			if(conn != null){
				try{
					ps = conn.prepareStatement("INSERT INTO towns (name, owner, assistant, bonus, balance, farewellmsg, welcomemsg, alliance, friendlyfire, nopvp, protected, creepernerf, sanctuary) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)"); //For multiple values use Table (Value1, Value2, Value3) VALUES(?,?,?)  then ps.setString(1, val); ps.setString(2, val); ps.setString(3, val);
					ps.setString(1, name);
                                        ps.setString(2, owner);
                                        ps.setString(3, assistant);
                                        ps.setInt(4, 0);
                                        ps.setDouble(5, 0);
                                        ps.setString(6, "You are now leaving " + name);
                                        ps.setString(7, "You are now entering " + name);
                                        ps.setString(8, "");
                                        ps.setInt(9, 0);
                                        ps.setInt(10, 0);
                                        ps.setInt(11, 0);
                                        ps.setInt(12, 0);
                                        ps.setInt(13, 0);
					ps.executeUpdate(); //Execute InsertChunk
				} catch (SQLException SQLE) { //May be thrown for cases where Key Already Exists
					log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception in Insert ", SQLE);
				}finally{
					try{
						if(ps != null ){ ps.close(); }
						if(conn != null ){ conn.close(); }
					}catch(SQLException SQLE){
						log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception closing connection ", SQLE);
					}
				}
			}
		}
    
    /**
     *
     */
    public void createTownTable(){
		String mysqltable = ("CREATE TABLE IF NOT EXISTS `towns` (`ID` INT(255) NOT NULL AUTO_INCREMENT, `name` varchar(50) NOT NULL, `owner` varchar(50) NOT NULL, `assistant` varchar(50) NOT NULL, `bonus` INT(5) NOT NULL, `balance` DECIMAL(50)NOT NULL, `farewellmsg` varchar(50) NOT NULL, `welcomemsg` varchar(50) NOT NULL, `alliance` varchar(50) NOT NULL, `friendlyfire` INT(1) NOT NULL, `nopvp` INT(1) NOT NULL, `protected` INT(1) NOT NULL, `creepernerf` INT(1) NOT NULL, `sanctuary` INT(1) NOT NULL, PRIMARY KEY (`ID`))");
		String sqlitetable = ("CREATE TABLE IF NOT EXISTS `towns` (`ID` integer primary key AUTOINCREMENT , `name` text, `owner` text, `assistant` text, `bonus` integer, `balance` real, `farewellmsg` text, `welcomemsg` text, `alliance` text, `friendlyfire` integer, `nopvp` integer, `protected` integer, `creepernerf` integer, `sanctuary` integer)");
		Connection conn = null;
		Statement st = null;
		try{
			conn = FiveStarTowns.getConnection();
		}catch(Exception SQLE){
			log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception setting Connection ", SQLE);
		}
		if(conn != null){ //Dont wanna try executing if the connection isnt set
			try{
				st = conn.createStatement(); //Creates Database Statement
				st.executeUpdate(FiveStarTowns.getConfig().useMySQL() ? mysqltable : sqlitetable); //Creates Table
			}catch (SQLException SQLE) {
				log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception creating table ", SQLE);
			}finally{
				try{
					if(st != null){ st.close(); } //Remember to close the connection
					if(conn != null ){ conn.close(); } //Remember to close the connection
				}catch(SQLException SQLE){
					log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception closing connection ", SQLE);
				}
			}

                }
      
    }
    
    /**
     *
     */
    public void createUserTable(){
		String mysqltable = ("CREATE TABLE IF NOT EXISTS `townsusers` (`ID` INT(255) NOT NULL AUTO_INCREMENT, `username` varchar(50) NOT NULL, `town` varchar(50) NOT NULL, PRIMARY KEY (`ID`))");
		String sqlitetable = ("CREATE TABLE IF NOT EXISTS `townsusers` (`ID` integer primary key AUTOINCREMENT, `username` text, `town` text)");
		
                Connection conn = null;
		Statement st = null;
		try{
			conn = FiveStarTowns.getConnection();
		}catch(Exception SQLE){
			log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception setting Connection ", SQLE);
		}
		if(conn != null){ //Dont wanna try executing if the connection isnt set
			try{
				st = conn.createStatement(); //Creates Database Statement
				st.executeUpdate(FiveStarTowns.getConfig().useMySQL() ? mysqltable : sqlitetable); //Creates Table
			}catch (SQLException SQLE) {
				log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception creating table ", SQLE);
			}finally{
				try{
					if(st != null){ st.close(); } //Remember to close the connection
					if(conn != null ){ conn.close(); } //Remember to close the connection
				}catch(SQLException SQLE){
					log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception closing connection ", SQLE);
				}
			}

                }
      
    }
    
    /**
     *
     * @param townname
     * @return
     */
    public List getTownPlayers(String townname){
        String ValueGet = "";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<String> players = new ArrayList<String>();
    		try{
				conn = FiveStarTowns.getConnection();
			}catch(Exception SQLE){
				log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception setting Connection ", SQLE);
			}
                        if(conn != null){ //Dont wanna try executing if the connection isnt set
			try{
				ps = conn.prepareStatement("SELECT * FROM townsusers WHERE town = ?");
				ps.setString(1, townname);
                                rs = ps.executeQuery(); //Execute Query
                        
    			while (rs.next()){
                                players.add(rs.getString("username"));
    			}
			}catch (SQLException SQLE){
				log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception in getValue ", SQLE);
			}finally{
				try{ //Connection Closing
					if(rs != null){ rs.close(); }
					if(ps != null){ ps.close(); }
					if(conn != null){ conn.close(); }
				}catch(SQLException SQLE){
					log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception closing connection ", SQLE);
				}
}
                        }
                        return players;
    }
    
    /**
     *
     * @param worldName
     */
    public void convert(String worldName){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean exists = false;
        try{
                conn = FiveStarTowns.getConnection();
        }catch(Exception SQLE){
                log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception setting Connection ", SQLE);
        }
        if(conn != null){ //Dont wanna try executing if the connection isnt set
                try{
                        
                        ps = conn.prepareStatement("SELECT * FROM chunks"); //Prepares a Statement
                        rs = ps.executeQuery(); //Executes a ResultSet
                        while (rs.next()){
                            rs.updateString("coords", rs.getNString("coords") + ":" + worldName);
                        } //ResultSet was returned so key exists
                } catch (SQLException ex) {
                        log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception in convert ", ex);
                }finally{
                        try{//Close Connections
                                if(rs != null ){ rs.close(); }
                                if(ps != null ){ ps.close(); }
                                if(conn != null ){ conn.close(); }
                        }catch(SQLException SQLE){
                                log.log(Level.SEVERE, "[FiveStarTowns] - SQL Exception closing connection ", SQLE);
                        }
                }
        }

    }

}

    
    
    
    
    
    
    
