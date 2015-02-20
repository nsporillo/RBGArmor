package net.porillo;

public enum Mode {
  
    SYNC(Lang.SYNC.toString()),
    FADE(Lang.FADE.toString()),
    HEALTH(Lang.HEALTH.toString());
    
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
