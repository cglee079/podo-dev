export default ({ app }, inject) => {
    inject("baseUrl", () => {
        if (process.server) {
            return process.env.internalServerUrl;
        }

        return process.env.externalServerUrl;
    });
};
