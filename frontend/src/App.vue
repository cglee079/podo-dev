<template>
  <div>
    <Nav></Nav>
    <router-view></router-view>
  </div>
</template>

<script>
  import Nav from '@/components/Nav'

  export default {
    components: {
      Nav
    },
    name: 'App'
  }
</script>

<style>
  @import url(http://fonts.googleapis.com/earlyaccess/notosanskr.css);

  * {
    margin: 0px;
    padding: 0px;
  }

  html, body {
    font-family: 'Noto Sans KR', sans-serif;
  }

</style>
