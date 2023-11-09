from __future__ import annotations
from inspect import isfunction
import traceback

from java import jclass
from java.chaquopy import JavaClass


def cp_info(target, ori):
    if ori is None:
        raise ValueError(f"Error: Origin of Target Composable should not be None. - <Origin>[{ori}], <Target>[{target}]")
    else:
        target.__dict__['__name__'] = ori.__name__
        target.__dict__['__doc__'] = ori.__doc__
        target.__dict__['__module__'] = ori.__module__


try:
    _runtime = jclass("io.github.thisisthepy.pycomposeui.RuntimeKt")
    print("Compose Runtime:", _runtime)
    ComposableWrapper = _runtime.ComposableWrapper
    print("Composable Wrapper:", ComposableWrapper)
    _runtime_android = jclass("io.github.thisisthepy.pycomposeui.Runtime_androidKt")
    print("Compose Android Runtime:", _runtime_android)
    ComposableTemplate = _runtime_android.ComposableTemplate
    print("Composable Template:", ComposableTemplate)
    ComposableLambdaImpl = jclass("androidx.compose.runtime.internal.ComposableLambdaImpl")
    print("Composable Lambda:", ComposableLambdaImpl)


    class Composable:
        """ Composable Function Class (Singleton) for Jetpack Compose """
        composer = None
        from_function = False

        @classmethod
        def register_composer(cls, composer):
            cls.composer = composer

        def __new__(cls, composable=None):
            """ When @Composable is called """
            if isinstance(composable, Composable):  # is child of Composable
                return composable()  # return an instance of the composable argument
            else:
                return super().__new__(cls)  # return a new instance of the Composable class

        def __init__(self, composable=None):
            """ When @Composable is called """
            if isfunction(composable):  # When the decorative target is a function
                self.compose = composable
                cp_info(self, composable)
                self.from_function = True
            elif composable is None or issubclass(composable, Composable):  # When the decorative target is a child class of Composable or itself
                if composable is not None:
                    cp_info(self, composable)
            else:  # When the decorative target is an object other than a child class of Composable
                if hasattr(composable, "content"):
                    target = composable  # When the compose method is staticmethod/classmethod
                    if isinstance(composable, staticmethod):  # When the compose method is staticmethod
                        self.from_function = True
                else:
                    target = composable()  # When the compose method is instancemethod
                self.compose = target.compose
                cp_info(self, composable)

        @staticmethod
        def compose(*args, **kwargs):
            """ Composition Content method for compose """
            pass

        def __invoke(self, *args, **kwargs):
            """ Invoke composableLambda / composableLambdaInstance
            Ref: https://sungbin.land/jetpack-compose-%EB%9F%B0%ED%83%80%EC%9E%84%EC%97%90%EC%84%9C-%EC%9D%BC%EC%96%B4%EB%82%98%EB%8A%94-%EB%A7%88%EB%B2%95-%EC%99%84%EC%A0%84%ED%9E%88-%ED%8C%8C%ED%95%B4%EC%B9%98%EA%B8%B0-composeinitial-4c4c306c0a8c
            !WARNING! keyword 'content' must be specified in the kwargs
            """
            composer = self.composer
            content = kwargs.pop("content", None)

            try:
                if isinstance(content, Composable) or content is None:
                    pass
                elif isinstance(content, JavaClass) or isinstance(content, ComposableLambdaImpl):
                    content = lambda: ComposableWrapper(content, composer, 1)  # Raw Kotlin Composable
                else:
                    raise ValueError(f"Error: Invalid Content Type. Please check your Composable's arguments - Current content type: {type(content)}")

                if content is not None:
                    kwargs['content'] = content

                composition_target = self.compose
                print(self.__dict__, composition_target, composition_target.__code__.co_varnames, args, kwargs)
                ComposableTemplate(lambda: composition_target(*args, **kwargs), composer, 1)  # Python Composable
            except Exception as err:
                print("-----------------------------------------------------------------------------------------------------------")
                traceback.print_exc()
                print("-----------------------------------------------------------------------------------------------------------")
                raise err

        def __call__(self, *args, **kwargs):
            """ Do Composition """
            if self.composer is None:
                raise RuntimeError("Composer for Composable must be registered before Composition starts.")
            try:
                index = self.compose.__code__.co_varnames.index("content") - (0 if self.from_function else 1)
                if "content" in kwargs:
                    content = kwargs.pop("content")
                else:
                    args = list(args)
                    content = args.pop(index)

                if content is None:
                    content = EmptyComposable
            except ValueError as _:
                content = None

            kwargs['content'] = content
            self.__invoke(*args, **kwargs)


    #TODO
    class ComposableClass(Composable):
        """ Composable Object Class (not Singleton) for Jetpack Compose """

        def __new__(cls, composable=None):
            """ When @Composable is called """
            if isinstance(composable, Composable):
                return composable  # TODO
            else:
                if hasattr(composable, "content"):
                    # When the content method is staticmethod/classmethod
                    class ComposableClass(Composable):
                        compose = composable.compose
                else:
                    # When the content method is instancemethod
                    class ComposableClass(Composable):
                        def __init__(self):
                            super().__init__()
                            self.compose = composable().compose

                ComposableClass.__name__ = composable.__name__
                ComposableClass.__doc__ = composable.__doc__
                ComposableClass.__module__ = composable.__module__
                return ComposableClass


    class KotlinComposable(Composable):
        """ Raw Kotlin Composable Wrapper Class """
        def __new__(cls, content):
            return super().__new__(cls)

        def __init__(self, content):
            super().__init__()
            self.compose = content
            print(self.__dict__, flush=True)


    @Composable
    class EmptyComposable(Composable):
        """ Empty Composition """
        def __init__(self):
            super().__init__()
            print(self.__dict__, flush=True)

except Exception as e:
    print("-----------------------------------------------------------------------------------------------------------")
    traceback.print_exc()
    print("ERROR: PyComposeUI Runtime Library is not Found.")
    print("-----------------------------------------------------------------------------------------------------------")
    raise e
