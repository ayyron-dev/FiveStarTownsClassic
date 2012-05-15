import java.util.List;


public class Town {
    
    List members;
    String name;
    String owner;
    String assistant;
    MySQL mysql = new MySQL();
    int bonusChunks;
    StunnerTowns plugin;
    
    public Town(String townName){
        plugin = StunnerTowns.getInstance();
        members = mysql.getTownPlayers(townName);
        name = townName;
        owner = mysql.getStringValue(name, "towns", "owner", "name");
        assistant = mysql.getStringValue(name, "towns", "assistant", "name");
        bonusChunks = mysql.getIntValue(name, "towns", "bonus", "name");
    }
    
    public String getOwner(){
        return owner;
    }
    
    public String getName(){
        return name;
    }
    
    public String getAssistant(){
        return assistant;
    }
    
    public List getMembers(){
        return members;
    }
    
    public TownPlayer getOwnerTP(){
        return plugin.getManager().getTownPlayer(owner);
    }
    
    public TownPlayer getAssistantTP(){
        return plugin.getManager().getTownPlayer(assistant);
    }
    
    public void setBonus(int num){
        mysql.updateIntEntry(name, "name", "towns", num, "bonus");
    }
    
    public void addBonus(int toAdd){
        int oldnum = mysql.getIntValue(name, "towns", "bonus", "name");
        mysql.updateIntEntry(name, "name", "towns", oldnum + toAdd, "bonus");
        
    }
    
    public int getMemberCount(){
        return getMembers().size();
    }
}
