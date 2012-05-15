
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Logger;


public class TownManager {
    
    private Logger log=Logger.getLogger("Minecraft");
    private StunnerTowns plugin;
    MySQL mysql = new MySQL();
    public HashMap<String, String> chunkmap = new HashMap();
    
    public TownManager(StunnerTowns plugin){
        this.plugin = plugin;
    }
    
    public void put(String key, String value){
       chunkmap.put(key, value);
    }
    
    public void remove(String key){
        chunkmap.remove(key);
    }
    
    public boolean containsKey(String key){
        return chunkmap.containsKey(key);
    }
    
    public String get(String key){
        return chunkmap.get(key);
    }
    
    public Town getTown(String name){
        if(mysql.keyExists(name, "towns", "name")){
            return new Town(name);
        }
        return null;
    }
    
    public TownPlayer getTownPlayer(String name){
        Player player = etc.getServer().getPlayer(name);
        if(player != null && mysql.keyExists(name, "townsusers", "username")){
            return new TownPlayer(player);
        }
        return null;
    }
            
    
    
    public boolean chunksConnected(int x, int z, String faction){
        int xx = x >> 4;
        int zz = z >> 4;
//        String chunky = (xx) + ":" + (zz);
        String check1 = (xx + 1) + ":" + (zz);
        String check2 = (xx - 1) + ":" + (zz);
        String check3 = (xx) + ":" + (zz + 1);
        String check4 = (xx) + ":" + (zz - 1);
        if(chunkmap.containsKey(check1)){
            if(chunkmap.get(check1).equalsIgnoreCase(faction)){
            return true;}
            }
        if(chunkmap.containsKey(check2)){
            if(chunkmap.get(check2).equalsIgnoreCase(faction)){
            return true;}
            }
        if(chunkmap.containsKey(check3)){
            if(chunkmap.get(check3).equalsIgnoreCase(faction)){
            return true;}
            }
        if(chunkmap.containsKey(check4)){
            if(chunkmap.get(check4).equalsIgnoreCase(faction)){
            return true;}
            }
        
        
        return false;
    }
    
    public int chunkAmount(String faction){
        String chunky = chunkmap.toString();
        String chunky1 = chunky.replace("{", "");
        String chunky2 = chunky1.replace("}", "");
        String[] chunky3 = chunky2.split(",");
        int chunksowned = 0;
        for(int i = 0; i < chunky3.length; i++){
            String[] chunky4 = chunky3[i].split("=");
            if(chunky4.length > 1 && chunky4[1].equalsIgnoreCase(faction)){
                chunksowned++;
            }
        }
        return chunksowned;
    }
        
    public boolean chunkReturn(Player player){
        int chunkx = (int)player.getX() >> 4;
        int chunkz = (int)player.getZ() >> 4;
        String chunky = chunkx + ":" + chunkz;
        String faction = "Wilderness";
        TownPlayer tp = getTownPlayer(player.getOfflineName());
        if(tp == null){return false;}
        String playerfaction = tp.getTownName();
        if(chunkmap.containsKey(chunky)){
            faction = chunkmap.get(chunky);}
        if(playerfaction.equalsIgnoreCase(faction)){
            return true;
        }
        if(faction.equalsIgnoreCase("Wilderness")){
        return true;
        }
        if(!playerfaction.equalsIgnoreCase(faction) && !faction.equalsIgnoreCase("Wilderness")){
            return false;
        }
        return true;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void AddHashMap(){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        try{
        conn = new MySQLConnector().getConnection();
        ps = conn.prepareStatement("SELECT * FROM chunks");
        result = ps.executeQuery();
        while(result.next()){
            chunkmap.put(result.getString("coords"), result.getString("town"));
        }
        
        }
        catch(SQLException e){
            log.severe("[StunnerTowns] - SQL Exception creating HashMap: " + e.toString());
        }
        finally {
            try {
              if (ps != null) {
                ps.close();
              }
              if (result != null) {
                result.close();
              }
              if (conn != null){
                conn.close();
              }
            }
        catch (SQLException e) {
            log.severe("[StunnerTowns] - SQL Exception closing HashMap connection: " + e.toString());
        }
        }
        log.info("[StunnerTowns] Chunk Data Loaded");
    }




}
