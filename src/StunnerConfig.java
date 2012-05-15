import java.io.*;
import java.util.logging.Logger;

public class StunnerConfig {
    
    StunnerTowns plugin;
    public boolean useFactions;
    public boolean pvp;
    public boolean protection;
    public int claimrank;
    public String servername;
    public int chunkmultiplier;
    public boolean mysql;
    private Logger log=Logger.getLogger("Minecraft");
    PropertiesFile settings;
    File dir = new File("plugins/config/StunnerTowns");
    
    public StunnerConfig(StunnerTowns plugin){
        this.plugin = plugin;
    }
    
    public void loadConfig(){
            if(!dir.exists()){
                dir.mkdirs();
            }
        settings = new PropertiesFile("plugins/config/StunnerTowns/StunnerTowns.properties");
        mysql = settings.getBoolean("use-canary-mysql", true);
        pvp = settings.getBoolean("pvp-off-in-towns", true);
        protection = settings.getBoolean("protection-on-in-towns", true);
        claimrank = settings.getInt("rank-to-claim-land", 1);
        servername = settings.getString("server-name", "Server");
        chunkmultiplier = settings.getInt("plot-multiplier-per-member", 3);
        useFactions = settings.getBoolean("use-factions", false);  
    }
    
    public boolean getMySQL(){
        return mysql;
    }
    
    public boolean getPvP(){
        return pvp;
    }
    
    public boolean getProtection(){
        return protection;
    }
    
    public boolean getUseFactions(){
        return useFactions;
    }
    
    public Integer getClaimRank(){
        return claimrank;
    }
    
    public Integer getChunkMultiplier(){
        return chunkmultiplier;
    }
    
    public String getServerName(){
        return servername;
    }

}
