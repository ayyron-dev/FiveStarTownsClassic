import java.io.*;
import java.util.logging.Logger;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class TownRankManager {
    
    private StunnerTowns plugin;
//    private List<Integer> ranknums = new ArrayList<Integer>();
    private HashMap<Integer, Integer> memnum = new HashMap<Integer, Integer>();
    private HashMap<Integer, List> flags = new HashMap<Integer, List>();
    private HashMap<Integer, String> tname = new HashMap<Integer, String>();
    private HashMap<Integer, String> mname = new HashMap<Integer, String>();
    private HashMap<Integer, String> aname = new HashMap<Integer, String>();
    private Logger log = Logger.getLogger("Minecraft");
    File dir = new File("plugins/config/StunnerTowns/Ranks");
    File file = new File("plugins/config/StunnerTowns/Ranks/TownRanks.txt");
    
    public TownRankManager(){
        plugin = StunnerTowns.getInstance();
    }
    
    public void loadTownRanks(){
        try{
            if(!dir.exists()){
                dir.mkdirs();
            }
            if(!file.exists()){
                file.createNewFile();
                FileWriter writer = new FileWriter(file);
                String NL = System.getProperty("line.separator");
                String toWrite = "#Syntax= rank:member number to get this rank:name of rank:mayor name:assistant name:flags allowed to set" +
                        NL + "#rank starts at 1 as lowest; higher numbers are higher rank;rank numbers must be 1,2,3,4,5,etc...." +
                        NL + "#EXAMPLE= 1:0:Village:Mayor:Assistant:nopvp,creepernerf" +
                        NL + "#flags= nopvp,creepernerf,protected,sanctuary,friendlyfire";
                writer.write(toWrite);
                writer.close();
                        
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null){
                if(!line.startsWith("#")){
                    String[] args = line.split(":");
                    int r = Integer.valueOf(args[0]);
                    memnum.put(r,Integer.valueOf(args[1]));
                    tname.put(r,args[2]);
                    mname.put(r,args[3]);
                    aname.put(r,args[4]);
                    List<String> f = new ArrayList();
                    String[] s = args[5].split(",");
                    for(int i = 0; i<s.length; i++){
                        f.add(s[i].trim());
                    }
                    flags.put(r, f);
                }
                line = reader.readLine();
            }
            reader.close();
            log.info("[StunnerTowns] Town Ranks Loaded.");
        }
        catch(IOException ex){log.info("[StunnerTowns] Error loading town ranks: " + ex.toString());
        }
        
        }
    
    public void refresh(){
        memnum.clear();
        tname.clear();
        flags.clear();
        loadTownRanks();
    }
    
    public int getMinTownMemberNum(int r){
        return memnum.get(r);
    }
    
    public int getTownRank(int membernum){
        int townsrank = 0;
        for(int i = 1 ; i <= memnum.size(); i++){
            if(membernum >= memnum.get(i)){
                townsrank = i;
            }
        }
        return townsrank;
    }
    
    public List getTownFlags(int r){
        return flags.get(r);
    }
    
    public String getTownRankName(int r){
        return tname.get(r);
    }
    
    public String getTownMayorName(int r){
        return mname.get(r);
    }
    
    public String getTownAssistantName(int r){
        return aname.get(r);
    }
}
