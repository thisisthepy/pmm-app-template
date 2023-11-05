package io.github.thisisthepy.pycompose.test.common

import androidx.compose.runtime.Composable

public actual fun getPlatformName(): String {
    return "PyComposeTest"
}

@Composable
public fun UIShow() {
    App()
}