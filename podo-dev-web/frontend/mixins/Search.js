export default {
    methods: {
        searchFacet(input) {

            return new Promise(resolve => {
                if (input.length < 1) {
                    return resolve([])
                }

                this.$axios.get("/api/blogs/facets", {
                    params: {
                        value: input
                    }

                }).then(res => {
                    res = res.data
                    const facets = res.data
                    resolve(facets)
                }).catch(err => {
                    console.log(err)
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
