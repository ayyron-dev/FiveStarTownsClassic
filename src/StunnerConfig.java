import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Somners
 */
public class StunnerConfig {
    
    FiveStarTowns plugin;
    private boolean useFactions;
    private boolean pvp;
    private boolean protection;
    private int claimrank;
    private String servername;
    private int chunkmultiplier;
    private boolean useCanaryMysql;
    private boolean useChat;
    private double townCost;
    private double landCost;
    private String townColor;
    private String chatSyntax;
    private boolean useDCO;
    private String availableWorlds;
    private boolean useMysql;
    private String os;
    private boolean pillarProtection;
    
    private Logger log=Logger.getLogger("Minecraft");
    PropertiesFile settings;
    PropertiesFile chat;
    File dir = new File("plugins/config/FiveStarTowns");
    
    /**
     *
     * @param plugin
     */
    public StunnerConfig(FiveStarTowns plugin){
        this.plugin = plugin;
    }
    
    /**
     *
     */
    public void loadConfig(){
            if(!dir.exists()){
                dir.mkdirs();
            }
        settings = new PropertiesFile("plugins/config/FiveStarTowns/FiveStarTowns.properties");
        chat = new PropertiesFile("plugins/config/FiveStarTowns/FiveStarChat.properties");
        useCanaryMysql = settings.getBoolean("use-canary-mysql", true);
        servername = settings.getString("server-name", "Server");
        chunkmultiplier = settings.getInt("plot-multiplier-per-member", 3);
        townCost = settings.getDouble("town-cost", 1000);
        useDCO = settings.getBoolean("use-dConomy", true);
        landCost = settings.getDouble("bonus-land-cost", 100);
        useChat = chat.getBoolean("use-chat", true);
        chatSyntax = chat.getString("chat-syntax", "");
        townColor = chat.getString("town-chat-color","2");
        availableWorlds = chat.getString("available-worlds", "");
        useMysql = settings.getBoolean("use-mysql", true);
        os = settings.getString("operating-system", "unix");
        pillarProtection = settings.getBoolean("use-extra-pillar-protection", false);
        log.info("[FiveStarTowns] Plugin Config Loaded!");
        log.info("[FiveStarTowns] Chat Config Loaded!");
        
    }
    
    /**
     *
     * @return
     */
    public boolean useCanaryMysql(){
        return useCanaryMysql;
    }
    
    /**
     *
     * @return
     */
    public boolean getPvP(){
        return pvp;
    }
    
    /**
     *
     * @return
     */
    public boolean getProtection(){
        return protection;
    }
    
    /**
     *
     * @return
     */
    public boolean getUseFactions(){
        return useFactions;
    }
    
    /**
     *
     * @return
     */
    public Integer getClaimRank(){
        return claimrank;
    }
    
    /**
     *
     * @return
     */
    public Integer getChunkMultiplier(){
        return chunkmultiplier;
    }
    
    /**
     *
     * @return
     */
    public String getServerName(){
        return servername;
    }
    
    /**
     *
     * @return
     */
    public boolean useChat(){
        return useChat;
    }
    
    /**
     *
     * @return
     */
    public String getChatSyntax(){
        return chatSyntax;
    }
    
    /**
     *
     * @return
     */
    public String getTownColor(){
        return townColor;
    }
    
    /**
     *
     * @return
     */
    public double getTownCost(){
        return townCost;
    }
    
    /**
     *
     * @return
     */
    public boolean getUseDCO(){
        return useDCO;
    }
    
    /**
     *
     * @return
     */
    public double getLandCost(){
        return landCost;
    }
    
    /**
     *
     * @return
     */
    public List getAvailableWorlds(){
        String[] aw = availableWorlds.split(",");
        List worlds = new ArrayList();
        for(int i = 0; i < aw.length ; i++){
            worlds.add(aw[i].trim());
        }
        return worlds;
    }
    
    /**
     *
     * @return
     */
    public boolean useMySQL(){
        return useMysql;
    }
    
    /**
     *
     * @return
     */
    public String getOS(){
        return os;
    }
    
    /**
     *
     * @return
     */
    public boolean getPillarProtection(){
        return pillarProtection;
    }
}
