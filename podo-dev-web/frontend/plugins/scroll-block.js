import Vue from "vue";

const scrollBlock = {
    flag: [],

    block(type) {
        this.flag.push(type);
        document.body.style.overflow = "hidden";
        document.body.style.touchAction = "none";
    },

    unblock(type) {
        this.flag.splice(this.flag.indexOf(type, 1));

        if (!this.flag.length) {
            document.body.style.overflow = "unset";
            document.body.style.touchAction = "unset";
        }
    },

    unblockAll() {
        this.flag = [];
    }
};

const plugin = {
    install(Vue) {
        Vue.prototype.$scrollBlock = scrollBlock;
    }
};

Vue.use(plugin);
