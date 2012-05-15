
public class TownPlayer {
    String name;
    String townName;
    Town town;
    boolean isOwner = false;
    boolean isAssistant = false;
    MySQL mysql = new MySQL();
    StunnerTowns plugin;
    
    public TownPlayer(Player player){
        plugin = StunnerTowns.getInstance();
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
