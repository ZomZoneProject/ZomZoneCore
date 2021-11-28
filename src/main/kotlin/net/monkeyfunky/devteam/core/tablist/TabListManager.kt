package net.monkeyfunky.devteam.core.tablist

import net.monkeyfunky.devteam.core.tablist.packets.PacketFactory
import org.bukkit.entity.Player
import java.util.*


class TabListManager {
    val profiles = arrayOfNulls<UUID>(80)
    var factory: PacketFactory private set
    var builder: ITabListBuilder private set
    private val tabLists: HashSet<PlayerTabList> = hashSetOf()

    init {
        factory = PacketFactory()
        builder = TabListBuilder()

        for (i in 0..79) {
            profiles[i] = UUID.fromString(
                String.format(
                    "00000000-0000-00%s-0000-000000000000",
                    if (i < 10) "0$i" else i.toString()
                )
            )
        }
    }

    /**
     * Create TabList
     */
    fun createTabList(player: Player?) {
        val tabList = PlayerTabList(player)
        tabLists.add(tabList)
        player?.let { builder.getProfiles(it) }?.let { tabList.sendTabList(builder.getHeader(player), builder.getFooter(player), it) }
    }

    /**
     * Remove TabList
     */
    fun removeTabList(player: Player?) {
        getTabList(player)?.removeTabList()
    }

    /**
     * Get TabList
     */
    fun getTabList(player: Player?): PlayerTabList? {
        val var2: Iterator<*> = tabLists.iterator()
        var tabList: PlayerTabList
        do {
            if (!var2.hasNext()) {
                return null
            }
            val o = var2.next()!!
            tabList = o as PlayerTabList
        } while (!tabList.getPlayer()?.equals(player)!!)
        return tabList
    }

    fun setBuilder(builder: AbstractTabListBuilder) {
        this.builder = builder
    }
}