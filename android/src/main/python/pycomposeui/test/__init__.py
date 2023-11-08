from java import jclass

appview = jclass("io.github.thisisthepy.pycomposeui.PythonAppView_androidKt")
# print("Python: appview -", appview)
# composable_wrapper = appview.ComposableTemplate
# print("Composable:", composable_wrapper)
#
#
# def check():
#     return "Hi there, this is python code"
#
#
# def composable(pycomposefunc):
#     return lambda composer, val: composable_wrapper(pycomposefunc, composer, val)


box = jclass("androidx.compose.foundation.layout.BoxKt").Box
UI = jclass("io.github.thisisthepy.pycomposeui.PythonAppView_androidKt")
Alignment = UI.getAlignment()
Modifier = UI.getModifier()


def mybox(content):
    return lambda *args, **kwargs: content(*args, **kwargs)
