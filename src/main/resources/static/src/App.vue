<template>
  <v-app>
    <v-toolbar tabs>
      <v-toolbar-title>{{ msg }}</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-tabs
        slot="extension"
        v-model="activeTab"
        slider-color="yellow"
        align-with-title
      >
      <v-tab :key="0">
        Мун имущество
      </v-tab>
      <v-tab :key="1">
        Договоры НТО
      </v-tab>
      <v-tab :key="2">
        Журнал
      </v-tab>
      </v-tabs>
    </v-toolbar>

    <v-tabs-items v-model="activeTab">
      <v-tab-item :key="0">
        <rent></rent>
      </v-tab-item>
      <v-tab-item :key="1">
        <nto></nto>
      </v-tab-item>
      <v-tab-item :key="2">
        <journal></journal>
      </v-tab-item>
    </v-tabs-items>
  </v-app>
</template>
<script>

import axios from 'axios'
import rent from './rent.vue'
import nto from './nto.vue'
import journal from './journal.vue'

export default {
  name: 'mainapp',
  components: {
    'rent': rent,
    'nto': nto,
    'journal': journal
  },
  data () {
    return {
      msg: 'АИС "Имущество',
      objCount: null,
      activeTab: 1
    }
  },
  mounted: function() {
    //this.intervalLog = setInterval(this.showLog, 3000);
  },
  beforeDestroy () {
    //clearInterval(this.intervalLog)
  },
  methods: {
    test() {
      console.log([1, 2].reduce((a, b) => a + b))
    },
    onSuccess(response, file, fileList) {
      this.rentSheet = response;
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
$ag-mat-icons-path: "~ag-grid-community/src/styles/ag-theme-material/icons/";
$ag-icons-path: "~ag-grid-community/src/styles/ag-theme-base/icons/";

@import '~ag-grid-community/src/styles/ag-grid';
@import '~ag-grid-community/src/styles/ag-theme-material/sass/ag-theme-material';
</style>