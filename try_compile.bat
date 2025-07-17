@echo off
chcp 65001 >nul

echo ========================================
echo 微信支付通知拦截器 - 尝试本地编译
echo ========================================
echo.

echo 检查编译环境...

REM 检查Java
echo 1. 检查Java环境...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ 未找到Java环境
    echo.
    echo 请安装Java JDK 11或更高版本:
    echo https://adoptium.net/
    echo.
    goto :online_compile
) else (
    echo ✅ Java环境已安装
    java -version
    echo.
)

REM 检查Android SDK
echo 2. 检查Android SDK...
if "%ANDROID_HOME%"=="" (
    echo ❌ 未设置ANDROID_HOME环境变量
    echo.
    echo 请安装Android SDK并设置环境变量:
    echo https://developer.android.com/studio
    echo.
    goto :online_compile
) else (
    echo ✅ ANDROID_HOME已设置: %ANDROID_HOME%
    echo.
)

REM 尝试编译
echo 3. 开始编译...
echo.

echo 清理项目...
call gradlew.bat clean

echo.
echo 编译Debug版本...
call gradlew.bat assembleDebug

if %errorlevel% equ 0 (
    echo.
    echo ========================================
    echo ✅ 编译成功！
    echo ========================================
    echo.
    echo APK文件位置: app\build\outputs\apk\debug\app-debug.apk
    echo.
    echo 安装说明:
    echo 1. 将APK文件传输到Android设备
    echo 2. 在设备上启用"未知来源"安装
    echo 3. 安装APK文件
    echo 4. 打开应用并按照说明启用通知权限
    echo 5. 进行微信支付测试
    echo.
    goto :end
) else (
    echo.
    echo ❌ 编译失败
    echo.
    goto :online_compile
)

:online_compile
echo ========================================
echo 推荐使用在线编译方案
echo ========================================
echo.
echo 由于本地环境不完整，推荐使用以下在线编译方案:
echo.
echo 1. GitHub Actions (推荐):
echo    - 将项目上传到GitHub
echo    - 自动编译并提供APK下载
echo    - 详见: COMPILE_ONLINE.md
echo.
echo 2. 在线IDE:
echo    - Gitpod: https://gitpod.io
echo    - Replit: https://replit.com
echo.
echo 3. 本地安装完整环境:
echo    - Java JDK 11: https://adoptium.net/
echo    - Android Studio: https://developer.android.com/studio
echo.
echo 详细说明请查看 COMPILE_ONLINE.md 文件
echo.

:end
pause
