import UIKit
import Common

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    var window: UIWindow?
    let mainViewController = MainViewController(pyInitialize: pyInitialize, pyFinalize: pyFinalize, pyImport: pyImport, pyErrorOccurred: pyErrorOccurred, pyErrorPrint: pyErrorPrint, pyObjectGetAttrString: pyObjectGetAttrString, pyObjectCallObject: pyObjectCallObject)
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        
        window = UIWindow(frame: UIScreen.main.bounds)
        window?.rootViewController = mainViewController.rootView
        window?.makeKeyAndVisible()
        
        return true
    }

    func applicationWillTerminate(_ application: UIApplication) {
        mainViewController.onDestroy()
    }
}
