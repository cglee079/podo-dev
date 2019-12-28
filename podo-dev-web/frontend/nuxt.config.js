module.exports = {
    mode: "spa",
    debug: true,

    loading: {
        color: "#444444"
    },

    router: {
        base: "/"
    },

    serverMiddleware: ["redirect-ssl"],

    env: {
        frontendUrl: "https://www.podo-dev.com",
        internalServerUrl: "http://192.168.219.103:28080",
        externalServerUrl: "https://server.podo-dev.com",
        // internalServerUrl: 'http://192.168.219.102:28080',
        // externalServerUrl: 'http://localhost:28080',

        name: "podo-dev"
    },

    build: {
        // extend (config, { isDev, isClient }) {
        //     if (isDev && isClient) {
        //         config.module.rules.push({
        //             enforce: 'pre',
        //             test: /\.(js|vue)$/,
        //             loader: 'eslint-loader',
        //             exclude: /(node_modules)/
        //         })
        //     }
        // }
    },

    head: {
        title: "podo-dev",
        meta: [
            { charset: "utf-8" },
            {
                name: "viewport",
                content:
                    "user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width"
            },
            { hid: "description", name: "description", content: "podo-dev" },
            {
                hid: "article:media_name",
                property: "article:media_name",
                content: "podo-dev"
            },
            { hid: "og:title", property: "og:title", content: "podo-dev" },
            {
                hid: "og:description",
                property: "og:description",
                content: "podo-dev"
            },
            { hid: "og:image", property: "og:image", content: "/og-image.png" },
            {
                hid: "og:site_name",
                property: "og:site_name",
                content: "podo-dev"
            },
            { hid: "og:type", property: "og:type", content: "article" },
            { hid: "by", property: "by", content: "podo" },
            { hid: "referrer", property: "referrer", content: "always" }
        ],
        link: [
            { rel: "icon", href: "/favicon.ico?v=2" },
            {
                rel: "stylesheet",
                href: "https://fonts.googleapis.com/earlyaccess/notosanskr.css"
            }
        ]
    },

    plugins: [
        { src: "~plugins/axios.js" },
        { src: "~plugins/vue-scrollto.js" },
        { src: "~plugins/mixins.js" },
        { src: "~plugins/tooltip.js" },
        { src: "~plugins/autocomplete.js" },
        { src: "~plugins/scroll-progressbar.js" },
        { src: "~plugins/toast-editor.js" }
    ],

    modules: [
        "@nuxtjs/markdownit",
        "@tui-nuxt/editor",
        "@nuxtjs/axios",
        "@nuxtjs/toast",
        "@nuxtjs/universal-storage",

        [
            "nuxt-mq",
            {
                // Default breakpoint for SSR
                breakpoints: {
                    // default breakpoints - customize this
                    mobile: 720,
                    tablet: 1080,
                    desktop: Infinity
                },
                defaultBreakpoint: "mobile" // customize this for SSR
            }
        ]
    ],

    axios: {
        baseURL: "https://server.podo-dev.com",
        // baseURL: 'http://localhost:28080',
        // baseURL: 'http://192.168.219.102:28080',
        proxyHeaders: false,
        credentials: false
    },

    toast: {
        theme: "toasted-primary",
        position: "top-center",
        duration: 2000
    },

    tui: {
        editor: {}
    },

    storage: {
        cookie: {
            prefix: "podo-dev_"
        },
        localStorage: {
            prefix: "podo-dev_"
        }
    },

    markdownit: {
        preset: "default",
        linkify: true,
        breaks: true,
        use: ["markdown-it-div", "markdown-it-attrs"],
        injected: true
    }
};
