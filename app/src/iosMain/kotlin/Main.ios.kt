import androidx.compose.ui.window.ComposeUIViewController
import platform.Foundation.NSLog
import platform.Foundation.NSString
import platform.UIKit.UIViewController
import platform.posix.setenv
import python.native.ffi.*


val customBuiltinImportString = """
    import sys, imp, types
    from os import environ
    from os.path import exists, join

    try:
        # python 3
        import _imp

        EXTS = _imp.extension_suffixes()
        sys.modules['subprocess'] = types.ModuleType(name='subprocess')
        sys.modules['subprocess'].PIPE = None
        sys.modules['subprocess'].STDOUT = None
        sys.modules['subprocess'].DEVNULL = None
        sys.modules['subprocess'].CalledProcessError = Exception
        sys.modules['subprocess'].check_output = None
    except ImportError:
        EXTS = ['.so']

    # Fake redirection to supress console output
    if environ.get('KIVY_NO_CONSOLE', '0') == '1':
        class fakestd(object):
            def write(self, *args, **kw): pass
            def flush(self, *args, **kw): pass
        sys.stdout = fakestd()
        sys.stderr = fakestd()

    # Custom builtin importer for precompiled modules
    class CustomBuiltinImporter(object):
        def find_module(self, fullname, mpath=None):
            # print(f'find_module() fullname={fullname} mpath={mpath}')
            if '.' not in fullname:
                return
            if not mpath:
                return
            part = fullname.rsplit('.')[-1]
            for ext in EXTS:
                fn = join(list(mpath)[0], '{}{}'.format(part, ext))
            # print('find_module() {}'.format(fn))
            if exists(fn):
                return self
            return

        def load_module(self, fullname):
            f = fullname.replace('.', '_')
            mod = sys.modules.get(f)
            if mod is None:
                # print('LOAD DYNAMIC', f, sys.modules.keys())
                try:
                    mod = imp.load_dynamic(f, f)
                except ImportError:
                    # import traceback; traceback.print_exc();
                    # print('LOAD DYNAMIC FALLBACK', fullname)
                    mod = imp.load_dynamic(fullname, fullname)
                sys.modules[fullname] = mod
                return mod
            return mod

    sys.meta_path.insert(0, CustomBuiltinImporter())
    """


fun pyInitialize() {
    // Init Python
    Py_Initialize()

    // Import Built-In functions
    PyRun_SimpleString(customBuiltinImportString)
    NSLog("INFO: Python Built-in functions are loaded.")
    PyRun_SimpleString("print('HAHAHAHAHAHAHAHAHAHAHAHAHAHJ')")
}

fun pyFinalize() {
    Py_Finalize()
}

//fun pyImport(module: NSString) -> UInt64 {
//    let pointer: UnsafePointer<CChar>? = module.utf8String
//    return PyImport_Import(PyUnicode_DecodeFSDefault(pointer))
//}
//
//fun pyErrorOccurred(): Boolean {
//    return PyErr_Occurred()
//}
//
//fun pyErrorPrint() {
//    PyErr_Print()
//}
//
//fun pyObjectGetAttrString(obj: UInt64, attr: NSString) -> UInt64 {
//    let pointer: UnsafePointer<CChar>? = attr.utf8String
//    let ptrObj = UnsafeMutablePointer<PyObject>(bitPattern: obj)
//    return PyObject_GetAttrString(ptrObj, PyUnicode_DecodeFSDefault(pointer))
//}
//
//fun pyObjectCallObject(callable: UInt64, args: UInt64) -> UInt64 {
//    let callableObj = UnsafeMutablePointer<PyObject>(bitPattern: callable)
//    let argsObj = UnsafeMutablePointer<PyObject>(bitPattern: args)
//    return PyObject_CallObject(callableObj, argsObj)
//}


fun MainViewController(): UIViewController = ComposeUIViewController {
    pyFinalize()
    App()
    pyFinalize()
}
