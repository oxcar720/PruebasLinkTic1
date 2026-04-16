FROM node:22

WORKDIR /app

COPY package*.json ./

RUN npm install

COPY . .
RUN chmod -R 755 /app/node_modules/.bin
EXPOSE 3000

CMD ["npm", "run", "dev"]