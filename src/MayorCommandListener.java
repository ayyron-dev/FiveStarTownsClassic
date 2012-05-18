import java.util.List;
import java.util.logging.Logger;


public class MayorCommandListener extends PluginListener{
    private Logger log=Logger.getLogger("Minecraft");
    private StunnerTowns plugin;
    private StunnerCommandListener scl;
    public MayorCommandListener(){
        this.plugin = StunnerTowns.getInstance();
        scl = plugin.getCommandListener();
    }
    
    public boolean onCommand(Player player, String[] cmd){
        if(player.canUseCommand("/stunnertowns") && (cmd[0].equalsIgnoreCase("/town") || cmd[0].equalsIgnoreCase("/t"))){
        TownPlayer tp = plugin.getManager().getTownPlayer(player.getOfflineName());
        if(tp!=null && tp.isOwner()){
            
//                                          //
//                Disband Command           //
//                                          //
                
            if(cmd[1].equalsIgnoreCase("disband")){
                    Town town = tp.getTown();
                    MySQL mysql = new MySQL();
                    mysql.removeChunk(town.getName());
                    mysql.delete("towns", "name", tp.getTownName());
                    List<Player> players = etc.getServer().getPlayerList();
                    for(int i = 0; i<players.size(); i++){
                        mysql.delete("townsusers", "username", players.get(i).getOfflineName());
                    }
                    player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fTown §a'§b"+town.getName()+"§a'§f has been disbanded.");
                    return true;
                }
            
    //                                  //
    //            Set Assistant         //
    //                                  //
            if((cmd[1].equalsIgnoreCase("setassistant") || cmd[1].equalsIgnoreCase("seta ")) && cmd.length == 3){
                MySQL mysql = new MySQL();
                Player aplayer = etc.getServer().getPlayer(cmd[2]);
                if(aplayer == null){
                    player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a]  §fThat player doesn't appear to exist.");
                    return true;
                }
                mysql.updateStringEntry(tp.getTownName(), "name", "towns", cmd[2], "assistant");
                player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a]  §fYou have set assitant to: " + cmd[2]);
                return true;
            }
    

    
    //                                  //
    //            set farewell          //
    //                                  //  
            
            if(cmd[1].equalsIgnoreCase("setwelcome") && cmd.length > 2){
                StringBuilder sb = new StringBuilder();
                for(int i = 2; i < cmd.length ; i++){
                    sb.append(cmd[i]).append(" ");
                }
                tp.getTown().setWelcome(sb.toString().trim());
                player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fWelcome Message set to: " + sb.toString().trim());
                return true;
            }
            
            
            
            
            
            
            
            
            
    //                                  //
    //            set farewell           //
    //                                  //      
            
            if(cmd[1].equalsIgnoreCase("setfarewell") && cmd.length > 2){
                StringBuilder sb = new StringBuilder();
                for(int i = 2; i < cmd.length ; i++){
                    sb.append(cmd[i]).append(" ");
                }
                tp.getTown().setFarewell(sb.toString().trim());
                player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fFarewell Message set to: " + sb.toString().trim());
                return true;
            }
            
            
            
            
            
            
            
            
            
        }
        
        }
        return false;
    }
    
    
}
