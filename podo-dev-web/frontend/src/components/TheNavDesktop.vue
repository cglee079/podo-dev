<template>
    <div>
        <div id="navMenu">
            <span>이력</span>
            <span><router-link :to="{name : 'BlogList',}">블로그</router-link></span>
            <span><router-link :to="{name : 'TagList'}">태그</router-link></span>
        </div>

        <div id="search">
            <autocomplete
                    :search="search"
                    :autoSelect="true"
                    @submit="submit"/>
        </div>
    </div>
</template>

<script>
    import search from "@/mixins/search"

    export default {
        name: "TheNavDesktop",
        mixins : [search],
    }
</script>

<style lang="scss" scoped>
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

    #search /deep/{
        transform: scale(0.85);
    }

</style>
