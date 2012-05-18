
public class StunnerMoveListener extends PluginListener{
    FiveStarTowns plugin;
    
    public StunnerMoveListener(){
        this.plugin = FiveStarTowns.getInstance();
    }
    
    public void onPlayerMove(Player player, Location from, Location to){
        Pos pto = new Pos(to);
        Pos pfrom = new Pos(from);
        int tox = pto.x >> 4, toz = pto.z >> 4, fromx = pfrom.x >> 4, fromz = pfrom.z >>4;
        String chunkfrom = fromx+ ":" + fromz;
        String chunkto = tox + ":" + toz;
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
                player.sendMessage("  §a- §bPvP §fis §benabled §fhere.");
                return;
            }
            Town town = plugin.getManager().getTown(chunk2);
            if(town != null){
                player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fNow Entering: §b" + town.getRankName()+ " " + town.getName() + " Territory§f.");
                player.sendMessage("§a"+town.getMayorName()+"§b:§f " + town.getOwner());
                player.sendMessage("§aFlags§b:§f " + town.getFlagString());
                player.sendMessage("  §a- §f" + town.getWelcome());
                return;
            }

        }
        
        
        
    }
}
