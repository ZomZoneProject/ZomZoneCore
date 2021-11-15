package net.monkeyfunky.devteam.core.events

import net.monkeyfunky.devteam.core.tablist.TabList
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent


class TabListListener : Listener {
    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        if (TabList.getManager().getTabList(e.player) == null) {
            TabList.getManager().createTabList(e.player)
        }
    }
}