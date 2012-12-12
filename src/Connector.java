
import java.sql.Connection;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Somners
 */
public interface Connector {
    
    /**
     *
     * @return
     */
    public Connection getConnection();
    
}
