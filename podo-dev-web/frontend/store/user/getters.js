export default {
    getUser(state) {
        return state.user
    },

    isLogin(state) {
        return state.isLogin
    },

    isAdmin(state) {
        return state.user.isAdmin
    }
}
