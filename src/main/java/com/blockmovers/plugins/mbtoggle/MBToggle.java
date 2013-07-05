package com.blockmovers.plugins.mbtoggle;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MBToggle extends JavaPlugin implements Listener {
    static final Logger log = Logger.getLogger("Minecraft"); //set up our logger
    
    public List<String> mbToggle = new ArrayList();
    
    @Override
    public void onEnable() {
        PluginDescriptionFile pdffile = this.getDescription();
        PluginManager pm = this.getServer().getPluginManager(); //the plugin object which allows us to add listeners later on

        pm.registerEvents(this, this);

        log.info(pdffile.getName() + " version " + pdffile.getVersion() + " is enabled.");
    }

    @Override
    public void onDisable() {
        PluginDescriptionFile pdffile = this.getDescription();

        log.info(pdffile.getName() + " version " + pdffile.getVersion() + " is disabled.");
    }
    
    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String alias, String[] args) {
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("version")) {
                PluginDescriptionFile pdf = this.getDescription();
                cs.sendMessage(pdf.getName() + " " + pdf.getVersion() + " by MDCollins05");
            }
        } else {
            if (cs instanceof Player) {
                if (!cs.hasPermission("mbtoggle.use")) {
                    return true;
                }
                if (this.mbToggle.contains(cs.getName())) {
                    this.mbToggle.remove(cs.getName());
                    cs.sendMessage(ChatColor.RED + "MB Toggled off!");
                } else {
                    this.mbToggle.add(cs.getName());
                    cs.sendMessage(ChatColor.GREEN + "MB toggled on!");
                }
            }
        }
        return true;
    }

    @EventHandler
    public void onPlayerAsyncChat(AsyncPlayerChatEvent event) {
        Player p = event.getPlayer();
        if (this.mbToggle.contains(p.getName())) {
            event.setCancelled(true);
            p.chat("/mb " + event.getMessage());
        }
    }
    
    @EventHandler
    public void OnPlayerQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        if (this.mbToggle.contains(p.getName())) {
            this.mbToggle.remove(p.getName());
        }
    }
}

