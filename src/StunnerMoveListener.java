
public class StunnerMoveListener extends PluginListener{
    StunnerTowns plugin;
    
    public StunnerMoveListener(){
        this.plugin = StunnerTowns.getInstance();
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
            player.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §fYou are now entering: §b" + chunk2 + " Territory.");
            if(plugin.getConfig().getPvP() && !chunk2.equalsIgnoreCase("Wilderness")){
                player.sendMessage("  §a- §bPvP §fis §bdisabled §fhere.");
            }
            if(chunk2.equalsIgnoreCase("Wilderness")){
                player.sendMessage("  §a- §bPvP §fis §benabled §fhere.");
            }
            if(plugin.getConfig().getProtection() && !chunk2.equalsIgnoreCase("Wilderness")){
                player.sendMessage("  §a- §fOnly Town Residents can build here.");
            }
        }
        
        
        
    }
}
