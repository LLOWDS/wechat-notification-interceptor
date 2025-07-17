#!/bin/bash

echo "下载Gradle Wrapper JAR文件..."

# 创建目录
mkdir -p gradle/wrapper

# 下载gradle-wrapper.jar
curl -L -o gradle/wrapper/gradle-wrapper.jar https://github.com/gradle/gradle/raw/v7.3.3/gradle/wrapper/gradle-wrapper.jar

if [ $? -eq 0 ]; then
    echo "✅ Gradle Wrapper JAR下载成功"
    
    # 设置执行权限
    chmod +x gradlew
    
    echo "现在可以运行 ./gradlew assembleDebug 来编译项目"
else
    echo "❌ 下载失败，请检查网络连接"
    echo "你也可以手动下载gradle-wrapper.jar文件到gradle/wrapper/目录"
fi
