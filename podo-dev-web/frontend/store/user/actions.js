export default {
    login() {
        this.$axios.$get("/api/login/enabled").then(res => {
            const result = res.result;
            if (result) {
                window.location.href =
                    process.env.externalServerUrl + "/login/google";
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

    checkLogin({ commit }, { token, callback }) {
        if (token) {
            this.$axios.defaults.headers.common["Authorization"] =
                "Bearer " + token;

            this.$axios
                .$get("/api/user")
                .then(res => {
                    const user = res.result;
                    commit("doLogin", user);

                    this.$storage.setLocalStorage("token", token);

                    if (callback) {
                        callback();
                    }
                })
                .catch(() => {
                    this.$storage.removeLocalStorage("token");
                    delete this.$axios.defaults.headers.common["Authorization"]; //
                });
        }
    }
};
