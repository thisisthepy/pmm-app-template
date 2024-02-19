import UIKit
import Common

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    var window: UIWindow?
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        
        window = UIWindow(frame: UIScreen.main.bounds)
        let mainViewController = ViewControllerKt.MainViewController(pyInitialize: pyInitialize, pyFinalize: pyFinalize, pyImport: pyImport, pyObjectGetAttrString: pyObjectGetAttrString, pyObjectCallObject: pyObjectCallObject)
        window?.rootViewController = mainViewController
        window?.makeKeyAndVisible()
        
        return true
    }
}
