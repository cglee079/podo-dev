<template>
    <a id="btnScrollTop" ref="btnScrollTop" href="#" v-scroll-to="'body'">TOP</a>
</template>

<script>
    export default {
        name: "TheTopButton",
        created() {
            window.onscroll = () => {
                if(window.scrollY > 100){
                    this.$refs.btnScrollTop.classList.add("on");
                } else{
                    this.$refs.btnScrollTop.classList.remove("on");
                }
            }
        }
    }
</script>

<style lang="scss" scoped >
    #btnScrollTop {
        position: fixed;
        right: 1rem;
        bottom: 1rem;
        width: 1.5rem;
        height: 1.5rem;
        border-radius: 2rem;
        background: #000000;
        color: #FFF;
        font-size: 0.7rem;
        font-weight: bold;
        padding: 1rem;
        z-index: 0;
        cursor: pointer;
        opacity: 0;
        transition: opacity .3s;
        display: flex;
        justify-content: center;
        align-items: center;

        &.on {
            opacity: 0.7;
        }
    }
</style>
