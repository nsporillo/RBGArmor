package net.porillo;

public enum Mode {

    SYNC(Lang.SYNC_NAME.cap(), Lang.SYNC_DESC.toString()),
    FADE(Lang.FADE_NAME.cap(), Lang.FADE_DESC.toString()),
    HEALTH(Lang.HEALTH_NAME.cap(), Lang.HEALTH_DESC.toString());

    private String desc;
    private String name;

    Mode(String name, String desc) {       
        this.name = name;
        this.desc = desc;
    }
    
    public String getName() {
        return name;
    }

    public String getDescription() {
        return desc;
    }
}
