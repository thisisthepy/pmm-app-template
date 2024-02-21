package io.github.thisisthepy.pycomposeui.test.common

import device.resource.util.Platform


class JsPlatform: Platform {
    override val name: String = "Web/JS"
}

actual fun getPlatform(): Platform = JsPlatform()