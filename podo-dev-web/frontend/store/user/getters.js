export default {
    getUserinfo(state) {
        return state.userinfo
    },

    isLogin(state) {
        return state.isLogin
    },

    isAdmin(state) {
        return state.userinfo.isAdmin
    }
}
