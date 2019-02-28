<template>
<v-container grid-list-xs fluid>
   <v-layout row wrap>
      <v-flex xs10 sm5>
         <upload source-type="rent" v-on:upload-success="onSuccessUpload"></upload>
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
               <td :class="'cntr-' + props.item.isExists">{{ props.item.sheetName }}</td>
               <td :class="'cntr-' + props.item.isExists">{{ props.item.subject }}</td>
            </tr>
         </template>
         </v-data-table>
      </v-flex>
      <!--<v-flex xs7>
      <div class="headline">Журнал</div>
      <div ref="log" style="max-height: 300px; overflow-y: auto">
         <div :class="'log-' + item.level" v-for="(item, index) in logitems" :key="index">
         {{ item.message }}
         </div>
         </div>
      </v-flex>-->
   </v-layout>
</v-container>
</template>

<script>
import axios from 'axios'
import upload from './upload.vue'

export default {
   components: {
      'upload': upload
   },
   data () {
      return {
         selected: [],
         pagination: {
         sortBy: 'sheetName'
         },
         headers: [
            { text: 'Лист', value: 'sheetName' },
            { text: 'Арендатор', value: 'subject' }
         ],
         sheets: [
         ],
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
   methods: {
      onSuccessUpload(data) {
         this.sheets = data.sheets
         this.isImportEnabled = true
      },
      importSelected() {
         let self = this
         let codes = self.selected.reduce((map, sheet) => {map[sheet.sheetName] = 1; return map;}, {})
         self.isImportEnabled = false
         self.isActive = true
         self.importSettings.threshFull *= 1
         axios.post('/import', {sheetCodes: codes, settings: self.importSettings})
            .then(result => {
               //self.logitems.push({message: "Результат: " + result.data})
               self.isImportEnabled = true
               self.isActive = false
            })
            .catch(error => {
               //self.logitems.push({level: 'ERROR', message: "Ошибка: " + error})
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

   }
}
</script>

<style lang="scss">
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
.v-input--checkbox .v-label {
  font-size: 10px;
}
.cntr-true {
  color: lightgray;
}
</style>