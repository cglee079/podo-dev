/**
 * Beautiful Hover Tooltip
 */
import Vue from 'vue'
import Tooltip from 'vue-directive-tooltip'
import 'vue-directive-tooltip/dist/vueDirectiveTooltip.css'

Vue.use(Tooltip, {
    delay: 300,
    placement: 'right',
    triggers: ['hover'],
    offset: 0
})
