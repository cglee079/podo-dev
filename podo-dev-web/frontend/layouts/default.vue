<template>
    <div>
        <the-nav/>
        <div id="main" :class="$mq">
            <nuxt/>
        </div>
        <the-footer/>
        <the-top-button/>
    </div>
</template>

<script>
    import {mapActions, mapGetters} from 'vuex'
    import TheFooter from "../components/TheFooter"
    import TheTopButton from "../components/TheTopButton"
    import TheNav from "../components/TheNav"

    export default {
        name: 'App',
        layout: 'blog',
        metaInfo: {
            title: 'podo-dev',
            htmlAttrs: {
                lang: 'kr',
            },
            meta: [
                {charset: 'utf-8'},
                {name: 'author', content: 'podo'},
                {property: 'og:type', content: 'website'},
                {property: 'og:site_name', content: 'podo-dev'},
            ]
        },

        components: {
            'the-nav': TheNav,
            'the-footer': TheFooter,
            'the-top-button': TheTopButton
        },
        computed: {
            ...mapGetters({
                isLogin: 'user/isLogin'
            })
        },
        methods: {
            ...mapActions({
                checkLogin: "user/checkLogin"
            }),
        },
        created() {
            // 로그인 성공 시,
            const query = this.$route.query

            if (query && query.token) {
                this.checkLogin({
                    token: query.token,
                    callback: () => {
                        this.$toast.show("로그인하였습니다")
                    }
                })
                this.$router.push({name: 'index'})
            }

            // 새로 고침 시
            const savedToken = this.$storage.getLocalStorage("token")
            if (savedToken) {
                this.checkLogin({token: savedToken, callback: null})
            }
        }
    }
</script>

<style lang="scss" scoped>
    #main {
        margin: 50px auto 150px auto;
        max-width: 960px;
        min-height: 100vh;

        &.mobile {
            margin: 20px auto 150px auto;
            max-width: 100%;
        }
    }
</style>


<style lang="scss">
    :root {
        --nav-height: 65px;
    }

    /*@import url('https://fonts.googleapis.com/earlyaccess/notosanskr.css');*/

    a, abbr, acronym, address, applet, article, aside,
    audio, b, bdi, bdo, big, blockquote, body, button, canvas,
    caption, center, cite, code, dd, del, details, dfn,
    div, dl, dt, em, embed, fieldset, figcaption, figure,
    footer, form, header, hgroup, h1, h2, h3, h4, h5, h6,
    html, i, iframe, img, input, ins, kbd, label, legend,
    li, map, mark, menu, nav, object, ol, p, pre, q, rp,
    rt, ruby, s, samp, section, select, small, span, strike,
    strong, summary, sub, sup, table, tbody, td, textarea,
    tfoot, th, thead, time, tr, tt, u, ul, var, video {
        margin: 0;
        padding: 0;
        font-size: 100%;
        border: 0;
        vertical-align: baseline;
    }

    html, body {
        font-family: 'Noto Sans KR', sans-serif;
    }

    textarea, input {
        font-family: 'Noto Sans KR', sans-serif !important;

        &:disabled {
            background: #F1F1F1;
        }
    }

    a {
        color: inherit;
        text-decoration: none;
    }
</style>
