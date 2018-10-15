<!DOCTYPE html>
<html>
<html>
<head>
    <script type="text/javascript" src="https://cdn-hangzhou.goeasy.io/goeasy.js"></script>
    <script type="text/javascript">
        var goeasy = new GoEasy({
            appkey: 'BC-554a3c0772034f42bf753a901bb0b5b7',
            userId:'123',
            userData:'he is him'
        });
        function chaxun() {
            goeasy.hereNow(
                {
                    channels: ['letschat'], //要查询的channel 列表
                    includeUsers: true, //可选项，是否返回用户列表，默认false
                    distinct: true //可选项，是否过滤相同的userId，默认false
                },
                function (response) {
                    console.log(response);
                });
        }
        function quxiao() {
            goeasy.unsubscribePresence({
                channel: 'letschat',
                //取消成功
                onSuccess: function () {
                    alert("取消订阅presence 成功.")
                },
//取消失败
                onFailed: function (error) {
                    alert("取消订阅presence 失败.");
                }
            });
        }
        function dingyue() {
            goeasy.subscribePresence({
                channel: 'letschat',
                //订阅Presence 成功
                onSuccess: function () {
                    alert('Presence subscription success.');
                },
//订阅Presence 失败
                onFailed: function (error) {
                    alert('Presence subscription failure.');
                },
//当有客户端状态发生变化
                onPresence: function (presenceEvents) {
                    var data = JSON.stringify(presenceEvents)
                    console.log("agga" + presenceEvents);
                    alert('Presence:' + data);
                }
            });
        }
        goeasy.subscribe({
            channel: 'letschat',
            onFailed: function (error) {
                alert("failed motherfucker!")
            },
            onSuccess: function(){
            },
            onMessage: function(message){
                //收到消息的第一行，打出日志，以这个时间作为检查的标准
                writeToScreen(message.content);
            }
        });
        function publishMessage() {
            var publishMessage = document.getElementById("content").value;
            goeasy.publish({
                channel: 'letschat',
                message: publishMessage,
                onFailed: function (error) {
                    alert(error.code+" : "+error.content);
                    writeToScreen('<span style="color:red;">系统出错啦</span>' + msg.data);
                },
                onSuccess: function(){
                    writeToScreen(publishMessage);
                    document.getElementById("content").value='';
                }
            });
        }
        //聊天信息写入窗口中
        function writeToScreen(message) {
            var pre = document.createElement("p");
            pre.style.wordWrap = "break-word";
            pre.innerHTML = message;
            output.appendChild(pre);
        }
    </script>
</head>
<body>
<h2>聊天室</h2>
<div
        style="width: 400px; height: 260px; overflow: scroll; border: 1px solid;"
        id="output"></div>
<br/>
<div style="text-align: left;">
    <form action="">
        <input id="content" name="message" value="" type="text"
               style="width: 400px; height: 60px; border: 1px solid;"> <br />
        <br /> <input onclick="publishMessage()" value="发送" type="button" />
    </form>
    <button onclick="chaxun()">查询</button>
    <button onclick="dingyue()">订阅</button>
    <button onclick="quxiao()">取消</button>
</div>
</body>
</html>

</body>


</html>