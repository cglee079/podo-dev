<template>
    <section id="resume" :class="$mq">
        <div id="name">
            <h1>Lee Changoo => { podo }</h1>
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

        <div v-for="resume in resumes" :key="resume.resumeKey" class="item">
            <div class="head">{{ resume.resumeTitle }}</div>
            <div class="content">
                <div v-for="(content, index) in resume.contents" :key="index">
                    <toast-custom-viewer :value="content" />
                </div>
            </div>
        </div>

        <div id="experiences" class="item">
            <div class="head">EXPERIENCES</div>
            <div class="content">
                <p v-for="experience in experiences" :key="experience.experienceId">
                    <a :href="experience.relateLink" target="_blank">
                        <span class="date">{{ experience.experienceAt }}.</span>
                        <span class="value">{{ experience.title }}</span>
                    </a>
                </p>
            </div>
        </div>
    </section>
</template>

<script>
import ToastCustomViewer from "../../components/global/ToastCustomViewer";

export default {
    name: "Resume",
    components: {
        ToastCustomViewer
    },
    head() {
        return {
            title: `${process.env.NAME} : resume`,
            meta: [
                { hid: "description", name: "description", content: "podo's resume" },
                { property: "og:description", content: "podo's resume" }
            ],
            link: [{ rel: "canonical", href: `${process.env.STATIC_URL}/resume` }]
        };
    },

    data() {
        return {
            resumes: [],
            experiences: []
        };
    },

    async asyncData({ $axios, app }) {
        const resumes = await $axios.$get(`${app.$baseUrl()}/api/resumes`);
        const experiences = await $axios.$get(`${app.$baseUrl()}/api/resumes/experiences`);

        return {
            resumes: resumes.contents,
            experiences: experiences.contents
        };
    }
};
</script>

<style lang="scss" scoped>
#resume {
    max-width: var(--max-width);
    margin: 0px auto;

    /*background: #fcfcfc;*/
    /*border: 1px solid #fafafa;*/
    border-radius: 15px;
    padding: 100px;

    color: #222222;
    cursor: default;

    &.mobile,
    &.tablet {
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

        h1 {
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

        &#experiences {
            .content p {
                .date {
                    display: inline-block;
                    width: 110px;
                }

                a[href] .value {
                    text-decoration: underline;
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

            div {
                margin-bottom: 40px;
            }
        }
    }
}
</style>
