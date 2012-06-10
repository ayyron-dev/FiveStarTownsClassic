
import java.util.List;
import java.util.logging.Logger;


public class OwnerCommandListener extends PluginListener{
    private Logger log=Logger.getLogger("Minecraft");
    private FiveStarTowns plugin;
    private StunnerCommandListener scl;
    public OwnerCommandListener(){
        this.plugin = FiveStarTowns.getInstance();
        scl = plugin.getCommandListener();
    }
    
    public boolean onCommand(Player player, String[] cmd){
        if(player.canUseCommand("/fivestartowns") && (cmd[0].equalsIgnoreCase("/town") || cmd[0].equalsIgnoreCase("/t"))){
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
    //            set welcome           //
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
            
            
      
            
    //                                   //
    //            set farewell           //
    //                                   //      
            
            if(cmd[1].equalsIgnoreCase("setfarewell") && cmd.length > 2){
                StringBuilder sb = new StringBuilder();
                for(int i = 2; i < cmd.length ; i++){
                    sb.append(cmd[i]).append(" ");
                }
                tp.getTown().setFarewell(sb.toString().trim());
                player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fFarewell Message set to: " + sb.toString().trim());
                return true;
            }
            
            
    //                                   //
    //            toggle flags           //
    //                                   //        
            
            if(cmd[1].equalsIgnoreCase("toggle") && cmd.length >= 2){
                Town town = tp.getTown();
                if(cmd.length == 2){
                    player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fAvailable toggles for you are: ");
                    player.sendMessage("    §a- " + town.getAvailableFlags());
                    return true;
                }
                if (cmd.length == 4) {
                    if(cmd[2].equalsIgnoreCase("nopvp")){
                        if(town.canUseFlag("nopvp")){
                            if(cmd[3].equalsIgnoreCase("on")){
                                town.setNoPvp(1);
                                player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §b" +cmd[3]+ " §fhas been set to §b" + cmd[3]);
                                return true;
                            }
                            if(cmd[3].equalsIgnoreCase("off")){
                                town.setNoPvp(0);
                                player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §b" +cmd[3]+ " §fhas been set to §b" + cmd[3]);
                                return true;
                            }
                        }
                        player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fYou cannot toggle: §b" + "nopvp");
                        return true;
                    }
                    if(cmd[2].equalsIgnoreCase("creepernerf")){
                        if(town.canUseFlag("creepernerf")){
                            if(cmd[3].equalsIgnoreCase("on")){
                                town.setCreeperNerf(1); 
                                player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §b" +cmd[3]+ " §fhas been set to §b" + cmd[3]);
                                return true;
                            }
                            if(cmd[3].equalsIgnoreCase("off")){
                                town.setCreeperNerf(0); 
                                player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §b" +cmd[3]+ " §fhas been set to §b" + cmd[3]);
                                return true;
                            }
                        }
                        player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fYou cannot toggle: §b" + "creepernerf");
                        return true;
                    }
                    if(cmd[2].equalsIgnoreCase("friendlyfire")){
                        if(town.canUseFlag("friendlyfire")){
                            if(cmd[3].equalsIgnoreCase("on")){
                                town.setFriendlyFire(1);
                                player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §b" +cmd[3]+ " §fhas been set to §b" + cmd[3]);
                                return true;
                            }
                            if(cmd[3].equalsIgnoreCase("off")){
                                town.setFriendlyFire(0);
                                player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §b" +cmd[3]+ " §fhas been set to §b" + cmd[3]);
                                return true;
                            }
                        }
                        player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fYou cannot toggle: §b" + "friendlyfire");
                        return true;
                    }
                    if(cmd[2].equalsIgnoreCase("protected")){
                        if(town.canUseFlag("protected")){
                            if(cmd[3].equalsIgnoreCase("on")){
                                town.setProtected(1);
                                player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §b" +cmd[3]+ " §fhas been set to §b" + cmd[3]);
                                return true;
                            }
                            if(cmd[3].equalsIgnoreCase("off")){
                                town.setProtected(0);
                                player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §b" +cmd[3]+ " §fhas been set to §b" + cmd[3]);
                                return true;
                            }
                        }
                        player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fYou cannot toggle: §b" + "protected");
                        return true;
                    }
                    if(cmd[2].equalsIgnoreCase("sanctuary")){
                        if(town.canUseFlag("sanctuary")){
                            if(cmd[3].equalsIgnoreCase("on")){
                                town.setSanctuary(1);
                                player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §b" +cmd[3]+ " §fhas been set to §b" + cmd[3]);
                                return true;
                            }
                            if(cmd[3].equalsIgnoreCase("off")){
                                town.setSanctuary(0);
                                player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §b" +cmd[3]+ " §fhas been set to §b" + cmd[3]);
                                return true;
                            }
                        }
                        player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fYou cannot toggle: §b" + "sanctuary");
                        return true;
                    }
                }
            }
            
            
            
            
            
            
            
        }
        
        }
        return false;
    }
    
    
    

    
    
}
