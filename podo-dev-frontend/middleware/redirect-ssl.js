const redirectSSL = require("redirect-ssl");

export default function({ app }) {
    app.use(redirectSSL);
    app.use(nuxt.render); // if using nuxt
}
