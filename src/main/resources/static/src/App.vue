<template>
  <v-app>
    <v-toolbar app>{{ msg }}</v-toolbar>
    <v-content>
      <v-container grid-list-xs fluid>
        <v-layout row wrap>
        <v-flex xs12 sm6>
          <v-text-field label="Загрузить файл" @click='pickFile' v-model='imageName' prepend-icon='attach_file'></v-text-field>
          <input
						type="file"
						style="display: none"
						ref="attach"
						@change="onFilePicked"
					>
        </v-flex>
        <v-flex xs12 sm6>
          <v-btn color="success">Success</v-btn>
        </v-flex>
        </v-layout>
        <v-layout row wrap>
          <v-flex xs12>
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
                  <td>{{ props.item.sheetName }}</td>
                  <td>{{ props.item.cntrNum }}</td>
                  <td>{{ props.item.subject }}</td>
                </tr>
              </template>
            </v-data-table>
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
      msg: 'Welcome to web server',
      fileList: [],
      objCount: null,
      file: null,
      isActive: false,
      sheetInfo: "",
      imageName: null,
      selected: [],
      pagination: {
        sortBy: 'sheetName'
      },
      headers: [
          { text: 'Лист', value: 'sheetName' },
          { text: 'Договор', value: 'cntrNum', sortable: true },
          { text: 'Арендатор', value: 'subject' }
      ],
      sheets: [
      ]
    }
  },
  mounted: function() {
    //Notification({title: "KAMC", message: "Hello", type: "info", dangerouslyUseHTMLString: true});
  },
  methods: {
    pickFile() {
      this.$refs.attach.click ()

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
        })
        .catch(error => {
          console.error(error)
        })
      }
    },
    toggleAll() {
      if (this.selected.length) this.selected = []
      else this.selected = this.sheets.slice()
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
          self.isActive = true
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
}
</style>