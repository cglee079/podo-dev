export default ({app, store}) => {
    app.router.beforeEach((to, from, next) => {
        const isAdmin = store.getters['user/isAdmin']

        //권한 제어
        if (!isAdmin) {
            if (to.name === 'blogs-post' || to.name === 'blogs-seq-post') {
                next({name: 'index'})
                return
            }
        }

        next()
    })
}
