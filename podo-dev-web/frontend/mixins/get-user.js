export default {
    watch: {
        $route: {
            handler() {
                if(!this.$storage){
                    return;
                }

                const tokenInStorage = this.$storage.getLocalStorage("token");
                if (tokenInStorage) {
                    store.dispatch("user/checkLogin", tokenInStorage);
                }
            },
            immediate: true
        }
    }
};
