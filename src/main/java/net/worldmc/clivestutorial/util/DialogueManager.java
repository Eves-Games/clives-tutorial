package net.worldmc.clivestutorial.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.worldmc.clivestutorial.ClivesTutorial;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.stream.Collectors;

public class DialogueManager {
    private final ClivesTutorial plugin;
    private final List<Component> dialogues;

    public DialogueManager(ClivesTutorial plugin) {
        this.plugin = plugin;
        FileConfiguration config = plugin.getConfig();

        MiniMessage mm = MiniMessage.miniMessage();

        this.dialogues = config.getStringList("dialogue").stream()
                .map(mm::deserialize)
                .collect(Collectors.toList());
    }

    public void startDialogue(Player player) {
        scheduleNextMessage(player, 0);
    }

    private void scheduleNextMessage(Player player, int index) {
        if (index >= dialogues.size()) {
            return;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                Component message = dialogues.get(index);
                player.sendMessage(Component.empty());
                player.sendMessage(Component.text("Clive: ", NamedTextColor.GRAY).append(message));
                player.playSound(player.getLocation(), Sound.ENTITY_DONKEY_AMBIENT, 1.0f, 1.0f);

                long delay = Math.min(Math.max(message.toString().length() / 5, 60), 200);

                scheduleNextMessage(player, index + 1, delay);
            }
        }.runTaskLater(plugin, index == 0 ? 0 : 40);
    }

    private void scheduleNextMessage(Player player, int index, long delay) {
        new BukkitRunnable() {
            @Override
            public void run() {
                scheduleNextMessage(player, index);
            }
        }.runTaskLater(plugin, delay);
    }
}