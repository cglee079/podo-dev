<template>
    <div id="resume" :class="$mq">
        <div id="name">
            <span>이찬구, Podo</span>
        </div>

        <div class="item">
            <div class="head">OBJECT</div>
            <div class="content">
                <p><b>Podo is Good, Delicious And Best!!!!</b></p>
            </div>
        </div>

        <div class="item">
            <div class="head">SKILL</div>
            <div class="content">
                <p>Java, Spring Framework</p>
                <p>Vue, JSP, Javascript, JQuery, CSS</p>
                <p>MySQL, MyBatis, JPA, Solr, RabbitMQ</p>
            </div>
        </div>

        <div class="item">
            <div class="head">WORK EXPERIENCE</div>
            <div class="content">

                <div>
                    <p><b># (주)줌인터넷 담당, 2019.06.10</b></p>
                    <p>- 신사업개발실 서버 개발자</p>
                    <p>- Spring, JPA</p>
                </div>

                <div>
                    <p><b># (주)넥스젠어쏘시에이트 인턴&사원, 2017.07.17 ~ 2018.07.21</b></p>
                    <p>- 7개월 산학협력 인턴, 5개월 정규직</p>
                    <p>- LMS & HRD 관련 SI</p>
                    <p>- 웹 피벗테이블 개발, 회사 솔루션 반영</p>
                    <p>- 기존 운용 서비스 유지보수 및 고도화</p>
                    <p>- 전자정부프레임워크, Spring, MyBatis, Javascript, JQuery</p>
                </div>
            </div>
        </div>

        <div class="item">
            <div class="head">TOY PROJECT</div>
            <div class="content">
                <div>
                    <p><b># 가상화폐 가격 알리미</b></p>
                    <p>- 가상화폐관련 텔레그램 챗봇</p>
                    <p>- 커뮤니티 배포 후 운용, 누적사용자 2,000명, 해외사용자를 위한 영어 지원</p>
                    <p>- Spring, Mybatis, Telegram, Docker, English Support</p>
                </div>

                <div>
                    <p><b># CHANGOO'S</b></p>
                    <p>- 개인용 포트폴리오/기술블로그 웹사이트 개발, 하이브리드 앱 개발</p>
                    <p>- Spring, JSP, MyBatis, MySql, Solr, JQuery, CSS, Android</p>
                </div>
            </div>
        </div>

        <div class="item">
            <div class="head">EDUCATION</div>
            <div class="content">
                <div>
                    <p>2011.03 - 2018.02</p>
                    <p>한성대학교 컴퓨터공학부 수석 졸업</p>
                    <p>학점 4.22/4.5 (전공학점 4.39/4.5)</p>
                </div>
            </div>
        </div>

        <div class="item">
            <div class="head">CERTIFICATION</div>
            <div class="content">
                <div>
                    <p>2017.03.16, 정보처리기사</p>
                    <p>2017.11.26, TOEIC 840</p>
                    <p>2016.12.16, MOS 마스터</p>
                    <p>2015.09.11, GTQ 1급</p>
                </div>
            </div>
        </div>


        <div class="item">
            <div class="head">LINK</div>
            <div class="content">
                <div>
                    <p>Git, <a href="https://github.com/cglee079" target="_blank">https://github.com/cglee079</a></p>
                </div>
            </div>
        </div>

    </div>
</template>

<script>
    export default {
        name: "ResumeVue"
    }
</script>

<style lang="scss" scoped>
    #resume {
        background: #FBFBFE;
        border-radius: 15px;
        padding: 100px;

        color: #222222;
        cursor: default;

        &.mobile, &.tablet {
            margin-top: 50px;
            padding: 0px 20px;
        }

        #name {
            transition: padding 0.5s;

            &:hover {
                padding-left: 20px;
            }

            span {
                font-size: 2.2rem;
                font-weight: bold;
                padding-bottom: 3px;
                /*border-bottom: 5px solid #afa3d9;*/
            }
        }

        .item {
            margin-top: 80px;

            .head {
                border-left: 5px solid #d0c0d9;
                padding-left: 10px;
                font-size: 1.2rem;
                font-weight: bold;
            }

            .content {
                margin-top: 20px;
                margin-left: 15px;

                p {
                    margin-top: 7px;
                }

                div {
                    margin-bottom: 40px;
                }

            }
        }
    }

</style>
