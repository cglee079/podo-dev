<template>
    <div id="nav">
        <div>Podo.logo</div>
        <div id="navMenu">
            <span>이력</span>
            <span>블로그</span>
            <span>사진</span>
        </div>
        <div>
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
                countries : ['A', 'AA']
            }
        },
        methods: {
            search(input) {
                //TODO
                //get Value From Solr
                if (input.length < 1) { return [] }
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

<style scoped>
    #nav {
        display: flex;
        justify-content: space-between;
        align-items: center;
        height: 100px;
        border-bottom: 0.5px solid #E7E7E7;
        padding: 0px 100px;
    }

    #navMenu span {
        margin: 0px 20px;
    }

    .autocomplete {
        font-size: 1rem;
    }

</style>
