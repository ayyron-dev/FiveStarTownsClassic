
public class StunnerExplosionListener extends PluginListener {
    
    FiveStarTowns plugin;
        
    public StunnerExplosionListener(){
        plugin = FiveStarTowns.getInstance();
    }
    
    @Override
    public boolean onExplode(Block block, OEntity entity, java.util.HashSet blocksaffected){
        MySQL mysql = new MySQL();
        int x = block.getX() >> 4, z = block.getZ() >> 4;
        String chunky = x+":"+z;
        String townName = mysql.getStringValue(chunky, "chunks", "town", "coords");
        Town town = plugin.getManager().getTown(townName);
        if(town != null){
            if(town.getCreeperNerf()){
                return true;
            }
        }
        return false;
    }
    
    
}
