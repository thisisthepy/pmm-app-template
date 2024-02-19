package io.github.thisisthepy.python

import kotlin.experimental.ExperimentalNativeApi


@OptIn(ExperimentalNativeApi::class)
actual typealias WeakReference<T> = kotlin.native.ref.WeakReference<T>
