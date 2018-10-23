<!DOCTYPE html>
<html>
<html>
<head>
    <script type="text/javascript" src="https://cdn-hangzhou.goeasy.io/goeasy.js"></script>
    <link rel="stylesheet" href="../assets/js/kindeditor-4.1.7/themes/default/default.css"/>
    <script type="text/javascript" src="../assets/js/kindeditor-4.1.7/kindeditor.js"></script>
    <script type="text/javascript" src="../assets/js/kindeditor-4.1.7/kindeditor-all.js"></script>
    <script type="text/javascript" src="../assets/js/kindeditor-4.1.7/kindeditor-all-min.js"></script>
    <script type="text/javascript" src="../assets/js/kindeditor-4.1.7/kindeditor-min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script type="text/javascript">
        var goeasy = new GoEasy({
            appkey: 'BC-554a3c0772034f42bf753a901bb0b5b7',
            userId:'${userid}',
            userData:'${username}'
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
                //如果不是本人发的，则显示到屏幕上
                var username = message.content.split("::",2)[1];
                if(username!="${username}"){
                    writeToScreen(message.content);
                }
            }
        });
        function publishMessage() {
            editor.sync();
            var publishMessage =$("#content").val();
            goeasy.publish({
                channel: 'letschat',
                message:  publishMessage+'::${username}',
                onFailed: function (error) {
                    alert(error.code+" : "+error.content);
                    writeToScreen('<span style="color:red;">系统出错啦</span>' + msg.data);
                },
                onSuccess: function(){
                    writeToScreen('${username}:: '+publishMessage);
                    document.getElementById("content").value='';
                }
            });
        }
        //聊天信息写入窗口中
        function writeToScreen(message) {
            var pre = document.createElement("p");
            pre.style.wordWrap = "break-word";
            var username = message.split("::",1)[0];
            var content = message.split("::",2)[1];
            //如果是本人发的，则放到对话框的邮编
            if(username=="${username}"){
                pre.style.setProperty('text-align','right');
                pre.innerHTML =content+":"+username;
            }else
                pre.innerHTML =username+":"+content;
            output.appendChild(pre);
        }
        /*使用须知*/
        var editor;
        KindEditor.ready(function (K) {
            editor = K.create('#content', {
                allowFileManager: true,
                resizeType: 1,
                allowPreviewEmoticons: true,
                allowImageUpload: true,
                allowFileManage: true,
                uploadJson: 'uploadImage',
                imageSizeLimit: '1MB', //批量上传图片单张最大容量
                imageUploadLimit: 10, //批量上传图片同时上传最多个数

                // items: [
                //     'source', '|', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
                //     'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
                //     'insertunorderedlist', '|', 'emoticons', 'image', 'multiimage', 'link']

                items: [
                    'source', '|', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
                    'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
                    'insertunorderedlist', 'lineheight', '|', 'emoticons', 'image', 'multiimage', 'link']
            });
        });
    </script>
</head>
<body>
<h2>聊天室</h2>
<div
        style="width: 1000px; height: 400px; overflow: scroll; border: 1px solid;"
        id="output"></div>
<br/>
<div style="text-align: left;">
    <form action="">
        <textarea id="content" name="content" style="width: 70%; height: 30%;"></textarea>
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