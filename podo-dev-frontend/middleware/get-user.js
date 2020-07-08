export default ({ app, store }) => {
    const tokenInStorage = app.$storage.getLocalStorage("token");
    if (tokenInStorage) {
        store.dispatch("user/checkLogin", tokenInStorage);
    }
};
