from java import jclass

composable_wrapper = jclass("io.github.thisisthepy.pycomposeui.ComposableTemplate")


def check():
    return "Hi there, this is python code"

def composable(pycomposefunc):
    return composable_wrapper(pycomposefunc)


@composable
def testview(content):
    jclass("androidx.compose.material3.Text")
