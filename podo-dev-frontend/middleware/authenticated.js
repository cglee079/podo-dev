
export default ({ store, redirect }) => {
    if (!store.getters["user/isAdmin"]) {
        redirect({ name: "blogs" });
    }
};
