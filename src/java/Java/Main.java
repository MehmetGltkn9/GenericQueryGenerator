package Java;

import jakarta.jms.Connection;
import java.beans.IntrospectionException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {
    
    public static void main(String[] args) {
        Connection connection = null;
        GenericDAO gd = new GenericDAO(connection);
        
        Car car = new Car("CAR45674", "Toyota", "Red", 4);
        
        //Car car = new Car();
        
        Building building = new Building("Hastane", 45, true);
        
        try {
            gd.insertEntity(car, "Cars");
            gd.insertEntity(building, "Buildings");
            gd.getEntity(car, "Cars");
            gd.getEntity(building, "Buildings");
            gd.deleteEntity(car, "Cars");
            gd.deleteEntity(building,"Buildings" );
            gd.updateEntity(car, "Cars", 3);
            gd.updateEntity(building, "Buildings", 4);
            
        } 
        catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } 
//        catch (IntrospectionException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        } 
      catch (IllegalAccessException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
}
