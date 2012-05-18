
import java.util.logging.Logger;


public class AssistantCommandListener extends PluginListener{
    private Logger log=Logger.getLogger("Minecraft");
    private StunnerTowns plugin;
    private StunnerCommandListener scl;
    
    public AssistantCommandListener(){
        this.plugin = StunnerTowns.getInstance();
        scl = plugin.getCommandListener();
    }
    
    public boolean onCommand(Player player, String[] cmd){
        if(player.canUseCommand("/stunnertowns") && (cmd[0].equalsIgnoreCase("/town") || cmd[0].equalsIgnoreCase("/t"))){
        TownPlayer tp = plugin.getManager().getTownPlayer(player.getOfflineName());
        if(tp!=null && (tp.isAssistant() || tp.isOwner())){
    
    //                                  //
    //            Claiming Land         //
    //                                  //

                if(cmd[1].equalsIgnoreCase("claim") && tp != null && (tp.isOwner() || tp.isAssistant())){
                    int chunkx = (int)player.getX() >> 4;
                    int chunkz = (int)player.getZ() >> 4;
                    Town town = tp.getTown();
                    String chunky = chunkx+":"+chunkz;
                    String townName = town.getName(); 
                    MySQL mysql = new MySQL();
                    int chunksowned = plugin.getManager().chunkAmount(townName);
                    int chunksallowed = (plugin.getConfig().getChunkMultiplier() * town.getMemberCount()) + town.getBonus(); 
                        if(chunksowned >= chunksallowed){
                            player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fYour town already owns the max amount of land plots.");
                            player.sendMessage("  §a-§bHint§a- §fTry unclaiming land if you really need this land plot.");
                            return true;
                        }
                        if(!plugin.getManager().chunksConnected((int)player.getX(), (int)player.getZ(), townName) && chunksowned > 0){
                            player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fLand Section not connected to your town.");
                            player.sendMessage("  §a-§bHint§a- §fTry claiming land next to your existing town.");
                            return true;
                        }
                        if(plugin.getManager().containsKey(chunky)){
                            if(plugin.getManager().get(chunky).equalsIgnoreCase("Wilderness")){
                                plugin.getManager().remove(chunky);
                                plugin.getManager().put(chunky, townName);
                                mysql.updateStringEntry("chunk", chunky,"chunks", townName, "town");
                                player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fLand Claimed!");
                                return true;
                            }
                            else{
                                player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fLand is owned by: §b" + plugin.getManager().get(chunky));
                                return true;
                            }
                        }
                        if(!plugin.getManager().containsKey(chunky)){
                            plugin.getManager().put(chunky, townName);
                            mysql.InsertChunk(chunky, townName);
                            player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fLand Claimed!");
                            return true;
                        }
                }

    //                                      //
    //            Unclaiming Land           //
    //                                      //

                if(cmd[1].equalsIgnoreCase("unclaim") && tp != null && (tp.isOwner() || tp.isAssistant())){

                    int chunkx = (int)player.getX() >> 4;
                    int chunkz = (int)player.getZ() >> 4;
                    String chunky = chunkx+":"+chunkz;
                    String townName = tp.getTownName();
                    if(plugin.getManager().containsKey(chunky)){
                        MySQL mysql = new MySQL();
                        if(plugin.getManager().get(chunky).equalsIgnoreCase(townName)){
                            plugin.getManager().remove(chunky);
                            plugin.getManager().put(chunky, "Wilderness");
                            mysql.updateStringEntry("chunk", chunky,"chunks", "Wilderness", "town");
                            player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fLand unclaimed");
                            return true;
                        }
                        else{
                            player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fYour town does not own this land.");
                            return true;
                        }
                    }
                    else{
                        player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fYour town does not own this land.");
                        return true;
                    }
                }
                
    //                                      //
    //            Buy more Land             //
    //                                      //
            if(cmd[1].equalsIgnoreCase("buyland") && cmd.length == 3){
                if(plugin.getConfig().getUseDCO()){
                    String moneyname = (String)etc.getLoader().callCustomHook("dCBalance", new Object[] {"Money-Name"});
                    String balance = String.valueOf(tp.getTown().getBalance());
                    int num = Integer.valueOf(cmd[2]);
                    double cost = Double.valueOf(cmd[2]) * plugin.getConfig().getLandCost();
                    Town town = tp.getTown();
                    if(cost <= town.getBalance()){
                        town.removeBalance(cost);
                        town.addBonus(num);
                        player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a]  §fYou town has baught §b" + num +"§f new plots.");
                        player.sendMessage("    §a- §fYou towns bank balance is: §b" + town.getBalance() +" "+ moneyname);
                        return true;
                    }
                    player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a]  §fYou can't afford this. You towns bank balance is: §b" + balance +" "+ moneyname);
                    return true;
                }
        
            }
            
            
    //                                  //
    //            Invite Town           //
    //                                  //
            if(cmd[1].equalsIgnoreCase("invite") && tp != null){
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
