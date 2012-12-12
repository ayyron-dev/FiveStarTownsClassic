
/**
 *
 * @author Somners
 */
public class StunnerExplosionListener extends PluginListener {
    
    private FiveStarTowns plugin;
    private Database data;
        
    /**
     *
     */
    public StunnerExplosionListener(){
        plugin = FiveStarTowns.getInstance();
        data = plugin.getDatabase();
    }
    
    /**
     *
     * @param block
     * @param entity
     * @param blocksaffected
     * @return
     */
    @Override
    public boolean onExplode(Block block, OEntity entity, java.util.HashSet blocksaffected){
        int x = block.getX() >> 4, z = block.getZ() >> 4;
        String chunky = x+":"+z + ":" + block.getWorld().getName();
        String townName = data.getStringValue(chunky, "chunks", "town", "coords");
        Town town = plugin.getManager().getTown(townName);
        if(town != null){
            if(town.getCreeperNerf()){
                return true;
            }
        }
        return false;
    }
    
    
}
