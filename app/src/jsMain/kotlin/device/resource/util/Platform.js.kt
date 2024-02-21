package device.resource.util


class JsPlatform: Platform {
    override val name: String = "Web/JS"
}

actual fun getPlatform(): Platform = JsPlatform()