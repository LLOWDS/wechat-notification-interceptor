@echo off
chcp 65001 >nul

echo 开始编译微信支付通知拦截器...

REM 检查是否有Android SDK
if "%ANDROID_HOME%"=="" (
    echo 错误: 未设置ANDROID_HOME环境变量
    echo 请先安装Android SDK并设置环境变量
    pause
    exit /b 1
)

REM 检查gradle wrapper
if not exist "gradlew.bat" (
    echo 错误: 未找到gradlew.bat文件
    echo 请确保在项目根目录运行此脚本
    pause
    exit /b 1
)

REM 清理项目
echo 清理项目...
call gradlew.bat clean

REM 编译debug版本
echo 编译debug版本...
call gradlew.bat assembleDebug

if %errorlevel% equ 0 (
    echo ✅ 编译成功!
    echo APK文件位置: app\build\outputs\apk\debug\app-debug.apk
    echo.
    echo 安装说明:
    echo 1. 将APK文件传输到Android设备
    echo 2. 在设备上安装APK
    echo 3. 打开应用并按照说明启用通知权限
    echo 4. 进行微信支付测试
) else (
    echo ❌ 编译失败，请检查错误信息
)

pause
