<template>
<v-container grid-list-xs fluid>
   <v-layout row wrap>
      <v-flex xs12>
         <v-layout row wrap>
            <v-flex xs-6>
               <upload source-type="nto" v-on:upload-success="onSuccessUpload"></upload>
            </v-flex>
            <v-flex xs-6>
               <v-btn color="success" :disabled="!isImportEnabled" @click="importSelected">Импортировать</v-btn>
            </v-flex>
         </v-layout>
         <v-layout row wrap>
            <ag-grid-vue style="width: 100%; height: 400px;"
                  :gridOptions="gridOptions"
                  class="ag-theme-material"
                  :columnDefs="columnDefs"
                  :rowData="rowData"
                  rowSelection="multiple"
                  pagination=false
                  paginationPageSize=50
                  >
            </ag-grid-vue>
         </v-layout>
      </v-flex>
   </v-layout>
</v-container>
</template>

<script>
import axios from 'axios'
import upload from './upload.vue'
import {AgGridVue} from "ag-grid-vue";
import {format, compareAsc} from 'date-fns'


let InfoComponent = {
    template: '<a href="#" v-on:click="onClick">{{ value() }}</a>',
    methods: {
        value() {
            return "Откр.";
        },
        onClick() {
           alert(JSON.stringify(this.params.data))
        }
    }
};

export default {
   components: {
      'upload': upload,
      AgGridVue,
      InfoComponent
   },
   data () {
      return {
         gridApi: null,
         gridColumnApi: null,
         columnDefs: null,
         rowData: null,
         isImportEnabled: false
      }
   },
   methods: {
      onSuccessUpload(data) {
         this.rowData = data.sheets[0].items
         //this.gridApi.sizeColumnsToFit(); 
         let self = this
         self.isImportEnabled = true
         this.$nextTick(function () {
            self.gridColumnApi.autoSizeColumns(['sbj']);   
         })
      },
      dateFormatter(params) {
         return format(params.value, 'DD.MM.YYYY')
      },
      importSelected() {
         alert('Пока не реализовано')
      }
   },
   mounted() {
      this.gridApi = this.gridOptions.api;
      this.gridColumnApi = this.gridOptions.columnApi;
   },
   beforeMount() {
      this.gridOptions = {}
      this.columnDefs = [
         {headerName: 'Договор', field: 'cntrNum', width: 130, 
            checkboxSelection: true, headerCheckboxSelection: true},
         {headerName: "Карточка", field: "json", width: 130, 
            cellRendererFramework: 'InfoComponent'},
         {headerName: 'Дата заключения', field: 'cntrConfirmDate', width: 180, 
            valueFormatter: this.dateFormatter},
         {headerName: 'Место размещения', field: 'placement', resizable: true, width: 350},
         {headerName: 'Название', field: 'sbj', resizable: true, width: 250}
      ];
   }
}
</script>

<style lang="css">
</style>