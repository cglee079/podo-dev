<template>
    <footer id="footer">
        <div class="contacts">
            <span class="contact">
                <a target="_blank" href="https://github.com/cglee079">
                    <img :src="images.iconGit" alt="git" />
                </a>
            </span>
            <span class="border"></span>
            <span class="contact">
                <a target="_blank" href="https://www.linkedin.com/in/changoo-lee-205662180">
                    <img :src="images.iconLinkedin" alt="linkedIn" />
                </a>
            </span>
            <span class="border"></span>
            <span class="contact">
                <a href="mailto:cglee079@gmail.com">
                    <img :src="images.iconEmail" alt="email" />
                </a>
            </span>
        </div>

        <div id="copyright">
            <div>
                <a>Copyrightⓒ2019 Podo</a>
                ,
                <nuxt-link :to="{ name: 'license' }">License</nuxt-link>
            </div>
        </div>
    </footer>
</template>

<script>
export default {
    name: "TheFooter",
    data() {
        return {
            images: {
                iconGit: require("../assets/icons/icon-git.png"),
                iconEmail: require("../assets/icons/icon-email.png"),
                iconLinkedin: require("../assets/icons/icon-linkedin.png")
            }
        };
    }
};
</script>

<style lang="scss" scoped>
#footer {
    left: 0;
    right: 0;

    .contacts {
        width: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
        padding: 20px 0;
        background: #ffffff;
        border-top: 1px solid #ececec;

        .contact {
            padding: 10px 20px;
            cursor: pointer;

            a img {
                height: 1.1rem;
                opacity: 0.8;
            }
        }

        .border {
            width: 3px;
            height: 3px;
            border-radius: 3px;
            background: #777;
        }
    }

    #copyright {
        padding: 30px 1.5rem;
        text-align: center;
        font-size: 0.8rem;
        background: #111;
        color: #fff;
    }
}
</style>
