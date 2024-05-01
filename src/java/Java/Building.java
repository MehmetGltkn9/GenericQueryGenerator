package Java;

public class Building {
    private String name;
    private long size;
    private boolean isEmpty;

    public Building() {
        
    }

    public Building(String name, long size, boolean isEmpty) {
        this.name = name;
        this.size = size;
        this.isEmpty = isEmpty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public boolean isIsEmpty() {
        return isEmpty;
    }

    public void setIsEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }
    
    
}
