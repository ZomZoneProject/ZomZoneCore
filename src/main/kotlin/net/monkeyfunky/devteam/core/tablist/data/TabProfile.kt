package net.monkeyfunky.devteam.core.tablist.data

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import java.util.*


class TabProfile(id: UUID?, number: Int, text: String?, ping: Int, head: Property) : GameProfile(id, text) {
    private var text: String? = null
    private var ping = 0

    init {
        this.text = text
        this.ping = ping
        setHead(head)
    }

    override fun getName(): String {
        return ""
    }

    fun getText(): String? {
        return text
    }

    fun setText(text: String): Boolean {
        return if (this.text == text) {
            false
        } else {
            this.text = text
            true
        }
    }

    fun getPing(): Int {
        return ping
    }

    fun setPing(ping: Int): Boolean {
        return if (this.ping == ping) {
            false
        } else {
            this.ping = ping
            true
        }
    }

    fun setHead(property: Property): Boolean {
        val p = properties["textures"].stream().findFirst().orElse(null)
        return if (p != null) {
            false
        } else {
            if (p === property) return false

            properties.removeAll("textures")
            properties.put("textures", property)
            true
        }
    }
}