package io.github.thisisthepy.pycomposeui.test.common

import device.UIKit.UIDevice
import device.resource.util.Platform

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()