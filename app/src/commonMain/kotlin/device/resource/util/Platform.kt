package device.resource.util

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform