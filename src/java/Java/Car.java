
package Java;

public class Car {
    private String id;
    private String name;
    private String colour;
    private int tires;

    public Car() {
        
    }

    public Car(String id, String name, String colour, int tires) {
        this.id = id;
        this.name = name;
        this.colour = colour;
        this.tires = tires;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public int getTires() {
        return tires;
    }

    public void setTires(int tires) {
        this.tires = tires;
    }
    
    
}
