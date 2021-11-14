package net.monkeyfunky.devteam.core.tablist.data

import com.mojang.authlib.properties.Property

class TabListEntry(display: String = "") {
    companion object {
        val DEFAULT: Property = Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTYzNjg1MjU4OTMyNSwKICAicHJvZmlsZUlkIiA6ICI4NjMzYjgzN2Q1MWM0MjMyODM4MjEzMGEzODMzMjUyYyIsCiAgInByb2ZpbGVOYW1lIiA6ICJOdXZvbGUiLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzE3MjFkZWUwZjdlZjNiMmJhNmYxZmNiZGVkMDcwNzFmZjFmZmNhZDAyNDY1MDQxNjRhODBmOGQ0YTNkMTY1NSIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9LAogICAgIkNBUEUiIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzIzNDBjMGUwM2RkMjRhMTFiMTVhOGIzM2MyYTdlOWUzMmFiYjIwNTFiMjQ4MWQwYmE3ZGVmZDYzNWNhN2E5MzMiCiAgICB9CiAgfQp9", "x0Pqb1zvgDkAKM2Ey5i9udPYRv8P9xbTTBoqDqTXm5GcyDh5gji5u/MhgPmxqByS787MKlWmw7/Hsi9ncUeoemfHMQsx70zMCtrbUE/JoLhdPI+jEcUSBtnVFz2+EE4iQy+uXCBxb120bntAhq8eB/VKZWVahVtrRSXT5U+uAjbBndK98Mcw9y0t2b39mGj5VehBKn4LDkwLai7C2kJokOD/3L2bXt4YniQvyvJYiIHoE5XeQmSFe9YRWYZXtOq9/RxX8p+zyOW6RbHzWwmBmcBQdSAP2q5Hvc8VIrZYANsll+S5RbVYdRQv2+w2u+3VDycEBtRKQifPVm9xO0dQG4or1n291Q84EZAu/9pyQuZ+lhqvhyDGtRSLtSzansuKBTdUNr0mrsMKKFLuXbkusMemE2djwplrUE7YJY4d3D28H+vik1xQodzCGGzHvuS3PWiBH1SKangH301Ij79OxyvJCMPhaFkgLq66f9c6aayZV9edZCzTflJsXZrbNbCqo4yiTBh9mcfdI8ODcrzXzSnlwPODkGKrtIPjMAs3/0JLZxhZKxn1HjD0JjytRCgO2fQd/8U7AXHWqYrI5qBIfuA1sVjninPYAwX+dM83pKu8TRwZOUJKTE4oF5QlJashPgC+TQxc6DkPC/hlRMV7BdncYfyC6CpVlHsIF/cSEWE=")
    }

    var text: String = ""
    var ping = 1
    var head: Property? = null

    init {
        head = DEFAULT
        text = display
    }

    fun setPing(ping: Int): TabListEntry {
        this.ping = ping
        return this
    }

    fun setHead(head: Property?): TabListEntry {
        this.head = head
        return this
    }

    fun removeHead(): TabListEntry {
        return setHead(DEFAULT)
    }

    fun clone(): TabListEntry {
        return TabListEntry(text).setPing(ping).setHead(head)
    }
}