
public class StunnerSpawnListener extends PluginListener{
    FiveStarTowns plugin;
    
    public StunnerSpawnListener(){
        plugin = FiveStarTowns.getInstance();
    }
    
    public boolean onMobSpawn(Mob mob){
        MySQL mysql = new MySQL();
        int x = (int)mob.getX() >> 4, z = (int)mob.getZ() >> 4;
        String chunky = x+":"+z;
        String townName = mysql.getStringValue(chunky, "chunks", "town", "coords");
        Town town = plugin.getManager().getTown(townName);
        if(town != null){
            if(town.getSanctuary()){
                return true;
            }
        }
        return false;
    }
}
