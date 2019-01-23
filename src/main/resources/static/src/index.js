import Vue from 'vue'

import { Button, Select, Option, Dialog, Input, Tabs, TabPane, Upload, Switch, Alert, Popover } from 'element-ui'
import elementLang from 'element-ui/lib/locale/lang/en'
import elementLocale from 'element-ui/lib/locale'
elementLocale.use(elementLang);
Vue.component(Button.name, Button);
Vue.component(Select.name, Select);
Vue.component(Option.name, Option);
Vue.component(Dialog.name, Dialog);
Vue.component(Input.name, Input);
Vue.component(Tabs.name, Tabs);
Vue.component(TabPane.name, TabPane);
Vue.component(Upload.name, Upload);
Vue.component(Switch.name, Switch);
Vue.component(Alert.name, Alert);
Vue.component(Popover.name, Popover);
import 'element-ui/lib/theme-chalk/index.css';

import App from './App.vue' 

new Vue({
  el: '#app',
  render: h => h(App)
})