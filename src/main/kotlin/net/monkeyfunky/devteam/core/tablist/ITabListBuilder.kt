package net.monkeyfunky.devteam.core.tablist

import net.monkeyfunky.devteam.core.tablist.data.TabListEntry
import org.bukkit.entity.Player

interface ITabListBuilder {
    /**
     * Get Header
     */
    fun getHeader(player: Player): String

    /**
     * Get Footer
     */
    fun getFooter(player: Player): String

    /**
     * Get Profiles
     */
    fun getProfiles(player: Player): Array<TabListEntry>
}