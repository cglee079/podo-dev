<template>
    <div id="wrapNav">
        <header id="nav" :class="$mq">
            <div id="logo">
                <router-link :to="{ name: 'index' }">
                    <img src="../assets/podo-dev.svg" width="57px" alt="logo" />
                </router-link>
            </div>

            <the-nav-desktop
                id="theNavDesktop"
                ref="theNavDesktop"
                :userinfo="getUserinfo"
                :isLogin="isLogin"
                @login="login"
                @logout="clickLogout"
            />

            <the-nav-mobile
                id="theNavMobile"
                :isAdmin="isAdmin"
                :isLogin="isLogin"
                @login="login"
                @logout="clickLogout"
            />
        </header>

        <client-only>
            <vue-scroll-progress-bar
                height="2px"
                backgroundColor="#666666"
                zIndex="0"
            />
        </client-only>
    </div>
</template>

<script>
import TheNavMobile from "./TheNavMobile";
import TheNavDesktop from "./TheNavDesktop";
import { mapActions, mapGetters } from "vuex";

export default {
    name: "TheNav",
    components: {
        "the-nav-desktop": TheNavDesktop,
        "the-nav-mobile": TheNavMobile
    },
    computed: {
        ...mapGetters({
            isAdmin: "user/isAdmin",
            isLogin: "user/isLogin",
            getUserinfo: "user/getUserinfo"
        })
    },
    methods: {
        ...mapActions({
            logout: "user/logout",
            login: "user/login"
        }),

        clickLogout() {
            this.toastConfirm("정말 로그아웃 하시겠습니까?", () => {
                this.logout(() => {
                    this.$toast.show("로그아웃 되었습니다");
                    this.$router.push({ name: "index" });
                });
            });
        }
    }
};
</script>

<style lang="scss" scoped>
/deep/ .progress-bar-container--container {
    position: fixed;
    width: 100%;
    top: 0;
    left: 0;
}

/deep/ .nuxt-link-active {
    text-decoration: underline;
}

#wrapNav {
    z-index: 9;
    top: 0;
    left: 0;
    right: 0;
    position: sticky;
    background: #ffffff;
    opacity: 0.95;
    height: var(--nav-height);
    border-bottom: 0.7px solid #eeeeee;
}

#nav {
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 100%;
    padding: 0px 5%;

    #logo {
        z-index: 1;
        margin-top: 5px;
    }

    #theNavDesktop {
        display: flex;
        justify-content: center;
        align-items: center;
    }

    #theNavMobile {
        display: none;
    }

    &.tablet,
    &.mobile {
        #theNavDesktop {
            display: none;
        }

        #theNavMobile {
            display: block;
        }
    }
}
</style>
