package net.worldmc.clivestutorial.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.worldmc.clivestutorial.ClivesTutorial;
import org.jetbrains.annotations.NotNull;

public class ContinueCommand implements CommandExecutor {
    private final ClivesTutorial plugin;

    public ContinueCommand(ClivesTutorial plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;
        plugin.getDialogueManager().startDialogue(player);
        return true;
    }
}