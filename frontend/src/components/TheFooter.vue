<template>
    <div id="footer">
        <div class="info-contact">
            <div class="btn-contact btn-git"><a target="_blank" href="https://github.com/cglee079">GIT</a></div>
            <div class="border-circle"></div>
            <div class="btn-contact btn-insta"><a target="_blank" href="https://www.instagram.com/cglee079">INSTAGRAM</a></div>
            <div class="border-circle"></div>
            <div class="btn-contact btn-email"><a href="mailto:cglee079@gmail.com">E-MAIL</a></div>
        </div>

        <div class="info-copyright">
            <div>Copyrightⓒ2018 CHANGOO</div>
            <div></div>
        </div>
    </div>
</template>

<script>

    export default {
        name: 'TheFooter',
    }

</script>

<style scoped>

    #footer {
        left: 0;
        right: 0;
    }

    .info-contact {
        width: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
        font-size: 0.8rem;
        padding: 15px 0;
        background: #FFF;
        border-top: 0.5px solid #ececec;
    }

    .info-contact .btn-contact{
        padding: 10px;
        cursor: pointer;
    }


    .info-contact .border-circle{
        width : 3px;
        height : 3px;
        border-radius: 3px;
        background: #777;
    }

    .info-copyright{
        padding: 30px 1.5rem;
        text-align: center;
        font-size: 0.8rem;
        background: #111;
        color : #FFF;
    }

</style>
