const redirectSSL = require('redirect-ssl')
export default function ({ app }) {
// Add middleware
    app.use(redirectSSL)
    app.use(nuxt.render) // if using nuxt

}
