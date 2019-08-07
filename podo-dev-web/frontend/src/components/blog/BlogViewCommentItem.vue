<template>
    <div class="comment">
        <div class="header">
            <div class="info">
                <a class="writer">{{comment.username}}</a>
                <a class="create-at">{{comment.createAt}}</a>
            </div>
            <div class="menu">
                <a>답글</a>
                <a>삭제</a>
            </div>
        </div>

        <div class="contents" v-html="comment.contents"/>
    </div>
</template>

<script>
    export default {
        name: "BlogViewCommentItem",
        props : {
            comment : Object
        }
    }
</script>

<style scoped>
    .comment {
        padding-top: 30px;
        padding-bottom: 20px;
        border-bottom: 1px solid #F1F1F1;
    }

    .header{
        display: flex;
        justify-content: space-between;
        margin-bottom: 10px;
    }

    .header .info .writer {
        color: #4c2b2b;
        font-weight: bold;
        margin-right: 5px;
    }

    .header .info .create-at{
        color: #777;
        font-size: 0.8rem;
    }

    .header .menu {
        font-size: 0.8rem;
        color: #444;
        text-align: right;
    }

    .header .menu a {
        margin-left: 7px;
        cursor: pointer;
    }

    .comment .contents {
        color: #444;
        font-size: 0.9rem;
        word-break: break-all;
        word-wrap: break-word;
    }

</style>
