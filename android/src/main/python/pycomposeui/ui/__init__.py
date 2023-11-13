from pycomposeui.runtime import Composable, EmptyComposable

from java import jclass
import traceback

try:
    _alignment = jclass("androidx.compose.ui.Alignment")
    _modifier = jclass("androidx.compose.ui.Modifier")

except Exception as e:
    print("-----------------------------------------------------------------------------------------------------------")
    traceback.print_exc()
    print("ERROR: PyComposeUI Ui Library is not Found.")
    print("-----------------------------------------------------------------------------------------------------------")
    raise e
