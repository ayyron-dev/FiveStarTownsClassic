import java.io.*;
import java.util.logging.Logger;

public class StunnerConfig {
    
    StunnerTowns plugin;
    private boolean useFactions;
    private boolean pvp;
    private boolean protection;
    private int claimrank;
    private String servername;
    private int chunkmultiplier;
    private boolean mysql;
    private boolean useChat;
    private double townCost;
    private double landCost;
    private String townColor;
    private String chatSyntax;
    private boolean useDCO;
    
    private Logger log=Logger.getLogger("Minecraft");
    PropertiesFile settings;
    PropertiesFile chat;
    File dir = new File("plugins/config/StunnerTowns");
    
    public StunnerConfig(StunnerTowns plugin){
        this.plugin = plugin;
    }
    
    public void loadConfig(){
            if(!dir.exists()){
                dir.mkdirs();
            }
        settings = new PropertiesFile("plugins/config/StunnerTowns/StunnerTowns.properties");
        chat = new PropertiesFile("plugins/config/StunnerTowns/StunnerChat.properties");
        mysql = settings.getBoolean("use-canary-mysql", true);
        pvp = settings.getBoolean("pvp-off-in-towns", true);
        protection = settings.getBoolean("protection-on-in-towns", true);
        claimrank = settings.getInt("rank-to-claim-land", 1);
        servername = settings.getString("server-name", "Server");
        chunkmultiplier = settings.getInt("plot-multiplier-per-member", 3);
        useFactions = settings.getBoolean("use-factions", false);
        townCost = settings.getDouble("town-cost", 1000);
        useDCO = settings.getBoolean("use-dConomy", true);
        landCost = settings.getDouble("bonus-land-cost", 100);
        useChat = chat.getBoolean("use-chat", true);
        chatSyntax = chat.getString("chat-syntax", "");
        townColor = chat.getString("town-chat-color","2");
        
        
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
    
    public boolean useChat(){
        return useChat;
    }
    
    public String getChatSyntax(){
        return chatSyntax;
    }
    
    public String getTownColor(){
        return townColor;
    }
    
    public double getTownCost(){
        return townCost;
    }
    
    public boolean getUseDCO(){
        return useDCO;
    }
    
    public double getLandCost(){
        return landCost;
    }
}
