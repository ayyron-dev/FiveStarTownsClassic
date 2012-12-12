

/**
 *
 * @author Somners
 */
public class StunnerSpawnListener extends PluginListener{
    private FiveStarTowns plugin;
    private Database data;
    
    /**
     *
     */
    public StunnerSpawnListener(){
        plugin = FiveStarTowns.getInstance();
        data = FiveStarTowns.getDatabase();
    }
    
    /**
     *
     * @param mob
     * @return
     */
    @Override
    public boolean onMobSpawn(Mob mob){
        int x = (int)mob.getX() >> 4, z = (int)mob.getZ() >> 4;
        String chunky = x+":"+z + ":" + mob.getWorld().getName();
        String townName = data.getStringValue(chunky, "chunks", "town", "coords");
        Town town = plugin.getManager().getTown(townName);
        if(town != null){
            if(town.getSanctuary()){
                return true;
            }
        }
        return false;
    }
}
