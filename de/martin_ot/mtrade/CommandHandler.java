package de.martin_ot.mtrade;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Martin on 29.03.2016.
 */
public class CommandHandler implements CommandExecutor {

    private Main m;

    public CommandHandler(Main m) {
        this.m = m;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {

        Player s;
        if (sender instanceof Player) {
            s = (Player)sender;
        } else {
            sender.sendMessage("The TradeChat can't be used from console!");
            return true;
        }

            if(cmd.getName().equals("handel") || cmd.getName().equals("$")) {
               if(args.length <= 0) {
                   m.proceedHelp(s);
                   return true;
               } else if (args.length == 1) {
                   if (args[0].equals("abo")) {
                       m.proceedAboToggle(s);
                   } else if (args[0].equals("list")) {
                       m.proceedList(s);
                   } else if (args[0].equals("about")) {
                       m.proceedAbout(s);
                   } else {
                       m.proceedChat(s, args);
                   }
                   return true;
               } else {
                   m.proceedChat(s, args);
                   return true;
               }
            } else {
                return false;
            }
    }
}
