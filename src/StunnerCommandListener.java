
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;


/**
 *
 * @author Somners
 */
public class StunnerCommandListener extends PluginListener{
    private Logger log=Logger.getLogger("Minecraft");
    private FiveStarTowns plugin;
    private Database data;
    HashMap<Player, Player> acceptMap = new HashMap<Player, Player>();
    
    /**
     *
     */
    public StunnerCommandListener(){
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
            TownPlayer tp = plugin.getManager().getTownPlayer(player.getOfflineName());
        
            if(player.canUseCommand("/fivestartowns") && (cmd[0].equalsIgnoreCase("/town") || cmd[0].equalsIgnoreCase("/t"))){

    //                                  //
    //            Here Command          //
    //                                  //
                if(cmd.length == 1){
                    help(player);
                    return true;
                }
                if(cmd[1].equalsIgnoreCase("here")){
                    int chunkx = (int)player.getX() >> 4;
                    int chunkz = (int)player.getZ() >> 4;
                    String chunky = chunkx+":"+chunkz + ":" + player.getWorld().getName();
                    String townName = "Wilderness";
                    if(plugin.getManager().containsKey(chunky)){
                        townName = plugin.getManager().get(chunky);
                    }
                    Town town = plugin.getManager().getTown(townName);
                    
                    if(town != null){
                        int chunksallowed = (plugin.getConfig().getChunkMultiplier() * town.getMemberCount()) + town.getBonus(); 
                        player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fThis Land is: §b" + town.getRankName()+ " " + townName + " Territory§f.");
                        player.sendMessage("§a"+town.getMayorName()+"§b:§f " + town.getOwner());
                        player.sendMessage("§a"+town.getAssistantName()+"§b:§f " + town.getAssistant());
                        player.sendMessage("§aFlags§b:§f " + town.getFlagString());
                        player.sendMessage("§aClaimed Land§b:§f " + String.valueOf(plugin.getManager().chunkAmount(town.getName())) + "/" + String.valueOf(chunksallowed));
                        player.sendMessage("§aMembers§b:§f " + town.getMembers().toString());
                        return true;
                    }
                     player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fThis Land is: §b" + townName + " Territory§f.");
                    
                    return true;
                }

                

            
    //                                  //
    //            Create Town           //
    //                                  //            
            
            if(cmd[1].equalsIgnoreCase("create") && cmd.length > 2 && player.canUseCommand("/cancreatetown")){
                if(tp != null){
                    player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fYou are already in a town! Please leave it before starting a new one.");
                    return true;
                }
                String moneyname = "";
                if(plugin.getConfig().getUseDCO()){
                    moneyname = (String)etc.getLoader().callCustomHook("dCBalance", new Object[] {"Money-Name"});
                    Double pbalance = (Double)etc.getLoader().callCustomHook("dCBalance", new Object[] {"Account-Balance", player.getOfflineName()});
                    if(pbalance < plugin.getConfig().getTownCost()){
                        player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fNot enough §b" + moneyname + " §fto start a town.");
                        player.sendMessage("    §b- §fYou need:  §b" + plugin.getConfig().getTownCost() + " " +  moneyname);
                        return true;
                    }
                }
                
                StringBuilder sb = new StringBuilder();
                for(int i = 2 ; i < cmd.length ; i++){
                    sb.append(cmd[i] + " ");
                }
                String townName = sb.toString().trim();
                if(data.keyExists(townName, "towns", "name")){
                    player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fTown §a'§b"+townName+"§a'§f already exists.");
                    return true;
                }
                data.insertTownPlayer(player.getOfflineName(), townName);
                data.insertTown(townName, player.getOfflineName(), "");
                player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fTown §a'§b"+townName+"§a'§f has been created!");
                if(plugin.getConfig().getUseDCO()){
                    etc.getLoader().callCustomHook("dCBalance", new Object[] {"Account-Withdraw", player.getOfflineName(), plugin.getConfig().getTownCost()});
                    player.sendMessage("    §b- §fYou have been charged:  §b" + plugin.getConfig().getTownCost() + " " +  moneyname);
                }
               
                return true;
            }
            

            
    //                                  //
    //            Accept Town           //
    //                                  //
            if(cmd[1].equalsIgnoreCase("accept") && tp == null ){
                if(!acceptMap.containsKey(player)){
                    player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §f No pending invites at this time.");
                    return true;
                }
                TownPlayer otp = plugin.getManager().getTownPlayer(acceptMap.get(player).getOfflineName());
                if(cmd[2].equalsIgnoreCase(otp.getTownName())){
                    data.insertTownPlayer(player.getOfflineName(), otp.getTownName());
                    player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §bYou have accepted the invitation to " + otp.getTownName()+".");
                   acceptMap.get(player).sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §b"+player.getOfflineName()+"§f has accepted an invitation to your town.");
                   acceptMap.remove(player);
                }
            }
            
            
    //                                  //
    //           Info command           //
    //                                  //            
            if(cmd[1].equalsIgnoreCase("info") && cmd.length > 2 ){
                StringBuilder sb = new StringBuilder();
                for(int i = 2; i < cmd.length ; i++){
                    sb.append(cmd[i]).append(" ");
                }
                Town town = plugin.getManager().getTown(sb.toString().trim());
                if(town == null){
                    player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fThis Town Does not exists.");
                }
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
            
            
            
            
            
     
    //                                  //
    //           Help Menu              //
    //                                  //
            if(cmd[1].equalsIgnoreCase("help")){
                if(cmd.length == 3){
                    if(cmd[2].equalsIgnoreCase("member") || cmd[2].equalsIgnoreCase("m")){
                        memberHelp(player);
                        return true;
                    }
                    if(cmd[2].equalsIgnoreCase("assistant") || cmd[2].equalsIgnoreCase("a")){
                        assistantHelp(player);
                        return true;
                    }
                    if(cmd[2].equalsIgnoreCase("owner") || cmd[2].equalsIgnoreCase("o")){
                        ownerHelp(player);
                        return true;
                    }
                }
                help(player);
                return true;            
            }
            
            
            
                if(player.isAdmin()){
                    if(cmd[1].equalsIgnoreCase("convert")){
                        data.convert(cmd[2]);
                        player.sendMessage("§4Conversion Attempted, hopefully it worked.");
                        return true;
                    }
                    
                    if(cmd[1].equalsIgnoreCase("addbonus") && cmd.length == 4){
                        StringBuilder sb = new StringBuilder();
                        for(int i = 2; i < cmd.length ; i++){
                            sb.append(cmd[i]).append(" ");
                        }
                        Town town = plugin.getManager().getTown(sb.toString().trim());
                        if(town != null){
                            town.addBonus(Integer.valueOf(cmd[3]));
                            player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §f Added bonus land plots to: §b" + town.getName());
                            return true;
                        }
                        player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §f Town does not exist: §b" + cmd[2]);
                        return true;
                    }
                    
                    if(cmd[1].equalsIgnoreCase("setbonus") && cmd.length == 4){
                        StringBuilder sb = new StringBuilder();
                        for(int i = 2; i < cmd.length ; i++){
                            sb.append(cmd[i]).append(" ");
                        }
                        Town town = plugin.getManager().getTown(sb.toString().trim());
                        if(town != null){
                            town.setBonus(Integer.valueOf(cmd[3]));
                            player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §f Set bonus land plots of: §b" + town.getName());
                            return true;
                        }
                        player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §f Town does not exist: §b" + cmd[2]);
                        return true;
                    }
                } 
                plugin.getMemberCommandListener().onCommand(player, cmd);
                return true;
            }
        return false;
    }
        
        
        /**
     *
     * @param player
     */
    public void help(Player player){
            player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fFiveStarTowns help");
            player.sendMessage("  §a- /t info [town]    §fget this towns info");
            player.sendMessage("  §a- /t here  §fSee who owns the town where you are standing, if anyoneypooooooooooooooujhhhhhh");
            player.sendMessage("  §a- /t accept [townname]  §fac cept an invitation to a town");
            player.sendMessage("  §a- /t create  [townname]§fcreate a town");
            player.sendMessage("  §a- /t help §fview this menu");
            player.sendMessage("  §a- /t help [member|m]  §ftown member help");
            player.sendMessage("  §a- /t help [assistant|a]  §ftown assistant help");
            player.sendMessage("  §a- /t help [owner|o]  §ftown owner help");
        }
        
        /**
     *
     * @param player
     */
    public void memberHelp(Player player){
            TownPlayer tp = plugin.getManager().getTownPlayer(player.getName());
            if(tp != null && (tp.isAssistant() || tp.isOwner())){
                player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fFiveStarTowns Member Help");
                player.sendMessage("  §a- /t leave    §fleave your town");
                player.sendMessage("  §a- /t info §fget info about your town");
                if(plugin.getConfig().getUseDCO()){
                    player.sendMessage("  §a- /t donate [amount]  §fdonate money to your town");
                    player.sendMessage("  §a- /t bank §fview your towns bank account");
                }
                return;
            }
            player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fYou are not a Town Member.");
        }
        
        /**
     *
     * @param player
     */
    public void assistantHelp(Player player){
            TownPlayer tp = plugin.getManager().getTownPlayer(player.getName());
            if(tp != null && (tp.isAssistant() || tp.isOwner())){
                player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fFiveStarTowns Assistant Help");
                player.sendMessage("  §a- /t claim    §fclaim land for your town");
                player.sendMessage("  §a- /t unclaim §funclaim land for your town");
                if(plugin.getConfig().getUseDCO()){
                    player.sendMessage("  §a- /t buyland [message]  §fbuy more land for yout town");
                }
                player.sendMessage("  §a- /t invite [playername]  §finvite a player to your town");
                player.sendMessage("  §a- /t kick [playername]  §fkick a player out of your town");
                return;
            }
            player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fYou are not a Town Assistant.");
        }
        
        
        /**
     *
     * @param player
     */
    public void ownerHelp(Player player){
            TownPlayer tp = plugin.getManager().getTownPlayer(player.getName());
            if(tp != null && tp.isOwner()){
                player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fFiveStarTowns Owner Help");
                player.sendMessage("  §a- /t toggle    §fview flags you can toggle for your town");
                player.sendMessage("  §a- /t toggle [flag] [on|off] §ftoggle flags on or off");
                player.sendMessage("  §a- /t setwelcome [message]  §fset your towns welcome message");
                player.sendMessage("  §a- /t setfarewell [message]  §fset your towns farewell message");
                player.sendMessage("  §a- /t setassistant [playername]  §fset your towns assistant");
                player.sendMessage("  §a- /t seta [playername]  §fset your towns assistant");
                player.sendMessage("  §a- /t disband  §fdisband your town");
                return;
            }
            player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fYou are not a Town Owner.");
        }
    
}
