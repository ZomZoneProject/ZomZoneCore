package net.monkeyfunky.devteam.core

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent

class EventListener : Listener {
    private val block = listOf(
        "/pl",
        "/plugins",
        "/help",
        "/?"
    )

    @EventHandler
    fun onCommand(e: PlayerCommandPreprocessEvent) {
        if (block.any { e.message.contains(it) }) {
            e.isCancelled = true
        }
    }
}