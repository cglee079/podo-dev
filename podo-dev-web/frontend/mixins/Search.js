export default {
    methods: {
        searchFacet(searchValue) {
            return new Promise(resolve => {
                if (searchValue.length < 1) {
                    return resolve([]);
                }

                this.$axios
                    .$get("/api/blogs/words", {
                        params: {
                            searchValue: searchValue
                        }
                    })
                    .then(res => {
                        const facets = res.result.contents;
                        resolve(facets);
                    })
                    .catch(() => {
                        resolve([]);
                    });
            });
        },

        submit(result) {
            this.$emit("submit");

            if (!result) {
                this.$toast.show("검색어를 정확히 입력해주세요");
                return;
            }

            this.$router.push({name: "blogs", query: {search: result}});
        }
    }
};
