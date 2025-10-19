package me.duart.cucm.commands;

import me.duart.cucm.CustomUnknownCommandMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand extends Command {

    private final CustomUnknownCommandMessage plugin;

    public ReloadCommand(CustomUnknownCommandMessage plugin) {
        super("cucmreload");
        setDescription("Reload CustomUnknownCommandMessage config");
        setPermission("cucm.admin");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String label, String[] args) {
        if (!sender.hasPermission("cucm.reload")) {
            sender.sendRichMessage("<red>You don’t have permission to do that.");
            return true;
        }

        plugin.reloadConfig();
        plugin.rebuildCache();
        sender.sendRichMessage("<green>[CUCM] Config reloaded.");
        return true;
    }
}
