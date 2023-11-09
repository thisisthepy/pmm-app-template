from pycomposeui.runtime import Composable, EmptyComposable
from pycomposeui.material3 import SimpleText

from java import jclass
import traceback


try:
    @Composable
    def UiTestCase(text: str = "UiTestCase"):
        SimpleText(text)


    @Composable
    class UiTest:
        def compose(self, content: Composable = EmptyComposable):
            UiTestCase(text="UiTestCase in UiTest")
            #content()


    @Composable
    class BasicText:
        @classmethod
        def compose(cls, text: str = "BasicText"):
            SimpleText(text)


    @Composable
    class RichText(Composable):
        @staticmethod
        def compose(content: Composable = EmptyComposable):
            BasicText("Basic Text inside of Rich Text")
            content()
except Exception as err:
    print("-----------------------------------------------------------------------------------------------------------")
    traceback.print_exc()
    print("-----------------------------------------------------------------------------------------------------------")
    raise err
