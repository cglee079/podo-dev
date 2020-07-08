export default ({ $axios, redirect, app, store }) => {
    $axios.defaults.timeout = 30000;

    $axios.interceptors.response.use(
        res => {
            return res;
        },

        err => {
            let response = err.response;
            
            if(!(response && response.status)){
                return Promise.reject(err);
            }
            
            if (response.status === 400) {
                const data = response.data;
                if (data.errors && app.$toast) {
                    data.errors.forEach(err => app.$toast.show(err.message));
                }
            }

            if (response.status === 401) {
                app.$toast.show("죄송합니다, 권한이 없습니다");
                store.dispatch("user/logout");
            }

            if (response.status === 403) {
                app.$toast.show("죄송합니다, 권한이 없습니다");
                store.dispatch("user/logout");
                redirect({ name: "blogs" });
            }

            if (response.status === 404) {
                app.$toast.show("죄송합니다, 찾을 수 없는 URL 입니다");
            }

            if (response.status === 500) {
                app.$toast.show("죄송합니다, 알 수 없는 에러가 발생했습니다");
            }

            return Promise.reject(err);
        }
    );
};
