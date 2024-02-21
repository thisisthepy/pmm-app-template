package io.github.thisisthepy.pycomposeui.test.common


class JsPlatform: Platform {
    override val name: String = "Web/JS"
}

actual fun getPlatform(): Platform = JsPlatform()