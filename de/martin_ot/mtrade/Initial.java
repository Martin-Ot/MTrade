package de.martin_ot.mtrade;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Martin on 29.03.2016.
 */
public class Initial extends JavaPlugin {

    private CommandHandler ch;
    private Main m;
    private PartHandler eh;

    public void onEnable() {
            readConfig();
            m = new Main(this);
            ch = new CommandHandler(m);
            eh = new PartHandler(m);
            getServer().getPluginManager().registerEvents(eh, this);
            this.getCommand("handel").setExecutor(ch);
            this.getCommand("$").setExecutor(ch);

    }

    public void readConfig() {
        reloadConfig();
        FileConfiguration c = getConfig();
        c.options().header("MTrade 1.9 by Martin-Ot.de | Use Back-/ n to start new line | Colorcodes are availabe | Use UTF-8");
        //Master Section
        c.addDefault("Global.prefix", "&6[Handel] &7>> ");
        c.addDefault("Global.format", "&6%player: &e%message");
        c.addDefault("Global.list", "&eOnline im Handelschat (%count): &r%names");
        //Messages Section
        c.addDefault("Message.join", "&e%player betritt den Handelschat.");
        c.addDefault("Message.leave", "&e%player verlässt den Handelschat.");
        c.addDefault("Message.abo", "&eDu hast den Handelschat abonniert.");
        c.addDefault("Message.deabo", "&eDu hast den Handelschat deabonniert.");
        c.addDefault("Message.noPlayer", "&cAktuell ist kein Spieler im Handelschat!");
        c.addDefault("Message.noAbo", "&cDu musst den Handelschat abonnieren um dort etwas schreiben zu können! &4/handel abo");
        c.addDefault("Message.relog", "&eWillkommen zurück! Der Handelschat wurde automatisch wieder abonniert.");
        c.addDefault("Message.onlyYou", "&cNur du bist im Handelschat online.");
        c.addDefault("Message.help", "&eNutze den Handelchat um mit handelwilligen Spielern in Kontakt zu treten.\n&6/handel abo &e- Abonniere den Handelschat\n&6/handel [Nachricht] &e- Sende eine Nachricht\n&6/handel list &e- Zeige alle Spieler im Handelschat");
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
