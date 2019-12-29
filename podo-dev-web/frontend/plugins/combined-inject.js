export default ({ app }, inject) => {
    inject("baseUrl", () => {
        if (process.server) {
            return process.env.INTERNAL_SERVER_URL;
        }

        return process.env.EXTERNAL_SERVER_URL;
    });

};
