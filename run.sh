#!/usr/local/bin/bash

echo "" >> build_log.log &&
date >> build_log.log &&
./gradlew assembleDebug >> build_log.log &&
adb push ./app/build/outputs/apk/app-debug.apk /data/local/tmp/com.example.ud4.todo_list
adb shell pm install -r "/data/local/tmp/com.example.ud4.todo_list"
