var dataGetter = {
  post: function(url, data, success){
    $.ajax({
      type:"get",
      url: url,
      data: data,
      dataType: "jsonp",
      jsonp:"callback",
      success:function(res){
        success(res);
      }
    })
  },

  get: function(url, success){
    $.ajax({
      type:"GET",
      url: url,
      dataType: "jsonp",
      jsonp:"callback",
      success:function(res){
        success(res);
      }
    })
  },
  get2: function(url, data,success){
    $.ajax({
      type:"GET",
      url: url,
      data:data,
      dataType: "jsonp",
      jsonp:"callback",
      success:function(res){
        success(res);
      }
    })
  },

  getCap: function(url, success){
    $.ajax({
      type: 'GET',
      url: url,
      data: {
        refresh: 1
      },
      dataType:'jsonp',
      jsonp:'callback',
      success: function(res){
        success(res);
      }
    });
  }
};var stoneCommon = {
  userAgent: navigator.userAgent,
  count: 120,
  lang: '',
  msg: {
    cn:{
      ms1: '鑾峰彇楠岃瘉鐮�',
      ms2: '閲嶆柊鑾峰彇(',
    },
    en:{
      ms1: 'Get validation code',
      ms2: 'Regain(',
    }
  },
  running: false,
  inputPass: 'input_pass',
  inputError: 'input_error',
  errorCls: '.stone_line_error, .login_pop_error, .stone_input_error',

  getCookieLang: function(){
    stoneCommon.lang = stoneCommon.getCookie('cookieLang') || 'cn';
  },

  setCountDown: function($ele){
    var unale = 'stone_btn_resend_unable',
      msg = stoneCommon.msg[stoneCommon.lang].ms1,
      reGetMsg = stoneCommon.msg[stoneCommon.lang].ms2  + stoneCommon.count +'s)',
      $value = $ele.find('span');

    $value.text(reGetMsg);
    $ele.addClass(unale);

    if(!stoneCommon.running){
      stoneCommon.running = true;
      var intetval = setInterval(function(){
        if(stoneCommon.count > 0){
          stoneCommon.count--;
          stoneCommon.setCountDown($ele);
        }else{
          stoneCommon.count = 120;
          $value.text(msg);
          $ele.removeClass(unale);
          stoneCommon.running = false;
          clearInterval(intetval);
        }
      }, 1000);
    }
  },

  showError: function($ele, msg){
    $ele.removeClass(stoneCommon.inputPass).addClass(stoneCommon.inputError);
    $ele.nextAll(stoneCommon.errorCls).text(msg).show();
  },

  hideError: function($ele){
    $ele.removeClass(stoneCommon.inputError).removeClass(stoneCommon.inputPass);
    $ele.nextAll(stoneCommon.errorCls).text('').hide();
  },

  inputFocus: function($ele){
    $ele.addClass(stoneCommon.inputPass);
  },

  setCookie: function(name, value){
    var mins = 1,
      exp = new Date();
    exp.setTime(exp.getTime() + mins*60*1000);
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
  },

  getCookie: function(name){
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
    if(arr=document.cookie.match(reg))
      return unescape(arr[2]);
    else
      return null;
  },

  delCookie: function(name){
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval = stoneCommon.getCookie(name);
    if(cval != null){
      document.cookie= name + "="+cval+";expires="+exp.toGMTString();
    }
  },

  getUrlParams: function(name){
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
      var pair = vars[i].split("=");
      if(pair[0] == name){
        return pair[1];
      }
    }
    return '';
  },

  addMeta: function(){
    var oMeta = document.createElement('meta'),
      tMeta = document.createElement('meta'),
      rMeta = document.createElement('meta');
    // 淇敼title
    document.title = 'Stone娓告垙骞冲彴';

    // 娣诲姞闄愬埗360鎬ラ€熸祻瑙堝櫒
    oMeta.name = 'renderer';
    oMeta.content = 'webkit|ie-comp|ie-stand';
    document.getElementsByTagName('head')[0].appendChild(oMeta);
    // 娣诲姞description
    tMeta.name = 'description';
    tMeta.content = 'Stone娓告垙骞冲彴鏄敱铚楃墰娓告垙鐮斿彂鐨勯涓笓娉ㄧ簿鍝丳C娓告垙鍙戣骞冲彴,鑷村姏浜庡湪鍏ㄧ悆鑼冨洿鍐呯攧閫夌簿鍝丳C娓告垙锛屼负涓浗鐜╁浠甫鏉ュ鏍风殑浣充綔浣撻獙銆係tone娓告垙骞冲彴涓虹敤鎴锋彁渚涜喘涔般€佷笅杞姐€佹縺娲汇€佺ぞ缇ょ瓑涓€绯诲垪娓告垙浣撻獙鏈嶅姟锛屽畾鏈熷紩杩涙鐗堟父鎴忥紝鍊″娓告垙灏辫鐜╁ソ鐨勩€�';
    document.getElementsByTagName('head')[0].appendChild(tMeta);
    // keywords
    rMeta.name = 'keywords';
    rMeta.content = 'Stone, Stone娓告垙骞冲彴锛孲tone娓告垙骞冲彴瀹樼綉锛孲tone娓告垙骞冲彴涓嬭浇锛孲tone娓告垙骞冲彴娉ㄥ唽锛孲tone娓告垙骞冲彴绀煎寘锛岃湕鐗涙父鎴忓钩鍙帮紝鏂硅垷锛屾柟鑸熺敓瀛樿繘鍖栵紝鏂硅垷鍥芥湇锛屾矙鐩掓父鎴� ';
    document.getElementsByTagName('head')[0].appendChild(rMeta);
  },

  totleFlow: function(){
    var _hmt = _hmt || [];
    (function() {
      var hm = document.createElement("script");
      hm.src = "https://hm.baidu.com/hm.js?58e2a11651a091667cfa215bfa33b893";
      var s = document.getElementsByTagName("script")[0];
      s.parentNode.insertBefore(hm, s);
    })();
  },

  getBrowserVersion: function(){
    var agent = stoneCommon.userAgent,
      isOpera = agent.indexOf("Opera") > -1,
      isIe = agent.indexOf("compatible") > -1 && agent.indexOf("MSIE") > -1 && !isOpera,
      hadShowVerion = stoneCommon.getCookie('hadShowVerion');
    $('body').append('<div class="stone_version">\
                    <div class="stone_version_container">\
                      <a class="stone_chrome" href="http://www.google.cn/chrome/browser/desktop/"></a>\
                      <a class="stone_IE" href="https://support.microsoft.com/zh-cn/help/17621/internet-explorer-downloads"></a>\
                      <div class="stone_continue"></div>\
                    </div>\
                  </div>');

    if (isIe && !hadShowVerion) {
      var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
      reIE.test(agent);
      var fIEVersion = parseFloat(RegExp["$1"]);

      if(fIEVersion < 9){
        $('.stone_version').show();

        $('.stone_continue').click(function(){
          $('.stone_version').hide();
          stoneCommon.setCookie('hadShowVerion', 'true');
        });

      }
    }
  },

  setHolder: function($ele, value){
    var flag = false;

    if($ele.attr('type') == 'password'){
      flag = true;
      $ele.attr('type', 'text');
    }

    $ele.val(value);
    $ele.focus(function(){
      if($ele.val() === value){
        $ele.val('')
      }
      if(flag){
        $ele.attr('type', 'password');
      }

    }).blur(function(){
      if($ele.val() === ''){
        $ele.val(value);
        $ele.attr('type', 'text');
      }
    });
  },

  addClick: function(){
    $('body').keydown(function(e){
      if(e.keyCode ==13){
        if(!stoneHeader.logined){
          $('#submit').trigger('click');
        }
        $('.stone_btn_submit').trigger('click');

      }
    });
  },

  setCookieLang: function(){
    var lang = stoneCommon.getUrlParams('lang') || 'cn';
    stoneCommon.setCookie('cookieLang', lang);
  },


};

