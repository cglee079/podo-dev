<template>
    <div id="tags">
        <div v-for="(values, key) in tags"
             v-bind:key="key"
             class="wrap-tag"
        >
            <div class="chosung">
                {{key}}
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

            clickTagValue(value){
                this.$router.push({name : 'BlogList', query  : { tag : value}})
            }
        },

        created() {
            this.loadTagValues(0)
        }
    }
</script>

<style scoped>
    #tags div.wrap-tag {
        padding-bottom: 30px;
        border-bottom: 1px solid #F1F1;
        margin: 0px 20px 30px 20px
    }

    #tags div.wrap-tag div.chosung {
        font-size: 1.7rem;
        font-weight: bold;
        margin-bottom: 20px;
    }

    #tags div.wrap-tag div.values span.value{
        cursor: pointer;
        font-size: 1.2rem;
        display: inline-block;
        margin: 5px 10px 0px 5px;
    }


</style>
