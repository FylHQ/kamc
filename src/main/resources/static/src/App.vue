<template>
  <v-app>
    <v-toolbar app>{{ msg }}</v-toolbar>
    <v-content>
      <v-container grid-list-xs fluid>
        <v-layout row wrap>
          <v-flex xs10 sm5>
            <v-text-field label="Загрузить файл" @click='pickFile' v-model='imageName' prepend-icon='attach_file'></v-text-field>
            <input
              type="file"
              style="display: none"
              ref="attach"
              @change="onFilePicked"
            >
          </v-flex>
          <v-flex xs2 sm1>
            <v-progress-circular
              :indeterminate="isActive"
              v-if="isActive"
              color="primary"
            >
            </v-progress-circular>
          </v-flex>
          <v-flex xs12 sm6>
            <v-layout row wrap>
              <v-flex sm12 md4>
                <v-btn color="success" :disabled="!isImportEnabled" @click="importSelected">Импортировать</v-btn>
              </v-flex>
              <!--<v-flex sm4 md4>
                <v-checkbox caption
                  v-model="importSettings.ignoreCheap"
                  label="Игнор. < 50000"
                ></v-checkbox>
              </v-flex>-->
              <v-flex sm4 md3>
                <v-checkbox
                  v-model="importSettings.ignoreAll"
                  label="Игнор. любые"
                ></v-checkbox>
              </v-flex>
              <v-flex sm4 md4>
                <v-checkbox caption
                  v-model="importSettings.createNew"
                  label="Создавать новые"
                ></v-checkbox>
              </v-flex>
              <v-flex sm4 md1>
                <v-text-field
                  v-model="importSettings.threshFull"
                ></v-text-field>
              </v-flex>
            </v-layout>
          </v-flex>
        </v-layout>
        <v-layout row wrap>
          <v-flex xs5>
            <v-data-table
              v-model="selected"
              :headers="headers"
              :items="sheets"
              item-key="sheetName"
              select-all
              :pagination.sync="pagination"
            >
              <template slot="headers" slot-scope="props">
                <tr>
                  <th>
                    <v-checkbox
                      :input-value="props.all"
                      :indeterminate="props.indeterminate"
                      primary
                      hide-details
                      @click.stop="toggleAll"
                    ></v-checkbox>
                  </th>
                  <th
                    v-for="header in props.headers"
                    :key="header.text"
                    :class="['column sortable', pagination.descending ? 'desc' : 'asc', header.value === pagination.sortBy ? 'active' : '']"
                     @click="changeSort(header.value)"
                  >
                    <v-icon small>arrow_upward</v-icon>
                    {{ header.text }}
                  </th>
                </tr>
              </template>

              <template slot="items" slot-scope="props">
                <tr :active="props.selected" @click="props.selected = !props.selected">
                  <td>
                    <v-checkbox
                      :input-value="props.selected"
                      primary
                      hide-details
                    ></v-checkbox>
                  </td>
                  <td :class="'cntr-' + props.item.isExists">{{ props.item.sheetName }}</td>
                  <td :class="'cntr-' + props.item.isExists">{{ props.item.subject }}</td>
                </tr>
              </template>
            </v-data-table>
          </v-flex>
          <v-flex xs7>
           <div class="headline">Журнал</div>
           <div ref="log" style="max-height: 300px; overflow-y: auto">
            <div :class="'log-' + item.level" v-for="(item, index) in logitems" :key="index">
              {{ item.message }}
            </div>
            </div>
          </v-flex>
        </v-layout>
      </v-container>
  
    <!--<el-upload action="/upload" name="xlsx" class="upload-demo" :limit="1" :on-success="onSuccess">
      <el-button plain slot="trigger" type="primary" icon="el-icon-upload">Загрузить файл</el-button>
      <div slot="tip" class="el-upload__tip">Выберите xlsx файл</div>
    </el-upload>

    Проверка Oracle (число записей в i3_object)
    <el-button plain @click="getCount">Запрос</el-button>
    <label><el-input style="width: 200px" placeholder="Click button to count" size="default" readonly maxlength="20" v-model="objCount"></el-input></label>
    <pre>
    </pre>-->
  
  </v-content>
  </v-app>
