<template>
    <a id="btnScrollTop" ref="btnScrollTop" href="#" v-scroll-to="'body'" :class="$mq">
        TOP
    </a>
</template>

<script>
export default {
    name: "TheTopButton",
    methods: {
        onScroll() {
            if (process.server) {
                return;
            }

            if (window.scrollY > 100) {
                this.$classie.add(this.$refs.btnScrollTop, "on");
                return;
            }

            this.$classie.remove(this.$refs.btnScrollTop, "on");
        }
    },
    mounted() {
        window.addEventListener("scroll", this.onScroll);
    }
};
</script>

<style lang="scss" scoped>
#btnScrollTop {
    position: fixed;
    right: 1vw;
    bottom: 1.5rem;
    height: 1.5rem;
    margin-right: 10px;
    border-radius: 3rem;
    background: #000000;
    color: #fff;
    font-size: 0.7rem;
    font-weight: bold;
    padding: 1rem;
    z-index: 0;
    cursor: pointer;
    opacity: 0;
    transition: opacity 0.3s;
    display: flex;
    justify-content: center;
    align-items: center;

    &.desktop{
        right: calc(((100vw - var(--wild-max-width))/2) - 8vw);
    }

    &.on {
        opacity: 0.7;
    }
}
</style>
