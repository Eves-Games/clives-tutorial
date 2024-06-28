package net.worldmc.clivestutorial.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.worldmc.clivestutorial.ClivesTutorial;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;
import java.util.stream.Collectors;

public class DialogueManager {
    private final ClivesTutorial plugin;
    private final List<Component> dialogues;
    private final long messageDelay;

    public DialogueManager(ClivesTutorial plugin) {
        this.plugin = plugin;
        FileConfiguration config = plugin.getConfig();

        MiniMessage mm = MiniMessage.miniMessage();

        this.dialogues = config.getStringList("dialogue").stream()
                .map(mm::deserialize)
                .collect(Collectors.toList());

        this.messageDelay = config.getLong("message-delay", 3) * 20;
    }

    public void startDialogue(Player player) {
        new DialogueTask(player).runTaskTimer(plugin, 0L, messageDelay);
    }

    private class DialogueTask implements Runnable {
        private final Player player;
        private int index = 0;
        private BukkitTask task;

        public DialogueTask(Player player) {
            this.player = player;
        }

        @Override
        public void run() {
            if (index >= dialogues.size()) {
                sendFinalMessage(player);
                task.cancel();
                return;
            }

            Component message = dialogues.get(index);
            player.sendMessage(Component.empty());
            player.sendMessage(Component.text("Clive: ", NamedTextColor.GRAY).append(message));
            player.playSound(player.getLocation(), Sound.ENTITY_DONKEY_AMBIENT, 1.0f, 1.0f);

            index++;
        }

        public void runTaskTimer(ClivesTutorial plugin, long delay, long period) {
            this.task = plugin.getServer().getScheduler().runTaskTimer(plugin, this, delay, period);
        }
    }

    private void sendFinalMessage(Player player) {
        Component message = Component.text("Click here to join the Earth server!")
                .color(NamedTextColor.GOLD)
                .clickEvent(ClickEvent.runCommand("/server earth"))
                .hoverEvent(HoverEvent.showText(Component.text("Click to join the Earth server")));

        player.sendMessage(Component.empty());
        player.sendMessage(message);
    }
}