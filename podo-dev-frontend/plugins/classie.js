import Vue from "vue";
import classie from "classie";

const plugin = {
    install(Vue) {
        Vue.prototype.$classie = classie;
    }
};

Vue.use(plugin);
