package net.worldmc.clivestutorial.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.joinMessage(null);
        Component message = Component.text("Click this message to continue")
                .color(NamedTextColor.GOLD)
                .clickEvent(ClickEvent.runCommand("/continue"))
                .hoverEvent(HoverEvent.showText(Component.text("Click to start the tutorial")));

        event.getPlayer().sendMessage(message);
    }
}
