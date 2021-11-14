package net.monkeyfunky.devteam.core.tablist

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import net.monkeyfunky.devteam.core.tablist.data.TabListEntry
import net.monkeyfunky.devteam.economy.Economy
import net.monkeyfunky.devteam.zomzonestatus.Status
import net.monkeyfunky.devteam.zomzonestatus.StatusTypes
import net.monkeyfunky.devteam.zomzonestatus.db.StatusAPI
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.util.*
import kotlin.collections.HashMap

class TabListBuilder {
    var header = "WELCOME TO ZOMZONE!!!!!!!!!"
    var footer = ""
    private val hash: HashMap<UUID, Status> = hashMapOf()

    fun getProfiles(player: Player): Array<TabListEntry> {
        val profiles: MutableList<TabListEntry> = mutableListOf()

        val status: Status
        if (!hash.containsKey(player.uniqueId)) {
            status = StatusAPI().getStatus(player)
            hash[player.uniqueId] = status
        } else {
            status = hash[player.uniqueId]!!
        }

        val iterator = Bukkit.getOnlinePlayers().iterator()
        while (profiles.size < 80) {
            if (profiles.size == 0 || profiles.size == 20 || profiles.size == 40) {
                profiles.add(TabListEntry("${ ChatColor.YELLOW }    参加者 (${ Bukkit.getOnlinePlayers().size })    "))
                continue
            }
            if (iterator.hasNext() && profiles.size < 60) {
                profiles.add(getPlayerProperties(iterator.next()))
                continue
            }

            if(profiles.size == 60) {
                profiles.add(TabListEntry("${ ChatColor.AQUA }    ステータス    "))
                profiles.add(TabListEntry())
                profiles.add(TabListEntry("Status Point: ${ status.getStatus(StatusTypes.POINT) }"))
                profiles.add(TabListEntry())
                profiles.add(TabListEntry("弓術: ${ status.getStatus(StatusTypes.BOW) }"))
                profiles.add(TabListEntry("剣術: ${ status.getStatus(StatusTypes.SWORD) }"))
                profiles.add(TabListEntry("防御: ${ status.getStatus(StatusTypes.SHIELD) }"))
                profiles.add(TabListEntry("体力: ${ status.getStatus(StatusTypes.HEALTH) }"))
                profiles.add(TabListEntry("銃葬: ${ status.getStatus(StatusTypes.GUN) }"))
                profiles.add(TabListEntry())
                profiles.add(TabListEntry("所持金: ${ Economy.getInstance().api.get(player.uniqueId) }"))
            }
            profiles.add(TabListEntry())
        }

        return profiles.toTypedArray()
    }

    private fun getPlayerProperties(player: Player): TabListEntry {
        val profile = TabListEntry(player.displayName)
        return profile.setHead(getTextures(player))
    }

    private fun getTextures(player: Player): Property {
        val entityPlayer: Any? = player.javaClass.getDeclaredMethod("getHandle").invoke(player)
        val gameProfile: GameProfile = entityPlayer?.javaClass!!.superclass.getDeclaredMethod("getProfile").invoke(entityPlayer) as GameProfile

        return gameProfile.properties.get("textures").iterator().next()
    }
}