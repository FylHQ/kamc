<template>
  <div id="mainapp" style="padding: 0">
    <h1>{{ msg }}</h1>
    
    <el-upload action="/upload" name="xlsx" class="upload-demo" :limit="1" :on-success="onSuccess">
      <el-button plain slot="trigger" type="primary" icon="el-icon-upload">Загрузить файл</el-button>
      <div slot="tip" class="el-upload__tip">Выберите xlsx файл</div>
    </el-upload>

    Проверка Oracle (число записей в i3_object)
    <el-button plain @click="getCount">Запрос</el-button>
    <label><el-input style="width: 200px" placeholder="Click button to count" size="default" readonly maxlength="20" v-model="objCount"></el-input></label>
    <pre>
    </pre>
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
      fileList: [],
      objCount: null,
      file: null,
      isActive: false,
      sheetInfo: ""
    }
  },
  mounted: function() {
    Notification({title: "KAMC", message: "Hello", type: "info", dangerouslyUseHTMLString: true});
  },
  methods: {
    test() {
      console.log([1, 2].reduce((a, b) => a + b))
    },
    onSuccess(response, file, fileList) {
      this.sheetInfo = response;
      Notification({title: "Загрузка", message: "Показаны данные первого листа", type: "info"});
      //console.log(response)
    },
    getCount() {
      let self = this
      axios.get('/obj/count')
        .then(result => {
          self.objCount = result.data;
          self.isActive = true
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
.el-upload-list__item-name {
  height: fit-content;
  padding: 10px;
}
.count {
  border: 1px solid blue;
}
</style>