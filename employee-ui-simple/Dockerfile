# Use lightweight NGINX image
FROM nginx:alpine

# Copy your static HTML file to the NGINX default directory
COPY index.html /usr/share/nginx/html/index.html

# Expose port 80
EXPOSE 80

# Start NGINX
CMD ["nginx", "-g", "daemon off;"]

