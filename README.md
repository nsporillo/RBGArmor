# RBGArmor   [![Build Status](https://travis-ci.org/nsporillo/RBGArmor.svg?branch=master)](https://travis-ci.org/nsporillo/RBGArmor)

RBGArmor is a dynamic armor coloring shifting plugin. Armor with RGB lore shifts colors based on a predefined coloring scheme called a [Mode](https://github.com/nsporillo/RBGArmor/blob/master/src/net/porillo/util/Mode.java). 
* RGBArmor is the only plugin that features an always smooth rainbow loop!
* RGBArmor lets you config the update speed and # of colors that can be displayed
* RGBArmor has language support! 

### Notice
- RBGArmor is a premium plugin on the [SpigotMC](http://www.spigotmc.org/resources/) , if you use this plugin and your server makes money, please support development and purchase the resource. If for some reason you cannot pay and would like a copy, I can make exceptions - just contact me.  

## Example
![alt text](http://nick.porillo.net/images/rg.gif "Example")
- Note: Gif is sped up, actual speed based on config

## Commands
- /rgb : Base command for RBGArmor, displays help
- /rgb off - Turns off armor coloring
- /rgb set mode - Sets your armor coloring mode 
- /rgb list - Lists the available coloring modes
- /rgb debug - Displays dynamic debug scoreboard for armor color

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
 


