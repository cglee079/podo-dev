export default {
    methods: {
        searchFacet(searchValue) {

            return new Promise(resolve => {
                if (searchValue.length < 1) {
                    return resolve([])
                }

                this.$axios.$get("/api/blogs/facets", {
                    params: {
                        searchValue: searchValue
                    }

                }).then(res => {
                    const facets = res.result
                    resolve(facets)
                }).catch(err => {
                    resolve([])
                })
            })

        },

        submit(result) {
            console.log(result)
            this.$emit('submit')

            if (!result) {
                this.$toast.show("검색어를 정확히 입력해주세요")
                return
            }

            this.$router.push({name: 'index', query: {search: result}})
        },
    }
}
