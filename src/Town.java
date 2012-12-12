

import java.util.List;

/**
 *
 * @author Somners
 */
public class Town{
    
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
    private Database data;
    private int bonusChunks;
    private FiveStarTowns plugin;
    private TownRankManager trm;
    
    /**
     *
     * @param townName
     */
    public Town(String townName){
        plugin = FiveStarTowns.getInstance(); 
        data = plugin.getDatabase();
        trm = plugin.getTownRankManager();
        members = data.getTownPlayers(townName);
        membernum = members.size();
        name = townName;
        owner = data.getStringValue(name, "towns", "owner", "name");
        assistant = data.getStringValue(name, "towns", "assistant", "name");
        bonusChunks = data.getIntValue(name, "towns", "bonus", "name");
        balance = data.getDoubleValue(name, "towns", "balance", "name");
        if(data.getIntValue(name, "towns", "nopvp", "name") == 1){
            nopvp = true;
        }
        if(data.getIntValue(name, "towns", "friendlyfire", "name") == 1){
            friendlyfire = true;
        }
        if(data.getIntValue(name, "towns", "sanctuary", "name") == 1){
            sanctuary = true;
        }
        if(data.getIntValue(name, "towns", "protected", "name") == 1){
            protection = true;
        }
        if(data.getIntValue(name, "towns", "creepernerf", "name") == 1){
            creepernerf = true;
        }
        rank = trm.getTownRank(membernum);
    }
    
    /**
     *
     * @return
     */
    public String getOwner(){
        return owner;
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
    public String getAssistant(){
        return assistant;
    }
    
    /**
     *
     * @return
     */
    public List getMembers(){
        return members;
    }
    
    /**
     *
     * @return
     */
    public int getMemberSize(){
        return membernum;
    }
    
    /**
     *
     * @return
     */
    public TownPlayer getOwnerTP(){
        return plugin.getManager().getTownPlayer(owner);
    }
    
    /**
     *
     * @return
     */
    public TownPlayer getAssistantTP(){
        return plugin.getManager().getTownPlayer(assistant);
    }
    
    /**
     *
     * @param tp
     */
    public void setAssistant(TownPlayer tp){
        String tpname = tp.getName();
        data.updateStringEntry(name, "name", "towns", tpname, "assistant");
    }
    
    /**
     *
     * @return
     */
    public int getBonus(){
        return bonusChunks;
    }
    /**
     *
     * @param num
     */
    public void setBonus(int num){
        data.updateIntEntry(name, "name", "towns", num, "bonus");
    }
    
    /**
     *
     * @param toAdd
     */
    public void addBonus(int toAdd){
        int oldnum = data.getIntValue(name, "towns", "bonus", "name");
        data.updateIntEntry(name, "name", "towns", oldnum + toAdd, "bonus");
    }
    
    /**
     *
     * @return
     */
    public int getMemberCount(){
        return getMembers().size();
    }
    
    /**
     *
     * @return
     */
    public double getBalance(){
        return balance;
    }
    
    /**
     *
     * @param toAdd
     */
    public void addBalance(double toAdd){
        balance = balance + toAdd;
        data.updateDoubleEntry(name, "name", "towns", balance, "balance");
    }
    
    /**
     *
     * @param toRemove
     */
    public void removeBalance(double toRemove){
        balance = balance - toRemove;
        data.updateDoubleEntry(name, "name", "towns", balance, "balance");
    }
    
    /**
     *
     * @return
     */
    public int getTownRank(){
        return rank;
    }
    
    /**
     *
     * @return
     */
    public String getRankName(){
        return trm.getTownRankName(rank);
    }
    
    /**
     *
     * @return
     */
    public String getMayorName(){
        return trm.getTownMayorName(rank);
    }
    
    /**
     *
     * @return
     */
    public String getAssistantName(){
        return trm.getTownAssistantName(rank);
    }
    
    /**
     *
     * @param flag
     * @return
     */
    public boolean canUseFlag(String flag){
        List flags = trm.getTownFlags(rank);
        if(flags.contains(flag)){
            return true;
        }
        return false;
    }
    
    /**
     *
     * @param toSet
     */
    public void setNoPvp(int toSet){
        data.updateDoubleEntry(name, "name", "towns", toSet, "nopvp");
    }
    
    /**
     *
     * @param toSet
     */
    public void setProtected(int toSet){
        data.updateDoubleEntry(name, "name", "towns", toSet, "protected");
    }
    
    /**
     *
     * @param toSet
     */
    public void setSanctuary(int toSet){
        data.updateDoubleEntry(name, "name", "towns", toSet, "sanctuary");
    }
    
    /**
     *
     * @param toSet
     */
    public void setCreeperNerf(int toSet){
        data.updateDoubleEntry(name, "name", "towns", toSet, "creepernerf");
    }
    
    /**
     *
     * @param toSet
     */
    public void setFriendlyFire(int toSet){
        data.updateDoubleEntry(name, "name", "towns", toSet, "friendlyfire");
    }
    
    /**
     *
     * @return
     */
    public boolean getNoPvp(){
        return nopvp;
    }
    
    /**
     *
     * @return
     */
    public boolean getProtected(){
        return protection;
    }
    
    /**
     *
     * @return
     */
    public boolean getSanctuary(){
        return sanctuary;
    }
    
    /**
     *
     * @return
     */
    public boolean getCreeperNerf(){
        return creepernerf;
    }
    
    /**
     *
     * @return
     */
    public boolean getFriendlyFire(){
        return friendlyfire;
    }
    
    /**
     *
     * @return
     */
    public String getFlagString(){
        StringBuilder sb = new StringBuilder();
        if(protection){
            sb.append("|Protected|");
        }
        if(sanctuary){
            sb.append("|Sanctuary|");
        }
        if(nopvp){
            sb.append("|No-PvP|");
        }
        if(creepernerf){
            sb.append("|No-Creeper|");
        }
        if(friendlyfire){
            sb.append("|Friendly Fire|");
        }
        return sb.toString();
    }
    
    /**
     *
     * @return
     */
    public String getAvailableFlags(){
        StringBuilder sb = new StringBuilder();
        List flags = trm.getTownFlags(rank);
        for(int i = 0; i < flags.size() ; i++){
            sb.append("|").append(flags.get(i)).append("|");
        }
        return sb.toString();
    } 
    
    /**
     *
     * @return
     */
    public String getWelcome(){
        return data.getStringValue(name, "towns", "welcomemsg", "name");
    }
    
    /**
     *
     * @return
     */
    public String getFarewell(){
        return data.getStringValue(name, "towns", "farewellmsg", "name");
    }
    
    /**
     *
     * @param msg
     */
    public void setFarewell(String msg){
        data.updateStringEntry(name, "name", "towns", msg, "farewellmsg");
    }
    
    /**
     *
     * @param msg
     */
    public void setWelcome(String msg){
        data.updateStringEntry(name, "name", "towns", msg, "welcomemsg");
    }
    
}
