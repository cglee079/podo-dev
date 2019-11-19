export default {
    login() {
        this.$axios.$get('/login/enabled')
            .then(res => {
                const result = res.result
                if (result) {
                    window.location.href = process.env.externalServerUrl + "/login/google"
                } else {
                    this.$toast.show("다른 브라우저로 로그인해주세요")
                }
            })
            .catch(err => {

            })
    },
    /**
     * 로그아웃
     * @param commit
     */
    logout({commit}, callback) {
        this.$axios
            .$post("/logout")
            .then(res => {
                delete this.$axios.defaults.headers.common['Authorization'] //

                commit('doLogout')
                this.$storage.removeLocalStorage("token")

                if (callback) {
                    callback()
                }
            })
            .catch(err => {
            })
    },

    /**
     * 로그인 성공 시, 사용자 정보 갱신
     * @param commit
     * @param token
     */
    checkLogin({commit}, {token, callback}) {

        if (token) {
            this.$axios.defaults.headers.common['Authorization'] = 'Bearer ' + token

            this.$axios
                .$get("/api/user")
                .then(res => {
                    //사용자 정보 확인
                    const user = res.result
                    commit('doLogin', user)

                    this.$storage.setLocalStorage("token", token)

                    if (callback) {
                        callback()
                    }

                })
                .catch(err => {
                    //사용자 정보 확인 실패
                    this.$storage.removeLocalStorage("token")
                    delete this.$axios.defaults.headers.common['Authorization'] //
                })

        }
    }
}
