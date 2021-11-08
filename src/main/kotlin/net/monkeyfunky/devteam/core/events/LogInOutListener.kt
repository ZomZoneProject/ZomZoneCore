package net.monkeyfunky.devteam.core.events

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class LogInOutListener : Listener {
    companion object {
        lateinit var FIRST_JOIN_MESSAGE : String
        lateinit var NORMAL_JOIN_MESSAGE : String
        lateinit var QUIT_MESSAGE : String
    }

    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        if (e.player.hasPlayedBefore()) {
            e.joinMessage = NORMAL_JOIN_MESSAGE.replace("%Player%", e.player.name)
        } else {
            e.joinMessage = FIRST_JOIN_MESSAGE.replace("%Player%", e.player.name)
        }
    }

    @EventHandler
    fun onPlayerQuit(e: PlayerQuitEvent) {
        e.quitMessage = QUIT_MESSAGE.replace("%Player%", e.player.name)
    }
}
