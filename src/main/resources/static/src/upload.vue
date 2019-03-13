<template>
   <v-content>
         <v-text-field :label="name" @click='pickFile' v-model='imageName' prepend-icon='attach_file'></v-text-field>
         <input
            type="file"
            style="display: none"
            ref="attach"
            @change="onFilePicked"
         >
   </v-content>
</template>

<script>
import axios from 'axios'

export default {
   props: ['sourceType', 'name'],
   data () {
      return {
         imageName: null,
      }
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
            formData.append('sourceType', self.sourceType)

            axios.post('/upload', formData)
               .then(result=>{
                  self.$emit("upload-success", result.data)
               })
               .catch(error => {
                  console.error(error)
               })

         }
      },
   }
}
</script>