export default ({ app, store }) => {
    if (process.server) {
        return;
    }

    const tokenInStorage = app.$storage.getLocalStorage("token");
    if (tokenInStorage) {
        store.dispatch("user/checkLogin", tokenInStorage);
    }
};
