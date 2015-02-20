package net.porillo.commands;

import static net.porillo.Utility.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.porillo.RBGArmor;

import org.bukkit.command.CommandSender;

public class CommandHandler {

	private Map<String, Command> cmds = new HashMap<String, Command>();
	
	public CommandHandler(RBGArmor plugin) {
	    cmds.put("debug", new DebugCommand(plugin));
	    cmds.put("off", new OffCommand(plugin));
	    cmds.put("set", new SetCommand(plugin));
	    cmds.put("list", new ListCommand(plugin));
	}

    public void runCommand(CommandSender s, String l, String[] a) {
        if (a.length == 0 || this.cmds.get(a[0].toLowerCase()) == null) {
            this.showHelp(s, l);
            return;
        }
        List<String> args = new ArrayList<String>(Arrays.asList(a));
        Command cmd = this.cmds.get(args.remove(0).toLowerCase());
        if (args.size() < cmd.getRequiredArgs()) {
            cmd.showHelp(s, l);
            return;
        }
        cmd.runCommand(s, args);
    }

	public void showHelp(CommandSender s, String l) {
	    String dash = getSym("-", 8);
        send(s, "&2" + dash + "&9" + " RGBArmor " + "&2" + dash);		
		for (Command cmd : this.cmds.values()) {
			if (cmd.checkPermission(s)) {
				cmd.showHelp(s, l);
			}
		}
	}
}