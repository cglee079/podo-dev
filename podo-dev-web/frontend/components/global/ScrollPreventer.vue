<template>
    <div></div>
</template>

<script>
import bus from "../../utils/bus";

export default {
    name: "ScrollPreventer",
    data() {
        return {
            flag: []
        };
    },
    methods: {
        prevent(type) {
            this.flag.push(type);
            document.body.style.overflow = "hidden";
            document.body.style.touchAction = "none";
        },

        unset(type) {
            this.flag.splice(this.flag.indexOf(type, 1));

            if (!this.flag.length) {
                document.body.style.overflow = "unset";
                document.body.style.touchAction = "unset";
            }
        },

        unsetAll() {
            this.flag = [];
        }
    },

    created() {
        bus.$on("scroll:prevent", this.prevent);
        bus.$on("scroll:unset", this.unset);
        bus.$on("scroll:unset-all", this.unsetAll);
    },

    beforeDestroy() {
        bus.$off("scroll:prevent");
        bus.$off("scroll:unset");
        bus.$off("scroll:unset-all");
    }
};
</script>

<style scoped></style>
