@echo off
chcp 65001 >nul

echo ========================================
echo 打包微信支付通知拦截器项目
echo ========================================
echo.

set PROJECT_NAME=WeChatNotificationInterceptor
set ZIP_NAME=%PROJECT_NAME%_Source.zip

echo 正在创建项目压缩包...

REM 检查是否有7zip或其他压缩工具
where 7z >nul 2>&1
if %errorlevel% equ 0 (
    echo 使用7zip压缩...
    7z a -tzip %ZIP_NAME% * -x!*.zip -x!.git -x!build -x!.gradle
    goto :success
)

where winrar >nul 2>&1
if %errorlevel% equ 0 (
    echo 使用WinRAR压缩...
    winrar a -afzip %ZIP_NAME% * -x*.zip -x.git -xbuild -x.gradle
    goto :success
)

REM 使用PowerShell压缩
echo 使用PowerShell压缩...
powershell -command "Compress-Archive -Path '*.gradle*', 'app', 'gradle', 'README.md', 'COMPILE_ONLINE.md', '*.bat', '*.sh', '.github' -DestinationPath '%ZIP_NAME%' -Force"

:success
if exist %ZIP_NAME% (
    echo.
    echo ✅ 项目打包成功！
    echo.
    echo 压缩包: %ZIP_NAME%
    echo.
    echo 使用说明:
    echo 1. 将压缩包传输到有Android开发环境的电脑
    echo 2. 解压缩文件
    echo 3. 运行 gradlew.bat assembleDebug 编译
    echo 4. 或者上传到GitHub使用Actions自动编译
    echo.
    echo 详细编译说明请查看 COMPILE_ONLINE.md
) else (
    echo ❌ 打包失败
)

echo.
pause
