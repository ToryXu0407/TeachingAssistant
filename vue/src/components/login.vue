
<template>
  <div id="app">
    <h1 class="top">臭鱼烂虾社区</h1>
    <div><img src="../img/xia.png"/></div>
   <div>
      用户名:<input type="text" v-model="loginInfoVo.username" placeholder="请输入用户名" class="from-1"/>
      <br/>
      密码：<input type="password" v-model="loginInfoVo.password" placeholder="请输入密码" class="from-1"/>
      <br/>
      <button v-on:click="login" class="from-2">登录</button>
    </div>
       <p class="zhuce">
         <span>注册账号</span>
         <span>忘记密码</span>
       </p>
    </div>
  </div>  
</template>

<script>
export default {
  name:'login',
    data () {
    return {
      loginInfoVo: { username: '', password: '' },
      responseResult: []
    }
  },
  methods: {
    login () {
			let params = new URLSearchParams();
			params.append('username', this.loginInfoVo.username);
			params.append('password', this.loginInfoVo.password);
			this.$axios.post('/loginSubmit',params)
			.then((successResponse)=>{
    this.responseResult = JSON.stringify(successResponse.data)
    if (successResponse.data.code === 200) {
    	this.$router.push({
				path:'/',
				query:{
					loginInfoVo:successResponse.data.data
				}})
    }
}).catch(failResponse => {})
    }
},
	
}
</script>
<style>
#app{
  width: 100%;
  margin: 0 auto;
}
  .from{
    width: 500px;
    margin: 0 auto;
    margin-top: 10%;
  }
  .from-1{
    width: 100%;
    height: 60px;
    font-size: 24px;
    border: 1px solid #aaa;
    border-radius: 10px;
    outline: none;
    margin: 0 auto;
    margin-top: 10px;
  }
.from-2{
  width: 100%;
  height: 60px;
  font-size: 24px;
  margin-top: 10px;
  border: none;
  border-radius: 10px;
  outline: none;
  background-color: #e4b9c0;
  color: #fff;
  cursor: pointer;
}
  .zhuce span{
    cursor: pointer;
    margin-right: 20px;
  }
.zhuce span:hover{
  color: red;
}
  .top{
    text-align: center;
    margin-top: 8%;
  }
</style>
