<template>
    <div id="nav" :class="$mq">
        <div id="logo">
            <img src="@/assets/logo4.svg" width="70px"/>
        </div>

        <the-nav-desktop id="theNavDesktop" />
        <the-nav-mobile id="theNavMobile"/>
    </div>

</template>

<script>
    import TheNavMobile from "./TheNavMobile";
    import TheNavDesktop from "./TheNavDesktop";

    export default {
        name: 'TheNav',
        components: {
            'the-nav-desktop': TheNavDesktop,
            'the-nav-mobile': TheNavMobile,
        },
    }

</script>

<style scoped lang="scss">

    #nav {
        z-index: 3;
        display: flex;
        justify-content: space-between;
        align-items: center;
        top: 0;
        left: 0;
        right: 0;
        background: #FFFFFF;
        height: var(--nav-height);
        border-bottom: 1px solid #E7E7E7;
        padding: 0px 5%;
        position: sticky;


        #logo {
            margin-top: 5px;
        }

        #theNavDesktop {
            display: flex;
            justify-content: center;
            align-items: center;
        }

        #theNavMobile {
            display: none;
        }

        &.tablet, &.mobile {
            #theNavDesktop {
                display: none;
            }

            #theNavMobile {
                display: block;
            }
        }
    }

</style>
