<template>
    <div id="footer">
        <div class="contacts">
            <span class="contact">
                <a target="_blank" href="https://github.com/cglee079">
                    <img :src="images.iconGit"/>
                </a>
            </span>
            <span class="border"></span>
            <span class="contact">
                <a target="_blank" href="https://www.instagram.com/cglee079">
                    <img :src="images.iconInstagram"/>
                </a>
            </span>
            <span class="border"></span>
            <span class="contact">
                <a href="mailto:cglee079@gmail.com">
                    <img :src="images.iconEmail"/>
                </a>
            </span>
        </div>

        <div class="copyright">
            <div>Copyrightⓒ2018 Podo</div>
        </div>
    </div>
</template>

<script>

    export default {
        name: 'TheFooter',
        data(){
            return {
                images :{
                    iconGit : require('../assets/images/icon-git.png'),
                    iconEmail : require('../assets/images/icon-email.png'),
                    iconInstagram : require('../assets/images/icon-instagram.png')
                }
            }
        }
    }

</script>

<style scoped>

    #footer {
        left: 0;
        right: 0;
    }

    .contacts {
        width: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
        padding: 20px 0;
        background: #FFFFFF;
        border-top: 1px solid #ECECEC;
    }

    .contacts .contact {
        padding: 10px 20px;
        cursor: pointer;
    }

    .contacts .contact a img{
        height: 1.1rem;
        opacity: 0.8;
    }

    .contacts .border {
        width: 3px;
        height: 3px;
        border-radius: 3px;
        background: #777;
    }

    .copyright {
        padding: 30px 1.5rem;
        text-align: center;
        font-size: 0.8rem;
        background: #111;
        color: #FFF;
    }
</style>
