<template>
    <div id="custom-viewer" :class="$mq">
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

            pre{
                overflow-x: auto;
                border-radius: 5px;

                code{
                    white-space : unset !important;
                }
            }

            h1, h2, h3, h4, h5{
                border-bottom: 0.5px solid #dbdbdb;
                line-height: 2rem;
            }

        }

        &.mobile{
            .tui-editor-contents {
                font-size: 0.9rem !important;
            }
        }

    }
</style>
