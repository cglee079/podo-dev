<template>
    <div id="resume" ref="resume" :class="$mq">
        <div id="name">
            <span>이찬구, Podo</span>
        </div>

        <div v-for="item in items"
             class="item">

            <div class="head">{{item.head}}</div>
            <div class="content">
                <div v-for="content in item.contents">
                    <div v-html="content"/>
                </div>
            </div>

        </div>
    </div>
</template>

<script>
    export default {
        name: "ResumeVue",
        data() {
            return {
                items: [],
            }
        },
        methods: {
            loadResumes() {
                this.$axios
                    .get('/api/resumes')
                    .then(res => {
                        res = res.data
                        this.items = res.data
                        this.$refs.resume.classList.add("on")

                    })
                    .catch(err => {
                        console.log(err)
                    })
            },
        },
        created() {
            this.loadResumes()
        },
    }
</script>

<style lang="scss" scoped>
    #resume {
        background: #FAFAFA;
        border-radius: 15px;
        padding: 100px;

        color: #222222;
        cursor: default;
        opacity: 0;
        margin-top: -50px;
        transition: margin 1.5s, opacity 1s;

        &.on{
            margin-top: 0px;
            opacity: 1;
        }

        &.mobile, &.tablet {
            background: unset;
            margin-top: 40px;
            padding: 0px 20px;

            /deep/ .item {
                margin-top: 50px;
            }
        }

        #name {
            transition: padding 0.5s;

            &:hover {
                padding-left: 20px;
            }

            span {
                font-size: 2rem;
                font-weight: bold;
                padding-bottom: 3px;
                /*border-bottom: 5px solid #afa3d9;*/
            }
        }

        /deep/ .item {
            margin-top: 80px;

            .head {
                border-left: 5px solid #d0c0d9;
                padding-left: 10px;
                font-size: 1.2rem;
                font-weight: bold;
            }

            .content {
                font-size: 0.95rem;
                margin-top: 20px;
                margin-left: 15px;

                p {
                    margin-top: 7px;
                }

                div {
                    margin-bottom: 40px;
                }

            }
        }
    }

</style>
