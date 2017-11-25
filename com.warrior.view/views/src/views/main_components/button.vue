<template>
  <Button :type="type" :size="size" :icon="icon" v-if="hasPermission(perStr)" v-on:click="click">{{text}}</Button>
</template>
<<script>
export default {
  name: 'permissionButton',
  props:{
    type:'',
    size:'',
    icon:'',
    perStr:'',
    text:''
  },
  methods:{
      click(){
         this.$emit('increment');
      },
      hasPermission(perStr){
        let str = this.$store.getters.getPerStr;
        if (str == undefined || str==='') {
          return false;
        }
        return str.indexOf(perStr) >= 0 ? true : false;
      }
  }
}
</script>
