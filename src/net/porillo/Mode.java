package net.porillo;

public enum Mode {
  
    SYNC("All armor pieces are updated with same color"),
    FADE("Each armor piece gets the next color update"),
    HEALTH("Armor color is based on health");
    
    private String desc;
    
    Mode(String desc) {
        this.desc = desc;
    }
    
    @Override
    public String toString() {
        return Character.toUpperCase(name().charAt(0)) + name().substring(1).toLowerCase();
    }
    
    public String getDescription() {
        return desc;
    }
}
