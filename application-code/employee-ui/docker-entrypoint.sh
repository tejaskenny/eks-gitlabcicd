#!/bin/sh
set -e

LOG_DIR="/mnt/logs"
mkdir -p $LOG_DIR
ACCESS_LOG="$LOG_DIR/access.log"
ERROR_LOG="$LOG_DIR/error.log"
mkdir -p "$LOG_DIR"


rm -f /var/log/nginx/access.log /var/log/nginx/error.log
touch "$ACCESS_LOG" "$ERROR_LOG"
ln -sf "$ACCESS_LOG" /var/log/nginx/access.log
ln -sf "$ERROR_LOG" /var/log/nginx/error.log
tail -F "$ACCESS_LOG" &
tail -F "$ERROR_LOG" >&2 &

exec nginx -g "daemon off;"
