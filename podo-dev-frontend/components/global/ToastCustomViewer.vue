<template>
    <div id="custom-viewer" :class="$mq">
        <div v-html="$md.render(value)" ref="serverOnly" class="tui-editor-contents">
        </div>

        <client-only>
            <!-- 에디터를 로딩해 exts가 먹힘, 버그-->
            <TuiEditorViewer :value="value" :exts="config.editor.options.exts"></TuiEditorViewer>
        </client-only>
    </div>
</template>

<script>
export default {
    name: "ToastCustomViewer",
    props: {
        value: String
    },
    data() {
        return {
            config: {
                editor: {
                    options: {
                        exts: ["uml", "chart", "table"]
                    }
                }
            }
        };
    },
    mounted() {
        this.$refs.serverOnly.innerHTML = "";
    }
};
</script>
