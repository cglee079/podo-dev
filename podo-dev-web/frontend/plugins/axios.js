export default ({$axios, redirect, app, store}) => {

    $axios.defaults.timeout = 10000

    $axios.interceptors.response.use(
        res => {
            return res
        },

        err => {
            console.log(err)

            if (err.response && err.response.status && err.response.status === 401) {
                app.$toast.show("죄송합니다, 권한이 없습니다")

            }

            if (err.response && err.response.status && err.response.status === 403) {
                app.$toast.show("죄송합니다, 권한이 없습니다")
                store.dispatch('user/logout')
                redirect({name: 'index'})
            }

            if (err.response && err.response.status && err.response.status === 400) {
                const response = err.response.data
                if (response.errors && app.$toast) {
                    response.errors.forEach(err => app.$toast.show(err))
                }
            }

            return Promise.reject(err)
        }
    )
}
