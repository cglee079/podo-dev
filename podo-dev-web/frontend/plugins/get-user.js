export default ({app, query} ) => {
    if (query && query.accessToken) {
        const accessToken = query.accessToken;
        app.store.dispatch("user/checkLogin", accessToken).then(() => {
            app.$toast.show("로그인하였습니다");
            app.router.push({ name: "blogs" });
        });
    }

    //app.router. query, store.dispatch
    // 새로 고침 시
    const tokenInStorage = app.$storage.getLocalStorage("token");
    if (tokenInStorage) {
        app.store.dispatch("user/checkLogin", tokenInStorage);
    }
};
