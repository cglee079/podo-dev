<template>
    <div id="nav" :class="$mq">
        <div id="logo">
            <img src="@/assets/logo4.svg" width="70px"/>
        </div>

        <div id="navMenu">
            <span>이력</span>
            <span><router-link :to="{name : 'BlogList',}">블로그</router-link></span>
            <span><router-link :to="{name : 'TagList'}">태그</router-link></span>
        </div>

        <div id="search">
            <autocomplete
                    :search="search"
                    :get-result-value="getResultValue"
                    @submit="submit"/>
        </div>

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
                <img src="@/assets/logo4.svg"/>
            </div>


            <div class="mobile-nav-menu">
                <router-link :to="{name : 'BlogList'}">이력</router-link>
            </div>
            <div class="mobile-nav-menu">
                <router-link :to="{name : 'BlogList'}">블로그</router-link>
            </div>
            <div class="mobile-nav-menu">
                <router-link :to="{name : 'TagList'}">태그</router-link>
            </div>

            <div class="mobile-nav-menu"><a href="https://github.com/cglee079" target="_blank">Github</a></div>
            <div class="mobile-nav-menu"><a href="https://www.instagram.com/cglee079" target="_blank">Instagram</a>
            </div>
            <div class="mobile-nav-menu"><a>로그인</a></div>


        </div>
    </div>
</template>

<script>
    import Autocomplete from '@trevoreyre/autocomplete-vue'

    export default {
        name: 'TheNav',
        components: {
            Autocomplete
        },
        methods: {
            getResultValue(result) {
                return result
            },

            search(input) {

                return new Promise(resolve => {
                    if (input.length < 1) {
                        return resolve([])
                    }

                    this.$axios.get("/api/blogs/facets", {
                        params: {
                            value: input
                        }
                    }).then(res => {
                        res  = res.data
                        const facets = res.data
                        resolve(facets)
                    }).catch(err => {
                        resolve([])
                    })
                })

            },

            submit(result) {
                if (!result) {
                    this.$toasted.show("검색어를 정확히 입력해주세요")
                    return
                }

                this.$router.push({name: 'BlogList', query: {search: result}})
            },

            /**
             * 모바일 아이콘 클릭시
             */
            onMobileMenu() {
                this.$refs.bgMobileNavs.classList.add("on")
                this.$refs.mobileNavs.classList.add("on")

                document.body.style.overflow = "hidden"
                document.body.style.touchAction = "none"
            },

            offMobileMenu() {
                this.$refs.bgMobileNavs.classList.remove("on")
                this.$refs.mobileNavs.classList.remove("on")
                document.body.style.overflow = "unset"
                document.body.style.touchAction = "unset"
            },

            clickBtnMobileNavIcon() {
                this.onMobileMenu()

                this.$el.addEventListener('click', (e) => {
                    if (e.target.id === 'bgMobileNavs') {
                        this.offMobileMenu()
                    }
                })
            }

        },
        mounted() {
            const els = document.getElementsByClassName("mobile-nav-menu");
            for (let el of els) {
                el.onclick = () => {
                    this.offMobileMenu()
                }
            }
        }
    }

</script>

<style scoped lang="scss">
    $nav-height: 70px;

    #nav {
        display: flex;
        justify-content: space-between;
        align-items: center;
        top: 0;
        left: 0;
        right: 0;
        background: #FFFFFF;
        height: $nav-height;
        border-bottom: 1px solid #E7E7E7;
        padding: 0px 5%;

        #logo {
            margin-top: 5px;
        }

        #navMenu {
            position: absolute;
            left: 0;
            right: 0;
            text-align: center;

            span {
                margin: 0px 20px;
                cursor: pointer;
            }
        }

        #search {
            transform: scale(.8);
        }

        #btnMobileNavIcon {
            display: none;
        }

        #bgMobileNavs {
            display: none;
        }

        #mobileNavs {
            display: none;
        }

        &.tablet, &.mobile {
            #navMenu {
                display: none;
            }

            #search {
                display: none;
            }

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
                    height: $nav-height;

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
        }
    }

</style>
