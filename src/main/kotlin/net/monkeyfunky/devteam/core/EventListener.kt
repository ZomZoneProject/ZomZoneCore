package net.monkeyfunky.devteam.core

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent

class EventListener : Listener {
    private val blocked = listOf(
        "/pl",
        "/plugins",
        "/help",
        "/?"
    )

    @EventHandler
    fun onCommand(e: PlayerCommandPreprocessEvent) {
        if (blocked.any { e.message.contains(it) } && !e.player.isOp) {
            e.isCancelled = true
        }
    }
}