# Use Node.js official Alpine base image
FROM node:18-alpine

# Set working directory
WORKDIR /app

# Copy package files and install deps
COPY package*.json ./
RUN npm install

# Copy rest of the code
COPY . .

# Expose UI port
EXPOSE 80

# Start server
CMD ["npm", "start"]

