<template>
    <div>
        <div id="navMenus">
            <span>이력</span>
            <span><router-link :to="{name : 'BlogList',}">블로그</router-link></span>
            <span><router-link :to="{name : 'TagList'}">태그</router-link></span>
            <span v-if="isAdmin && isLogin"><router-link :to="{name : 'BlogPost'}">글쓰기</router-link></span>
        </div>

        <div id="search">
            <autocomplete
                    :search="search"
                    :autoSelect="true"
                    @submit="submit"/>
        </div>

        <div id="loginMenus">
            <span v-if="!isLogin" @click="login">로그인</span>
            <span v-if="isLogin" @click="logout">로그아웃</span>
        </div>
    </div>
</template>

<script>
    import search from "@/mixins/search"

    export default {
        name: "TheNavDesktop",
        props: {
            isAdmin: Boolean,
            isLogin: Boolean
        },
        mixins: [search],
        methods: {
            login() {
                this.$emit("login")
            },
            logout() {
                this.$emit("logout")
            },
        },
    }
</script>

<style lang="scss" scoped>
    #navMenus {
        position: absolute;
        left: 0;
        right: 0;
        z-index: -1;
        text-align: center;

        span {
            margin: 0px 20px;
            cursor: pointer;
        }

    }

    #search /deep/ {
        transform: scale(0.85);
    }

    #loginMenus{
        span{
            cursor: pointer;
            font-size: 0.9rem;
            padding: 10px;
        }
    }

</style>
