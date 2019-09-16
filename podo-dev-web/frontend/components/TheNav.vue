<template>
    <div id="nav" :class="$mq">
        <div id="logo">
            <router-link :to="{name : 'index'}">
                <img src="@/assets/podo-dev.svg" width="70px"/>
            </router-link>
        </div>

        <the-nav-desktop
            id="theNavDesktop"
            ref="theNavDesktop"
            :user="getUser"
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
    </div>

</template>

<script>
    import TheNavMobile from "./TheNavMobile"
    import TheNavDesktop from "./TheNavDesktop"
    import {mapActions, mapGetters} from 'vuex'

    export default {
        name: 'TheNav',
        components: {
            'the-nav-desktop': TheNavDesktop,
            'the-nav-mobile': TheNavMobile,
        },
        computed: {
            ...mapGetters({
                isAdmin: 'user/isAdmin',
                isLogin: 'user/isLogin',
                getUser: 'user/getUser',
            })
        },
        methods: {
            ...mapActions({
                logout: "user/logout"
            }),

            clickLogout() {
                this.toastConfirm("정말 로그아웃 하시겠습니까?", () => {
                    this.logout(() => {
                        this.$toast.show("로그아웃 되었습니다")
                        this.$router.push({name: 'index'})
                    })
                })
            },
            login() {
                this.$axios.$get('/api/login-enabled')
                    .then(res => {
                        const result = res.data
                        if (result) {
                            window.location.href = process.env.externalServerUrl + "/login/google"
                        } else {
                            this.$toast.show("다른 브라우저로 로그인해주세요")
                        }
                    })
                    .catch(err => {

                    })

            }
        },
    }

</script>

<style lang="scss" scoped>

    #nav {
        z-index: 9;
        display: flex;
        justify-content: space-between;
        align-items: center;
        top: 0;
        left: 0;
        right: 0;
        background: #FFFFFF;
        height: var(--nav-height);
        border-bottom: 1px solid #E7E7E7;
        padding: 0px 5%;
        position: sticky;


        #logo {
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

        &.tablet, &.mobile {
            #theNavDesktop {
                display: none;
            }

            #theNavMobile {
                display: block;
            }
        }
    }

</style>
