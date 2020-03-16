export default {
    watch: {
        $route: {
            handler() {
                this.$scrollBlock.unblockAll();
            },
            immediate: true
        }
    }
};
