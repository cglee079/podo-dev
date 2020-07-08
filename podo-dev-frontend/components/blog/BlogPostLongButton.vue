<template>
    <div id="button" @click="$emit('click')">
        <div>{{value}}</div>
    </div>
</template>

<script>
export default {
    name: "BlogPostLongButton",
    props:{
        value : String
    }
};
</script>

<style lang="scss" scoped>
#button {
    height: 30px;
    border: 1px solid #ccc;
    background: #fafafa;
    cursor: pointer;
    display: flex;

    div {
        flex: 1;
        display: flex;
        justify-content: center;
        align-items: center;
    }
}
</style>
