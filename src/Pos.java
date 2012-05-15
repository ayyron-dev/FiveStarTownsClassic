public class Pos {
    Pos(Location l) {
        x = (int) l.x;
        y = (int) l.y;
        z = (int) l.z;
    }
    int x, y, z;
    public boolean equals(Object o) {
        if (!getClass().isAssignableFrom(o.getClass()))
            return false;
        Pos p = (Pos) o;
        return x == p.x && y == p.y && z == p.z;
}
}