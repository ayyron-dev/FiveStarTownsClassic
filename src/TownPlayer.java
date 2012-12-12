

/**
 *
 * @author Somners
 */
public class TownPlayer{
    private String name;
    private String townName;
    private Town town;
    private boolean isOwner = false;
    private boolean isAssistant = false;
    private Database data;
    private FiveStarTowns plugin;
    
    /**
     *
     * @param player
     */
    public TownPlayer(Player player){
        plugin = FiveStarTowns.getInstance();
        data = plugin.getDatabase();
        name = player.getOfflineName();
        townName = data.getStringValue(name, "townsusers", "town", "username");
        if(name.equalsIgnoreCase(data.getStringValue(townName, "towns", "owner", "name"))){
            isOwner = true;
        }
        if(name.equalsIgnoreCase(data.getStringValue(townName, "towns", "assistant", "name"))){
            isAssistant = true;
        }
        town = plugin.getManager().getTown(townName);
    }
    
    /**
     *
     * @return
     */
    public String getName(){
        return name;
    }
    
    /**
     *
     * @return
     */
    public String getTownName(){
        return townName;
    }
    
    /**
     *
     * @return
     */
    public Town getTown(){
        return town;
    }
    
    /**
     *
     * @return
     */
    public boolean isOwner(){
        return isOwner;
    }
    
    /**
     *
     * @return
     */
    public boolean isAssistant(){
        return isAssistant;
    }
        
}
