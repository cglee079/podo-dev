export default {
    methods: {
        async fetchWords(searchValue) {
            if (searchValue.length < 1) {
                return [];
            }

            try {
                const response = await this.$axios.$get("/api/blogs/words", {
                    params: {
                        searchValue: searchValue
                    }
                });

                return response.contents;

            } catch (e) {
                return [];
            }
        },

        search(result) {
            if (!result) {
                this.$toast.show("검색어를 정확히 입력해주세요");
                return;
            }

            this.$router.push({ name: "blogs", query: { search: result } });
        }
    }
};
