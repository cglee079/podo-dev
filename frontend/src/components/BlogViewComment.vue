<template>
    <div id="wrapComment">
        <div id="count">댓글 (<a class="comment-cnt">1</a>)</div>

        <div id="comments">
            <comment-item></comment-item>
        </div>

        <div id="write">
            <textarea id="contents" placeholder="소중한 댓글을 입력해주세요."/>
            <div id="sub">
                <div id="user">
                    <div id="username">
                        <input type="text" placeholder="USERNAME">
                    </div>
                    <div id="password">
                        <input type="password" placeholder="PASSWORD">
                    </div>
                </div>
                <div id="submit">등록</div>
            </div>
        </div>

    </div>
</template>

<script>
    import BlogViewCommentItem from '@/components/BlogViewCommentItem'

    export default {
        name: "BlogViewComment",
        components: {
            'comment-item': BlogViewCommentItem
        }
    }
</script>

<style scoped>

    #wrapComment {
        margin-top: 100px;
    }

    #count {
        font-size: 1.25rem;
        font-weight: bold;
    }

    /*** Comment Write ****/
    #write {
        margin-top: 20px;
    }

    #write #contents {
        width: 100%;
        box-sizing: border-box;
        height: 7rem;
        padding: 10px;
        resize: none;
        overflow: hidden;
        border: 0.7px solid #E1E1E1;
        border-radius: 3px;
    }

    #write #sub{
        display: flex;
        justify-content: space-between;
    }

    #write #sub #user {
        display: flex;
        justify-content: space-between;
        margin-top: 5px;
        height: 2rem;
    }

    #write #sub #user div {
        margin-right: 3px;
    }

    #write #sub #user input {
        width: 7rem;
        height: 2rem;
        font-size: 0.8rem;
        padding-left: 5px;
        border: 1px solid #E1E1E1;
        border-radius: 3px;
    }

    #write #sub #submit{
        display: flex;
        justify-content: center;
        align-items: center;
        height: 2rem;
        font-size: 0.8rem;
        padding: 0px 20px;
        color: #FFFFFF;
        border-radius: 3px;
        background: #222222;
        cursor: pointer;

    }


    /*!** Comment Modify ***!*/
    /*.comment-modify {*/
    /*    display: flex;*/
    /*    flex-flow: row wrap;*/
    /*    height: 50px;*/
    /*}*/

    /*.comment-modify .comment-write-contents {*/
    /*    flex: 1;*/
    /*    height: 100%;*/
    /*    resize: none;*/
    /*    overflow: hidden;*/
    /*    border: 0.7px solid #DDD;*/
    /*}*/

    /*.comment-modify .comment-write-submit {*/
    /*    color: #FFF;*/
    /*    height: 100%;*/
    /*    padding: 0 10px;*/
    /*    background: #222;*/
    /*    cursor: pointer;*/
    /*    font-size: 0.75rem;*/
    /*}*/

    /*.btn-modify.open {*/
    /*    opacity: 1;*/
    /*}*/


    /*!* Comment Reply *!*/
    /*.comment-reply {*/
    /*    display: flex;*/
    /*    width: 80%;*/
    /*    margin: 5px auto;*/
    /*    height: 20px;*/
    /*    font-size: 0.75rem;*/
    /*}*/

    /*.comment-reply-content {*/
    /*    flex: 1;*/
    /*    height: 100%;*/
    /*    overflow-y: auto;*/
    /*    overflow-x: hidden;*/
    /*    resize: none;*/
    /*}*/

    /*.comment-reply-submit {*/
    /*    color: #FFF;*/
    /*    padding: 0 10px;*/
    /*    height: 100%;*/
    /*    background: #222;*/
    /*    cursor: pointer;*/
    /*    font-size: 0.75rem;*/
    /*}*/

    /*.btn-reply.open {*/
    /*    opacity: 1;*/
    /*}*/
</style>
