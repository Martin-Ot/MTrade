package de.martin_ot.mtrade;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by Martin on 29.03.2016.
 */
public class Main {

    Initial i;
    Logger log;
    ArrayList<Player> online;
    ArrayList<String> keep;
    String prefix;

    public Main(Initial i) {
        this.i = i;
        log = i.getLogger();
        online = new ArrayList<Player>();
        keep = new ArrayList<String>();
        prefix = ChatColor.translateAlternateColorCodes('&', i.getConfig().getString("Global.prefix"));
        log.info("Started up sucessfully.");
    }

    public void proceedChat(Player p, String[] msg) {
        String tmp = "";
        boolean first = true;
        for (String s : msg) {
            if (first) {
                tmp += s;
                first = false;
            } else {
                tmp += " " + s;
            }
        }
        this.proceedChat(p, tmp);
    }

    public void proceedChat(Player p, String msg) {
        if (online.size() <= 1 && online.contains(p)) {
            p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', i.getConfig().getString("Message.onlyYou")));
        } else if (!online.contains(p)) {
            p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', i.getConfig().getString("Message.noAbo")));
        } else {
            log.info("Player Message: " + msg);
            String tmp = ChatColor.translateAlternateColorCodes('&', i.getConfig().getString("Global.format"));
            tmp = tmp.replace("%player", p.getName());
            tmp = tmp.replace("%message", msg);
            this.sendMessage(tmp);
        }
    }

    public void proceedAboToggle(Player p) {
        if(online.contains(p)) {
            online.remove(p);
            keep.remove(p.getUniqueId().toString());
            this.sendMessage(ChatColor.translateAlternateColorCodes('&', i.getConfig().getString("Message.leave").replace("%player", p.getName())));
            p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', i.getConfig().getString("Message.deabo")));
        } else {
            this.sendMessage(ChatColor.translateAlternateColorCodes('&', i.getConfig().getString("Message.join").replace("%player", p.getName())));
            online.add(p);
            p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', i.getConfig().getString("Message.abo")));
        }
    }

    public void proceedJoin(final Player p) {
        if(keep.contains(p.getUniqueId().toString())) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(i, new Runnable() {
                public void run() {
                    p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', i.getConfig().getString("Message.relog")));
                }
            }, 100L);
            this.sendMessage(ChatColor.translateAlternateColorCodes('&', i.getConfig().getString("Message.join").replace("%player", p.getName())));
            online.add(p);
        }
    }

    public void proceedLogout(Player p) {
        if(online.contains(p)) {
            if(!keep.contains(p.getUniqueId().toString())) {
                keep.add(p.getUniqueId().toString());
            }
            online.remove(p);
            this.sendMessage(ChatColor.translateAlternateColorCodes('&', i.getConfig().getString("Message.leave").replace("%player", p.getName())));
        }
    }

    public void proceedList(Player p) {
        if (online.size() == 0) {
            p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', i.getConfig().getString("Message.noPlayer")));
        } else if (online.size() == 1 && online.contains(p)) {
            p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', i.getConfig().getString("Message.onlyYou")));
        } else {
            String ret = "";
            boolean first = true;
            for (Player k : online) {
                if (first) {
                    ret += k.getName();
                    first = false;
                } else {
                    ret += ", ";
                    ret += k.getName();
                }
            }
            String tmp = ChatColor.translateAlternateColorCodes('&', i.getConfig().getString("Global.list"));
            tmp = tmp.replace("%count", "" + online.size());
            tmp = tmp.replace("%names", ret);
            p.sendMessage(prefix + tmp);
        }
    }

    public void proceedAbout(Player p) {
        p.sendMessage(prefix + "§6MTrade §eby Martin-Ot.de | 30.3.16 for weltensiedler.eu");
        p.sendMessage("§eCache-> Online: " + online.size() + " Remind: " + keep.size());
    }

    public void proceedHelp(Player p) {
        p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', i.getConfig().getString("Message.help")));
    }

    public void sendMessage(String msg) {
        for (Player p : online) {
            try {
                p.sendMessage(prefix + msg);
            } catch (Exception ex) {
                online.remove(p);
            }
        }
    }
}
