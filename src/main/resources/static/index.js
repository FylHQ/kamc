import Vue from 'vue'

import 'vuetify/dist/vuetify.min.css'
import 'material-design-icons-iconfont/dist/material-design-icons.css'
import 'roboto-fontface/css/roboto/roboto-fontface.css'

import Vuetify from 'vuetify'
import ru from 'vuetify/es5/locale/ru'

Vue.use(Vuetify, {
  iconfont: 'md',
  lang: {
    locales: { ru },
    current: 'ru'
  }
})

import App from './src/App.vue' 

new Vue({
  el: '#app',
  render: h => h(App)
})
