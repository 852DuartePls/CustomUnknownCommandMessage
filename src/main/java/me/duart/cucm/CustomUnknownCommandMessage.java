package me.duart.cucm;

import me.duart.cucm.commands.ReloadCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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
    private final CopyOnWriteArrayList<String> rawCache = new CopyOnWriteArrayList<>();
    private boolean useList;

    private boolean papiPresent = false;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        rebuildCache();
        detectSoftDependencies();

        getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getCommandMap().register("cucmreload", new ReloadCommand(this));
    }

    @EventHandler
    public void onUnknownCommand(@NotNull UnknownCommandEvent event) {
        if (cache.isEmpty()) return;

        int index = useList ? ThreadLocalRandom.current().nextInt(cache.size()) : 0;
        Component chosen;
        String raw = rawCache.get(index);

        if (event.getSender() instanceof Player player) {
            raw = applyInternalPlaceholders(player, raw);

            if (papiPresent && raw.contains("%")) {
                raw = me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, raw);
            }

        } else {
            raw = raw
                    .replace("{player}", "Console")
                    .replace("<player>", "Console")
                    .replace("{displayname}", "Console")
                    .replace("<displayname>", "Console");
        }

        chosen = mini.deserialize(raw);

        event.message(chosen);
    }

    private @NotNull String applyInternalPlaceholders(@NotNull Player player, @NotNull String text) {
        String displayName = mini.serialize(player.displayName());
        return text
                .replace("{player}", player.getName())
                .replace("<player>", player.getName())
                .replace("{displayname}", displayName)
                .replace("<displayname>", displayName);
    }

    public void rebuildCache() {
        cache.clear();
        rawCache.clear();

        useList = getConfig().getBoolean("use-multiple-messages", false);
        if (useList) {
            for (String raw : getConfig().getStringList("message-list")) {
                rawCache.add(raw);
                cache.add(mini.deserialize(raw));
            }
        } else {
            String raw = getConfig().getString("single-message",
                    "<red>Unknown Command, please use <gold>/help</gold> for more information.");
            rawCache.add(raw);
            cache.add(mini.deserialize(raw));
        }
    }

    public void detectSoftDependencies() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            papiPresent = true;
            getLogger().info("PlaceholderAPI detected, hooking into it...");
        }
    }
}
