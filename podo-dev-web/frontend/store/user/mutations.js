export default {
    doLogin(state, user) {
        state.isLogin = true;
        state.userinfo.oAuthType = user.oauthType;
        state.userinfo.username = user.username;
        state.userinfo.picture = user.picture;
        state.userinfo.isAdmin = user.isAdmin;
    },
    doLogout(state) {
        state.isLogin = false;
        state.userinfo.oAuthType = "";
        state.userinfo.username = "";
        state.userinfo.picture = "";
        state.userinfo.isAdmin = false;
    }
};
