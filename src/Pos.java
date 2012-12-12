/**
 *
 * @author Somners
 */
public class Pos {
    Pos(Location l) {
        x = (int) l.x;
        y = (int) l.y;
        z = (int) l.z;
        xx = x >> 4;
        zz = z >> 4;
        loc = l;
    }
    int x, y, z, xx, zz;
    Location loc;
    public boolean equals(Object o) {
        if (!getClass().isAssignableFrom(o.getClass()))
            return false;
        Pos p = (Pos) o;
        return x == p.x && y == p.y && z == p.z;
}
    /**
     *
     * @return
     */
    public String getCoord(){
        return xx + ":" + zz + ":" + loc.getWorld().getName();
    }
}