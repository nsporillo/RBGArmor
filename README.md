# RBGArmor

RBGArmor is a plugin designed for Bukkit which allows players to equip specialized armor (lore based) which constantly shifts color in a rainbow pattern. Color is synced for all four armor pieces, but not all are required to be worn! 

### Notice
- RBGArmor is a premium plugin on the [SpigotMC](http://www.spigotmc.org/resources/) , if you use this plugin and your server makes money, please support development and purchase the resource. If for some reason you cannot pay and would like a copy, I can make exceptions - just contact me.  

## Example
![alt text](http://nick.porillo.net/images/rg.gif "Example")
- Note: Gif is sped up, actual speed based on config

## Commands
- /rg : Base command for RBGArmor
- /rg help - Help command
- /rg off - Turns off armor coloring
- /rg set mode - Sets your armor coloring mode 
- /rg modes - Lists the available coloring modes

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
`24` | `7` | `9.45s`
64 | 2 | 6.4s 
24 | 5 | 6s

I strongly recommend you take the time to test out the engine settings until you find one you like. Please remember that a higher refresh rate will be lighter on your server load, and thus a 24 color to 7 refresh is probably the most efficient, while still decent looking. 
 


