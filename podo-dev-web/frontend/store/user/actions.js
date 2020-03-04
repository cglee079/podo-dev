export default {
    async login(store, registrationId) {
        const isAllowed = await this.$axios.$get(`/api/login/enabled?oAuthType=${registrationId}`);
        if (isAllowed) {
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
            this.$axios.defaults.headers.common["Authorization"] = "Bearer " + token;

            try {
                const user = await this.$axios.$get("/api/user");
                commit("doLogin", user);

                this.$storage.setLocalStorage("token", token);

                return user;
            } catch {
                this.$storage.removeLocalStorage("token");
                delete this.$axios.defaults.headers.common["Authorization"];
            }
        }
    }
};
