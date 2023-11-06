package io.github.thisisthepy.pycomposeui.test.common

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform