<template>
<v-container grid-list-xs fluid>
   <v-layout row wrap>
      <v-flex xs10 sm4>
         <upload source-type="prclcost" name="Кадастровая стоимость ЗУ" 
            v-on:upload-begin="() => {this.isActive = true; this.isImportEnabled = false;}"
            v-on:upload-error="() => {this.isActive = false; this.isImportEnabled = false;}"
            v-on:upload-success="(data) => {
               this.isActive = false;
               this.isImportEnabled = true;
               this.onSuccessUpload(data);
            }"></upload>
      </v-flex>
      <v-flex xs2 sm1>
         <v-progress-circular
         :indeterminate="isActive"
         v-if="isActive"
         color="primary"
         >
         </v-progress-circular>
      </v-flex>
      <v-flex xs12 sm7>
         <v-layout row wrap justify-space-between>
            <v-flex sm6>
               <v-btn color="success" :disabled="!isImportEnabled" @click="importSelected">Импортировать</v-btn>
            </v-flex>
            <v-flex sm3>
               <v-text-field
                  v-model="importSettings.year"
                  label="Год"
               ></v-text-field>
            </v-flex>
            <v-flex sm3>
               <v-text-field
                  v-model="importSettings.description"
                  label="Примечание"
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
            year: (new Date()).getFullYear() + 1,
            description: 'Импорт от ' + (new Date()).toLocaleDateString("ru-RU")
         }
      }
   },
   beforeMount() {
      this.gridOptions = {
      },
      this.columnDefs = [
         {headerName: 'Лист', field: 'sheetName', width: 150, 
            sortable: true, checkboxSelection: true, headerCheckboxSelection: true},
         {headerName: 'Описание', field: 'description', 
            sortable: true, width: 500, tooltipField: 'description'}
      ];
   },
   methods: {
      onGridReady() {
         this.gridApi = this.gridOptions.api;
         this.gridColumnApi = this.gridOptions.columnApi;
      },
      onSuccessUpload(data) {
         this.rowData = data.sheets
         const self = this
         this.$nextTick(() => self.gridApi.sizeColumnsToFit())
      },
      importSelected() {
         let self = this
         let codes = self.gridApi.getSelectedNodes()
            .reduce((map, rowNode) => {map[rowNode.data.sheetName] = 1; return map;}, {})
         
         self.isImportEnabled = false
         self.isActive = true
         self.importSettings.year *= 1
         axios.post('/import', {importCode: 'prclcost', codes: codes, settings: self.importSettings})
            .then(result => {
               self.isImportEnabled = true
               self.isActive = false
            })
            .catch(error => {
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