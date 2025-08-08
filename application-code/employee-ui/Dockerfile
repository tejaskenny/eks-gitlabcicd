# Use lightweight NGINX image
FROM nginx:alpine

# Copy static HTML file
COPY index.html /usr/share/nginx/html/index.html

# Copy entrypoint script
COPY docker-entrypoint.sh /docker-entrypoint.sh
RUN chmod +x /docker-entrypoint.sh

# Expose port 80
EXPOSE 80

# Use custom entrypoint
ENTRYPOINT ["/docker-entrypoint.sh"]
