<template>
    <div id="tags">
        <div v-for="value in tags"
             v-bind:key="value"
             class="tag">
            #{{value}}
        </div>
    </div>
</template>

<script>

    export default {
        name: 'BlogListTagValues',
        data() {
            return {
                tags: []
            }
        },
        methods: {
            loadTagValues() {

                this.$axios
                    .get('/api/tags/values')
                    .then(res => {
                        res = res.data
                        this.tags = res.data
                    })
                    .catch(err => {
                        console.log(err)
                    })
            },
        },

        created() {
            this.loadTagValues(0)
        }
    }
</script>

<style scoped>
    #tags div.tag {
        display: inline-block;
        margin: 5px 10px;
        cursor: pointer;
        opacity: 0.8;
    }

    #tags div.tag:hover {
        opacity: 1;
    }

</style>
