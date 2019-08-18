<template>
    <div id="custom-viewer">
        <viewer :value="value"/>
    </div>
</template>

<script>
    import {Viewer} from '@toast-ui/vue-editor'

    export default {
        name: "ToastCustomViewer",
        components: {
            'viewer': Viewer
        },
        props: {
            value: String
        },
    }
</script>

<style lang="scss">
    /**
        Toast Viewer Override CSS
     */
    #custom-viewer {
        .tui-editor-contents {
            font-size: 1rem !important;
            word-break: break-all;
        }

        .tui-editor-contents pre{
            overflow-x: auto;
            border-radius: 5px;
        }

        .tui-editor-contents pre code{
            white-space : unset !important;
        }
    }
</style>
