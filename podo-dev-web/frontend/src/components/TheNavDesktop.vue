<template>
    <div>
        <div id="navMenu">
            <span>이력</span>
            <span><router-link :to="{name : 'BlogList',}">블로그</router-link></span>
            <span><router-link :to="{name : 'TagList'}">태그</router-link></span>
        </div>

        <div id="search">
            <the-nav-search
                :beautiful="true"
            />
        </div>
    </div>
</template>

<script>
    import TheNavSearch from "./TheNavSearch";

    export default {
        name: "TheNavDesktop",
        components: {
            TheNavSearch,
        },
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

    #search {
        transform: scale(.8);
    }

</style>
