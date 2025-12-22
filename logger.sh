#!/bin/bash

# background_log.sh
java -verbose:class src/main/java/com/example/Main.java > class-loading.txt 2>&1 &
PID=$!

echo "Java実行中（PID: $PID）..."
wait $PID

echo "実行完了．ログ出力済み: class-loading.txt"
echo "行数: $(wc -l < class-loading.txt)"