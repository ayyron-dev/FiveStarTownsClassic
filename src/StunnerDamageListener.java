
public class StunnerDamageListener {
    StunnerTowns plugin;
    
    public StunnerDamageListener(){
        plugin = StunnerTowns.getInstance();
    }
    
        public boolean onDamage(PluginLoader.DamageType type, BaseEntity Attacker, BaseEntity Defender, int amount){
            if(type == PluginLoader.DamageType.ENTITY && plugin.getConfig().getPvP()){
                if(Attacker.isPlayer() && Defender.isPlayer()){
                    Player attack = Attacker.getPlayer();
                    Player defend = Defender.getPlayer();
                    int x = (int)defend.getX() >> 4;
                    int z = (int)defend.getZ() >> 4;
                    String chunky = x + ":" + z;
                    if(!plugin.getManager().containsKey(chunky)){
                        return false;
                    }
                    if(!plugin.getManager().get(chunky).equalsIgnoreCase("Wilderness")){
                        attack.sendMessage("§a[§b" + plugin.getConfig().getServerName() + "§a] §f This is §b" + plugin.getManager().get(chunky) + " Territory §f you can't §bPvP §fhere.");
                        return true;
                    }
                }
            }
            return false;
    }
}
