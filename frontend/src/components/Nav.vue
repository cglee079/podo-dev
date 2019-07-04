<template>
  <div id="nav">
    <div>Podo</div>
    <div id="navMenu">
      <span>이력</span>
      <span>블로그</span>
      <span>사진</span>
    </div>
    <div>search</div>
  </div>
</template>

<script>
  export default {
    name: 'Nav.vue'
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
</style>
