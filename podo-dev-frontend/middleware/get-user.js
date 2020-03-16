export default ({ app, store }) => {
    console.log('get-user');

    const tokenInStorage = app.$storage.getLocalStorage("token");
    if (tokenInStorage) {
        store.dispatch("user/checkLogin", tokenInStorage);
    }
};
