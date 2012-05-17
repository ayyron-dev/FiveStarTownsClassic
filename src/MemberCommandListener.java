import java.util.logging.Logger;


public class MemberCommandListener extends PluginListener{
    private Logger log=Logger.getLogger("Minecraft");
    private StunnerTowns plugin;
    
    public MemberCommandListener(){
        this.plugin = StunnerTowns.getInstance();
    }
    
    public boolean onCommand(Player player, String[] cmd){
        if(player.canUseCommand("/stunnertowns") && (cmd[0].equalsIgnoreCase("/town") || cmd[0].equalsIgnoreCase("/t"))){
        TownPlayer tp = plugin.getManager().getTownPlayer(player.getOfflineName());
        if(tp!=null){
    //                                  //
    //            Leave Town            //
    //                                  //
            if(cmd[1].equalsIgnoreCase("leave")){
                MySQL mysql = new MySQL();
                mysql.delete("townsusers", "username", tp.getName());
                player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a]  §fYou have left your town");
                Town town = tp.getTown();
                if(town.getMembers().size() < 1){
                    player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a]  §fNo members left in town, town has been deleted.");
                    mysql.delete("towns", "name", tp.getTownName());
                }
            }
            
//                                      //
//            Info Command              //
//                                      //
            if(cmd[1].equalsIgnoreCase("info") && cmd.length == 2){
                Town town = tp.getTown();
                int chunksallowed = (plugin.getConfig().getChunkMultiplier() * town.getMemberCount()) + town.getBonus(); 
                 player.sendMessage("§a[§b" + tp.getTownName() + "§a]");
                 player.sendMessage("§aOwner§b:§f " + town.getOwner());
                 player.sendMessage("§aAssistant§b:§f " + town.getAssistant());
                 player.sendMessage("§aClaimed Land§b:§f " + String.valueOf(plugin.getManager().chunkAmount(tp.getTownName())) + "/" + String.valueOf(chunksallowed));
                 player.sendMessage("§aMembers§b:§f " + town.getMembers().toString());
                 return true;
            }
            
//                                      //
//            Donate Command            //
//                                      //
            if(cmd[1].equalsIgnoreCase("donate") && cmd.length == 2){
            
        
        
            }

        }
        }
        return false;
    }
}