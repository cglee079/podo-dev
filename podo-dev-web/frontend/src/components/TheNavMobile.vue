<template>
    <div>
        <div id="btnMobileNavIcon" @click="clickBtnMobileNavIcon">
            <div id="wrapMobileNavIcon">
                <div id="mobileNavIcon">
                    <span></span>
                    <span></span>
                    <span></span>
                </div>
            </div>
        </div>

        <div id="bgMobileNavs" ref="bgMobileNavs"/>
        <div id="mobileNavs" ref="mobileNavs">

            <div id="mobileMenuHeader">
                <img src="@/assets/logo.svg"/>
            </div>

            <div class="mobile-nav-menu search">
                <a @click="clickSearch">검색</a>
            </div>

            <div class="mobile-nav-menu">
                <router-link :to="{name : 'Resume'}">이력</router-link>
            </div>

            <div class="mobile-nav-menu">
                <router-link :to="{name : 'BlogList'}">블로그</router-link>
            </div>
            <div class="mobile-nav-menu">
                <router-link :to="{name : 'TagList'}">태그</router-link>
            </div>

            <div v-if="isAdmin && isLogin"
                 class="mobile-nav-menu">
                <router-link :to="{name : 'BlogPost'}">글쓰기</router-link>
            </div>

            <div class="mobile-nav-menu"><a href="https://github.com/cglee079" target="_blank">Github</a></div>
            <div class="mobile-nav-menu"><a href="https://www.instagram.com/cglee079" target="_blank">Instagram</a>
            </div>
            <div v-if="!isLogin" @click="login" class="mobile-nav-menu"><a>로그인</a></div>
            <div v-if="isLogin" @click="logout" class="mobile-nav-menu"><a>로그아웃</a></div>
        </div>

        <div id="mobileSearch" ref="mobileSearch">
            <autocomplete
                    :search="search"
                    :autoSelect="true"
                    placeholder="검색어를 입력해주세요"
                    baseClass="autocomplete-mobile"
                    @submit="submitSearch"/>
        </div>

    </div>
</template>

<script>
    import search from "@/mixins/search"

    export default {
        name: "TheNavMobile",
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

            onExport() {
                this.$refs.bgMobileNavs.classList.add("on")
                document.body.style.overflow = "hidden"
                document.body.style.touchAction = "none"
            },

            offExport() {
                this.$refs.bgMobileNavs.classList.remove("on")
                document.body.style.overflow = "unset"
                document.body.style.touchAction = "unset"
            },

            clickSearch() {
                this.onSearch()
            },

            submitSearch(input) {
                this.offMobileMenu()
                this.offSearch()
                this.submit(input)
            },

            onSearch() {
                this.onExport()
                this.$refs.mobileSearch.classList.add("on")
            },

            offSearch() {
                this.offExport()
                this.$refs.mobileSearch.classList.remove("on")
            },

            onMobileMenu() {
                this.onExport();
                this.$refs.mobileNavs.classList.add("on")
            },

            offMobileMenu() {
                this.offExport();
                this.$refs.mobileNavs.classList.remove("on")
            },

            clickBtnMobileNavIcon() {
                this.onMobileMenu()

                this.$el.addEventListener('click', (e) => {
                    if (e.target.id === 'bgMobileNavs') {
                        this.offMobileMenu()
                        this.offSearch()
                    }
                })
            }
        },

        created() {
        },

        mounted() {
            const els = document.getElementsByClassName("mobile-nav-menu");
            for (let el of els) {
                if (!el.classList.contains("search")) {
                    el.onclick = () => {
                        this.offMobileMenu()
                    }
                }
            }
        }

    }
</script>

<style lang="scss">
    .autocomplete-mobile-input {
        height: var(--nav-height);
        border-bottom: 1px solid #E7E7E7;
        width: 100%;
        padding: 0px 10px;
    }

    ul.autocomplete-mobile-result-list {
        height: calc(100vh - var(--nav-height));
        overflow-x: hidden;
        overflow-y: auto;

        li.autocomplete-mobile-result {
            list-style-type: none;
            text-align: left;
            padding: 20px 10px;
            border-bottom: 1px solid #ECECEC;

            :hover, &[aria-selected=true] {
                background-color: rgba(0, 0, 0, .06)
            }
        }
    }
</style>

<style lang="scss" scoped >
    /** 모바일 아이콘 **/
    #btnMobileNavIcon {
        display: block;

        #wrapMobileNavIcon {
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 10px;

            #mobileNavIcon {
                width: 20px;
                height: 15px;
                transform: rotate(0deg);
                cursor: pointer;

                span {
                    display: block;
                    position: absolute;
                    height: 1.5px;
                    width: 100%;
                    background: #000;
                    border-radius: 9px;
                    opacity: 1;
                    left: 0;
                    transform: rotate(0deg);
                }

                span:nth-child(1) {
                    top: 0px;
                }

                span:nth-child(2) {
                    top: 6px;
                }

                span:nth-child(3) {
                    top: 12px;
                }
            }
        }
    }

    /** 모바일 메뉴 **/
    #bgMobileNavs {
        overflow: hidden;
        position: fixed;
        z-index: 1;
        left: 0;
        right: 0;
        bottom: 0;
        top: 0;
        background: #000000;
        opacity: 0.6;
        display: none;

        &.on {
            display: block;
        }
    }

    #mobileNavs {
        display: block;
        position: fixed;
        right: -50%;
        top: 0;
        bottom: 0;
        width: 50%;
        z-index: 2;
        text-align: right;
        background: #FFF;
        transition: right .3s cubic-bezier(0.215, 0.61, 0.355, 1);

        &.on {
            right: 0;
        }

        #mobileMenuHeader {
            background: #FFF;
            display: flex;
            align-items: center;
            height: 70px;

            img {
                width: 40%;
                max-height: 40%;
                margin: 0px auto;
            }
        }

        .mobile-nav-menu {
            padding: 0px 20px;
            background: #FFFFFF;
            color: #444444;

            a {
                padding: 15px 0px;
                display: block;
            }
        }

    }

    #mobileSearch {
        display: block;
        position: fixed;
        right: -80%;
        top: 0;
        bottom: 0;
        width: 80%;
        z-index: 3;
        text-align: right;
        background: #FFF;
        transition: right .3s cubic-bezier(0.215, 0.61, 0.355, 1);

        &.on {
            right: 0;
        }

    }
</style>
