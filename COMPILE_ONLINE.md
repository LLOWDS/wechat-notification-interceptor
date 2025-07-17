# 在线编译Android APK指南

由于本地环境没有安装Android SDK和Java，我们可以使用以下几种方式在线编译APK：

## 方法1: 使用GitHub Actions (推荐)

1. **上传到GitHub**:
   ```bash
   # 初始化git仓库
   git init
   git add .
   git commit -m "Initial commit: WeChat notification interceptor"
   
   # 创建GitHub仓库并推送
   # 在GitHub上创建新仓库，然后：
   git remote add origin https://github.com/你的用户名/wechat-notification-interceptor.git
   git branch -M main
   git push -u origin main
   ```

2. **自动编译**:
   - 推送代码后，GitHub Actions会自动开始编译
   - 在仓库的"Actions"标签页可以查看编译进度
   - 编译完成后，在Artifacts中下载APK文件

## 方法2: 使用在线IDE

### 2.1 Gitpod
1. 访问 https://gitpod.io
2. 输入你的GitHub仓库URL
3. 在线环境会自动配置Android开发环境
4. 运行 `./gradlew assembleDebug` 编译

### 2.2 Replit
1. 访问 https://replit.com
2. 创建新的Android项目
3. 上传项目文件
4. 使用内置终端编译

## 方法3: 使用Android Studio在线版

### 3.1 Android Studio Online
1. 访问 https://developer.android.com/studio/run/emulator-online
2. 上传项目文件
3. 使用在线Android Studio编译

## 方法4: 本地安装Android SDK (如果需要)

如果你想在本地编译，需要安装：

### Windows环境:
1. **安装Java JDK 11**:
   - 下载: https://adoptium.net/
   - 设置JAVA_HOME环境变量

2. **安装Android SDK**:
   - 下载Android Studio: https://developer.android.com/studio
   - 或者只下载命令行工具: https://developer.android.com/studio/command-line
   - 设置ANDROID_HOME环境变量

3. **编译项目**:
   ```cmd
   gradlew.bat assembleDebug
   ```

### Linux/Mac环境:
1. **安装Java JDK 11**:
   ```bash
   # Ubuntu/Debian
   sudo apt update
   sudo apt install openjdk-11-jdk
   
   # macOS (使用Homebrew)
   brew install openjdk@11
   ```

2. **安装Android SDK**:
   ```bash
   # 下载命令行工具
   wget https://dl.google.com/android/repository/commandlinetools-linux-8512546_latest.zip
   unzip commandlinetools-linux-8512546_latest.zip
   
   # 设置环境变量
   export ANDROID_HOME=$HOME/Android/Sdk
   export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin
   export PATH=$PATH:$ANDROID_HOME/platform-tools
   
   # 安装必要组件
   sdkmanager "platform-tools" "platforms;android-33" "build-tools;33.0.0"
   ```

3. **编译项目**:
   ```bash
   ./gradlew assembleDebug
   ```

## 编译输出

编译成功后，APK文件位置：
- `app/build/outputs/apk/debug/app-debug.apk`

## 安装和测试

1. 将APK文件传输到Android设备
2. 在设备上启用"未知来源"安装
3. 安装APK文件
4. 按照应用内说明启用通知权限
5. 进行微信支付测试

## 故障排除

### 编译错误
- 检查网络连接（下载依赖需要网络）
- 确保Java版本正确（推荐JDK 11）
- 清理项目：`./gradlew clean`

### 权限问题
- Linux/Mac: `chmod +x gradlew`
- Windows: 以管理员身份运行

### 依赖下载失败
- 使用国内镜像源（在gradle.properties中配置）
- 检查防火墙设置

## 推荐方案

对于快速测试，推荐使用 **GitHub Actions** 方法：
1. 简单易用，无需本地环境
2. 自动化编译，结果可靠
3. 支持多种Android版本测试
4. 编译日志完整，便于调试

编译完成后，你就可以获得一个可以安装在Android设备上的APK文件来测试微信通知拦截功能了。
