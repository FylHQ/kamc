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
      <v-flex xs-12>
         <ag-grid-vue style="width: 100%; height: 400px;"
               :gridOptions="gridOptions"
               class="ag-theme-material"
               :columnDefs="columnDefs"
               :rowData="rowData"
               rowSelection="multiple"
               pagination=false
               paginationPageSize=50
               @grid-ready="onGridReady"
               >
         </ag-grid-vue>
      </v-flex>
   </v-layout>
</v-container>
</template>

<script>
import axios from 'axios'
import upload from './upload.vue'
import {AgGridVue} from "ag-grid-vue";

export default {
   components: {
      'upload': upload,
      AgGridVue,
   },
   data () {
      return {
         gridOptions: null,
         gridApi: null,
         gridColumnApi: null,

         columnDefs: null,
         rowData: [],

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
   beforeMount() {
      this.gridOptions = {
         isRowSelectable: rowNode => {
            return !rowNode.data.isExists
         },
         getRowClass: params => {
            return 'cntr-' + params.data.isExists
         }
      }
      this.columnDefs = [
         {headerName: 'Лист', field: 'sheetName', width: 150, 
            sortable: true, checkboxSelection: true, headerCheckboxSelection: true},
         {headerName: 'Загружен', field: 'isExists', hide: true, sort: "asc"},
         {headerName: 'Арендатор', field: 'subject', 
            sortable: true, width: 500, tooltipField: 'subject'}
      ];
   },
   methods: {
      onGridReady() {
         this.gridApi = this.gridOptions.api;
         this.gridColumnApi = this.gridOptions.columnApi;
      },
      onSuccessUpload(data) {
         this.rowData = data.sheets
         this.isImportEnabled = true

         const self = this
         this.$nextTick(() => self.gridApi.sizeColumnsToFit())
      },
      importSelected() {
         let self = this
         let codes = self.gridApi.getSelectedNodes()
            .reduce((map, rowNode) => {map[rowNode.data.sheetName] = 1; return map;}, {})
         
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
      }
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