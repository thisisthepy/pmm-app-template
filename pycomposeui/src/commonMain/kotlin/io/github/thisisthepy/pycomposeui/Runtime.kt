package io.github.thisisthepy.pycomposeui

import androidx.compose.runtime.Composable
import kotlin.experimental.ExperimentalObjCName
import kotlin.jvm.JvmName
import kotlin.native.ObjCName


/*
 * Description: Composable Wrapper contains Kotlin Composable can be invoked by Python functions
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName("composableWrapper")
@JvmName("composableWrapper")
@Composable
fun composableWrapper(content: @Composable (args: Array<Any>) -> Any, args: Array<Any>): Any {
    return content(args)
}