</template>
<script>

import axios from 'axios'
//import {Notification} from 'element-ui'
export default {
  name: 'mainapp',
  data () {
    return {
      msg: 'АИС "Имущество',
      fileList: [],
      objCount: null,
      file: null,
      isImportEnabled: false,
      sheetInfo: "",
      imageName: null,
      selected: [],
      pagination: {
        sortBy: 'sheetName'
      },
      headers: [
          { text: 'Лист', value: 'sheetName' },
          /*{ text: 'Договор', value: 'cntrNum', sortable: true },*/
          { text: 'Арендатор', value: 'subject' }
      ],
      sheets: [
      ],
      logitems: [],
      logtimestamp: new Date().getTime(),
      intervalLog: null,
      isImportEnabled: false,
      isActive: false,
      importSettings: {
        ignoreCheap: false,
        ignoreAll: false,
        threshFull: 5,
        createNew: false
      }
    }
  },
  mounted: function() {
    this.intervalLog = setInterval(this.showLog, 3000);
    //Notification({title: "KAMC", message: "Hello", type: "info", dangerouslyUseHTMLString: true});
  },
  beforeDestroy () {
    clearInterval(this.intervalLog)
  },
  methods: {
    pickFile() {
      this.$refs.attach.click ()

    },
    showLog() {
      var self = this
      axios.get('/log', {params: {from: this.logtimestamp}})
        .then(result=>{
          result.data.forEach(item => {
            self.logitems.push(item)
            self.logtimestamp = item.timestamp
          })
          if (result.data.length)
            self.$refs.log.scrollTop = self.$refs.log.scrollHeight
          //self.logitems = result.data
        })
    },
    onFilePicked(e) {
      var self = this;
      var files = e.target.files || e.dataTransfer.files;       
      if (files.length > 0) {
        self.imageName = files[0].name
        const formData = new FormData()
        formData.append('xlsx', files[0])
        axios.post('/upload', formData)
        .then(result=>{
          self.sheets = result.data.sheets
          self.isImportEnabled = true
        })
        .catch(error => {
          console.error(error)
        })
      }
    },
    importSelected() {
      let self = this
      let codes = self.selected.reduce((map, sheet) => {map[sheet.sheetName] = 1; return map;}, {})
      self.isImportEnabled = false
      self.isActive = true
      self.importSettings.threshFull *= 1
      axios.post('/import', {sheetCodes: codes, settings: self.importSettings})
      .then(result => {
        self.logitems.push({message: "Результат: " + result.data})
        self.isImportEnabled = true
        self.isActive = false
      })
      .catch(error => {
        self.logitems.push({level: 'ERROR', message: "Ошибка: " + error})
        self.isImportEnabled = true
        self.isActive = false
      })
    },
    toggleAll() {
      if (this.selected.length) this.selected = []
      else this.selected = this.sheets.filter(item => !item.isExists)
    },
    changeSort (column) {
        if (this.pagination.sortBy === column) {
          this.pagination.descending = !this.pagination.descending
        } else {
          this.pagination.sortBy = column
          this.pagination.descending = false
        }
      },
    test() {
      console.log([1, 2].reduce((a, b) => a + b))
    },
    onSuccess(response, file, fileList) {
      this.sheetInfo = response;
      //Notification({title: "Загрузка", message: "Показаны данные первого листа", type: "info"});
      //console.log(response)
    },
    getCount() {
      let self = this
      axios.get('/obj/count')
        .then(result => {
          self.objCount = result.data;
          self.isImportEnabled = true
          //Notification({title: "Подсчет", message: "Данные получены", type: "info"});
        })
        .catch(() => {
          //Notification({title: "Подсчет", message: "Ошибка запроса данных", type: "error"});
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
  ;
}

.log-INFO {
  font-size: 10px;
}
.log-ERROR {
  color: red;
}
.log-WARN {
  color: blue;
  font-size: 10px;
}
.log-DEBUG {
  color: gray;
  font-size: 10px;
}
.cntr-true {
  color: lightgray;
}

.v-input--checkbox .v-label {
  font-size: 10px;
}
</style>