<template>
    <div id="wrapExport" ref="wrapExport" :class="$mq">
        <div id="bgExport"/>
        <div id="exportMenus">
            <div id="export">
                <div id="btnExportExit" @click="offExport()">
                    <span></span>
                    <span></span>
                </div>

                <div id="exportThumbnail">
                    <div id="bgThumbnail"/>
                    <div id="thumbnail">
                        <img v-if="blog.thumbnail" :src="blog.thumbnail"/>
                        <img v-else src="@/assets/podo-dev.svg" width="50%"/>
                    </div>
                </div>

                <div id="menus">
                    <div class="menu" @click="clickCopyUrl()">
                        <img src="@/assets/btns/btn-export-url.svg" width="70px"/>
                        <span>URL 복사</span>
                    </div>

                    <div class="menu" @click="clickExportKakao()">
                        <img src="@/assets/btns/btn-export-kakaotalk.svg" width="70px"/>
                        <span>카카오톡 공유하기</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>

    export default {
        name: "BlogViewExport",
        props: {
            blog: Object,
        },
        watch: {},
        methods: {
            onExport() {
                this.$refs.wrapExport.classList.add("on")
                document.body.style.overflow = "hidden"
                document.body.style.touchAction = "none"
            },

            offExport() {
                this.$refs.wrapExport.classList.remove("on")
                document.body.style.overflow = "unset"
                document.body.style.touchAction = "unset"
            },

            clickCopyUrl() {
                const dummy = document.createElement('input')
                const text = window.location.href;

                document.body.appendChild(dummy);
                dummy.value = text;
                dummy.select();
                document.execCommand('copy');

                document.body.removeChild(dummy);

                this.$toast.show("URL이 복사되었습니다")

                this.offExport()
            },

            clickExportKakao() {
                const url = window.location.href
                const path = window.location.pathname
                const title = this.blog.title
                let thumbnail = ''

                if (this.blog.thumbnail) {
                    thumbnail = this.blog.thumbnail
                }

                // 카카오링크 버튼을 생성
                Kakao.Link.sendDefault({
                    objectType: 'feed',
                    content: {
                        title: title,
                        imageUrl: thumbnail,
                        imageWidth: 300,
                        imageHeight: 200,
                        link: {
                            mobileWebUrl: url,
                            webUrl: url
                        }
                    },

                    social: {
                        commentCount: this.blog.commentCnt,
                        viewCount: this.blog.hitCnt
                    },
                    buttons: [
                        {
                            title: '웹에서 보기',
                            link: {
                                mobileWebUrl: url,
                                webUrl: url
                            }
                        },
                        {
                            title: "앱에서 보기",
                            link: {
                                androidExecParams: "path=" + path,
                            }
                        }
                    ]
                })

                this.offExport()
            }


        },

        mounted() {

            this.$el.addEventListener('click', (e) => {
                if (e.target.id === 'exportMenus') {
                    this.offExport()
                }
            })


            //중복로딩 방지
            const kakaoJs = '//developers.kakao.com/sdk/js/kakao.min.js'
            const script = document.querySelector('script[src="' + kakaoJs + '"]')
            if(script){
                return
            }

            const recaptchaScript = document.createElement('script')
            recaptchaScript.setAttribute('src', kakaoJs)
            document.head.appendChild(recaptchaScript)

            recaptchaScript.onload = () => {
                const appKey = 'ff0d9af3010f7f6ab00c1efe66299e31'
                Kakao.init(appKey)
            }
        }
    }
</script>

<style lang="scss" scoped>
    #wrapExport {
        display: none;

        &.on {
            display: unset;
        }

        &.mobile {
            #exportMenus {
                #export {
                    width: 100%;
                    height: 100%;
                    max-height: 100%;
                    border-radius: unset;
                }
            }
        }

        #bgExport {
            z-index: 10;
            position: fixed;
            top: 0px;
            left: 0;
            right: 0;
            bottom: 0;
            background: #000000;
            opacity: 0.8;
        }

        #exportMenus {
            z-index: 11;
            position: fixed;
            top: 0px;
            left: 0;
            right: 0;
            bottom: 0;

            justify-content: center;
            align-items: center;
            flex-flow: column;

            display: flex;

            #export {
                width: 350px;
                height: 500px;
                max-height: 60%;
                position: relative;
                border-radius: 3px;
                overflow: hidden;

                display: flex;
                flex-flow: column;


                #btnExportExit {
                    z-index: 11;
                    position: absolute;
                    right: 0;
                    top: 0;
                    margin: 7px;
                    padding: 13px;
                    cursor: pointer;

                    span {
                        display: block;
                        position: absolute;
                        height: 1.5px;
                        width: 100%;
                        background: #000000;
                        border-radius: 9px;
                        left: 0;
                    }

                    span:nth-child(1) {
                        -webkit-transform: rotate(45deg);
                        -moz-transform: rotate(45deg);
                        -o-transform: rotate(45deg);
                        transform: rotate(45deg);
                    }

                    span:nth-child(2) {
                        -webkit-transform: rotate(-45deg);
                        -moz-transform: rotate(-45deg);
                        -o-transform: rotate(-45deg);
                        transform: rotate(-45deg);
                    }
                }


                #exportThumbnail {
                    width: 100%;
                    height: 50%;
                    position: relative;

                    #bgThumbnail {
                        position: absolute;
                        left: 0;
                        right: 0;
                        top: 0;
                        bottom: 0;
                        background: #FFFFFF;
                    }

                    #thumbnail {
                        position: absolute;
                        left: 0;
                        right: 0;
                        top: 0;
                        bottom: 0;
                        display: flex;
                        align-items: center;
                        justify-content: center;

                        img {
                            border-radius:  5px;
                            max-height: 70%;
                            max-width: 80%;
                        }
                    }

                }

                #menus {
                    flex: 1;
                    background: #FFFFFF;
                    cursor: pointer;
                    border-top: 1px solid #E7E7E7;
                    color: #444444;

                    .menu {
                        display: flex;
                        align-items: center;
                        padding: 8px 20px;
                        font-weight: bold;
                        cursor: pointer;
                        border-bottom: 1px solid #E7E7E7;

                        img {
                            width: 30px;
                            margin: 0.7rem 1rem;
                        }
                    }
                }
            }
        }
    }


</style>
