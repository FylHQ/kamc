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
            <v-flex xs-12>
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
            </v-flex>
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
         gridOptions: null,
         gridApi: null,
         gridColumnApi: null,
         columnDefs: null,
         rowData: [],
         isImportEnabled: false
      }
   },
   methods: {
      onSuccessUpload(data) {
         this.rowData = data.sheets[0].items
         //this.gridApi.sizeColumnsToFit(); 
         let self = this
         self.isImportEnabled = true
         //this.$nextTick(() => self.gridColumnApi.autoSizeColumns(['sbj']));   
         this.$nextTick(() => self.gridApi.sizeColumnsToFit())
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
         {headerName: 'Договор', field: 'cntrNum', width: 120, 
            checkboxSelection: true, headerCheckboxSelection: true},
         {headerName: "Карточка", field: "json", width: 100, 
            cellRendererFramework: 'InfoComponent'},
         {headerName: 'Дата заключения', field: 'cntrConfirmDate', width: 120, 
            valueFormatter: this.dateFormatter},
         {headerName: 'Место размещения', field: 'placement', resizable: true, 
            width: 250, tooltipField: 'placement'},
         {headerName: 'Название', field: 'sbj', 
            tooltipField: 'sbj', resizable: true, width: 350}
      ];
   }
}
</script>

<style lang="css">
</style>