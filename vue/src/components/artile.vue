<template>
      <ul>
    <li v-for="article in articles">
      {{ article.label }}:{{ article.brief}}
    </li>
  </ul>
</template>

<script>
export default {
  name:'artile',
  data(){
    return  {
      articles:''
    }
  },
	methods: {
		getArticle(){
						this.$axios.post('article/getArticle')
						.then((successResponse)=>{
					this.responseResult = JSON.stringify(successResponse.data)
					console.log(this.responseResult)
					if (successResponse.data.code === 200) {
						this.articles = successResponse.data.data;
					}
			}).catch(failResponse => {})
		}
	},
	mounted: function() {
		this.getArticle()
		},
}
</script>

<style>
    
</style>
