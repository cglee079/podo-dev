import Vue from "vue";
import ToastCustomViewer from "../components/global/ToastCustomViewer";
import 'tui-editor/dist/tui-editor-extUML';
import 'tui-editor/dist/tui-editor-extChart';
import 'tui-editor/dist/tui-editor-extTable';
import 'tui-editor/dist/tui-editor-extColorSyntax';

Vue.component("toast-custom-viewer", ToastCustomViewer);
