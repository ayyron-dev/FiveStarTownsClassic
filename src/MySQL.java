import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQL {
    private Logger log=Logger.getLogger("Minecraft");
    MySQLConnector connector = new MySQLConnector();

    
    public boolean keyExists(String key, String table, String field){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean exists = false;
        try{
                conn = connector.getConnection();
        }catch(Exception SQLE){
                log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception setting Connection ", SQLE);
        }
        if(conn != null){ //Dont wanna try executing if the connection isnt set
                try{
                        
                        ps = conn.prepareStatement("SELECT * FROM "+table+" WHERE "+field+" = ?"); //Prepares a Statement
                        ps.setString(1, key); //Sets the ? to the String "Key"
                        rs = ps.executeQuery(); //Executes a ResultSet
                        if (rs.next()){exists = true; } //ResultSet was returned so key exists
                } catch (SQLException ex) {
                        log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception in keyExsists ", ex);
                }finally{
                        try{//Close Connections
                                if(rs != null && !rs.isClosed()){ rs.close(); }
                                if(ps != null && !ps.isClosed()){ ps.close(); }
                                if(conn != null && !conn.isClosed()){ conn.close(); }
                        }catch(SQLException SQLE){
                                log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception closing connection ", SQLE);
                        }
                }
                if(exists){return true;}
                else{return false;}
        }
        return false;

}
    
    public void delete(String table, String field, String key){
        Connection conn = null;
			PreparedStatement ps = null;
			try{
				conn = connector.getConnection();
			}catch(Exception SQLE){
				log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception setting Connection ", SQLE);
			}
			if(conn != null){
				try{
					ps = conn.prepareStatement("DELETE FROM "+table+" WHERE "+field+"=?"); //For multiple values use Table (Value1, Value2, Value3) VALUES(?,?,?)  then ps.setString(1, val); ps.setString(2, val); ps.setString(3, val);
					ps.setString(1, key);
					ps.executeUpdate(); //Execute InsertChunk
				} catch (SQLException SQLE) { //May be thrown for cases where Key Already Exists
					log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception in Insert ", SQLE);
				}finally{
					try{
						if(ps != null && !ps.isClosed()){ ps.close(); }
						if(conn != null && !conn.isClosed()){ conn.close(); }
					}catch(SQLException SQLE){
						log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception closing connection ", SQLE);
					}
				}
			}
    }
    
    public void InsertChunk(String coords, String strang){
			Connection conn = null;
			PreparedStatement ps = null;
			try{
				conn = connector.getConnection();
			}catch(Exception SQLE){
				log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception setting Connection ", SQLE);
			}
			if(conn != null){
				try{
					ps = conn.prepareStatement("INSERT INTO chunks (coords, town) VALUES(?,?)"); //For multiple values use Table (Value1, Value2, Value3) VALUES(?,?,?)  then ps.setString(1, val); ps.setString(2, val); ps.setString(3, val);
					ps.setString(1, coords);
                                        ps.setString(2, strang);
					ps.executeUpdate(); //Execute InsertChunk
				} catch (SQLException SQLE) { //May be thrown for cases where Key Already Exists
					log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception in Insert ", SQLE);
				}finally{
					try{
						if(ps != null && !ps.isClosed()){ ps.close(); }
						if(conn != null && !conn.isClosed()){ conn.close(); }
					}catch(SQLException SQLE){
						log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception closing connection ", SQLE);
					}
				}
			}
		}
    

        
            public void insertTownPlayer(String name, String town){
			Connection conn = null;
			PreparedStatement ps = null;
			try{
				conn = connector.getConnection();
			}catch(Exception SQLE){
				log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception setting Connection ", SQLE);
			}
			if(conn != null){
				try{
					ps = conn.prepareStatement("INSERT INTO townsusers (username, town) VALUES(?,?)"); //For multiple values use Table (Value1, Value2, Value3) VALUES(?,?,?)  then ps.setString(1, val); ps.setString(2, val); ps.setString(3, val);
					ps.setString(1, name);
                                        ps.setString(2, town);
					ps.executeUpdate(); //Execute InsertChunk
				} catch (SQLException SQLE) { //May be thrown for cases where Key Already Exists
					log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception in Insert ", SQLE);
				}finally{
					try{
						if(ps != null && !ps.isClosed()){ ps.close(); }
						if(conn != null && !conn.isClosed()){ conn.close(); }
					}catch(SQLException SQLE){
						log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception closing connection ", SQLE);
					}
				}
			}
		}
    
    public void updateStringEntry(String key, String field, String table, String updateKey, String updateField){
			Connection conn = null;
			PreparedStatement ps = null;
			try{
				conn = connector.getConnection();
			}catch(Exception SQLE){
				log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception setting Connection ", SQLE);
			}
			if(conn != null){
				try{
					ps = conn.prepareStatement("UPDATE "+table+" SET "+updateField+" = ? WHERE "+field+" = ? LIMIT 1");
					ps.setString(1, updateKey);
                                        ps.setString(2, key);
					ps.executeUpdate();
				}catch (SQLException SQLE) {
					log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception in Update ", SQLE);
				}finally{
					try{
						if(ps != null){ ps.close(); }
						if(conn != null){ conn.close(); }
					}catch(SQLException SQLE){
						log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception closing connection ", SQLE);
					}
				}
			}
		}
    
    public void updateIntEntry(String key, String field, String table, int updateKey, String updateField){
			Connection conn = null;
			PreparedStatement ps = null;
			try{
				conn = connector.getConnection();
			}catch(Exception SQLE){
				log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception setting Connection ", SQLE);
			}
			if(conn != null){
				try{
					ps = conn.prepareStatement("UPDATE "+table+" SET "+updateField+" = ? WHERE "+field+" = ? LIMIT 1");
					ps.setInt(1, updateKey);
                                        ps.setString(2, key);
					ps.executeUpdate();
				}catch (SQLException SQLE) {
					log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception in Update ", SQLE);
				}finally{
					try{
						if(ps != null){ ps.close(); }
						if(conn != null){ conn.close(); }
					}catch(SQLException SQLE){
						log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception closing connection ", SQLE);
					}
				}
			}
		}    
    
    public void updateDoubleEntry(String key, String field, String table, double updateKey, String updateField){
			Connection conn = null;
			PreparedStatement ps = null;
			try{
				conn = connector.getConnection();
			}catch(Exception SQLE){
				log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception setting Connection ", SQLE);
			}
			if(conn != null){
				try{
					ps = conn.prepareStatement("UPDATE "+table+" SET "+updateField+" = ? WHERE "+field+" = ? LIMIT 1");
					ps.setDouble(1, updateKey);
                                        ps.setString(2, key);
					ps.executeUpdate();
				}catch (SQLException SQLE) {
					log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception in Update ", SQLE);
				}finally{
					try{
						if(ps != null){ ps.close(); }
						if(conn != null){ conn.close(); }
					}catch(SQLException SQLE){
						log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception closing connection ", SQLE);
					}
				}
			}
		} 
    
    
    public String getStringValue(String key, String table, String fieldtoget, String field){ //Can be anything like int double long String float etc....
			String ValueGet = "";
			Connection conn = null;
			PreparedStatement ps = null;
                        ResultSet rs = null;
    		try{
				conn = connector.getConnection();
			}catch(Exception SQLE){
				log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception setting Connection ", SQLE);
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
				log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception in getValue ", SQLE);
			}finally{
				try{ //Connection Closing
					if(rs != null){ rs.close(); }
					if(ps != null){ ps.close(); }
					if(conn != null){ conn.close(); }
				}catch(SQLException SQLE){
					log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception closing connection ", SQLE);
				}
}
			return ValueGet;
}   
                        return null;
    
    
}
    
        public Integer getIntValue(String key, String table, String fieldtoget, String field){ //Can be anything like int double long String float etc....
			int ValueGet = -1;
			Connection conn = null;
			PreparedStatement ps = null;
                        ResultSet rs = null;
    		try{
				conn = connector.getConnection();
			}catch(Exception SQLE){
				log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception setting Connection ", SQLE);
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
				log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception in getValue ", SQLE);
			}finally{
				try{ //Connection Closing
					if(rs != null){ rs.close(); }
					if(ps != null){ ps.close(); }
					if(conn != null){ conn.close(); }
				}catch(SQLException SQLE){
					log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception closing connection ", SQLE);
				}
    }
                        return ValueGet;
    }   
                        return null;


    }
        
    public Double getDoubleValue(String key, String table, String fieldtoget, String field){ //Can be anything like int double long String float etc....
			double ValueGet = -1;
			Connection conn = null;
			PreparedStatement ps = null;
                        ResultSet rs = null;
    		try{
				conn = connector.getConnection();
			}catch(Exception SQLE){
				log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception setting Connection ", SQLE);
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
				log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception in getValue ", SQLE);
			}finally{
				try{ //Connection Closing
					if(rs != null){ rs.close(); }
					if(ps != null){ ps.close(); }
					if(conn != null){ conn.close(); }
				}catch(SQLException SQLE){
					log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception closing connection ", SQLE);
				}
    }
                        return ValueGet;
    }   
                        return null;


    }
        
    public void removeChunk(String town){
    			String ValueGet = "";
			Connection conn = null;
			PreparedStatement ps = null;
                        ResultSet rs = null;
    		try{
				conn = connector.getConnection();
			}catch(Exception SQLE){
				log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception setting Connection ", SQLE);
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
				log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception in getValue ", SQLE);
			}finally{
				try{ //Connection Closing
					if(rs != null){ rs.close(); }
					if(ps != null){ ps.close(); }
					if(conn != null){ conn.close(); }
				}catch(SQLException SQLE){
					log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception closing connection ", SQLE);
				}
}
                        }
    
    
    
}

    public void createChunkTable(){
		String table = ("CREATE TABLE IF NOT EXISTS `chunks` (`ID` INT(255) NOT NULL AUTO_INCREMENT, `coords` varchar(50) NOT NULL, `town` varchar(50) NOT NULL, PRIMARY KEY (`ID`))");
		Connection conn = null;
		Statement st = null;
		try{
			conn = connector.getConnection();
		}catch(Exception SQLE){
			log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception setting Connection ", SQLE);
		}
		if(conn != null){ //Dont wanna try executing if the connection isnt set
			try{
				st = conn.createStatement(); //Creates MySQL Statement
				st.executeUpdate(table); //Creates Table
			}catch (SQLException SQLE) {
				log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception creating table ", SQLE);
			}finally{
				try{
					if(st != null && !st.isClosed()){ st.close(); } //Remember to close the connection
					if(conn != null && !conn.isClosed()){ conn.close(); } //Remember to close the connection
				}catch(SQLException SQLE){
					log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception closing connection ", SQLE);
				}
			}

    }
      
}

    
            public void insertTown(String name, String owner, String assistant){
			Connection conn = null;
			PreparedStatement ps = null;
			try{
				conn = connector.getConnection();
			}catch(Exception SQLE){
				log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception setting Connection ", SQLE);
			}
			if(conn != null){
				try{
					ps = conn.prepareStatement("INSERT INTO towns (name, owner, assistant, bonus, balance, farewellmsg, welcomemsg, alliance, friendlyfire, nopvp, protected, crepernerf, sanctuary) VALUES(?,?,?,?,?,?,?,?,?)"); //For multiple values use Table (Value1, Value2, Value3) VALUES(?,?,?)  then ps.setString(1, val); ps.setString(2, val); ps.setString(3, val);
					ps.setString(1, name);
                                        ps.setString(2, owner);
                                        ps.setString(3, assistant);
                                        ps.setInt(4, 0);
                                        ps.setDouble(5, 0);
                                        ps.setString(6, "You are now leaving " + name);
                                        ps.setString(7, "You are now entering " + name);
                                        ps.setString(8, "");
                                        ps.setString(9, "false");
					ps.executeUpdate(); //Execute InsertChunk
				} catch (SQLException SQLE) { //May be thrown for cases where Key Already Exists
					log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception in Insert ", SQLE);
				}finally{
					try{
						if(ps != null && !ps.isClosed()){ ps.close(); }
						if(conn != null && !conn.isClosed()){ conn.close(); }
					}catch(SQLException SQLE){
						log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception closing connection ", SQLE);
					}
				}
			}
		}
    
    public void createTownTable(){
		String table = ("CREATE TABLE IF NOT EXISTS `towns` (`ID` INT(255) NOT NULL AUTO_INCREMENT, `name` varchar(50) NOT NULL, `owner` varchar(50) NOT NULL, `assistant` varchar(50) NOT NULL, `bonus` INT(5) NOT NULL, `balance` DECIMAL(50)NOT NULL, `farewellmsg` varchar(50) NOT NULL, `welcomemsg` varchar(50) NOT NULL, `alliance` varchar(50) NOT NULL, `friendlyfire` INT(1) NOT NULL, `nopvp` INT(1) NOT NULL, `protected` INT(1) NOT NULL, `creepernerf` INT(1) NOT NULL, `sanctuary` INT(1) NOT NULL, PRIMARY KEY (`ID`))");
		Connection conn = null;
		Statement st = null;
		try{
			conn = connector.getConnection();
		}catch(Exception SQLE){
			log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception setting Connection ", SQLE);
		}
		if(conn != null){ //Dont wanna try executing if the connection isnt set
			try{
				st = conn.createStatement(); //Creates MySQL Statement
				st.executeUpdate(table); //Creates Table
			}catch (SQLException SQLE) {
				log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception creating table ", SQLE);
			}finally{
				try{
					if(st != null && !st.isClosed()){ st.close(); } //Remember to close the connection
					if(conn != null && !conn.isClosed()){ conn.close(); } //Remember to close the connection
				}catch(SQLException SQLE){
					log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception closing connection ", SQLE);
				}
			}

                }
      
    }
    
    public void createUserTable(){
		String table = ("CREATE TABLE IF NOT EXISTS `townsusers` (`ID` INT(255) NOT NULL AUTO_INCREMENT, `username` varchar(50) NOT NULL, `town` varchar(50) NOT NULL, PRIMARY KEY (`ID`))");
		Connection conn = null;
		Statement st = null;
		try{
			conn = connector.getConnection();
		}catch(Exception SQLE){
			log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception setting Connection ", SQLE);
		}
		if(conn != null){ //Dont wanna try executing if the connection isnt set
			try{
				st = conn.createStatement(); //Creates MySQL Statement
				st.executeUpdate(table); //Creates Table
			}catch (SQLException SQLE) {
				log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception creating table ", SQLE);
			}finally{
				try{
					if(st != null && !st.isClosed()){ st.close(); } //Remember to close the connection
					if(conn != null && !conn.isClosed()){ conn.close(); } //Remember to close the connection
				}catch(SQLException SQLE){
					log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception closing connection ", SQLE);
				}
			}

                }
      
    }
    
    public List getTownPlayers(String townname){
        String ValueGet = "";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<String> players = new ArrayList<String>();
    		try{
				conn = connector.getConnection();
			}catch(Exception SQLE){
				log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception setting Connection ", SQLE);
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
				log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception in getValue ", SQLE);
			}finally{
				try{ //Connection Closing
					if(rs != null){ rs.close(); }
					if(ps != null){ ps.close(); }
					if(conn != null){ conn.close(); }
				}catch(SQLException SQLE){
					log.log(Level.SEVERE, "[StunnerTowns] - SQL Exception closing connection ", SQLE);
				}
}
                        }
                        return players;
    }

}

    
    
    
    
    
    
    
