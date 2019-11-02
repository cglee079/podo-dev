export default {
    doLogin(state, user) {
        state.isLogin = true
        state.userinfo.name = user.name
        state.userinfo.email = user.email
        state.userinfo.picture = user.picture
        state.userinfo.isAdmin = user.isAdmin
    },
    doLogout(state) {
        state.isLogin = false
        state.userinfo.name = ''
        state.userinfo.email = ''
        state.userinfo.picture = ''
        state.userinfo.isAdmin = false
    }
}
