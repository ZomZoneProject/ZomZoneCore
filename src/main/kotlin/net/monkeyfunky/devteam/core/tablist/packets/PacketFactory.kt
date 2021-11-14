package net.monkeyfunky.devteam.core.tablist.packets

import net.monkeyfunky.devteam.core.tablist.data.TabProfile
import net.monkeyfunky.devteam.core.utils.NMSUtils
import java.lang.reflect.Array
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method

class PacketFactory {
    private var infoListField: Field? = null
    private var enumPlayerInfoActionClass: Class<*>? = null
    private var entityPlayerClass: Class<*>? = null
    private var packetPlayOutPlayerInfoConstructor: Constructor<*>? = null
    private var playerInfoDataConstructor: Constructor<*>? = null
    private var gameModeNotSet: Any? = null
    private var fromStringOrNull: Method? = null

    init {
        if (infoListField == null) {
            val clazz = Class.forName("net.minecraft.server." + NMSUtils.serverVersion + ".PacketPlayOutPlayerInfo")
            infoListField = clazz.getDeclaredField("b")
            entityPlayerClass = Class.forName("net.minecraft.server." + NMSUtils.serverVersion + ".EntityPlayer")
            val classes = clazz.classes
            var n = 0
            while (n < classes.size) {
                val packetClass = classes[n]
                val constructors = packetClass.constructors
                if (packetClass.simpleName == "PlayerInfoData") {
                    for (element in constructors) {
                        val constructor: Constructor<*> = element
                        if (constructor.parameterCount == 5) {
                            playerInfoDataConstructor = constructor
                            constructor.isAccessible = true
                            break
                        }
                    }
                } else if (packetClass.simpleName == "EnumPlayerInfoAction") {
                    enumPlayerInfoActionClass = packetClass
                }
                ++n
            }
            packetPlayOutPlayerInfoConstructor = clazz.getDeclaredConstructor(
                enumPlayerInfoActionClass,
                Array.newInstance(entityPlayerClass, 0).javaClass
            )
            (packetPlayOutPlayerInfoConstructor as Constructor<out Any>).isAccessible = true
            val enums = Class.forName("net.minecraft.server." + NMSUtils.serverVersion + ".EnumGamemode").enumConstants
            for (element in enums) {
                if (element.toString().equals("NOT_SET", ignoreCase = true)) {
                    gameModeNotSet = element
                }
            }
            if (gameModeNotSet == null) {
                throw Exception("Cannot find enum net.minecraft.server." + NMSUtils.serverVersion + ".EnumGamemode.NOT_SET")
            }
            fromStringOrNull =
                Class.forName("org.bukkit.craftbukkit." + NMSUtils.serverVersion + ".util.CraftChatMessage")
                    .getDeclaredMethod(
                        "fromStringOrNull",
                        String::class.java
                    )
        }
    }

    private fun getEnumPlayerInfoAction(action: String?): Any? {
        val enums: kotlin.Array<out Any>? = enumPlayerInfoActionClass!!.enumConstants
        if (enums?.size != null) {
            for (element in enums) {
                if (element.toString().equals(action, ignoreCase = true)) {
                    return element
                }
            }
        }
        return null
    }

    @Throws(Exception::class)
    fun getPacket(action: String?, vararg profiles: TabProfile): Any? {
        val actionEnum = getEnumPlayerInfoAction(action)
        val packet: Any? = packetPlayOutPlayerInfoConstructor?.newInstance(actionEnum, Array.newInstance(entityPlayerClass, 0))
        val size = profiles.size
        val infoList: MutableList<Any?> = (infoListField?.get(packet) as List<Any?>).toMutableList()
        for (n in 0 until size) {
            val profile: TabProfile = profiles[n]
            infoList.add(
                playerInfoDataConstructor?.newInstance(
                    packet,
                    profile,
                    profile.getPing(),
                    gameModeNotSet,
                    fromStringOrNull?.invoke(null as Any?, profile.getText())
                )
            )
        }
        return packet
    }
}