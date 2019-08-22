<template>
    <div id="nav" :class="$mq">
        <div id="logo">
            <img src="@/assets/logo4.svg" width="70px"/>
        </div>

        <div id="navMenu">
            <span>이력</span>
            <span><router-link :to="{name : 'BlogList',}">블로그</router-link></span>
            <span><router-link :to="{name : 'TagList'}">태그</router-link></span>
        </div>

        <div id="search">
            <autocomplete
                    :search="search"
                    :get-result-value="getResultValue"
                    :autoSelect="true"
                    @submit="submit"/>
        </div>

       <the-nav-mobile id="theNavMobile"/>


    </div>
</template>

<script>
    import Autocomplete from '@trevoreyre/autocomplete-vue'
    import TheNavMobile from "./TheNavMobile";

    export default {
        name: 'TheNav',
        components: {
            'the-nav-mobile' : TheNavMobile,
            'autocomplete' : Autocomplete
        },
        methods: {
            getResultValue(result) {
                return result
            },

            search(input) {

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
                if (!result) {
                    this.$toasted.show("검색어를 정확히 입력해주세요")
                    return
                }

                this.$router.push({name: 'BlogList', query: {search: result}})
            },

        }
    }

</script>

<style scoped lang="scss">
    $nav-height: 65px;

    #nav {
        z-index: 10000;
        display: flex;
        justify-content: space-between;
        align-items: center;
        top: 0;
        left: 0;
        right: 0;
        background: #FFFFFF;
        height: $nav-height;
        border-bottom: 1px solid #E7E7E7;
        padding: 0px 5%;
        position: sticky;


        #logo {
            margin-top: 5px;
        }

        #navMenu {
            position: absolute;
            left: 0;
            right: 0;
            text-align: center;

            span {
                margin: 0px 20px;
                cursor: pointer;
            }
        }

        #search {
            transform: scale(.8);
        }

        #theNavMobile{
            display: none;
        }

        &.tablet, &.mobile {
            #navMenu {
                display: none;
            }

            #search {
                display: none;
            }

            #theNavMobile{
                display: block;
            }

        }
    }

</style>
