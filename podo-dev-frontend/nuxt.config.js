module.exports = {
    // mode: "spa",
    // debug: true,

    serverMiddleware: ["redirect-ssl"],

    server: {
        port: 8000,
        host: "0.0.0.0"
    },

    loading: {
        color: "#444444"
    },

    router: {
        base: "/",
        middleware: ["get-user"]
    },

    env: {
        NAME: "podo-dev",
        VERSION: "2.1.0",
        STATIC_URL: "https://www.podo-dev.com",
        INTERNAL_SERVER_URL: "http://192.168.219.103:28080",
        // EXTERNAL_SERVER_URL: "http://localhost:28080",
        EXTERNAL_SERVER_URL: "https://server.podo-dev.com"
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
                href:
                    "https://fonts.googleapis.com/css?family=Noto+Sans+KR:300,400,700&display=swap"
            }
        ]
    },

    plugins: [
        { src: "~plugins/axios.js" },
        { src: "~plugins/vue-scrollto.js" },
        { src: "~plugins/custom-toast.js" },
        { src: "~plugins/tooltip.js" },
        { src: "~plugins/autocomplete.js" },
        { src: "~plugins/combined-inject.js" },
        { src: "~plugins/scroll-progressbar.js" },
        { src: "~plugins/classie.js", ssr: false },
        { src: "~plugins/get-user.js" },
        { src: "~plugins/scroll-block.js", ssr: false },
        { src: "~plugins/toast-editor.js", ssr: false }
    ],

    modules: [
        "@nuxtjs/markdownit",
        "@tui-nuxt/editor",
        "@nuxtjs/axios",
        "@nuxtjs/toast",
        "@nuxtjs/universal-storage",
        "@nuxtjs/google-analytics",
        "@nuxtjs/google-adsense",

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
        proxyHeaders: false,
        credentials: false
    },

    toast: {
        theme: "toasted-primary",
        position: "top-center",
        duration: 2000
    },

    tui: {
        editor: {
            stylesheet: {
                editor: "~/assets/css/tui/custom-tui-editor.css",
                contents: "~/assets/css/tui/custom-tui-content.css",
                codeHighlight: "~/assets/css/tui/custom-highlight.css",
                highlightJs: "~/assets/css/tui/custom-highlight.css",
                codemirror: "~/assets/css/tui/custom-codemirror.css"
            }
        }
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
    },

    googleAnalytics: {
        id: "UA-155243224-1"
    },

    "google-adsense": {
        id: "ca-pub-3301697379976707"
    }
};
