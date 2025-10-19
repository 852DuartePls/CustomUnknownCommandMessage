package me.duart.cucm;

import me.duart.cucm.commands.ReloadCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.command.UnknownCommandEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

public final class CustomUnknownCommandMessage extends JavaPlugin implements Listener {

    private final MiniMessage mini = MiniMessage.miniMessage();
    private final CopyOnWriteArrayList<Component> cache = new CopyOnWriteArrayList<>();
    private boolean useList;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        rebuildCache();

        getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getCommandMap().register("cucmreload", new ReloadCommand(this));
    }

    @EventHandler
    public void onUnknownCommand(@NotNull UnknownCommandEvent event) {
        if (useList && !cache.isEmpty()) {
            event.message(cache.get(ThreadLocalRandom.current().nextInt(cache.size())));
        } else if (!cache.isEmpty()) {
            event.message(cache.get(0));
        }
    }

    public void rebuildCache() {
        cache.clear();
        ConfigurationSection config = getConfig();
        useList = config.getBoolean("use-multiple-messages", false);

        if (useList) {
            for (String raw : config.getStringList("message-list")) {
                cache.add(mini.deserialize(raw));
            }
        } else {
            cache.add(mini.deserialize(config.getString("single-message", "<red>Unknown Command, please use <gold>/help</gold> for more information.")));
        }
    }
}
