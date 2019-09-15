export default {
    /**
     * 로그아웃
     * @param commit
     */
    logout({commit}, callback) {
        this.$axios
            .$post("/auth/logout")
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
                .$get("/auth/user")
                .then(res => {
                    //사용자 정보 확인
                    const user = res.data
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
