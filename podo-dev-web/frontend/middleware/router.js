export default ({ route, from, store, redirect }) => {
    const isAdmin = store.state.user.userinfo.isAdmin;

    if (!isAdmin) {
        if (route.name === "blogs-post" || route.name === "blogs-id-post") {
            redirect({ name: "blogs" });
        }
    }
};
