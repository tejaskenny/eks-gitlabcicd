#!/bin/sh

# Replace env vars in the template
envsubst '$API_BACKEND_URL' < /etc/nginx/templates/default.conf.template > /etc/nginx/conf.d/default.conf

# Start Nginx
exec "$@"

