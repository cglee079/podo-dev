import Vue from "vue";
import scrollBlackUnsetMixin from '../mixins/scroll-block-unset-mixin';

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

Vue.mixin(scrollBlackUnsetMixin);
Vue.use(plugin);
