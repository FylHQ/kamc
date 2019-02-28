<template>
<v-content>
    <v-container fluid>
      <v-layout>
      <!--<div ref="log" style="max-height: 300px; overflow-y: auto">-->
      <v-flex ref="log">
         <div :class="'log-' + item.level" v-for="(item, index) in logitems" :key="index">
         {{ item.message }}
         </div>
      </v-flex>
      </v-layout>
    </v-container>
</v-content>
</template>

<script>
import axios from 'axios'

export default {
   data () {
      return {
         intervalLog: null,
         logitems: [],
         logtimestamp: new Date().getTime()
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
      }
   }
}
</script>