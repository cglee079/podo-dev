FROM node:14

RUN mkdir -p /app
ENV HOST 0.0.0.0

COPY ./app /app
WORKDIR /app

RUN npm install --silent
RUN npm run build

CMD npm \
    start
