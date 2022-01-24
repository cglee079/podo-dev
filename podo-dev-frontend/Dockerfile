FROM node:12

RUN mkdir -p /app
ENV HOST 0.0.0.0

COPY . /app

WORKDIR /app

RUN npm install --silent
RUN npm run build

CMD npm start
