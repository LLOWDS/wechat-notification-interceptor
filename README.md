# 微信支付通知拦截器

这是一个Android应用，用于测试拦截微信支付成功通知的功能。该应用使用`NotificationListenerService`来监听系统通知，并筛选出微信的支付相关通知。

## 功能特性

- 🔍 实时监听微信应用的通知
- 💰 自动识别支付相关通知（支付成功、收款、转账、红包等）
- 📱 友好的用户界面显示拦截到的通知
- ⏰ 显示通知接收时间
- 🔒 安全的本地处理，不上传任何数据

## 安装和使用

### 1. 编译安装

#### 方法A: 在线编译 (推荐)
由于需要Android SDK环境，推荐使用在线编译：

1. **GitHub Actions自动编译**:
   - 将项目上传到GitHub
   - 自动编译并提供APK下载
   - 详见 `COMPILE_ONLINE.md`

2. **在线IDE编译**:
   - Gitpod: https://gitpod.io
   - Replit: https://replit.com

#### 方法B: 本地编译
如果你有Android开发环境：
```bash
# Linux/Mac
./gradlew assembleDebug

# Windows
gradlew.bat assembleDebug
```

#### 方法C: 使用预编译脚本
```bash
# Windows
try_compile.bat

# 会自动检查环境并给出编译建议
```

### 2. 启用通知访问权限
1. 安装应用后打开
2. 点击"启用通知权限"按钮
3. 在系统设置中找到"微信支付通知拦截器"
4. 开启通知访问权限
5. 返回应用

### 3. 测试功能
1. 确保应用显示"✅ 通知监听服务已启用"
2. 打开微信进行支付操作（可以给朋友转账1分钱测试）
3. 支付成功后，通知应该会出现在应用的列表中

## 支持的通知类型

应用会拦截包含以下关键词的微信通知：
- 支付成功
- 收款
- 转账
- 红包
- 微信支付
- 付款
- 收钱
- 收到转账
- 收到红包
- 已收钱
- 收款到账

## 技术实现

### 核心组件
- `WeChatNotificationListener`: 继承自`NotificationListenerService`的通知监听服务
- `MainActivity`: 主界面，显示拦截状态和通知列表
- `NotificationAdapter`: RecyclerView适配器，用于显示通知列表

### 权限要求
- `android.permission.BIND_NOTIFICATION_LISTENER_SERVICE`: 通知监听权限
- `android.permission.INTERNET`: 网络权限（预留）
- `android.permission.FOREGROUND_SERVICE`: 前台服务权限

### 工作原理
1. 应用注册`NotificationListenerService`
2. 系统将所有通知转发给服务
3. 服务筛选出微信包名的通知
4. 检查通知内容是否包含支付关键词
5. 通过LocalBroadcast将匹配的通知发送给主界面
6. 主界面更新UI显示拦截到的通知

## 注意事项

### 隐私和安全
- ⚠️ 此应用仅用于测试目的
- 🔒 所有数据仅在本地处理，不会上传到服务器
- 📱 建议测试完成后卸载应用或关闭通知权限

### 兼容性
- 最低支持Android 5.0 (API 21)
- 目标Android 13 (API 33)
- 已在以下设备测试：[请根据实际测试情况填写]

### 限制
- 需要手动授予通知访问权限
- 某些定制系统可能有额外限制
- 微信更新可能影响通知格式识别

## 故障排除

### 应用显示"❌ 需要启用通知访问权限"
1. 确保已在系统设置中开启权限
2. 重启应用
3. 如果仍有问题，尝试重启手机

### 无法拦截到通知
1. 确认微信通知功能已开启
2. 检查微信是否有支付相关通知产生
3. 尝试进行小额转账测试
4. 查看应用日志（使用adb logcat）

### 通知内容显示不完整
- 这是正常现象，系统对通知内容有长度限制
- 应用会优先显示bigText，其次是text内容

## 开发和调试

### 查看日志
```bash
adb logcat | grep WeChatNotificationListener
```

### 测试建议
1. 使用微信给朋友转账1分钱
2. 让朋友给你发红包
3. 使用微信支付购买商品
4. 观察应用是否正确拦截通知

## 免责声明

此应用仅供学习和测试使用。使用者需要：
- 遵守相关法律法规
- 尊重他人隐私
- 不得用于非法用途
- 测试完成后及时删除

## 许可证

本项目仅供学习交流使用，请勿用于商业用途。
