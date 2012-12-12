
/**
 *
 * @author Somners
 */
public class StunnerDamageListener extends PluginListener{
    private FiveStarTowns plugin;
    private Database data;
    
    /**
     *
     */
    public StunnerDamageListener(){
        plugin = FiveStarTowns.getInstance();
        data = plugin.getDatabase();
    }
    
        /**
     *
     * @param type
     * @param Attacker
     * @param Defender
     * @param amount
     * @return
     */
    public boolean onDamage(PluginLoader.DamageType type, BaseEntity Attacker, BaseEntity Defender, int amount){
            if(type == PluginLoader.DamageType.ENTITY){
                if(Attacker.isPlayer() && Defender.isPlayer()){
                    Player attack = Attacker.getPlayer();
                    Player defend = Defender.getPlayer();
                    int defx = (int)defend.getX() >> 4;
                    int defz = (int)defend.getZ() >> 4;
                    int attx = (int)defend.getX() >> 4;
                    int attz = (int)defend.getZ() >> 4;
                    String defChunky = defx + ":" + defz + ":" + defend.getWorld().getName();
                    String attChunky = attx + ":" + attz + ":" + attack.getWorld().getName();
                    String defLoc = data.getStringValue(defChunky, "chunks", "town", "coords");
                    String attLoc = data.getStringValue(attChunky, "chunks", "town", "coords");
                    Town defTown = plugin.getManager().getTown(defLoc);
                    Town attTown = plugin.getManager().getTown(attLoc);
                    if(defTown !=null){
                        if(defTown.getNoPvp()){
                            return true;
                        }
                    }
                    if(attTown !=null){
                        if(attTown.getNoPvp()){
                            return true;
                        }
                    }
                    TownPlayer dtp = plugin.getManager().getTownPlayer(defend.getOfflineName());
                    TownPlayer atp = plugin.getManager().getTownPlayer(attack.getOfflineName());
                    if(dtp != null && atp != null){
                        if(dtp.getTownName().equalsIgnoreCase(atp.getTownName()) && !atp.getTown().getFriendlyFire()){
                            return true;
                        }
                    }
                }
            }
            return false;
    }
}
