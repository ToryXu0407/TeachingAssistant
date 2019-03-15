<template>
	<div class="person" v-if="client.peer.nick===TeacherId">
    <!--<div class="person__name">-->
      <!--{{client.peer.nick}}-->
    <!--</div>-->
		<div class="person__video" ref="person"></div>
	</div>
</template>

<script>
  export default {
    props: [
      'client','TeacherId'
    ],
    created: function () {
    },
    mounted () {
      console.log(this.client.peer.nick);
        console.log(this.TeacherId);
        if(this.client.peer.nick===this.TeacherId){
            this.$refs.person.appendChild(this.client.video)
        }
    }
  }
</script>
