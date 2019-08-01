<template>
    <div>
        <the-nav></the-nav>
        <div id="main">
            <router-view></router-view>
        </div>
        <the-footer></the-footer>
    </div>
</template>

<script>
    import TheNav from '@/components/TheNav'
    import TheFooter from '@/components/TheFooter'

    export default {
        components: {
            'the-nav': TheNav,
            'the-footer': TheFooter
        },
        name: 'App'
    }
</script>
<style scoped>
    #main {
        width: 1080px;
        margin: 50px auto 150px auto;
    }

</style>
<style>
    @import url('http://fonts.googleapis.com/earlyaccess/notosanskr.css');

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

    textarea, input{
        font-family: 'Noto Sans KR', sans-serif !important;
    }

    a {
        color: inherit;
        text-decoration: none;
    }
</style>
