package net.worldmc.clivestutorial;

import net.worldmc.clivestutorial.commands.ContinueCommand;
import net.worldmc.clivestutorial.listeners.PlayerChatListener;
import net.worldmc.clivestutorial.listeners.PlayerJoinListener;
import net.worldmc.clivestutorial.util.DialogueManager;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class ClivesTutorial extends JavaPlugin {
    private DialogueManager dialogueManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        dialogueManager = new DialogueManager(this);

        // Register events
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerChatListener(), this);

        // Register messages
        getServer().getMessenger().registerOutgoingPluginChannel(this, "clives:tutorial");

        // Register commands
        registerContinueCommand();
    }

    private void registerContinueCommand() {
        PluginCommand continueCommand = getCommand("continue");
        if (continueCommand != null) {
            continueCommand.setExecutor(new ContinueCommand(this));
        } else {
            getLogger().warning("Failed to register 'continue' command. Is it missing from plugin.yml?");
        }
    }

    public DialogueManager getDialogueManager() {
        return dialogueManager;
    }
}
