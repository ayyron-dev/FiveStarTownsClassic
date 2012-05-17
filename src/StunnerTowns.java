
import java.util.logging.Logger;



public class StunnerTowns extends Plugin{
    private Logger log=Logger.getLogger("Minecraft");
    public static String name = "StunnerTowns";
    public static String version = "Beta Build 1";
    public static String creator = "5*stunner";
    private static StunnerTowns instance;
    private StunnerConfig stunnerconfig;
    private TownManager townmanager;
    private StunnerCommandListener scl;
    private StunnerMoveListener sml;
    private StunnerChat stunnerchat;
    private MySQLConnector connector = new MySQLConnector();
    private TownRankManager townrank;
    private MayorCommandListener mayorcommandlistener;
    private AssistantCommandListener assistantcommandlistener;
    private MemberCommandListener membercommandlistener;
    
    @Override
     public void disable() {    
        log.info(name + " version " + version + " disabled.");

    }
    @Override
    public void enable() {
        instance = this;
        stunnerconfig = new StunnerConfig(this);
        townmanager = new TownManager(this);
        scl = new StunnerCommandListener();
        mayorcommandlistener = new MayorCommandListener();
        assistantcommandlistener = new AssistantCommandListener();
        membercommandlistener = new MemberCommandListener();
        sml = new StunnerMoveListener();
        stunnerchat = new StunnerChat();
        townrank = new TownRankManager();
        log.info(name + " version " + version + " enabled.");
        connector.initialize();
        townrank.loadTownRanks();
        MySQL mysql = new MySQL();
        mysql.createChunkTable();
        mysql.createTownTable();
        mysql.createUserTable();
        stunnerconfig.loadConfig();
        stunnerchat.loadFiles();
        townmanager.AddHashMap();
        
    }
    @Override
    public void initialize() {
        log.info(name + " version " + version + " by " + creator + " has been initialized.");
        etc.getLoader().addListener(PluginLoader.Hook.COMMAND, scl, this, PluginListener.Priority.MEDIUM);
        etc.getLoader().addListener(PluginLoader.Hook.COMMAND, mayorcommandlistener, this, PluginListener.Priority.MEDIUM);
        etc.getLoader().addListener(PluginLoader.Hook.COMMAND, assistantcommandlistener, this, PluginListener.Priority.MEDIUM);
        etc.getLoader().addListener(PluginLoader.Hook.COMMAND, membercommandlistener, this, PluginListener.Priority.MEDIUM);
        etc.getLoader().addListener(PluginLoader.Hook.PLAYER_MOVE, sml, this, PluginListener.Priority.MEDIUM);
        etc.getLoader().addListener(PluginLoader.Hook.CHAT, stunnerchat, this, PluginListener.Priority.MEDIUM);
        etc.getLoader().addListener(PluginLoader.Hook.COMMAND, stunnerchat, this, PluginListener.Priority.MEDIUM);

    }
    
    public static StunnerTowns getInstance(){
        return instance;
    }
    
    public StunnerConfig getConfig(){
        return stunnerconfig;
    }
    
    public TownManager getManager(){
        return townmanager;
    }
    
    public TownRankManager getTownRankManager(){
        return townrank;
    }
    
    public StunnerCommandListener getCommandListener(){
        return scl;
    }
    
}
