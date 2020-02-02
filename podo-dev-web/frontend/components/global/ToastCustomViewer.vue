<template>
    <div id="custom-viewer" :class="$mq">
        <div v-html="$md.render(value)" ref="serverOnly" class="tui-editor-contents"></div>

        <client-only>
            <TuiEditorViewer :value="value" />
        </client-only>
    </div>
</template>

<script>
export default {
    name: "ToastCustomViewer",
    props: {
        value: String
    },

    mounted() {
        this.$refs.serverOnly.innerHTML = "";
    }
};
</script>
