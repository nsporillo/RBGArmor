# RainbowGear

RainbowGear is a plugin designed for Bukkit which allows players to equip specialized armor (lore based) which constantly shifts color in a rainbow pattern. Color is synced for all four armor pieces, but not all are required to be worn! 

## Example
![alt text](http://nick.porillo.net/images/rg.gif "Example")

## Commands
- /rg - Applies lore to your leather armor. Only available to OPs.
- Note: **I recommend you make a kit in essentials with the lore'd armor, so you can have kit cooldowns!**

## Color Engine: 

You can determine how many colors you want to display. 
You can determine how fast the color is updated.

**Default settings:** `64` colors and refresh rate of `5`

### What does this mean? 
`5 means 5 ticks`

With the default settings, armor is updated `4` times per second. 
This means that one cycle takes `16` seconds. 

#### How to speed up a cycle?
- Decrease colors
- Decrease refresh rate 

### Example
Colors | RefreshRate | Cycle | 
--- | --- | ---
64 | 5 | 16s 
24 | 5 | 6s
24 | 7 | 9.45s 


