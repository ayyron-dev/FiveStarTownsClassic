
/**
 *
 * @author Somners
 */
public class StunnerMoveListener extends PluginListener{
    FiveStarTowns plugin;
    
    /**
     *
     */
    public StunnerMoveListener(){
        this.plugin = FiveStarTowns.getInstance();
    }
    
    /**
     *
     * @param player
     * @param from
     * @param to
     */
    public void onPlayerMove(Player player, Location from, Location to){
        Pos pto = new Pos(to);
        Pos pfrom = new Pos(from);
        String chunkfrom = pfrom.getCoord();
        String chunkto = pto.getCoord();
//        player.sendMessage(tox + ":" + toz);
        String chunk1 = "";
        String chunk2 = "";
        if(plugin.getManager().containsKey(chunkto)){
            chunk2 = plugin.getManager().get(chunkto);
            }
        if(!plugin.getManager().containsKey(chunkto)){
            chunk2 = "Wilderness";
            }
        if(plugin.getManager().containsKey(chunkfrom)){
            chunk1 = plugin.getManager().get(chunkfrom);
            }
        if(!plugin.getManager().containsKey(chunkfrom)){
            chunk1 = "Wilderness";
            }
//        player.sendMessage(chunk2);
        if(chunk1.equalsIgnoreCase(chunk2)){
            return;
        }
        if(!chunk1.equalsIgnoreCase(chunk2)){
            if(chunk2.equalsIgnoreCase("Wilderness")){
                Town town = plugin.getManager().getTown(chunk1);
                player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fYou are now entering: §b" + chunk2 + " Territory.");
                if(town != null){
                    player.sendMessage("  §a- §f" + town.getFarewell());
                }
                return;
            }
            Town town = plugin.getManager().getTown(chunk2);
            if(town != null){
                player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fNow Entering: §b" + town.getRankName()+ " " + town.getName() + " Territory§f.");
                player.sendMessage("  §a- §f" + town.getWelcome());
                return;
            }

        }
        
        
        
    }
}
