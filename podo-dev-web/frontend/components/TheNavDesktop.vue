<template>
    <div>
        <div id="navMenus">
            <div>
                <nuxt-link :to="{ name: 'resume' }">이력</nuxt-link>
            </div>
            <div>
                <nuxt-link :to="{ name: 'blogs' }">블로그</nuxt-link>
            </div>
            <div><nuxt-link :to="{ name: 'log' }">로그</nuxt-link></div>
            <div v-if="userinfo.isAdmin && isLogin">
                <nuxt-link :to="{ name: 'blogs-post' }">글쓰기</nuxt-link>
            </div>
        </div>

        <div id="search">
            <autocomplete :search="fetchWords" :autoSelect="true" @submit="search" />
        </div>

        <div id="loginMenus">
            <div v-if="!isLogin">
                <router-link :to="{name : 'login'}" v-tooltip="{ content: 'Login', class: 'tooltip' }">
                    <img src="../assets/btns/btn-login2.svg" id="loginIcon" alt="btn-login" />
                </router-link>
            </div>

            <div v-if="isLogin" @click="logout">
                <div v-tooltip="{ content: 'Logout', class: 'tooltip' }">
                    <img :src="userinfo.picture" id="userIcon" alt="userIcon" />
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import SearchMixin from "../mixins/SearchMixin";

export default {
    name: "TheNavDesktop",
    props: {
        userinfo: Object,
        isLogin: Boolean
    },
    mixins: [SearchMixin],
    methods: {
        logout() {
            this.$emit("logout");
        }
    }
};
</script>

<style lang="scss" scoped>
#navMenus {
    position: absolute;
    left: 0;
    right: 0;
    display: flex;
    justify-content: center;
    align-items: center;

    div {
        margin: 0px 20px;
        cursor: pointer;
    }
}

#search /deep/ {
    transform: scale(0.85);
}

#loginMenus {
    z-index: 1;
    height: var(--nav-height);
    display: flex;
    justify-content: center;
    align-items: center;

    div {
        cursor: pointer;
        font-size: 0.9rem;
    }

    #loginIcon {
        margin-top: 5px;
        width : 35px;
        height : 35px;
        border-radius: 20px;
        opacity: 0.9;
    }

    #userIcon {
        margin-top: 5px;
        width : 35px;
        height: 35px;
        border-radius: 20px;
    }

    /deep/ {
        /* custom CSS */
        .vue-tooltip.tooltip {
            background-color: #333333;
        }

        .vue-tooltip.tooltip .tooltip-arrow {
            border-color: #333333;
        }
    }
}
</style>