$(function(){
  stoneCommon.addMeta();
  stoneCommon.totleFlow();
  stoneCommon.getBrowserVersion();
  stoneCommon.addClick();
  stoneCommon.setCookieLang();
  stoneCommon.getCookieLang();
});var stoneHeader = {
  userInfoUrl: 'https://stoneapi.snail.com/v2/user/info',
  loginUrl: 'https://stoneapi.snail.com/v2/user/security/login',
  logOutUrl: 'https://stoneapi.snail.com/v2/user/security/logout',
  recUrl: 'https://stoneapi.snail.com/v2/search/recommend-word/drop-list',
  serchUrl: 'https://stoneapi.snail.com/v2/search/recommend-word/quick-search',
  defaultUrl: 'https://stoneapi.snail.com/v2/search/recommend-word/keywords',
  logined: false,
  userInfo: null,
  $userName: null,
  $pwd: null,
  $exit: null,
  init: function(current){
    stoneHeader.appendCommon(current);
    stoneHeader.getUserInfo();
    stoneHeader.showSerchDefault();
    $('head').append('<meta name=renderer content=webkit>');
    // stoneHeader.refreshCurrentPage();
  },

  // 鑾峰彇鐢ㄦ埛淇℃伅锛屽彲鐢ㄤ簬鍒ゆ柇鏄惁鐧诲綍
  getUserInfo: function(){
    dataGetter.get(stoneHeader.userInfoUrl, function(res){
      if(res.code == 200){
        stoneHeader.logined = true;
        stoneHeader.userInfo = res;
      }
      stoneHeader.appendUser();
      stoneHeader.addEvent();

    });
  },

  // 娣诲姞澶撮儴鍏叡
  appendCommon: function(current){
    var $header = $('#stone_header'),
      $innerHtml;

    $innerHtml = $('<div class="stone_header">\
                    <div class="stone_container stone_heder_container">\
                      <div class="stone_heaer_cont">\
                        <a href="http://www.iplaystone.com" class="stone_header_logo"></a>\
                        <ul class="stone_header_menu">\
                          <li><a href="http://www.iplaystone.com">棣栭〉</a></li>\
                          <li id="allGames"><a href="http://www.iplaystone.com/store/">娓告垙搴�</a></li>\
                          <li><a href="http://stone.snail.com/social/index.html#/" id="circle">鍦堝瓙</a></li>\
                          <li><a id="center">鍏戞崲涓績</a></li>\
                          <li style="display:none"><a href="http://www.iplaystone.com/download/cn.html">甯姪涓績</a></li>\
                        </ul>\
                        <div class="stone_serch">\
                           <div class="stone_serch_input">\
                              <input type="text" class="serch_input" id="serch_input" placeholder="鎼滅储鍏抽敭璇�"/>\
                              <span class="serch_btn" id="serch_btn"></span>\
                           </div>\
                           <div class="stone_serch_list bor clearfix none">\
                              <div class="serchGame">\
                                <h3>娓告垙锛�</h3>\
                                <ul class="serch_game">\
                                   <li><a href=""><i>鏂硅垷</i>鐢熷瓨杩涘寲</a></li>\
                                </ul>\
                              </div>\
                              <p class="serch_line"></p>\
                              <div class="serchCircle">\
                                <h3>鍦堝瓙锛�</h3>\
                                <ul class="serch_circle">\
                                   <li><a href=""><i>鏂硅垷</i>鐢熷瓨杩涘寲</a></li>\
                                </ul>\
                              </div>\
                           </div>\
                           <div class="stone_serch_recommend bor clearfix none">\
                              <ul>\
                                 <li><a href=""><span class="rec_num">1</span><span class="rec_text">鏂硅垷鐢熷瓨杩涘寲</span></a></li>\
                                 <li><a href=""><span class="rec_num">2</span><span class="rec_text">鏂硅垷鐢熷瓨杩涘寲</span></a></li>\
                                 <li><a href=""><span class="rec_num">3</span><span class="rec_text">鏂硅垷鐢熷瓨杩涘寲</span></a></li>\
                                 <li><a href=""><span class="rec_num">4</span><span class="rec_text">鏂硅垷鐢熷瓨杩涘寲</span></a></li>\
                                 <li><a href=""><span class="rec_num">5</span><span class="rec_text">鏂硅垷鐢熷瓨杩涘寲</span></a></li>\
                                 <li><a href=""><span class="rec_num">6</span><span class="rec_text">鏂硅垷鐢熷瓨杩涘寲</span></a></li>\
                                 <li><a href=""><span class="rec_num">7</span><span class="rec_text">鏂硅垷鐢熷瓨杩涘寲</span></a></li>\
                                 <li><a href=""><span class="rec_num">8</span><span class="rec_text">鏂硅垷鐢熷瓨杩涘寲</span></a></li>\
                              </ul>\
                           </div>\
                        </div>\
                        <div class="stone_user_btn">\
                          <a href="http://a.woniu.com/ad2mEvA3" id="download_cms_btn" class="stone_btn_client">瀹㈡埛绔笅杞�</a>\
                        </div>\
                      </div>\
                      <div class="stone_heder_top">\
                        <div class="stone_header_user">\
                          <div class="stone_user_info"></div>\
                        </div>\
                      </div>\
                  </div>\
                  <div class="login_pop" id="login_pop">\
                    <div class="login_pop_outer">\
                      <i class="login_pop_cancle"></i>\
                      <div class="login_pop_title"><img src="http://www.iplaystone.com/static/common/images/loginPic.png"/></div>\
                      <div class="login_pop_input"><input id="userName" placeholder="鐢靛瓙閭/鎵嬫満鍙�" />\
                        <i class="login_pop_icon login_pop_user"></i>\
                        <div class="login_pop_error"></div>\
                      </div>\
                      <div class="login_pop_input"><input type="password" id="pwd" placeholder="瀵嗙爜" />\
                        <i class="login_pop_icon login_pop_pwd"></i>\
                        <div class="login_pop_error"></div>\
                      </div>\
                      <div class="login_pop_btn"><button id="submit">鐧诲綍</button></div>\
                      <div class="login_pop_box"><a href="http://www.iplaystone.com/static/web/resetPwd.html" class="login_pop_forget">蹇樿瀵嗙爜?</a><span></span><a href="http://www.iplaystone.com/static/web/register.html" target="_blank" class="login_pop_register">绔嬪嵆娉ㄥ唽</a></div>\
                    </div>\
                  </div>\
                  <div class="serch_mask"></div>\
                  <div class="stone_mask" id="stone_mask">\
                  </div>');

    $('#download_cms_btn').attr('src', $('#download_cms').data('cn'))

    $header.append($innerHtml);
    $('.stone_header .stone_header_menu > li').eq(current).addClass('current');
  },
  // 娣诲姞澶撮儴淇℃伅
  appendUser: function(){
    var $user = $('#stone_header .stone_user_info'),
      $userInfo,
      userInfo = stoneHeader.userInfo;

    if(stoneHeader.logined){
      userInfo.nickname = userInfo.nickname || userInfo.username;
      var photoUrl = userInfo.avatar?userInfo.avatar:'http://www.iplaystone.com/static/common/images/defalt.png';
      $userInfo = $('<div class="stone_info_logined">\
                      <span class="stone_nickname" title="'+userInfo.nickname+'">'+ userInfo.nickname +'</span>\
                      <i class="stone_arrow rotate"></i>\
                      <div class="stone_user_dropdown">\
                        <span class="stone_info_img"><img src='+ photoUrl +'></span>\
                        <div class="stone_no_hover" title="'+userInfo.nickname+'">'+ userInfo.nickname +'</div>\
                        <a href="javascript:;" id="perCenter">涓汉涓績</a>\
                        <div id="eixt">閫€鍑虹櫥褰�</div>\
                      </div>\
                   </div>');
      // <a href="http://www.iplaystone.com/static/web/consumeList.html">涓汉涓績</a>\
    }else{
      $userInfo = $('<div class="stone_info_unlogined"><span id="login">鐧诲綍</span><a href="http://www.iplaystone.com/static/web/register.html" id="register" class="margin_left_10">娉ㄥ唽</a></div>');
    }
    $user.empty().append($userInfo);

    $('#login').click(function(){
      // stoneHeader.shwoPop();
      var thisLocation = window.location.href;
      thisLocation = encodeURIComponent(thisLocation);
      window.location.href = 'http://www.iplaystone.com/static/web/login.html?history=' + thisLocation;
    });

    $("body").on('click','#perCenter',function(){
      window.open("http://stone.snail.com/personal/index.html","","top=100,left=500,width=778,height=569");
    })
    $('#eixt').click(function(){
      stoneHeader.logOut();
    });
  },
  logOut: function(){
    dataGetter.get(stoneHeader.logOutUrl, function(res){
      if(res.code == 200){
        stoneHeader.logined = false;
        stoneHeader.userInfo = null;
        stoneHeader.appendUser();
        window.location.href = 'http://www.iplaystone.com';
      }
    });
  },


  // 鐩戝惉浜嬩欢
  addEvent: function(){
    var $login = $('#login'),
      $register = $('#register'),
      $cancle = $('.login_pop_cancle'),
      $submit = $('#submit'),
      $allGames = $('#allGames'),
      $serchInput = $('#serch_input'),
      $serchBtn =$("#serch_btn"),
      $serchMask = $(".serch_mask");

    stoneHeader.$userName = $('#userName');
    stoneHeader.$pwd = $('#pwd');

    $register.click(function(){

    });

    $cancle.click(function(){
      stoneHeader.hidePop();
    });

    $submit.click(function(){
      stoneHeader.doLogin();
    });

    stoneHeader.$userName.focus(function(){
      stoneCommon.hideError($(this));
    }).blur(function(){
      stoneHeader.checkUser();
    });

    stoneHeader.$pwd.focus(function(){
      stoneCommon.hideError($(this));
    }).blur(function(){
      stoneHeader.checkPwd();
    });

    $allGames.click(function(){
      stoneCommon.delCookie('cookie');
    });

    $('#center').click(function(){
      var hrefUrl
      if(stoneHeader.logined){
        hrefUrl ="http://stone.snail.com/cdk/CDKey.html?lang=cn"
      }else{
        hrefUrl="http://www.iplaystone.com/static/web/login.html"
      }
      window.location.href = hrefUrl;
    })

    //鎼滅储
    // 1.榛樿鎺ㄨ崘
    $serchInput.focus(function () {
      if($(this).val() == ''){
        var recHtml = '';
        dataGetter.get(stoneHeader.recUrl, function(res){
          if(res.code == 200){
            for(i in res.data[0].matches){
              recHtml += '<li><a href="'+res.data[0].matches[i].url+'" target="_blank" tabindex="'+i+'"><span class="rec_num">'+(parseInt(i)+1)+'</span><span class="rec_text">'+res.data[0].matches[i].name+'</span></a></li>'
            }
            $(".stone_serch_recommend ul").html(recHtml)
            $(".stone_serch_recommend").removeClass('none')
            $serchMask.show();
          }else{
            console.log(res.message)
          }
        });
      }
    })
    // 2.瀹炴椂鎼滅储
    $serchInput.keyup(function () {
      var text = $(this).val(),
        gameHtml = '',
        circleHtml = '';
      setTimeout(function () {
        dataGetter.get2(stoneHeader.serchUrl,{
          q: $serchInput.val()
        },function(res){
          if(res.code == 200){
            var gameList = res.data[0].matches,
              circleList = res.data[1].matches;

            if(gameList !='' || circleList != ''){  // 浠绘剰鏈夌粨鏋滅殑鏃跺€欐墠鏄剧ず
              $(".stone_serch_recommend").addClass('none');
              $(".stone_serch_list").removeClass('none');
              $serchMask.show();
            }else{
              $(".stone_serch_recommend , .stone_serch_list").addClass('none');
              $serchMask.hide();
            }

            if(gameList !='' && circleList != '') {
              $(".serch_line").show();
            } else{
              $(".serch_line").hide();
            }

            // if(gameList !=''){  // 浠绘剰鏈夌粨鏋滅殑鏃跺€欐墠鏄剧ず
            //   $(".stone_serch_recommend").addClass('none');
            //   $(".stone_serch_list").removeClass('none');
            //   $serchMask.show();
            // }else{
            //   $(".stone_serch_recommend , .stone_serch_list").addClass('none');
            //   $serchMask.hide();
            // }

            // $(".serch_line,.serchCircle").hide();

            if(gameList != ''){
              $(".serchGame").show();
              for(i in gameList){
                gameHtml += '<li><a href="'+gameList[i].url+'" target="_blank" tabindex="'+i+'">'+stoneHeader.evalText(gameList[i].name, text)+'</a></li>'
              }
              $(".serch_game").html(gameHtml)
            }else{
              $(".serchGame").hide();
            }

            if(circleList != ''){
              $(".serchCircle").show();
              for(i in circleList){
                circleHtml += '<li><a href="'+circleList[i].url+'" target="_blank">'+stoneHeader.evalText(circleList[i].name, text)+'</a></li>'
              }
              $(".serch_circle").html(circleHtml)
            }else{
              $(".serchCircle").hide();
            }

          }else{
            console.log(res.message)
          }
        });
      },1000)
    })

    //3.鐐瑰嚮鎼滅储
    $serchBtn.click(function(){
      if($serchInput.val() == ''){
        window.location.href = 'http://stone.snail.com/search/ss.html?name='+ $serchInput.attr('defalttext')
      }else{
        window.location.href = 'http://stone.snail.com/search/ss.html?name='+ $serchInput.val()
      }
    })

    $serchMask.click(function(){
      $(".stone_serch_recommend,.stone_serch_list").addClass('none');
      $serchMask.hide();
    })

    var index = 1;//褰撳墠li
    var search_show = $(".serch_game");
    var type  = 'microblog';//褰撳墠鎼滅储绫诲瀷

    $("#serch_input").keydown(function (event) {//涓婁笅閿幏鍙栫劍鐐�
      var key = event.keyCode;
      if (key == 13) $serchBtn.trigger('click'); /*鍥炶溅鎼滅储*/
    });
  },
  // 鎼滅储榛樿鍏抽敭璇�
  evalText: function(text,code){
    keyWordReg = "/(" + code + ")/g";
    return text.replace(eval(keyWordReg),'<i>$1</i>');
  },
  showSerchDefault: function (){
    dataGetter.get(stoneHeader.defaultUrl, function(res){
      if(res.code == 200){
        $("#serch_input").attr({'placeholder':res.data,'defaltText':res.data})
      }else{
        console.log(res.message)
      }
    });
  },
  // 鏄剧ず鐧诲綍寮规
  showPop: function(){
    var $loginPop = $('.login_pop'),
      $mark = $('.stone_mask');
    $mark.show();
    $loginPop.fadeIn();
  },
  // 闅愯棌鐧诲綍寮规
  hidePop: function(){
    var $loginPop = $('.login_pop'),
      $mark = $('.stone_mask');
    $mark.hide();
    $loginPop.fadeOut();
  },
  // 鐧诲綍
  doLogin: function(){
    dataGetter.post(stoneHeader.loginUrl,{
      account: stoneHeader.$userName.val(),
      password: stoneHeader.$pwd.val()
    }, function(res){
      if(res.code == 200){
        stoneHeader.logined = true;
        stoneHeader.getUserInfo();
        stoneHeader.hidePop();

        location.reload();
      }else{
        stoneCommon.showError(stoneHeader.$pwd, '鐢ㄦ埛鍚嶆垨瀵嗙爜閿欒');
      }
    });
  },
  // 楠岃瘉鐢ㄦ埛淇℃伅
  checkUser: function(){
    var userName = stoneHeader.$userName.val();
    if(userName){
      stoneCommon.hideError(stoneHeader.$userName);
    }else{
      stoneCommon.showError(stoneHeader.$userName, '鐢ㄦ埛鍚嶄笉鑳戒负绌�');
    }
  },
  // 楠岃瘉瀵嗙爜
  checkPwd: function(){
    var pwdVal = stoneHeader.$pwd.val();
    if(pwdVal){
      stoneCommon.hideError(stoneHeader.$pwd);
    }else{
      stoneCommon.showError(stoneHeader.$pwd, '瀵嗙爜涓嶈兘涓虹┖');
    }
  },

  refreshCurrentPage: function(){
    location.reload();
  }

};
var stoneFooter = {
  lang: '',
  msg:{
    cn:{
      txt1: '瀹㈡埛绔�',
      txt2: '寮€鍙戣€呭钩鍙�',
      txt3: '鐢ㄦ埛鍗忚',
      txt4: 'Stone-game',
      txt5: 'Stone娓告垙骞冲彴',
      txt6: ' 漏 2015 铚楃墰鏁板瓧棣欐腐鏈夐檺鍏徃 鐗堟潈鎵€鏈�',
      txt7: 'Stone瀹樻柟绀剧兢 :',
      txt8: '涓嶆濂界帺锛�',
    },
    en:{
      txt1: 'Client',
      txt2: 'Stone Partner Platform',
      txt3: 'User Agreement',
      txt4: 'Stone-game',
      txt5: 'Stone Game Platform',
      txt6: '漏 Snail Digital (HK) Limited. All rights reserved.聽',
      txt7: 'Stone Official Community : ',
      txt8: 'More than fun!',
    },
    tw:{
      txt1: '瀹㈡埗绔�',
      txt2: '闁嬬櫦鑰呭钩鑷�',
      txt3: '浣跨敤鑰呭崝瀹�',
      txt4: 'Stone-game',
      txt5: 'Stone閬婃埐骞宠嚭',
      txt6: '漏2015铦哥墰鏁镐綅棣欐腐鏈夐檺鍏徃鐗堟瑠鎵€鏈�',
      txt7: 'StoneStone瀹樻柟绀剧兢 : ',
      txt8: '鍏ㄧ悆濂介亰鎴诧紝鐩″湪Stone锛�',
    }
  },
  init: function(){
    setTimeout(function(){
      stoneFooter.appendFooter();
    }, 100);
  },

  appendFooter: function(){

    stoneFooter.lang = stoneCommon.getCookie('cookieLang');

    if(stoneFooter.lang){
      var $footer = $('#stone_footer'),
        $aFooter = $('<div class="stone_footer">\
                          <div class="stone_container stone_footer_container">\
                              <h1>\
                                <span>'+ stoneFooter.msg[stoneFooter.lang].txt7 +'</span>\
                                <a target="_blank" href="http://weibo.com/u/6179228959?is_hot=1" class="stont_footer_item stont_footer_weibo">'+ stoneFooter.msg[stoneFooter.lang].txt4 +'</a>\
                                <div class="stont_footer_item stont_footer_weixin">\
                                  <span>'+ stoneFooter.msg[stoneFooter.lang].txt5 +'</span>\
                                  <div></div>\
                                </div>\
                              </h1>\
                              <h1 class="stone_footer_stone">\
                                <span>'+ stoneFooter.msg[stoneFooter.lang].txt8 +'</span>\
                                <a target="_blank" href="http://www.iplaystone.com/static/web/agreement.html" class="stont_footer_item">'+ stoneFooter.msg[stoneFooter.lang].txt3 +'</a>\
                                <a target="_blank" href="http://partner.iplaystone.com/cn/index.html" class="stont_footer_item">'+ stoneFooter.msg[stoneFooter.lang].txt2 +'</span>\
                                <a target="_blank" href="http://www.iplaystone.com" class="stont_footer_item">'+ stoneFooter.msg[stoneFooter.lang].txt1 +'</a>\
                              </h1>\
                              <h1 class="margin_top_10">'+ stoneFooter.msg[stoneFooter.lang].txt6 +'</h1>\
                          </div>\
                        </div>')
      $footer.empty().append($aFooter);
    }else{
      setTimeout(function(){
        stoneFooter.init();
      }, 100)
    }
  }

};

$(function(){
  var userAgent = navigator.userAgent;
  var isStone = false;
  var requestSource = '';
  if(userAgent.search(/Stone/) == -1){//pc
    stoneFooter.init();
  }
});
