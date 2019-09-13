<template>
    <div id="tags">
        <div id="all">
            <span v-for="(values, key) in tags"
                  v-bind:key="key"
                  class="wrap-tag"
            >
                    <span v-for="(value, index) in values"
                          v-bind:key="index"
                          @click="clickTagValue(value)"
                          class="value">

                        #{{value}}
                    </span>
            </span>
        </div>

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
                    })
                    .catch(err => {
                        console.error(err)
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

<style lang="scss" scoped >
    #tags {
        margin-top: 50px;

        div#all{
            margin: 60px 20px;
            text-align: center;
            > span > span{
                cursor: pointer;
                font-size: 1.2rem;
                display: inline-block;
                margin: 10px;
                color: #555555;

                &:hover{
                    color: #000000;
                }
            }
        }

        div.wrap-tag {

            padding-bottom: 30px;
            border-bottom: 1px solid #ecf0f5;
            margin: 100px 20px 50px 20px;

            div.chosung {
                margin-left: 5px;
                margin-bottom: 15px;

                span {
                    font-size: 1.7rem;
                    font-weight: bold;
                }
            }

            div.values span.value {
                cursor: pointer;
                font-size: 1.2rem;
                display: inline-block;
                margin: 5px 10px 0px 5px;
                color: #ec5621;
            }
        }
    }


</style>
