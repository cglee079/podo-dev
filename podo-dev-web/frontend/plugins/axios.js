export default ({ $axios, redirect, app, store }) => {
    $axios.defaults.timeout = 30000;

    $axios.interceptors.response.use(
        res => {
            return res;
        },

        err => {
            let response = err.response;

            if (response && response.status && response.status === 500) {
                app.$toast.show("죄송합니다, 알 수 없는 에러가 발생했습니다");
            }

            if (response && response.status && response.status === 401) {
                app.$toast.show("죄송합니다, 권한이 없습니다");
            }

            if (response && response.status && response.status === 403) {
                app.$toast.show("죄송합니다, 권한이 없습니다");
                store.dispatch("user/logout");
                redirect({ name: "blogs" });
            }

            if (response && response.status && response.status === 400) {
                const response = response.data;
                if (response.errors && app.$toast) {
                    response.errors.forEach(err => app.$toast.show(err));
                }
            }

            return Promise.reject(err);
        }
    );
};
