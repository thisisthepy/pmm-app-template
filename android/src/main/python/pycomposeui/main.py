from pycomposeui.runtime import Composable, EmptyComposable
from pycomposeui.material3 import SimpleText, SimpleColumn

from java import jclass
import traceback


@Composable
def UiTestCase(text: str = "UiTestCase"):
    SimpleText(text)


@Composable
class UiTest:
    def compose(self, content: Composable = EmptyComposable):
        SimpleColumn(Composable(lambda: {
            UiTestCase(text="UiTestCase in UiTest"),
            content()
        }))


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
