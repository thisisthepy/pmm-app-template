package io.github.thisisthepy.pycomposeui

import android.annotation.SuppressLint
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import com.chaquo.python.PyObject

//
///*
// * Description: Composable Template contains Python codes can be invoked by Kotlin functions
// */
//@JvmName("ComposableTemplate")
//@Composable
//fun ComposableTemplate(content: PyObject, vararg args: Any) {
//    content.call(*args)
//}


@SuppressLint("MutableCollectionMutableState")
@JvmName("rememberSaveableWrapper")
@Composable
fun rememberSaveableWrapper(init: PyObject, type: PyObject): MutableState<out Any> = when(type.toString()) {
    "int" -> {
        rememberSaveable { mutableIntStateOf(init.toInt()) }
    }
    "long" -> {
        rememberSaveable { mutableLongStateOf(init.toLong()) }
    }
    "boolean" -> {
        rememberSaveable { mutableStateOf(init.toBoolean()) }
    }
    "float" -> {
        rememberSaveable { mutableDoubleStateOf(init.toDouble()) }
    }
    "str" -> {
        rememberSaveable { mutableStateOf(init.toString()) }
    }
    else -> {
        println("Warning: A value that cannot be converted to a primitive type is passed as a argument to the rememberSaveable function." +
                "An exception may occur if CustomSaver is not specified... : Any - $init")
        rememberSaveable { mutableStateOf(init) }
    }
}
