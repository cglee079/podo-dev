FROM node:14

RUN mkdir -p /app
ENV HOST 0.0.0.0

COPY . /app

WORKDIR /app

RUN ls

RUN git config --global http.sslverify "false"
RUN git config --global url."https://".insteadOf git://

RUN rm -rf node_modules
RUN rm -rf package-lock.json
RUN npm cache clean --force
RUN npm install
RUN npm run build

CMD npm start
