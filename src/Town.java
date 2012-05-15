import java.util.List;


public class Town {
    
    private List members;
    private String name;
    private String owner;
    private String assistant;
    private Double balance;
    MySQL mysql = new MySQL();
    private int bonusChunks;
    private StunnerTowns plugin;
    
    public Town(String townName){
        plugin = StunnerTowns.getInstance();
        members = mysql.getTownPlayers(townName);
        name = townName;
        owner = mysql.getStringValue(name, "towns", "owner", "name");
        assistant = mysql.getStringValue(name, "towns", "assistant", "name");
        bonusChunks = mysql.getIntValue(name, "towns", "bonus", "name");
        balance = mysql.getDoubleValue(name, "towns", "balance", "name");
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
    
    public void setAssistant(TownPlayer tp){
        String tpname = tp.getName();
        mysql.updateStringEntry(name, "name", "towns", tpname, "assistant");
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
    
    public double getBalance(){
        return balance;
    }
    
    public void addBalance(double toAdd){
        balance = balance + toAdd;
        mysql.updateDoubleEntry(name, "name", "towns", balance, "balance");
    }
    
    public void removeBalance(double toRemove){
        balance = balance - toRemove;
        mysql.updateDoubleEntry(name, "name", "towns", balance, "balance");
    }
    
    
}
