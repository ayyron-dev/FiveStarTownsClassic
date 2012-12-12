
import java.util.logging.Logger;


/**
 *
 * @author Somners
 */
public class MemberCommandListener extends PluginListener{
    private Logger log=Logger.getLogger("Minecraft");
    private FiveStarTowns plugin;
    private Database data;
    
    /**
     *
     */
    public MemberCommandListener(){
        this.plugin = FiveStarTowns.getInstance();
        data = plugin.getDatabase();
    }
    
    /**
     *
     * @param player
     * @param cmd
     * @return
     */
    public boolean onCommand(Player player, String[] cmd){
        if(player.canUseCommand("/fivestartowns") && (cmd[0].equalsIgnoreCase("/town") || cmd[0].equalsIgnoreCase("/t"))){
        TownPlayer tp = plugin.getManager().getTownPlayer(player.getOfflineName());
        if(tp!=null){
    //                                  //
    //            Leave Town            //
    //                                  //
            if(cmd[1].equalsIgnoreCase("leave")){
                data.delete("townsusers", "username", tp.getName());
                player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a]  §fYou have left your town");
                Town town = tp.getTown();
                if(town.getMembers().size() < 1 || tp.isOwner()){
                    player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a]  §fNo members left in town, town has been deleted.");
                    data.delete("towns", "name", tp.getTownName());
                }
            }
            
//                                      //
//            Info Command              //
//                                      //
            if(cmd[1].equalsIgnoreCase("info") && cmd.length == 2){
                Town town = tp.getTown();
                int chunksallowed = (plugin.getConfig().getChunkMultiplier() * town.getMemberCount()) + town.getBonus(); 
                player.sendMessage("§a[§b" + town.getRankName()+ " " + tp.getTownName() + "§a]");
                player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fThis Land is: §b" + town.getRankName()+ " " + town.getName() + " Territory§f.");
                player.sendMessage("§a"+town.getMayorName()+"§b:§f " + town.getOwner());
                player.sendMessage("§a"+town.getAssistantName()+"§b:§f " + town.getAssistant());
                player.sendMessage("§aFlags§b:§f " + town.getFlagString());
                player.sendMessage("§aClaimed Land§b:§f " + String.valueOf(plugin.getManager().chunkAmount(town.getName())) + "/" + String.valueOf(chunksallowed));
                player.sendMessage("§aMembers§b:§f " + town.getMembers().toString());
                 return true;
            }
            
//                                      //
//            Donate Command            //
//                                      //
            if(cmd[1].equalsIgnoreCase("donate") && cmd.length == 3){
                if(plugin.getConfig().getUseDCO()){
                    Double donation = Double.valueOf(cmd[3]);
                    Double pbalance = (Double)etc.getLoader().callCustomHook("dCBalance", new Object[] {"Account-Balance", player.getOfflineName()});
                    if(pbalance - donation >= 0){
                        String moneyname = (String)etc.getLoader().callCustomHook("dCBalance", new Object[] {"Money-Name"});
                        etc.getLoader().callCustomHook("dCBalance", new Object[] {"Account-Withdraw", player.getOfflineName(), donation});
                        player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a]  §fYou have donated §b" + cmd[3] +" "+ moneyname + " §fto your town.");
                        tp.getTown().addBalance(donation);
                        return true;
                    }
                     player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a]  §fYou don't seem to have enough in your account to do that.");
                     return true;
                }
        
            }
            
//                                      //
//            view bank balance         //
//                                      //
            if(cmd[1].equalsIgnoreCase("bank") && cmd.length == 2){
                if(plugin.getConfig().getUseDCO()){
                    String moneyname = (String)etc.getLoader().callCustomHook("dCBalance", new Object[] {"Money-Name"});
                    String balance = String.valueOf(tp.getTown().getBalance());
                    player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a]  §fYou towns bank balance is: §b" + balance +" "+ moneyname);
                    return true;
                }
        
            }
            
            
            
            
            
            
            
            plugin.getAssistantCommandListener().onCommand(player, cmd);
        }
        }
        return false;
    }
}