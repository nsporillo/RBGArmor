package net.porillo;

public enum Mode {
  
    SYNC(Lang.SYNC_DESC.toString()),
    FADE(Lang.FADE_DESC.toString()),
    HEALTH(Lang.HEALTH_DESC.toString());
    
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
