
public class TownPlayer {
    private String name;
    private String townName;
    private Town town;
    private boolean isOwner = false;
    private boolean isAssistant = false;
    MySQL mysql = new MySQL();
    private FiveStarTowns plugin;
    
    public TownPlayer(Player player){
        plugin = FiveStarTowns.getInstance();
        name = player.getOfflineName();
        townName = mysql.getStringValue(name, "townsusers", "town", "username");
        if(name.equalsIgnoreCase(mysql.getStringValue(townName, "towns", "owner", "name"))){
            isOwner = true;
        }
        if(name.equalsIgnoreCase(mysql.getStringValue(townName, "towns", "assistant", "name"))){
            isAssistant = true;
        }
        town = plugin.getManager().getTown(townName);
    }
    
    public String getName(){
        return name;
    }
    
    public String getTownName(){
        return townName;
    }
    
    public Town getTown(){
        return town;
    }
    
    public boolean isOwner(){
        return isOwner;
    }
    
    public boolean isAssistant(){
        return isAssistant;
    }
        
}
