FROM node:12

RUN mkdir -p /app
ENV HOST 0.0.0.0

COPY . /app

WORKDIR /app

RUN ls

RUN npm install
RUN npm run build

CMD npm start
