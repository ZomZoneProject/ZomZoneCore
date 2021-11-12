package net.monkeyfunky.devteam.core.utils

import org.bukkit.Bukkit

object NMSUtils {
    val serverVersion: String
        get() {
            val version: String = try {
                Bukkit.getServer().javaClass.getPackage().name.replace(".", ",").split(",".toRegex()).toTypedArray()[3]
            } catch (e: ArrayIndexOutOfBoundsException) {
                e.printStackTrace()
                return "v1.16_R3"
            }
            return version
        }
}