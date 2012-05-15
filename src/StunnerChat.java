
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;


public class StunnerChat extends PluginListener{
    
    StunnerTowns plugin;
    private Logger log=Logger.getLogger("Minecraft");
    HashMap<String, String> nickname = new HashMap();
    HashMap<String, String> pcolor = new HashMap();
    HashMap<String, String> groupnick = new HashMap();
    ArrayList colorchars = new ArrayList();
    File file1 = new File("plugins/config/FactionChat/nicknames.txt");
    File file2 = new File("plugins/config/FactionChat/colors.txt");
    File file3 = new File("plugins/config/FactionChat/groupnicknames.txt");
    
    public StunnerChat() {
        plugin = StunnerTowns.getInstance();
    }
    
    public void loadFiles(){
        colorchars.add("a");colorchars.add("b");colorchars.add("c");colorchars.add("d");colorchars.add("e");colorchars.add("f");
        colorchars.add("1");colorchars.add("2");colorchars.add("3");colorchars.add("4");colorchars.add("5");colorchars.add("6");
        colorchars.add("7");colorchars.add("8");colorchars.add("9");
        try{  
            if(!file1.exists()){
                file1.createNewFile();
            }
            if(!file2.exists()){
                file2.createNewFile();
            }
            if(!file3.exists()){
                file3.createNewFile();
            }
            BufferedReader read1 = new BufferedReader(new FileReader(file1));


            String line1 = read1.readLine();

            while(line1 != null){
                String[] split = line1.split(":");
                nickname.put(split[0], split[1]);
                line1 = read1.readLine();
            }
            read1.close();
            BufferedReader read2 = new BufferedReader(new FileReader(file2));
            String line2 = read2.readLine();

            while(line2 != null){
                String[] split = line2.split(":");
                pcolor.put(split[0], split[1]);
                line2 = read2.readLine();
            }
            read2.close();
            BufferedReader read3 = new BufferedReader(new FileReader(file3));
            String line3 = read3.readLine();
            while(line3 != null){
                String[] split = line3.split(":");
                groupnick.put(split[0], split[1]);
                line3 = read3.readLine();
            }
            read3.close();
        }
        catch(IOException e){
            log.severe("[StunnerTowns] Error Creating/Loading Files: " + e.toString());
        }
        log.info("[StunnerTowns] Chat config loaded.");
    }
    
    
    public boolean onChat(Player player, java.lang.StringBuilder sbMessage){
        StringBuilder sb = new StringBuilder();
        String pon = player.getOfflineName();
        String toChat = plugin.getConfig().getChatSyntax();
        if (toChat.contains("group")){
            if(!player.hasNoGroups()){
                String group = player.getGroups().toString();
                if(groupnick.containsKey(group)){
                    group = groupnick.get(group);
                }
                if(group.equalsIgnoreCase("default")){
                   group = "Guest";
                }
                toChat.replace("group", "["+player.getColor()+group+"§f]");
            }
            else{
                toChat.replace("group","");
            }
        }
        if(toChat.contains("town")){
            TownPlayer tp = plugin.getManager().getTownPlayer(pon);
            if(tp != null){
                toChat.replace("town", "[§"+plugin.getConfig().getTownColor()+tp.getTownName()+"]");
            }
            else{
                toChat.replace("town","");
            }
        }
        if(toChat.contains("player")){
            String pname = player.getFullName();
            if(nickname.containsKey(pon)){
                pname.replace(pon, nickname.get(pon));
            }
            toChat.replace("player", "<"+pname+"§f>");
        }
        if(toChat.contains("message")){
            toChat.replace("message", ": " + sbMessage.toString());
        }
        while(toChat.contains(":")){
            toChat.replace(":", " ");
        }
        etc.getServer().messageAll(toChat);
        log.info("<"+pon+">"+sbMessage.toString());
        return true;
    
    }
    
    
    
    
    
    
    
}
