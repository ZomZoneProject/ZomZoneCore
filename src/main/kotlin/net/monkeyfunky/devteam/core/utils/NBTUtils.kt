package net.monkeyfunky.devteam.core.utils

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object NBTUtils {
    fun toString(stack: ItemStack?): String? {
        try {
            val craftItemStackClass = Class.forName("org.bukkit.craftbukkit.$serverVersion.inventory.CraftItemStack")
            val asNMSCopy = craftItemStackClass.getDeclaredMethod("asNMSCopy", ItemStack::class.java)
            val itemStack = asNMSCopy.invoke(null, stack)
            val itemStackClass = Class.forName("net.minecraft.server.$serverVersion.ItemStack")
            val getTagMethod = itemStackClass.getDeclaredMethod("getTag")
            val nbtTagCompoundClass = Class.forName("net.minecraft.server.$serverVersion.NBTTagCompound")
            val toStringMethod = nbtTagCompoundClass.getDeclaredMethod("toString")
            val nbtTagCompound = getTagMethod.invoke(itemStack) ?: return "${stack!!.type.name}\${}" // !!演算子を使用(TypeはNullにはならない)
            return toStringMethod.invoke(nbtTagCompound) as String
        } catch (e: ReflectiveOperationException) {
            e.printStackTrace()
        }
        return null
    }

    @Suppress("DEPRECATION")
    fun fromString(string: String?): ItemStack? {
        try {
            val splitString = string?.split("\$")
            val material = Material.getMaterial(splitString?.get(0).toString())
            val mojangsonParserClass = Class.forName("net.minecraft.server.$serverVersion.MojangsonParser")
            val parseMethod = mojangsonParserClass.getMethod("parse", String::class.java)
            val nbtBase = parseMethod.invoke(null, splitString?.get(1).toString())
            val itemClass = Class.forName("net.minecraft.server.$serverVersion.Item")
            val getByIdMethod = itemClass.getDeclaredMethod("getById", Int::class.javaPrimitiveType)
            val itemStackClass = Class.forName("net.minecraft.server.$serverVersion.ItemStack")
            var itemStack = itemStackClass.getConstructor(Any::class.java).newInstance(getByIdMethod.invoke(null, material?.id))
            val setTagMethod = itemStackClass.getDeclaredMethod("setTag", Any::class.java)
            itemStack = setTagMethod.invoke(itemStack, nbtBase)
            val craftItemStackClass = Class.forName("org.bukkit.craftbukkit.$serverVersion.ItemStack")
            val asBukkitCopyMethod = craftItemStackClass.getDeclaredMethod("asBukkitCopy", Any::class.java)
            return asBukkitCopyMethod.invoke(null, itemStack) as ItemStack
        } catch (e: ReflectiveOperationException) {
            e.printStackTrace()
        }
        return null
    }

    private val serverVersion: String
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
