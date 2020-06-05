FROM node:alpine

EXPOSE 8080
EXPOSE 3000

WORKDIR /usr/app

COPY ./front ./
RUN npm install

CMD ["npm", "start"]
