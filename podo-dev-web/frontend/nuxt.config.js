module.exports = {
    // mode: 'spa',

    debug: true,

    serverMiddleware: ['redirect-ssl'],

    env: {
        frontendUrl: 'https://www.podo-dev.com',
        internalServerUrl: 'http://192.168.219.103:28080',
        externalServerUrl: 'https://server.podo-dev.com',
        // externalServerUrl: 'http://localhost:28080',
        name: 'podo-dev'
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

    css: [
        //{ src: '', lang: 'css' }
    ],

    head: {
        title: 'podo-dev',
        meta: [
            {charset: 'utf-8'},
            {name: 'viewport', content: 'width=device-width, initial-scale=1, user-scalable=no'},
            {hid: "description", name: 'description', content: 'podo-dev'},
            {hid: "og:title", property: 'og:title', content: 'podo-dev'},
            {hid: "og:description", property: 'og:description', content: "podo-dev"},
            {hid: "og:image", property: 'og:image', content: "/og-image.png"},
            {hid: "og:site_name", property: 'og:site_name', content: "podo-dev"},
            {hid: "og:type", property: 'og:type', content: "article"}
        ],
        link: [
            {rel: 'icon', href: '/favicon.ico?v=2'},
            {rel: 'stylesheet', href: 'https://fonts.googleapis.com/earlyaccess/notosanskr.css'}
        ]
    },

    plugins: [
        {src: '~plugins/axios.js'},
        {src: '~plugins/router.js'},
        {src: '~plugins/vue-scrollto.js'},
        {src: '~plugins/mixins.js'},
        {src: '~plugins/tooltip.js'},
        {src: '~plugins/autocomplete.js'},
        {src: '~plugins/toast-editor.js'}
    ],

    loading: {color: '#ec5621'},

    modules: [
        '@tui-nuxt/editor',
        '@nuxtjs/axios',
        '@nuxtjs/toast',
        '@nuxtjs/universal-storage',

        ['nuxt-mq', {
            // Default breakpoint for SSR
            breakpoints: { // default breakpoints - customize this
                mobile: 720,
                tablet: 1080,
                desktop: Infinity,
            },
            defaultBreakpoint: 'mobile' // customize this for SSR
        }]
    ],

    axios: {
        baseURL: 'https://server.podo-dev.com',
        // baseURL: 'http://localhost:28080',
        // baseURL: 'http://192.168.219.103:28080',
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
            prefix: 'podo-dev_',
        },
        localStorage: {
            prefix: 'podo-dev_'
        },
    }


}
