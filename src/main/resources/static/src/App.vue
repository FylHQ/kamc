<template>
  <div id="mainapp" style="padding: 0">
    <h1>{{ msg }}</h1>
    <el-button size="small" @click="getCount">Get obj count</el-button>
    <label><el-input style="width: 200px" placeholder="Click button to count" size="small" readonly maxlength="20" v-model="objCount"></el-input></label>
  </div>
</template>

<script>
import axios from 'axios'
import {Notification} from 'element-ui'
export default {
  name: 'mainapp',
  data () {
    return {
      msg: 'Welcome to KAMC web server',
      objCount: null
    }
  },
  mounted: function() {
    Notification({title: "KAMC", message: "Hello", type: "info", dangerouslyUseHTMLString: true});
  },
  methods: {
    test() {
      console.log([1, 2].reduce((a, b) => a + b))
    },
    getCount() {
      let self = this
      axios.get('/obj/count')
        .then(result => {
          self.objCount = result.data;
          Notification({title: "Подсчет", message: "Данные получены", type: "info"});
        })
        .catch(() => {
          Notification({title: "Подсчет", message: "Ошибка запроса данных", type: "error"});
        })
      
    }
  }
}
</script>

<style lang="scss">
.count {
  border: 1px solid blue;
}
</style>