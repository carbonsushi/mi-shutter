package io.github.carbonsushi.mishutter

import com.github.kyuubiran.ezxhelper.init.EzXHelperInit
import com.github.kyuubiran.ezxhelper.utils.getMethod
import com.github.kyuubiran.ezxhelper.utils.hookBefore
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage

class Hooker : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName == "com.android.camera") {
            EzXHelperInit.initHandleLoadPackage(lpparam)
            getMethod(
                "com.xiaomi.camera.util.SystemProperties",
                "get",
                String::class.java,
                true,
                String::class.java
            ).hookBefore { param ->
                when ((param.args[0] as String)) {
                    "ro.miui.region" -> param.result = "US"
                    "ro.miui.customized.region" -> param.result = "us"
                }
            }
        }
    }
}