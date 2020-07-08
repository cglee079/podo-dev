import bus from "../../utils/bus";
import jwt from "jsonwebtoken";

export default {
    async login(store, registrationId) {
        const isAllowed = await this.$axios.$get(`/api/login/enabled?oAuthType=${registrationId}`);
        if (isAllowed) {
            bus.$emit("spinner:start", "post-blog-comment");
            window.location.href =
                process.env.EXTERNAL_SERVER_URL + `/oauth2/authorization/${registrationId}`;
        } else {
            this.$toast.show("다른 아이디로 로그인해주세요");
        }
    },

    async logout({ commit }, callback) {
        await this.$axios.$post("/api/logout");

        delete this.$axios.defaults.headers.common["Authorization"];

        commit("doLogout");
        this.$storage.removeLocalStorage("token");

        if (callback) {
            callback();
        }
    },

    async checkLogin({ commit }, token) {
        if (token) {
            const tokenValue = jwt.decode(token);
            const exp = tokenValue.exp;

            // 만료된 TOKEN 인가.
            if (Date.now() >= exp * 1000) {
                logout.call(this);
                return;
            }

            this.$axios.defaults.headers.common["Authorization"] = "Bearer " + token;
            const user = JSON.parse(tokenValue.sub);
            this.$storage.setLocalStorage("token", token);
            commit("doLogin", user);

            return user;
        }

        function logout() {
            this.$storage.removeLocalStorage("token");
            commit("doLogout");
            delete this.$axios.defaults.headers.common["Authorization"];
        }
    }
};
