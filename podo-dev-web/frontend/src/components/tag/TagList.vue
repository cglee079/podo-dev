<template>
    <div id="tags">
        <div v-for="(values, key) in tags"
             v-bind:key="key"
             class="wrap-tag"
        >
            <div class="chosung">
                <span>{{key}}</span>
            </div>

            <div class="values">
                <span v-for="(value, index) in values"
                      v-bind:key="index"
                      @click="clickTagValue(value)"
                      class="value">

                    #{{value}}
                </span>
            </div>

        </div>
    </div>
</template>

<script>

    export default {
        name: 'TagList',
        data() {
            return {
                tags: {}
            }
        },
        methods: {
            loadTagValues() {

                this.$axios
                    .get('/api/tags/values')
                    .then(res => {
                        res = res.data
                        this.tags = res.data
                        console.log(res.data)
                    })
                    .catch(err => {
                        console.log(err)
                    })
            },

            clickTagValue(value) {
                this.$router.push({name: 'BlogList', query: {tag: value}})
            }
        },

        created() {
            this.loadTagValues(0)
        }
    }
</script>

<style scoped lang="scss">
    #tags {

        div.wrap-tag {
            padding-bottom: 30px;
            margin: 0px 20px 50px 20px;

            div.chosung {
                margin-bottom: 20px;

                span {
                    font-size: 1.7rem;
                    font-weight: bold;
                    border-left: 5px solid #d4baba;
                    padding-left: 10px;
                }
            }

            div.values span.value {
                cursor: pointer;
                font-size: 1.2rem;
                display: inline-block;
                margin: 5px 10px 0px 5px;
            }
        }
    }


</style>
