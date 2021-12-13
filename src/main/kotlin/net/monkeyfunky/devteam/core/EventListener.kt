package net.monkeyfunky.devteam.core

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import java.util.regex.Pattern

class EventListener : Listener {
    private val pattern: Pattern = Pattern.compile("\\$\\{jdni:([^}]*)}")

    private val blocked = listOf(
        "/pl",
        "/plugins",
        "/help",
        "/?"
    )

    @EventHandler
    fun onCommand(e: PlayerCommandPreprocessEvent) {
        if (blocked.any { e.message.startsWith(it) } && !e.player.isOp) {
            e.isCancelled = true
        }
    }

    @EventHandler
    fun onPlayerChat(e: AsyncPlayerChatEvent) {
        if (pattern.matcher(e.message).find()) {
            e.message = "<" + e.player.name + "> ***********************"
        }
    }
}