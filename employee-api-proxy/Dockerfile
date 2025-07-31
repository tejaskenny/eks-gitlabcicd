FROM nginx:alpine

# Copy default.conf template
COPY default.conf.template /etc/nginx/templates/default.conf.template

# Entry point script
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh
# Copy static UI
COPY index.html /usr/share/nginx/html/index.html


EXPOSE 80

ENTRYPOINT ["/entrypoint.sh"]
CMD ["nginx", "-g", "daemon off;"]

