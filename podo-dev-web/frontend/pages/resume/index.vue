<template>
    <section id="resume" :class="$mq">
        <div id="name">
            <span>Lee Changoo => { Podo }</span>
        </div>

        <div id="info">
            <div>Junior, Server Developer</div>
            <div id="link">
                <a target="_blank" href="https://github.com/cglee079">
                    Git
                </a>
                <a target="_blank" href="https://www.linkedin.com/in/changoo-lee-205662180">
                    LinkedIn
                </a>
                <a href="mailto:cglee079@gmail.com">
                    Email
                </a>
            </div>
        </div>

        <div v-for="item in items"
             v-bind:key="item.id"
             class="item">

            <div class="head">{{item.head}}</div>
            <div class="content">
                <div v-for="content in item.contents"
                     v-bind:key="content.id"
                >
                    <div v-html="content"/>
                </div>
            </div>
        </div>

        <div id="experiences" class="item">
            <div class="head">EXPERIENCES</div>
            <div class="content">
                <p v-for="exp in experiences"
                   v-bind:key="exp.id"
                >
                    <a :href="exp.link" target="_blank">
                        <span class="date">
                            {{exp.experienceAt}}.
                        </span>
                        <span>{{exp.title}}</span>
                    </a>
                </p>
            </div>
        </div>

    </section>
</template>

<script>
    export default {
        name: "Resume",

        head() {
            return {
                title: process.env.name + " : resume",
                meta: [
                    {hid: "description", name: 'description', content: "podo's resume"},
                    {property: 'og:description', content: "podo's resume"},
                ],
                link: [
                    {rel: 'canonical', href: process.env.frontendUrl + "/resume"},
                ]
            }
        },

        data() {
            return {
                experiences: [],
                items: [],
            }
        },

        async asyncData({$axios, store}) {

            let baseUrl = process.env.externalServerUrl
            if (process.server) {
                baseUrl = process.env.internalServerUrl
            }

            const items = await $axios.$get(baseUrl + '/api/resumes')
            const experiences = await $axios.$get(baseUrl + '/api/resumes/experiences')

            return {
                items: items.data,
                experiences: experiences.data
            }
        },

    }
</script>

<style lang="scss" scoped>
    #resume {
        background: #FCFCFC;
        border: 1px solid #FAFAFA;
        border-radius: 15px;
        padding: 100px;

        color: #222222;
        cursor: default;

        &.mobile, &.tablet {
            background: unset;
            margin: 40px auto 0px auto;
            padding: 0px 20px;
            border: none;

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
            }
        }

        #info {
            margin-top: 20px;

            #link {
                a {
                    text-decoration: underline;
                    cursor: pointer;
                    margin-right: 5px;
                }
            }
        }

        /deep/ .item {
            margin-top: 80px;

            //Experiences Item
            &#experiences {
                .content {
                    p {
                        .date {
                            display: inline-block;
                            width: 110px;
                        }
                    }
                }
            }

            .head {
                border-left: 5px solid #d0c0d9;
                padding-left: 8px;
                font-size: 1.2rem;
                font-weight: bold;
            }


            .content {
                font-size: 0.95rem;
                margin-top: 20px;
                margin-left: 0.5rem;

                p {
                    margin-top: 7px;
                }

                a[href*=''] {
                    text-decoration: underline;
                }

                div {
                    margin-bottom: 40px;
                }

            }
        }
    }

</style>
