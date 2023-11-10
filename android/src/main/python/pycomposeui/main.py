from pycomposeui.runtime import Composable, EmptyComposable
from pycomposeui.material3 import SimpleText, SimpleColumn

from java import jclass
import traceback


@Composable
def UiTestCase(text: str = "UiTestCase"):
    SimpleText(text)


def ColumnContent(content: Composable = EmptyComposable):
    UiTestCase(text="UiTestCase in UiTest")
    content()


@Composable
class UiTest:
    def compose(self, content: Composable = EmptyComposable):
        SimpleColumn(lambda: ColumnContent(content))


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
