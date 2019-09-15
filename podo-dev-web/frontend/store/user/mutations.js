export default {
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
}
