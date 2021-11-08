package net.monkeyfunky.devteam.core.events

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.ServerListPingEvent

class MotdChangeListener : Listener {
    companion object {
        lateinit var MOTD_LIST : List<String>
    }

    @EventHandler
    fun onServerPing(e: ServerListPingEvent) {
        val motd = MOTD_LIST.shuffled()[0]

        e.motd = motd.replace("\$n", "\n").replace("&", "§")
    }
}