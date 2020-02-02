export default ({ store, redirect }) => {
    const isAdmin = store.state.user.userinfo.isAdmin;

    if (!isAdmin) {
        redirect({ name: "blogs" });
    }
};
