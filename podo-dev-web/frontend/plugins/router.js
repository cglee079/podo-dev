export default ({ app, store: { state } }) => {
    app.router.beforeEach((to, from, next) => {
        const isAdmin = state.user.userinfo.isAdmin;

        if (!isAdmin) {
            if (to.name === "blogs-post" || to.name === "blogs-id-post") {
                next({ name: "blogs" });
                return;
            }
        }

        next();
    });
};
