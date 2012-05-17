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
                
            if(cmd[1].equalsIgnoreCase("disband") && tp != null && tp.isOwner()){
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
            if((cmd[1].equalsIgnoreCase("setassistant") || cmd[1].equalsIgnoreCase("seta ")) && tp != null && tp.isOwner() && cmd.length == 3){
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
    //            Invite Town           //
    //                                  //
            if(cmd[1].equalsIgnoreCase("invite") && tp != null && (tp.isOwner() || tp.isAssistant())){
                if(cmd.length <= 2){
                    player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fToo few Arguments to invite a new player.");
                    player.sendMessage("    §a- §f/town invite [playername]");
                    return true;
                }
                Player iplayer = etc.getServer().getPlayer(cmd[2]);
                if(iplayer == null){
                    player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fThat player is not online or doesn't exists.");
                    return true;
                }
                TownPlayer itp = plugin.getManager().getTownPlayer(iplayer.getOfflineName());
                if(itp != null){
                    player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fThat player is already in a town.");
                    return true;
                }
                
                if(scl.acceptMap.containsKey(iplayer) && scl.acceptMap.get(iplayer).equals(player)){
                    iplayer.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §b"+player.getOfflineName()+"§f has invited you to §b"+tp.getTownName()+"§f.");
                    iplayer.sendMessage("    §a- To Accept Do:  §f/town accept [townname]");
                    player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §b"+iplayer.getOfflineName()+"§f has been invited your town.");
                    return true;
                }
                scl.acceptMap.put(iplayer, player);
                iplayer.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §b"+player.getOfflineName()+"§f has invited you to §b"+tp.getTownName()+"§f.");
                iplayer.sendMessage("    §a- To Accept Do:  §f/town accept [townname]");
                player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §b"+iplayer.getOfflineName()+"§f has been invited your town.");
                return true;
                
            }
    
        }
        }
        return false;
    }
    
    
}
