<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script src="https://cdn.jsdelivr.net/npm/vue@2.5.17/dist/vue.js"></script>
<head>
    <title>聊天室</title>
    <div id="app">
        <div v-html="message"></div>
    </div>
</head>
<body>


</body>
<script>
    new Vue({
        el: '#app',
        data: {
            message: '<h1>菜鸟教程</h1>'
        }
    })
</script>
</html>
