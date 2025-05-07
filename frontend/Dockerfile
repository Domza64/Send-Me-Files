# This is a DEVELOPMENT Image
FROM node:20-alpine

WORKDIR /app

# Copy the rest of the app
COPY . .

# Exit if .env.development file is missing
RUN if [ ! -f /app/.env.development ]; then \
      echo "ERROR: .env.development file is missing in /app"; \
      exit 1; \
    fi

# Install dependencies
RUN npm install

# Expose port Vite runs on
EXPOSE 5173

# Start Vite dev server
CMD ["npm", "run", "dev", "--", "--host"]
