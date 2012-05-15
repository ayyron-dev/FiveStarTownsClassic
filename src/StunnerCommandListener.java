
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;


public class StunnerCommandListener extends PluginListener{
    private Logger log=Logger.getLogger("Minecraft");
    private StunnerTowns plugin;
    HashMap<Player, Player> acceptMap = new HashMap<Player, Player>();
    
    public StunnerCommandListener(){
        this.plugin = StunnerTowns.getInstance();
    }
    
    
    
        public boolean onCommand(Player player, String[] cmd){
            TownPlayer tp = plugin.getManager().getTownPlayer(player.getOfflineName());
        
            if(player.canUseCommand("/stunnertowns") && (cmd[0].equalsIgnoreCase("/town") || cmd[0].equalsIgnoreCase("/t"))){

    //                                  //
    //            Here Command          //
    //                                  //
                if(cmd.length == 1){
                    help(player);
                }
                if(cmd[1].equalsIgnoreCase("here")){
                    int chunkx = (int)player.getX() >> 4;
                    int chunkz = (int)player.getZ() >> 4;
                    String chunky = chunkx+":"+chunkz;
                    String townName = "Wilderness";
                    if(plugin.getManager().containsKey(chunky)){
                        townName = plugin.getManager().get(chunky);
                    }
                    player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fThis Land is: §b" + townName + " Territory§f.");
                    if(!townName.equalsIgnoreCase("Wilderness")){
                        String members = plugin.getManager().getTown(townName).getMembers().toString();
                        player.sendMessage("§a=========================================================");
                        player.sendMessage("  §a-§bMembers§a- §f" + members);
                        return true;
                    }
                    return true;
                }

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
                    int chunksallowed = plugin.getConfig().getChunkMultiplier() * town.getMemberCount(); 
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
    //            Create Town           //
    //                                  //            
            
            if(cmd[1].equalsIgnoreCase("create") && cmd.length > 2){
                if(tp != null){
                    player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fYou are already in a town! Please leave it before starting a new one.");
                    return true;
                }
                StringBuilder sb = new StringBuilder();
                for(int i = 2 ; i < cmd.length ; i++){
                    sb.append(cmd[i] + " ");
                }
                String townName = sb.toString().trim();
                MySQL mysql = new MySQL();
                if(mysql.keyExists(townName, "towns", "name")){
                    player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fTown §a'§b"+townName+"§a'§f already exists.");
                    return true;
                }
                mysql.insertTownPlayer(player.getOfflineName(), townName);
                mysql.insertTown(townName, player.getOfflineName(), "");
                player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fTown §a'§b"+townName+"§a'§f has been created!");
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
                
                if(acceptMap.containsKey(iplayer) && acceptMap.get(iplayer).equals(player)){
                    iplayer.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §b"+player.getOfflineName()+"§f has invited you to §b"+tp.getTownName()+"§f.");
                    iplayer.sendMessage("    §a- To Accept Do:  §f/town accept [townname]");
                    player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §b"+iplayer.getOfflineName()+"§f has been invited your town.");
                    return true;
                }
                acceptMap.put(iplayer, player);
                iplayer.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §b"+player.getOfflineName()+"§f has invited you to §b"+tp.getTownName()+"§f.");
                iplayer.sendMessage("    §a- To Accept Do:  §f/town accept [townname]");
                player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §b"+iplayer.getOfflineName()+"§f has been invited your town.");
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
                    MySQL mysql = new MySQL();
                    mysql.insertTownPlayer(player.getOfflineName(), otp.getTownName());
                    player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §bYou have accepted the invitation to " + otp.getTownName()+".");
                   acceptMap.get(player).sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §b"+player.getOfflineName()+"§f has accepted an invitation to your town.");
                   acceptMap.remove(player);
                }
            }
            
    //                                  //
    //            Leave Town            //
    //                                  //
            if(cmd[1].equalsIgnoreCase("leave") && tp != null ){
                MySQL mysql = new MySQL();
                mysql.delete("townsusers", "username", tp.getName());
                player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a]  §fYou have left your town");
            }
            
    //                                  //
    //            Create Town           //
    //                                  //
            
            
            
            
                help(player);
                return true;

            }
        return false;
    }
        
        
        public void help(Player player){
            player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fStunnerTowns help");
            player.sendMessage("  §a- /t claim    §fClaim land for your town");
            player.sendMessage("  §a- /t unclaim  §fUnclaim land for your town");
            player.sendMessage("  §a- /t here  §fSee who owns the town where you are standing");
            player.sendMessage("  §a- /t unclaim  §fUnclaim land for your town");
            player.sendMessage("  §a- /t invite [playername]  §finvite a player to your town");
            player.sendMessage("  §a- /t accept [townname]  §faccept an invitation to a town");
            player.sendMessage("  §a- /t create  [townname]§fcreate a town");
            player.sendMessage("  §a- /t disband  §fdisband your town");
        }
    
}
