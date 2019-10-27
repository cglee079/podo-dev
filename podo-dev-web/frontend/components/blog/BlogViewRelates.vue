<template>
    <div id="wrapRelated" :class="$mq">
        <div id="head">
            '{{blogTitle}}' 관련된 다른글
        </div>

        <div id="related-items">
            <router-link
                v-for="relate in relates"
                :key="relate.id"
                :to="{name : 'blogs-id', params : {id : relate.id}}"
                :class="relate.id === blogId ? 'current' : ''"
                class="related-item"
            >
                <span class="title">
                    {{relate.title}} ({{relate.commentCount}})
                </span>
                <span class="date">
                    {{relate.createAt}}
                </span>
            </router-link>
        </div>
    </div>
</template>

<script>
    export default {
        name: "BlogViewRelates",
        components: {},
        props: {
            blogId: Number,
            blogTitle: String,
            relates: Array,
        },
        data() {
            return {}
        },
        computed: {}
    }
</script>

<style lang="scss" scoped>

    #wrapRelated {
        margin-top: 50px;
        color: #333333;

        #head {
            font-size: 0.85rem;
            padding: 3px 5px;
            border-bottom: 1px solid #E7E7E7;
            margin-bottom: 3px;
            font-weight: bold;
        }

        .related-item {
            display: flex;
            justify-content: space-between;

            color: #777777;
            font-size: 0.85rem;
            padding-left: 3px;
            margin: 7px 0px;

            &.current {
                color: #222222;
                text-decoration: underline;
            }

            .title {
                flex: 1;
                white-space: nowrap;
                overflow: hidden;
                margin-right: 10px;
                text-overflow: ellipsis;
            }
        }
    }

    #wrapRelated.mobile,
    #wrapRelated.tablet {
        padding-left: 5%;
        padding-right: 5%;
    }

</style>
