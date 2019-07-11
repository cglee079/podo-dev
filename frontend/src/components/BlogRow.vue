<template>
    <div class="blog-row">
        <img class="thumbnail"/>
        <div class="content">
            <div class="title">제목</div>
            <div class="desc">
                Header란? 본문 이외에 추가적인 정보를 교환할 수 있도록, HTTP 메세지 선두에 삽입되는 요소. 요청 Header Accept : 클라이언트가 허용하는 문서 타입 Accept-Encoding : 클라이언트가 인식 할 수 있는 인코딩 User-Agent : 클라이언트의 이름과 버전 Host : 서버의 도메인 (포트 포함) Refer : 링크를 제공한 페이지 정보 Cookie : 쿠키 응답 Header Server : 웹 서버의 정보 Content-type : 본문의 타입 Content-length : 본문의 길이 Content-Encoding : 본문의 인코딩
            </div>
            <div class="info">
                <div class="tags">
                    <span class="tag">#Network</span>
                    <span class="tag">#Test</span>
                    <span class="tag">#DFD</span>
                </div>
                <div class="subinfo">
                    <span>2019-03-25</span>
                    <span>댓글 0</span>
                </div>
            </div>

        </div>
    </div>
</template>

<script>
    export default {
        name: "BlogRow"
    }
</script>

<style scoped>
    .blog-row{
        display: flex;
        cursor: pointer;
        padding-bottom: 20px;
        margin-bottom: 20px;
        border-bottom: 1px solid #F1F1;
    }

    .thumbnail{
        margin: 0px 20px;
        width: 150px;
    }

    .content{
        flex: 1;
    }

    .title{
        color: #333;
        cursor: pointer;
        font-size: 1.25rem;
        font-weight: bold;
        transition: margin .3s cubic-bezier(0.215, 0.61, 0.355, 1);
        margin-bottom: 10px;
    }

    .blog-row:hover .title{
        margin-left: 10px;
    }

    .desc{
        display: box;
        overflow: hidden;
        -webkit-box-orient: vertical;
        white-space: normal;
        -webkit-line-clamp: 2;
        max-height: 3.3rem;
        color: #606060;
        word-break: break-all;
        margin-bottom: 15px;
        opacity: 0.9;
    }

    .info{
        display: flex;
        align-items: flex-end;
        justify-content: space-between;
        color: #9199a4;
        font-size: 0.9rem;
    }

    .info .tags .tag{
        padding: 2px 5px;
        margin-right : 4px;
    }

    .info .subinfo span{
        padding: 2px 5px;
        margin-left : 4px;
    }

</style>
