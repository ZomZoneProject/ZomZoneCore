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
                profiles.add(TabListEntry("${ ChatColor.LIGHT_PURPLE }    参加者 (${ Bukkit.getOnlinePlayers().size })    ").setHead(HeadColor.LIGHT_PURPLE))
                continue
            }
            if (iterator.hasNext() && profiles.size < 60) {
                profiles.add(getPlayerProperties(iterator.next()))
                continue
            }

            if(profiles.size == 60) {
                profiles.add(TabListEntry("${ ChatColor.AQUA }    ステータス    ").setHead(HeadColor.AQUA))
                profiles.add(TabListEntry())
                profiles.add(TabListEntry("${ ChatColor.GREEN }Status Point: ${ status.getStatus(StatusTypes.POINT) }pt").setHead(HeadColor.GREEN))
                profiles.add(TabListEntry())
                profiles.add(TabListEntry("${ ChatColor.DARK_PURPLE }弓術: ${ status.getStatus(StatusTypes.BOW) }pt (+${ status.getStatus(StatusTypes.BOW).toDouble() / 10 })").setHead(HeadColor.DARK_PURPLE))
                profiles.add(TabListEntry("${ ChatColor.RED }剣術: ${ status.getStatus(StatusTypes.SWORD) }pt (+${ status.getStatus(StatusTypes.SWORD).toDouble() / 10 })").setHead(HeadColor.RED))
                profiles.add(TabListEntry("${ ChatColor.BLUE }防御: ${ status.getStatus(StatusTypes.SHIELD) }pt (+${ status.getStatus(StatusTypes.SHIELD).toDouble() / 5 }%)").setHead(HeadColor.BLUE))
                profiles.add(TabListEntry("${ ChatColor.DARK_AQUA }体力: ${ status.getStatus(StatusTypes.HEALTH) }pt (+${ status.getStatus(StatusTypes.HEALTH).toDouble() / 10 })").setHead(HeadColor.DARK_AQUA))
                profiles.add(TabListEntry("${ ChatColor.YELLOW }銃葬: ${ status.getStatus(StatusTypes.GUN) }pt (+${ status.getStatus(StatusTypes.GUN).toDouble() / 5 })").setHead(HeadColor.YELLOW))
                profiles.add(TabListEntry())
                profiles.add(TabListEntry("${ ChatColor.GOLD }所持金: ${ Economy.getInstance().api.get(player.uniqueId) } Gully").setHead(HeadColor.GOLD))
            }
            profiles.add(TabListEntry())
        }

        return profiles.toTypedArray()
    }

    private fun getPlayerProperties(player: Player): TabListEntry {
        val profile = TabListEntry(player.playerListName)
        return profile.setHead(getTextures(player))
    }

    private fun getTextures(player: Player): Property {
        val entityPlayer: Any? = player.javaClass.getDeclaredMethod("getHandle").invoke(player)
        val gameProfile: GameProfile = entityPlayer?.javaClass!!.superclass.getDeclaredMethod("getProfile").invoke(entityPlayer) as GameProfile

        return gameProfile.properties.get("textures").iterator().next()
    }
}