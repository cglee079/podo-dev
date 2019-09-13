import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        serverUrl: 'https://server.podo-dev.com',
        isLogin: false,
        user: {
            name: '',
            email: '',
            picture: '',
            isAdmin: false,
        },
    },
    getters: {
        getUser(state) {
            return state.user
        },
        getServerUrl(state) {
            return state.serverUrl
        },
        isLogin(state) {
            return state.isLogin
        },
        isAdmin(state) {
            return state.user.isAdmin
        }
    },
    mutations: {
        doLogin(state, user) {
            state.isLogin = true
            state.user.name = user.name
            state.user.email = user.email
            state.user.picture = user.picture
            state.user.isAdmin = user.isAdmin
        },
        doLogout(state) {
            state.isLogin = false
            state.user.name = ''
            state.user.email = ''
            state.user.picture = ''
            state.user.isAdmin = false
        }
    },
    actions: {
        /**
         * 로그아웃
         * @param commit
         */
        logout({commit}, callback) {
            axios
                .post("/auth/logout")
                .then(res => {
                    delete axios.defaults.headers.common['Authorization'] //
                    commit('doLogout')
                    sessionStorage.removeItem("token")
                    if (callback) {
                        callback()
                    }
                })
                .catch(err => {
                        console.log(err)
                    }
                )
        },

        /**
         * 로그인 성공 시, 사용자 정보 갱신
         * @param commit
         * @param token
         */
        checkLogin({commit}, {token, callback}) {

            if (token) {
                axios.defaults.headers.common['Authorization'] = 'Bearer ' + token

                axios
                    .get("/auth/user")
                    .then(res => {
                        //사용자 정보 확인
                        res = res.data
                        const user = res.data
                        commit('doLogin', user)
                        sessionStorage.setItem("token", token)

                        if(callback){
                            callback()
                        }

                    })
                    .catch(err => {
                        //사용자 정보 확인 실패
                        sessionStorage.removeItem("token")
                        delete axios.defaults.headers.common['Authorization'] //
                    })

            }

        }
    }
})


