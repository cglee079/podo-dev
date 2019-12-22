<template>
    <div>
        <div id="navMenus">
            <span><router-link :to="{name : 'resume'}">이력</router-link></span>
            <span><router-link :to="{name : 'index'}">블로그</router-link></span>
            <span><router-link :to="{name : 'log'}">로그</router-link></span>
            <span v-if="userinfo.isAdmin && isLogin"><router-link :to="{name : 'blogs-post'}">글쓰기</router-link></span>
        </div>

        <div id="search">
            <autocomplete
                    :search="searchFacet"
                    :autoSelect="true"
                    @submit="submit"/>
        </div>

        <div id="loginMenus">
            <span v-if="!isLogin" @click="login">
                <span v-tooltip="{content:'Login By Google', class:'tooltip'}">
                <img src="@/assets/btns/btn-login.svg" id="loginIcon" alt="btn-login"/>
                </span>
            </span>

            <span v-if="isLogin" @click="logout">
                <span v-tooltip="{content:'Logout', class:'tooltip'}">
                <img :src="userinfo.profileImage" id="userIcon"/>
                </span>
            </span>
        </div>
    </div>
</template>

<script>
    export default {
        name: "TheNavDesktop",
        props: {
            userinfo: Object,
            isLogin: Boolean,
        },
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
        text-align: center;

        span {
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

        span {
            cursor: pointer;
            font-size: 0.9rem;
        }

        #loginIcon {
            margin-top: 5px;
            height: 37px;
            border-radius: 20px;
            opacity: 0.9;
        }

        #userIcon {
            margin-top: 5px;
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
