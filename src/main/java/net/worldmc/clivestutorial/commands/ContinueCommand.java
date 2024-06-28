package net.worldmc.clivestutorial.commands;

import net.worldmc.clivestutorial.util.DialogueManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.worldmc.clivestutorial.ClivesTutorial;
import org.jetbrains.annotations.NotNull;

public class ContinueCommand implements CommandExecutor {
    private final DialogueManager dialogueManager;

    public ContinueCommand(ClivesTutorial plugin) {
        this.dialogueManager = plugin.getDialogueManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;
        dialogueManager.startDialogue(player);
        return true;
    }
}