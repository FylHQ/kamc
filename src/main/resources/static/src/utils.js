import axios from 'axios'


export default class Utils {
   static uploadFile(files, sourceType) {
      if (files.length > 0) {
         const formData = new FormData()
         formData.append('xlsx', files[0])
         formData.append('sourceType', sourceType)
         return axios.post('/upload', formData)
      }
   }
}