<template>
    <div @click="click">
        {{value}}
    </div>
</template>

<script>
    export default {
        name: "SubButton",
        props: {
            value: '',
        },
        methods: {
            click(){
                this.$emit('click')
            }
        }

    }
</script>

<style scoped lang="scss">
    div {
        cursor: pointer;
        margin: 0px 5px;
        padding: 2px 10px;
        border-radius: 5px;
        border: 1px solid #AAAAAA;
        text-align: center;
        display: inline-block;

        &:hover{
            background: #111111;
            border: 1px solid #111111;
            color: #FFFFFF;
        }
    }

</style>
