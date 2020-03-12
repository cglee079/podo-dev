<template>
    <div id="spinner" ref="spinner">
        <div class="spinner">
            <div class="double-bounce1"></div>
            <div class="double-bounce2"></div>
        </div>
    </div>
</template>

<script>
import bus from "../../utils/bus";

export default {
    name: "Spinner",
    methods: {
        on(type) {
            if (process.server) {
                return;
            }

            this.$refs.spinner.classList.add("on");
            bus.$emit("scroll:prevent", type);
        },

        off(type) {
            if (process.server) {
                return;
            }

            this.$refs.spinner.classList.remove("on");
            bus.$emit("scroll:unset", type);
        }
    },

    created() {
        bus.$on("spinner:start", this.on);
        bus.$on("spinner:stop", this.off);
    },

    beforeDestroy() {
        bus.$off("spinner:start");
        bus.$off("spinner:stop");
    }
};
</script>

<style lang="scss" scoped>
#spinner {
    position: fixed;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    z-index: 5000;
    background: rgba(0, 0, 0, 0.05);
    display: none;

    &.on {
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .spinner {
        width: 40px;
        height: 40px;
        position: relative;

        .double-bounce1,
        .double-bounce2 {
            width: 100%;
            height: 100%;
            border-radius: 50%;
            background-color: #333;
            opacity: 0.6;
            position: absolute;
            top: 0;
            left: 0;
            -webkit-animation: sk-bounce 2s infinite ease-in-out;
            animation: sk-bounce 2s infinite ease-in-out;
        }

        .double-bounce2 {
            -webkit-animation-delay: -1s;
            animation-delay: -1s;
        }
    }
}

@keyframes sk-bounce {
    0%,
    100% {
        transform: scale(0);
        -webkit-transform: scale(0);
    }

    50% {
        transform: scale(1);
        -webkit-transform: scale(1);
    }
}

@-webkit-keyframes sk-bounce {
    0%,
    100% {
        -webkit-transform: scale(0);
    }
    50% {
        -webkit-transform: scale(1);
    }
}
</style>
