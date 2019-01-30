<template>
  <div id="mainapp" style="padding: 0">
    <h1>{{ msg }}</h1>
    <!--<b-field class="file">
        <b-upload v-model="file" @input="onInput">
            <a class="button is-primary">
                <b-icon icon="upload"></b-icon>
                <span>Click to upload</span>
            </a>
        </b-upload>
        <span class="file-name" v-if="file">
            {{ file.name }}
        </span>
    </b-field>-->
    
    <el-upload
      action="/upload"
      class="upload-demo"
      ref="upload"
      :limit="1"
      :on-success="onSuccess"
      name="xlsx"
      >
      <el-button plain slot="trigger" type="primary" icon="el-icon-upload">Загрузить файл</el-button>
      <div slot="tip" class="el-upload__tip">Выберите xlsx файл</div>
      </el-upload>

    Проверка Oracle (число записей в i3_object)
    <el-button plain @click="getCount">Запрос</el-button>
    <label><el-input style="width: 200px" placeholder="Click button to count" size="default" readonly maxlength="20" v-model="objCount"></el-input></label>
    <pre>
    {{ sheetInfo }}
    </pre>
        
    <!--<button @click="getCount">Get obj count</button>
    <b-message :active.sync="isActive" type="is-success" has-icon>
        {{ objCount }}
    </b-message>-->

    
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
    submitUpload() {
      this.$refs.upload.submit();
    },
    onInput(file) {
      const formData = new FormData()
      formData.append('xlsx', file)
      let self = this
      axios({
        method: 'POST',
        url: '/upload',
        data: formData,
        headers: {
            'Content-Type': 'multipart/form-data'
        },
      }).then(function (res) {
          self.sheetInfo = res.data
          console.log(res.data);
      })

      /*axios.post('/upload', formData, {})
        .then(function(response){
          self.sheetInfo = response.data
        })*/
      
      //axios.post('/upload', value)
      
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