import java.util.List;


public class Town {
    
    private List members;
    private String name;
    private String owner;
    private String assistant;
    private Double balance;
    private int membernum;
    private int rank;
    private boolean nopvp = false;
    private boolean friendlyfire = false;
    private boolean sanctuary = false;
    private boolean protection = false;
    private boolean creepernerf = false;
    MySQL mysql = new MySQL();
    private int bonusChunks;
    private StunnerTowns plugin;
    private TownRankManager trm;
    
    public Town(String townName){
        plugin = StunnerTowns.getInstance();
        trm = plugin.getTownRankManager();
        members = mysql.getTownPlayers(townName);
        membernum = members.size();
        name = townName;
        owner = mysql.getStringValue(name, "towns", "owner", "name");
        assistant = mysql.getStringValue(name, "towns", "assistant", "name");
        bonusChunks = mysql.getIntValue(name, "towns", "bonus", "name");
        balance = mysql.getDoubleValue(name, "towns", "balance", "name");
        if(mysql.getIntValue(name, "towns", "nopvp", "name") == 1){
            nopvp = true;
        }
        if(mysql.getIntValue(name, "towns", "friendlyfire", "name") == 1){
            friendlyfire = true;
        }
        if(mysql.getIntValue(name, "towns", "sanctuary", "name") == 1){
            sanctuary = true;
        }
        if(mysql.getIntValue(name, "towns", "protected", "name") == 1){
            protection = true;
        }
        if(mysql.getIntValue(name, "towns", "creepernerf", "name") == 1){
            creepernerf = true;
        }
        rank = trm.getTownRank(membernum);
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
    
    public int getMemberSize(){
        return membernum;
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
    
    public int getBonus(){
        return bonusChunks;
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
    
    public int getTownRank(){
        return rank;
    }
    
    public String getMayorName(){
        return trm.getTownMayorName(rank);
    }
    
    public String getAssistantName(){
        return trm.getTownAssistantName(rank);
    }
    
    public boolean canUseFlag(String flag){
        List flags = trm.getTownFlags(rank);
        if(flags.contains(flag)){
            return true;
        }
        return false;
    }
    
    public void setNoPvp(int toSet){
        mysql.updateDoubleEntry(name, "name", "towns", toSet, "nopvp");
    }
    
    public void setProtected(int toSet){
        mysql.updateDoubleEntry(name, "name", "towns", toSet, "protected");
    }
    
    public void setSanctuary(int toSet){
        mysql.updateDoubleEntry(name, "name", "towns", toSet, "sanctuary");
    }
    
    public void setCreeperNerf(int toSet){
        mysql.updateDoubleEntry(name, "name", "towns", toSet, "creepernerf");
    }
    
    public void setFriendlyFire(int toSet){
        mysql.updateDoubleEntry(name, "name", "towns", toSet, "friendlyfire");
    }
    
    public boolean getNoPvp(){
        return nopvp;
    }
    
    public boolean getProtected(){
        return protection;
    }
    
    public boolean getSanctuary(){
        return sanctuary;
    }
    
    public boolean getCreeperNerf(){
        return creepernerf;
    }
    
    public boolean getFriendlyFire(){
        return friendlyfire;
    }
    
    
    
}
