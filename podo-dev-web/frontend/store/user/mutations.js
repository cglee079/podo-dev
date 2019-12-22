export default {
    doLogin(state, user) {
        state.isLogin = true;
        state.userinfo.username = user.username;
        state.userinfo.email = user.email;
        state.userinfo.profileImage = user.profileImage;
        state.userinfo.isAdmin = user.isAdmin;
    },
    doLogout(state) {
        state.isLogin = false;
        state.userinfo.username = "";
        state.userinfo.email = "";
        state.userinfo.profileImage = "";
        state.userinfo.isAdmin = false;
    }
};
