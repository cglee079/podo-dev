export default {
    login(store, registrationId) {
        this.$axios.$get("/api/login/enabled").then(res => {
            const result = res.result;
            if (result) {
                window.location.href = process.env.EXTERNAL_SERVER_URL + `/oauth2/authorization/${registrationId}`;
            } else {
                this.$toast.show("다른 브라우저로 로그인해주세요");
            }
        });
    },

    logout({ commit }, callback) {
        this.$axios.$post("/api/logout").then(() => {
            delete this.$axios.defaults.headers.common["Authorization"];

            commit("doLogout");
            this.$storage.removeLocalStorage("token");

            if (callback) {
                callback();
            }
        });
    },

    async checkLogin({ commit }, token) {
        if (token) {
            this.$axios.defaults.headers.common["Authorization"] = "Bearer " + token;

            try {
                const response = await this.$axios.$get("/api/user");

                const user = response.result;
                commit("doLogin", user);

                this.$storage.setLocalStorage("token", token);

                return response;

            } catch {
                this.$storage.removeLocalStorage("token");
                delete this.$axios.defaults.headers.common["Authorization"];
            }
        }
    }
};
