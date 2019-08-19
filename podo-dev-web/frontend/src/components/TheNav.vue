<template>
    <div id="nav" :class="$mq">
        <div id="logo" :class="$mq">
            <img src="@/assets/logo4.svg" width="70px"/>
        </div>

        <div id="navMenu">
            <span>이력</span>
            <span><router-link :to="{name : 'BlogList',}">블로그</router-link></span>
            <span><router-link :to="{name : 'TagList'}">태그</router-link></span>
        </div>

        <div id="search" :class="$mq">
            <autocomplete :search="search" @submit="submit"></autocomplete>
        </div>
    </div>
</template>

<script>
    import Autocomplete from '@trevoreyre/autocomplete-vue'

    export default {
        name: 'TheNav',
        components: {
            Autocomplete
        },
        data() {
            return {
                countries: ['A', 'AA']
            }
        },
        methods: {
            search(input) {
                //TODO
                //get Value From Solr
                if (input.length < 1) {
                    return []
                }
                return this.countries.filter(country => {
                    return country.toLowerCase()
                        .startsWith(input.toLowerCase())
                })
            },
            submit(result) {
                console.log(result)
            }
        }

    }

</script>

<style scoped lang="scss">
    #nav {
        position: sticky;
        display: flex;
        justify-content: space-between;
        align-items: center;
        height: 100px;
        border-bottom: 1px solid #E7E7E7;
        padding: 0px 100px;

        &.mobile {
            height: 80px;
        }
    }

    #navMenu {
        position: absolute;
        left: 0;
        right: 0;
        text-align: center;
    }

    #navMenu span {
        margin: 0px 20px;
        cursor: pointer;
    }

    #logo {
        &.tablet, &.mobile {
            display: none;
        }
    }

    #search {
        transform: scale(.8);

        &.tablet, &.mobile {
            display: none;
        }
    }

</style>
