package net.monkeyfunky.devteam.core.tablist

import net.monkeyfunky.devteam.core.packets.PacketAPI
import net.monkeyfunky.devteam.core.tablist.data.TabListEntry
import net.monkeyfunky.devteam.core.tablist.data.TabProfile
import org.apache.commons.lang.Validate
import org.bukkit.entity.Player
import javax.annotation.Nonnull
import javax.annotation.Nullable

class PlayerTabList(player: Player?) {
    private val profiles: Array<TabProfile?> = arrayOfNulls(80)
    private var player: Player? = null

    init {
        this.player = player
    }

    private fun updateProfile(index: Int, @Nonnull entry: TabListEntry) {
        Validate.isTrue(index <= 79, "Bar index cannot be higher than 79!")
        Validate.isTrue(index >= 0, "Bar index cannot be smaller than 0!")
        TabList.getExecutor().submit {
            try {
                val profile: TabProfile? = profiles[index]
                var packet: Any?
                if (profile != null) {
                    if (entry.head?.let { profile.setHead(it) } == true) {
                        packet = TabList.getManager().factory.getPacket("ADD_PLAYER", profile)
                        PacketAPI.send(player, packet)
                    } else {
                        if (profile.setText(entry.text)) {
                            packet = TabList.getManager().factory.getPacket("UPDATE_DISPLAY_NAME", profile)
                            PacketAPI.send(player, packet)
                        }
                        if (profile.setPing(entry.ping)) {
                            packet = TabList.getManager().factory.getPacket("UPDATE_LATENCY", profile)
                            PacketAPI.send(player, packet)
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Update TabList
     */
    fun updateTabList(@Nullable header: String?, @Nullable footer: String?, @Nonnull entries: Array<TabListEntry>) {
        TabList.getExecutor().submit {
            try {
                Validate.isTrue(entries.size == 80, "entries table must have 80 objects!")
                for (i in 0..79) {
                    val entry: TabListEntry = entries[i]
                    updateProfile(i, entry)
                }
                sendHeaderFooter(header, footer)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Send TabList to Player
     */
    fun sendTabList(@Nullable header: String?, @Nullable footer: String?, @Nonnull entries: Array<TabListEntry>) {
        TabList.getExecutor().submit {
            try {
                Validate.isTrue(entries.size == 80, "entries table must have 80 objects!")
                for (i in 0..79) {
                    val entry: TabListEntry = entries[i]
                    val profile =
                        entry.head?.let {
                            TabProfile(TabList.getManager().profiles[i], entry.text, entry.ping, it)
                        }
                    profiles[i] = profile
                }
                sendHeaderFooter(header, footer)
                val packet: Any? = TabList.getManager().factory.getPacket("ADD_PLAYER", *profiles)
                PacketAPI.send(player, packet)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun sendHeaderFooter(@Nullable header: String?, @Nullable footer: String?) {
        TabList.getExecutor().submit { player!!.setPlayerListHeaderFooter(header, footer) }
    }

    @Nonnull
    fun getPlayer(): Player? {
        return player
    }

    @Nonnull
    fun getProfiles(): Array<TabProfile?> {
        return profiles
    }

    fun removeTabList() {
        TabList.getExecutor().submit {
            try {
                val packet: Any? = TabList.getManager().factory.getPacket("REMOVE_PLAYER", *profiles)
                PacketAPI.send(player, packet)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}