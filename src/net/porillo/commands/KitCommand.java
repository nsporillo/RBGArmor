package net.porillo.commands;

import java.util.List;

import net.porillo.RBGArmor;
import net.porillo.util.Lang;
import net.porillo.util.Utility;

import static org.bukkit.Material.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KitCommand extends BaseCommand {

    private ItemStack[] armor = new ItemStack[] { new ItemStack(LEATHER_BOOTS),
            new ItemStack(LEATHER_LEGGINGS), new ItemStack(LEATHER_CHESTPLATE),
            new ItemStack(LEATHER_HELMET) };
    
    public KitCommand(RBGArmor plugin) {
        super(plugin);
        super.setName("kit");
        super.addUsage(Lang.KIT_USAGE.toString());
        super.setPermission("rgbarmor.kit");
    }

    @Override
    public void runCommand(CommandSender s, List<String> args) {
        if (!this.checkPermission(s)) {
            this.noPermission(s);
            return;
        }

        if (s instanceof Player) {
            Player p = (Player) s;           
            for(ItemStack is : armor) {
                if (p.getInventory().firstEmpty() == -1) {
                    Utility.send(p, Lang.KIT_INVFULL.toString());
                    return;
                }
                p.getInventory().addItem(is); 
                
            }
            Utility.send(p, Lang.KIT_SUCCESS.toString());
        }
    }
}
