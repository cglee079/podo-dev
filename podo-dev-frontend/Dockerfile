FROM node:10

RUN mkdir -p /app
ENV HOST 0.0.0.0

COPY ./app /app
WORKDIR /app

RUN npm install
RUN npm run build

CMD npm \
    start
