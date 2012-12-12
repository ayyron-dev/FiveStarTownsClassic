
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


/**
 *
 * @author Somners
 */
public class FiveStarTowns extends Plugin{


    /**
     *
     */
    public static final Logger log=Logger.getLogger("Minecraft");
    /**
     *
     */
    public static String name = "FiveStarTowns";
    /**
     *
     */
    public static String version = "Beta Build 5";
    /**
     *
     */
    public static String creator = "somners";
    private static FiveStarTowns instance;
    private static StunnerConfig stunnerconfig;
    private static TownManager townmanager;
    private static StunnerCommandListener scl;
    private static StunnerMoveListener sml;
    private static StunnerChat stunnerchat;
    private static TownRankManager townrank;
    private static OwnerCommandListener mayorcommandlistener;
    private static AssistantCommandListener assistantcommandlistener;
    private static MemberCommandListener membercommandlistener;
    private static StunnerDamageListener sdl;
    private static StunnerExplosionListener sel;
    private static StunnerBlockListener sbl;
    private static StunnerSpawnListener ssl;
    private static Connector conn;
    private static Database data;
    
    /**
     *
     */
    @Override
     public void disable() {    
        log.info(name + " version " + version + " disabled.");

    }
    /**
     *
     */
    @Override
    public void enable() {
        instance = this;
        stunnerconfig = new StunnerConfig(this);
        stunnerconfig.loadConfig();
        if(!stunnerconfig.useMySQL()){
            downloadSqlite();
        }
        conn = (Connector) (stunnerconfig.useMySQL() ? new MySQLConnector() : new SQLiteConnection());
        data = new Database();
        townmanager = new TownManager(this);
        stunnerchat = new StunnerChat();
        scl = new StunnerCommandListener();
        mayorcommandlistener = new OwnerCommandListener();
        assistantcommandlistener = new AssistantCommandListener();
        membercommandlistener = new MemberCommandListener();
        sml = new StunnerMoveListener();
        townrank = new TownRankManager();
        sdl = new StunnerDamageListener();
        sel = new StunnerExplosionListener();
        sbl = new StunnerBlockListener();
        ssl = new StunnerSpawnListener();
        log.info(name + " version " + version + " enabled.");
        townrank.loadTownRanks();
//        MyClassLoader cl = (MyClassLoader) this.getClass().getClassLoader();
//        URLClassLoader loader ;
//        try {
//            loader = new URLClassLoader(new URL[] {new File("sqlite-jdbc.jar").toURI().toURL()}, this.getClass().getClassLoader());
//            loader.loadClass("org.sqlite.JDBC");
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(FiveStarTowns.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (MalformedURLException ex) {
//            Logger.getLogger(FiveStarTowns.class.getName()).log(Level.SEVERE, null, ex);
//        }
        data.createChunkTable();
        data.createExpTable();
        data.createTownTable();
        data.createUserTable();
        townmanager.AddHashMap();
        stunnerchat.loadFiles();
    }
    
    /**
     *
     */
    @Override
    public void initialize() {
        log.info(name + " version " + version + " by " + creator + " has been initialized.");
        etc.getLoader().addListener(PluginLoader.Hook.COMMAND, scl, this, PluginListener.Priority.MEDIUM);
        etc.getLoader().addListener(PluginLoader.Hook.COMMAND, mayorcommandlistener, this, PluginListener.Priority.MEDIUM);
        etc.getLoader().addListener(PluginLoader.Hook.COMMAND, assistantcommandlistener, this, PluginListener.Priority.MEDIUM);
        etc.getLoader().addListener(PluginLoader.Hook.COMMAND, membercommandlistener, this, PluginListener.Priority.MEDIUM);
        etc.getLoader().addListener(PluginLoader.Hook.PLAYER_MOVE, sml, this, PluginListener.Priority.MEDIUM);
        etc.getLoader().addListener(PluginLoader.Hook.DAMAGE, sdl, this, PluginListener.Priority.MEDIUM);
        etc.getLoader().addListener(PluginLoader.Hook.EXPLODE, sel, this, PluginListener.Priority.MEDIUM);
        etc.getLoader().addListener(PluginLoader.Hook.BLOCK_DESTROYED, sbl, this, PluginListener.Priority.MEDIUM);
        etc.getLoader().addListener(PluginLoader.Hook.BLOCK_CREATED, sbl, this, PluginListener.Priority.MEDIUM);
        etc.getLoader().addListener(PluginLoader.Hook.MOB_SPAWN, ssl, this, PluginListener.Priority.MEDIUM);
        if(getConfig().useChat()){
            etc.getLoader().addListener(PluginLoader.Hook.CHAT, stunnerchat, this, PluginListener.Priority.MEDIUM);
            etc.getLoader().addListener(PluginLoader.Hook.COMMAND, stunnerchat, this, PluginListener.Priority.MEDIUM);
        }
    }
    
    /**
     *
     * @return
     */
    public static FiveStarTowns getInstance(){
        return instance;
    }
    
    /**
     *
     * @return
     */
    public static StunnerConfig getConfig(){
        return stunnerconfig;
    }
    
    /**
     *
     * @return
     */
    public TownManager getManager(){
        return townmanager;
    }
    
    /**
     *
     * @return
     */
    public TownRankManager getTownRankManager(){
        return townrank;
    }
    
    /**
     *
     * @return
     */
    public StunnerCommandListener getCommandListener(){
        return scl;
    }
    
    /**
     *
     * @return
     */
    public MemberCommandListener getMemberCommandListener(){
        return membercommandlistener;
    }
    
    /**
     *
     * @return
     */
    public AssistantCommandListener getAssistantCommandListener(){
        return assistantcommandlistener;
    }
    
    /**
     *
     * @return
     */
    public OwnerCommandListener getOwnerCommandListener(){
        return mayorcommandlistener;
    }
    
    /**
     *
     * @return
     */
    public static Connection getConnection() {
        return conn.getConnection();
    }
    
    /**
     *
     * @return
     */
    public static Database getDatabase(){
        return data;
    }
    
    private void downloadSqlite(){
        File jdbc = new File("sqlite-jdbc.jar");
        if(!jdbc.exists()){
            try {
                saveUrl("sqlite-jdbc.jar", "http://www.xerial.org/maven/repository/artifact/org/xerial/sqlite-jdbc/3.7.2/sqlite-jdbc-3.7.2.jar");
            } catch (MalformedURLException ex) {
                Logger.getLogger(FiveStarTowns.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FiveStarTowns.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(getConfig().getOS().equalsIgnoreCase("unix")){
            File unix = new File("sqlite3");
            if(!unix.exists()){
                try {
                    saveUrl("unix-sqlite.zip", "http://sqlite.org/sqlite-shell-linux-x86-3071401.zip");
                    getZipFiles("unix-sqlite.zip");
                } catch (MalformedURLException ex) {
                    Logger.getLogger(FiveStarTowns.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(FiveStarTowns.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if(getConfig().getOS().equalsIgnoreCase("windows")){
            File wind = new File("sqlite3.dll");
            if(!wind.exists()){
                try {
                    saveUrl("windows-sqlite.zip", "http://sqlite.org/sqlite-dll-win32-x86-3071401.zip");
                    getZipFiles("windows-sqlite.zip");
                } catch (MalformedURLException ex) {
                    Logger.getLogger(FiveStarTowns.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(FiveStarTowns.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if(getConfig().getOS().equalsIgnoreCase("mac")){
            File mac = new File("sqlite3.exe");
            if(!mac.exists()){
                try {
                    saveUrl("mac-sqlite.zip", "http://sqlite.org/sqlite-shell-osx-x86-3071401.zip");
                    getZipFiles("mac-sqlite.zip");
                } catch (MalformedURLException ex) {
                    Logger.getLogger(FiveStarTowns.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(FiveStarTowns.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        
        
    }
    /**
     *
     * @param filename
     * @param urlString
     * @throws MalformedURLException
     * @throws IOException
     */
    public void saveUrl(String filename, String urlString) throws MalformedURLException, IOException
    {
    	BufferedInputStream in = null;
    	FileOutputStream fout = null;
    	try
    	{
    		in = new BufferedInputStream(new URL(urlString).openStream());
    		fout = new FileOutputStream(filename);

    		byte data[] = new byte[1024];
    		int count;
    		while ((count = in.read(data, 0, 1024)) != -1)
    		{
    			fout.write(data, 0, count);
    		}
    	}
    	finally
    	{
    		if (in != null)
    			in.close();
    		if (fout != null)
    			fout.close();
    	}
    }
    
    /**
     *
     * @param filename
     */
    public void getZipFiles(String filename)
    {
        try
        {
            String destinationname = "";
            byte[] buf = new byte[1024];
            ZipInputStream zipinputstream = null;
            ZipEntry zipentry;
            zipinputstream = new ZipInputStream(
                new FileInputStream(filename));

            zipentry = zipinputstream.getNextEntry();
            while (zipentry != null) 
            { 
                //for each entry to be extracted
                String entryName = zipentry.getName();
                System.out.println("entryname "+entryName);
                int n;
                FileOutputStream fileoutputstream;
                File newFile = new File(entryName);
                String directory = newFile.getParent();
                
                if(directory == null)
                {
                    if(newFile.isDirectory())
                        break;
                }
                
                fileoutputstream = new FileOutputStream(
                   destinationname+entryName);             

                while ((n = zipinputstream.read(buf, 0, 1024)) > -1)
                    fileoutputstream.write(buf, 0, n);

                fileoutputstream.close(); 
                zipinputstream.closeEntry();
                zipentry = zipinputstream.getNextEntry();

            }//while

            zipinputstream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
}
