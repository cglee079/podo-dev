<template>
    <div>
        <the-nav />
        <article id="main" :class="$mq">
            <nuxt />
        </article>
        <the-footer />
        <top-button />
        <spinner :loading="loading" />
    </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
import TheFooter from "../components/TheFooter";
import TopButton from "../components/global/TopButton";
import TheNav from "../components/TheNav";
import Spinner from "../components/global/Spinner";
import bus from "../utils/bus";

export default {
    metaInfo: {
        title: "podo-dev",
        htmlAttrs: {
            lang: "kr"
        },
        meta: [
            { charset: "utf-8" },
            { name: "author", content: "podo" },
            { property: "og:type", content: "website" },
            { property: "og:site_name", content: "podo-dev" }
        ]
    },

    components: {
        Spinner,
        TheNav,
        TheFooter,
        TopButton
    },

    computed: {
        ...mapGetters({
            isLogin: "user/isLogin"
        })
    },

    data() {
        return {
            loading: false
        };
    },
    methods: {
        ...mapActions({
            checkLogin: "user/checkLogin"
        }),

        startSpinner() {
            this.loading = true;
        },

        stopSpinner() {
            this.loading = false;
        }
    },

    created() {
        bus.$on("startSpinner", this.startSpinner);
        bus.$on("stopSpinner", this.stopSpinner);

        const query = this.$route.query;
        if (query && query.token) {
            const token = query.token;
            this.checkLogin(token).then(() => {
                this.$toast.show("로그인하였습니다");
                this.$router.push({ name: "blogs" });
            });
        }

        // 새로 고침 시
        const tokenInStorage = this.$storage.getLocalStorage("token");
        if (tokenInStorage) {
            this.checkLogin(tokenInStorage);
        }
    },

    beforeDestroy() {
        bus.$off("startSpinner");
        bus.$off("stopSpinner");
    }
};
</script>

<style lang="scss" scoped>
#main {
    overflow-x: hidden;
    margin: 50px auto 150px auto;
    max-width: 960px;
    min-height: 70vh;

    &.mobile {
        margin: 20px auto 150px auto;
        max-width: 100%;
    }
}
</style>

<style lang="scss">
:root {
    --nav-height: 60px;
    --max-width: 770px;
    --wild-max-width: 900px;
}

* {
    margin: 0;
    padding: 0;
    font-size: 100%;
    border: 0;
    vertical-align: baseline;
}

html,
body {
    font-family: "Noto Sans KR", sans-serif;
}

textarea,
input {
    font-family: "Noto Sans KR", sans-serif !important;

    &:disabled {
        background: #f1f1f1;
    }
}

a {
    color: inherit;
    text-decoration: none;
}
</style>
