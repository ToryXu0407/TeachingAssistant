<template>
  <div>HELLO WORLD!
  </div>
</template>

<script>
    export default {
        name: "chatRoomDetail",
      created: function () {
        var goEasy = new GoEasy({
          appkey: "BC-554a3c0772034f42bf753a901bb0b5b7",
          onConnected: function () {
            alert("成功连接GoEasy。");
          },
          onDisconnected: function () {
            alert("与GoEasy连接断开。");
          },
          onConnectFailed: function (error) {
            alert("与GoEasy连接失败，错误编码："+error.code+"错误信息："+error.content);
          }
        });
        goEasy.publish({
          channel: "letschat",
          message: "Hello, GoEasy!"
        });
      }
    }



</script>

<style scoped>

</style>
