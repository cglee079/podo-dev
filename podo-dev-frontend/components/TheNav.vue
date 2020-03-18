<template>
    <div id="wrapNav" :class="nav.showing === true ? 'on' : ''">
        <header id="nav" :class="$mq">
            <div id="logo">
                <nuxt-link :to="{ name: 'index' }">
                    <img src="../assets/podo-dev.svg" width="57px" alt="logo" />
                </nuxt-link>
            </div>

            <client-only>
                <the-nav-desktop
                    id="theNavDesktop"
                    ref="theNavDesktop"
                    :userinfo="getUserinfo"
                    :is-login="isLogin"
                    @logout="clickLogout"
                />

                <the-nav-mobile
                    id="theNavMobile"
                    :userinfo="getUserinfo"
                    :is-admin="isAdmin"
                    :is-login="isLogin"
                    @logout="clickLogout"
                />
            </client-only>
        </header>

        <client-only>
            <vue-scroll-progress-bar height="2px" backgroundColor="#666666" zIndex="0" />
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
    data() {
        return {
            nav: {
                showing: true
            },
            scroll: {
                before: 0, // 페이지 리로딩시 브라우져가 0으로 스크롤함.
                doing: null
            }
        };
    },
    methods: {
        ...mapActions({
            logout: "user/logout"
        }),

        clickLogout() {
            this.toastConfirm("정말 로그아웃 하시겠습니까?", () => {
                this.logout(() => {
                    this.$toast.show("로그아웃 되었습니다");
                    this.$router.push({ name: "blogs" });
                });
            });
        },
        onScroll() {
            if (process.server) {
                return;
            }

            window.clearTimeout(this.scroll.doing);

            this.scroll.doing = setTimeout(() => {
                this.nav.showing = !(window.scrollY > this.scroll.before);
                this.scroll.before = window.scrollY;
            }, 20);
        }
    },
    mounted() {
        window.addEventListener("scroll", this.onScroll);
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
    left: 0;
    right: 0;
    top: calc(var(--nav-height) * -1);
    position: sticky;
    background: #ffffff;
    height: var(--nav-height);
    border-bottom: 0.7px solid #eeeeee;
    transition: top 0.3s ease-in-out;

    &.on {
        top: 0;
    }
}

#nav {
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 100%;
    padding: 0 5%;

    max-width: var(--desktop-max-width);
    margin: 0px auto;

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
