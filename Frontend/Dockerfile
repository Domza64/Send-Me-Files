# This is a DEVELOPMENT Image
FROM node:20-alpine

WORKDIR /app
RUN chown -R node:node /app

# USER node

# Copy app files with correct ownership
COPY --chown=node:node . .

RUN npm install

EXPOSE 5173

CMD ["npm", "run", "dev", "--", "--host"]
