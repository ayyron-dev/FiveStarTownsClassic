
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;


public class StunnerChat extends PluginListener{
    
    FiveStarTowns plugin;
    private Logger log=Logger.getLogger("Minecraft");
    HashMap<String, String> nickname = new HashMap();
    HashMap<String, String> pcolor = new HashMap();
    HashMap<String, String> groupnick = new HashMap();
    ArrayList colorchars = new ArrayList();
    File file1 = new File("plugins/config/FiveStarTowns/nicknames.txt");
    File file3 = new File("plugins/config/FiveStarTowns/groupnicknames.txt");
    
    public StunnerChat() {
        plugin = FiveStarTowns.getInstance();
    }
    
    public void loadFiles(){
        try{  
            if(!file1.exists()){
                file1.createNewFile();
            }
            if(!file3.exists()){
                file3.createNewFile();
            }
//            Load player nicknames
            BufferedReader read1 = new BufferedReader(new FileReader(file1));
            String line1 = read1.readLine();
            while(line1 != null){
                String[] split = line1.split(":");
                nickname.put(split[0], split[1]);
                line1 = read1.readLine();
            }
            read1.close();
            log.info("[FiveStarTowns] Player NickNames Loaded!");
//            load group nicknames
            BufferedReader read3 = new BufferedReader(new FileReader(file3));
            String line3 = read3.readLine();
            while(line3 != null){
                String[] split = line3.split(":");
                groupnick.put(split[0], split[1]);
                line3 = read3.readLine();
            }
            read3.close();
            log.info("[FiveStarTowns] Group NickNames Loaded!");
        }
        catch(IOException e){
            log.severe("[FiveStarTowns] Error Creating/Loading Files: " + e.toString());
        }
    }
    
    
    public boolean onChat(Player player, java.lang.StringBuilder sbMessage){
        String pon = player.getOfflineName();
        String syntaxed = plugin.getConfig().getChatSyntax();
        String group = "";
        String town = "";
        String pname = "<"+player.getFullName()+"§f> ";
        String message = ": " + sbMessage.toString();
//          set groups for output
            if(!player.hasNoGroups()){
                String[] g = player.getGroups();
                String gname = g[0];
                if(groupnick.containsKey(group)){
                    gname = groupnick.get(group);
                }
                if(group.equalsIgnoreCase("default")){
                   gname = "Guest";
                }
                group = "["+player.getColor()+gname+"§f] ";
            }
            else{
                group = "["+player.getColor()+"Guest§f] ";
            }
        
//        Set town name for output
            TownPlayer tp = plugin.getManager().getTownPlayer(pon);
            if(tp != null){
                town = "[§"+plugin.getConfig().getTownColor()+tp.getTownName()+"§f] ";
            }
//          Change playername to nickname if it exists
            if(nickname.containsKey(pon)){
                pname = "<"+nickname.get(pon)+"§f> ";
            }
            StringBuilder sb = new StringBuilder();
            String[] syntax = syntaxed.split(":");
            for(int i = 0 ; i < syntax.length ; i++){
                if(syntax[i].equalsIgnoreCase("group")){
                    sb.append(group);
                }
                if(syntax[i].equalsIgnoreCase("player")){
                    sb.append(pname);
                }
                if(syntax[i].equalsIgnoreCase("message")){
                    sb.append(message);
                }
                if(syntax[i].equalsIgnoreCase("town")){
                    sb.append(town);
                }
            }
        etc.getServer().messageAll(sb.toString());
        log.info("<"+pon+"> "+sbMessage.toString());
        return true;
    
    }
    
        public boolean onCommand(Player player, String[] cmd){
        if(cmd[0].equalsIgnoreCase("/pnick") && player.canUseCommand("/fivestarchat")){
            if(cmd.length > 1){
                if(nickname.containsKey(cmd[1])){
                    nickname.remove(cmd[1]);
                }
                StringBuilder NICK = new StringBuilder();
                for(int i=2; i < cmd.length ; i++){
                    NICK.append(" " + cmd[i]);
                }
                String nick = NICK.toString();
                nickname.put(cmd[1], nick);
                Writer(player, cmd[1], nick, file1);
                return true;
            }
            return false;
        }
        if(cmd[0].equalsIgnoreCase("/gnick") && player.canUseCommand("/fivestarchat") && cmd.length == 3){
            if(groupnick.containsKey(cmd[1])){
                    groupnick.remove(cmd[1]);
                }
                groupnick.put(cmd[1], cmd[2]);
                Writer(player, cmd[1], cmd[2], file3);
                return true;
            
        }
    return false;
    }
    
    public void Writer(Player player, String key, String var, File file){
      try{
        BufferedReader read = new BufferedReader(new FileReader(file));
        StringBuilder toWrite = new StringBuilder();
        String line = read.readLine();
        String lineSep = System.getProperty("line.separator");
        while(line != null){
            String[] split = line.split(":");
            if(!key.equalsIgnoreCase(split[0])){
                toWrite.append(line);
                toWrite.append(lineSep);
            }
            
            line = read.readLine();
        }
        toWrite.append(key + ":" + var);
        toWrite.append(lineSep);
        read.close();
        BufferedWriter write = new BufferedWriter(new FileWriter(file));
        write.write(toWrite.toString());
        write.close();
        player.sendMessage("§2Chat Config Changed.");
      }
      catch(IOException e){
          log.severe("[FiveStarTowns] Error Writing Files: " + e.toString());
      }
    }
    
    
   
    
}
