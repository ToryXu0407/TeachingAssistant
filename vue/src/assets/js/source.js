/**
 *绉诲姩绔噰闆嗚剼鏈�
 *
 * 鎵嬫満娴忚鍣ㄤ笉鑳藉疄鐜颁簰閫氾紝濡傛灉鎵嬫満绔闂〉闈� 鎹㈡祻瑙堝櫒缁х画璁块棶锛屽垯鏁版嵁涓㈠け
 *
 * 鍦≒C涓� chrome涓嶈兘瀹炵幇flash鏁版嵁浜掗€�
 *
 *util 閫氱敤鏂瑰紡浜嬩欢
 *
 * */
setTimeout(function () {


  (function (doc, win, undefined) {
    var userAgent = navigator.userAgent;
    var isStone;
    var uid = '';
    if(userAgent.search(/Stone/) == -1){
      isStone = 0;
    }else{
      isStone = 1;
      uid = stone.getUID();
    }
    if (!win['WNMF']) win['WNMF'] = {};
    // if (WNMF.isLoadTy) return;

    var hasOwn = Object.prototype.hasOwnProperty;
    var _toString = function (obj) {
      return Object.prototype.toString.call(obj);
    };
    var _extend = function () {
      var deep = false,
        target = arguments[0] || {},
        i = 1,
        len = arguments.length,
        name, obj, copy, src, clone;
      if (typeof target === "boolean") {
        deep = arguments[0];
        target = arguments[1] || {};
        i = 2;
      }
      for (; i < len; i++) {
        obj = arguments[i];
        for (name in obj) {
          if (hasOwn.call(obj, name)) {
            copy = obj[name];
            src = target[name];
            if (typeof copy == 'undefined') {
              continue;
            }
            if (deep == false) {
              target[name] = copy;
            } else {
              if (_toString(copy) == "[object Object]") {
                clone = src && _toString(src) == "[object Object]" ? src : {}
                target[name] = _extend(true, clone, copy);
              } else if (_toString(copy) == "[object Array]") {
                clone = src && _toString(src) == "[object Array]" ? src : []
                target[name] = _extend(true, clone, copy);
              } else {
                target[name] = copy;
              }
            }
          }
        }
      }
      return target;
    };

    var util = (function () {
      //鏁板瓧杩囨护
      var arrayFilter = function (arr, filter) {
        var res = [], item;
        for (var i = 0, len = arr.length; i < len; i++) {
          item = arr[i];
          if (filter(item)) {
            res.push(item);
          }
        }
        return res;
      };
      //杩斿洖涓€涓厓绱犱笅鐨勬墍鏈夊瓙鑺傜偣
      var children = doc.documentElement.children ? function (el) {
        return el.children;
      } : function (el) {
        return arrayFilter(el.children, function (el) { return !!el.tagName; });
      };

      var getChild = function (ele, tab, fn) {
        var childs = children(ele);
        if (childs.length == 0) return;
        var item;
        for (var i = 0, len = childs.length; i < len; i++) {
          item = childs[i];
          if (fn(item)) {
            tab.push(item);
          }
          getChild(item, tab, fn);
        }
      };

      var addEvent = doc.attachEvent ? function (element, event, fn) {
        element.attachEvent("on" + event, function () {
          fn.call(element);
        })
      } : function (element, event, fn) {
        element.addEventListener(event, fn, false)
      };

      return {
        addEvent: addEvent,
        setCookie: function (k, v, t, p, m) {
          var r = k + "=" + encodeURIComponent(v);
          if (t) {
            if (typeof t == "string") {
              t = new Date(t.replace(/-/g, "/").replace(/\.\d+$/, ""));
            }
            r += "; expires=" + t.toGMTString();
          }
          if (p) {
            r += "; path=" + p;
          }
          if (m) {
            r += "; domain=" + m;
          }
          doc.cookie = r;
          return this;
        },
        removeCookie: function (name, path, domain) {
          this.setCookie(name, "", new Date(0), path, domain);
        },
        getCookie: function (name, str) {
          var re = new RegExp("(?:; )?" + name + "=([^;]*);?");
          if (str === undefined) {
            str = doc.cookie;
          }
          if (re.test(str)) {
            return decodeURIComponent(RegExp["$1"]);
          } else {
            return "";
          }
        },
        sendRequest: function (a, f) {
          var d = new Image(1, 1);
          d.onerror = d.onload = function () {
            d = null;
            !!f && f();
          };
          d.src = a;
        },
        bindClickAction: function (fn) {
          var eles = [];
          if (win.jQuery) {
            jQuery("[wn_tj_click_href]").click(fn);
            return;
          }
          if (doc.querySelectorAll) {
            eles = doc.querySelectorAll("[wn_tj_click_href]");
          } else {
            getChild(doc.body, eles, function (ele) {
              return ele.getAttribute("wn_tj_click_href");
            });
          }
          for (var i = 0, len = eles.length; i < len; i++) {
            addEvent(eles[i], "click", fn);
          }
        },
        obj2Str: function (obj) {
          var arr = [];
          for (var i in obj) {
            arr.push(i + "=" + obj[i]);
          }
          return arr.join('&');
        },
        inArray: function (array, str) {
          for (var i = 0, len = array.length; i < len; i++) {
            if (str == array[i]) {
              return i;
            }
          }
          return -1;
        },
        scriptLoad: function (url, opts, callback) {
          opts = opts || {};
          var node = doc.createElement("script");
          node.type = "text/javascript";
          node.src = url;
          for (var i in opts) {
            if (hasOwn.call(opts, i)) {
              node.setAttribute(i, opts[i]);
            }
          }
          var head = doc.getElementsByTagName("head")[0];
          if (!head) {
            head = doc.createElement("head");
            doc.documentElement.appendChild(head);
          }
          head.appendChild(node);
          if (!!callback) {
            node.onload = node.onerror = node.onreadystatechange = function () {
              if (/loaded|complete|undefined/.test(node.readyState)) {
                node.onload = node.onerror = node.onreadystatechange = null;
                node = null;
                callback();
              }
            };
          }
          return node;
        },
        flashVesion: (function () {
          if (navigator.plugins && navigator.mimeTypes.length) {
            var x = navigator.plugins["Shockwave Flash"];
            if (x) {
              return 1;
            }
          }
          else { /// Win IE (non mobile)
            var axo;
            try {
              axo = new window.ActiveXObject("ShockwaveFlash.ShockwaveFlash.7");
            } catch (e) {
              try {
                axo = new window.ActiveXObject("ShockwaveFlash.ShockwaveFlash.6");
              } catch (e) { }
              try {
                axo = new window.ActiveXObject("ShockwaveFlash.ShockwaveFlash");
              } catch (e) { }
            }
            if (axo != null) {
              return 1;
            }
          }
          return 0;
        })(),
        replace_: function (val) {
          var ary = val.split('|');
          for (var i = 0, len = ary.length; i < len; i++) {
            if (ary[i] == '-')
              ary[i] = '';
          }
          return ary.join('|');
        },
        getJsNode: function () {
          var scripts = doc.getElementsByTagName("script");
          var len = scripts.length;
          var item;
          while (len--) {
            item = scripts[len];
            if (item.src && item.src.indexOf('statistics_ty') != -1) {
              return item;
            }
          }
          return null;
        },
        getSearch: function (key, str) {
          return new RegExp("[?:; &]*" + key + "=([^&?=]*)&?").test(str || (location.search || '')) ? decodeURIComponent(RegExp["$1"]) : "";
        }
      }
    })();

    if (!doc.getElementsByClassName) {
      doc.getElementsByClassName = function (className) {
        var children = doc.getElementsByTagName('*');
        var res = [];
        for (var i = 0, len = children.length; i < len; i++) {
          var child = children[i];
          var classNames = child.className.split(' ');
          for (var j = 0, jl = classNames.length; j < jl; j++) {
            if (classNames[j] == className) {
              res.push(child);
              break;
            }
          }
        }
        return res;
      };
    };

    var _cookieObj = {};
    var getWNCookie = function (ck) {
      if (location.hostname.indexOf('.woniu.com') != -1) {
        ck({
          w_wd: util.getCookie("WNAD_wd") || "-",
          w_sd: util.getCookie("WNAD_sd") || "-",
          w_ud: util.getCookie("WNAD_ud") || "-",
          w_td: util.getCookie("WNAD_td") || "-",
          w_time: util.getCookie("WNAD_time")
        });
      } else {
        util.scriptLoad("http://1tj.woniu.com/ck?jscall=WNMF.wnCookie_ty", null, function () { ////////////xjw ////////////浠庤湕鐗涘煙澶嶅埗鍒板叾浠栧煙鍚�
          ck(_cookieObj);
        });
      }
    };

    _extend(WNMF, {
      isLoadTy: true,
      wnCookie_ty: function (str) {
        str = decodeURIComponent(str);
        _cookieObj = {
          w_wd: util.getCookie("WNAD_wd", str) || "-",
          w_sd: util.getCookie("WNAD_sd", str) || "-",
          w_ud: util.getCookie("WNAD_ud", str) || "-",
          w_td: util.getCookie("WNAD_td", str) || "-",
          w_time: util.getCookie("WNAD_time", str)
        }
      }
    });

    //閫氱敤 鍙傛暟
    var target_href_key = 'jz_flow';
    var jz_flow_id = util.getSearch(target_href_key);

    var domain = doc.domain;
    var rootDomain = '.' + domain.split(/\.+/).slice(-2).join('.');
    var nowTime = +new Date();

    (function () {
      if (jz_flow_id) {
        util.setCookie(target_href_key, jz_flow_id, new Date(nowTime + 7 * 24 * 60 * 60 * 1000), '/', rootDomain);
      } else {
        jz_flow_id = util.getCookie(target_href_key);
      }
    })();

    // 鍏ㄦ皯钀ラ攢
    var ty_flow_key = 'ty_flow'; // 鍏ㄦ皯钀ラ攢鎺ㄥ箍閾炬帴keyName
    var ty_flow_val = util.getSearch(ty_flow_key);
    //*****璁剧疆cookie鏈夊煙鍚嶅己鍒�

    (function () {
      if (ty_flow_val) {
        var expires = new Date(nowTime + 30 * 60 * 1000);
        var str = util.getCookie(ty_flow_key);
        if (str) {
          var tab = str.split(';');
          var index = util.inArray(tab, ty_flow_val);
          if (index !== -1) {
            tab.splice(index, 1);
          }
          tab.push(ty_flow_val);
          util.setCookie(ty_flow_key, tab.join(';'), expires, '/', '.snail.com');
        } else {
          util.setCookie(ty_flow_key, ty_flow_val, expires, '/', '.snail.com');
        }
      } else {
        ty_flow_val = util.getCookie(ty_flow_key);
      }
    })();

    //鑴氭湰缁熻
    (function () {
      /**
       *pro_domain瀵硅薄鐢ㄦ潵瀛樻斁缃戠珯鍩熷悕鍙婂叾瀵瑰簲鐨勪骇鍝�
       */
      var pro_domain = {
        domainList: '9yin.woniu.com,m.panda.woniu.com,www.woniu.com,9yinbbs.woniu.com,panda.bbs.woniu.com,9yin.jishi.woniu.com,mall.snail.com,panda.woniu.com,sg.9yin.woniu.com,9yang.woniu.com,9yang.bbs.woniu.com,hj.woniu.com,dg.woniu.com,mm.woniu.com,5jq.bbs.woniu.com,app.snail.com,m.app.snail.com,vc.woniu.com,hj.bbs.woniu.com,qe.woniu.com,sanguo.woniu.com,dhh.woniu.com,www.ageofwushu.com,mm.bbs.woniu.com,9yin.gh.woniu.com,act9y.bfage.com,5jq.woniu.com,tz.woniu.com,lz.woniu.com,hero2.woniu.com,m.mj.woniu.com,m.xzh.woniu.com,xzh.woniu.com,xzh.bbs.woniu.com,hero2.bbs.woniu.com,m.sanguo.woniu.com,hhweb.woniu.com,mj.woniu.com,ddp.woniu.com,lz.bbs.woniu.com,yyx.woniu.com,dsg.woniu.com,mj.bbs.woniu.com,m.ddp.woniu.com,mm.gh.woniu.com,yyx.bbs.woniu.com,ty.bbs.woniu.com,hj.gh.woniu.com,ddp.bbs.woniu.com,tg.9yin.woniu.com,tg.mm.woniu.com,tg.hj.woniu.com,tg.dg.woniu.co,tg.qe.woniu.com,tg.dsg.woniu.com,tg.x.woniu.com,tg.dwx.woniu.com,9yangsy.woniu.com,9yindp.woniu.com,9yinsy.woniu.com,aoa.woniu.com,bang.9yin.woniu.com,dwx.woniu.com,gyc.woniu.com,heart.woniu.com,hero.woniu.com,m.9yangsy.woniu.com,m.9yindp.woniu.com,m.9yinsy.woniu.com,m.gyc.woniu.com,m.heart.woniu.com,mhx.woniu.com,m.rxtz.woniu.com,m.ss.woniu.com,m.yyx.woniu.com,m.zt.woniu.com,panda4k.woniu.com,rxtz.woniu.com,sg.hj.woniu.com,ss.woniu.com,tg.5jq.woniu.com,tg.aoa.woniu.com,tg.dg.woniu.com,tg.dhh.woniu.com,tg.hero.woniu.com,tg.lz.woniu.com,tg.panda.woniu.com,tg.tz.woniu.com,tg.x.woniu.com,ty.woniu.com,wulin.9yin.woniu.com,x.woniu.com,zt.woniu.com,zt.bbs.woniu.com,9yinsy.bbs.woniu.com,rxtz.bbs.woniu.com,9yindp.bbs.woniu.com,gyc.bbs.woniu.com,9yangsy.bbs.woniu.com,9yin.mall.snail.com'.split(','),
        gameidList: [10, 38, 11, 10, 38, 10, 28, 38, 10, 41, 41, 17, 12, 14, 4, 36, 36, 1, 17, 15, 20, 18, 10, 14, 10, 10, 4, 7, 8, 30, 29, 31, 31, 31, 30, 20, 18, 29, 34, 8, 14, 20, 29, 34, 14, 14, 27, 17, 34, 10, 14, 17, 12, 15, 20, 21, 22, 53, 58, 54, 3, 10, 22, 40, 57, 9, 53, 58, 54, 40, 57, 16, 44, 60, 14, 42, 38, 44, 17, 60, 4, 3, 12, 18, 9, 8, 38, 7, 21, 27, 10, 21, 42, 42, 54, 44, 58, 40, 53, 10]
      };

      /**
       *褰撳墠浜у搧ID涓�0鍒欒〃绀烘棤娉曞尯鍒嗕骇鍝�
       *  */
      var pid = util.getCookie("1TJ_pid");
      var pro = (function () {
        var index = util.inArray(pro_domain.domainList, location.hostname);
        if (index != -1) {
          return pro_domain.gameidList[index];
        }
        return 0;
      })();
      util.setCookie('1TJ_pid', pro, '', '/', rootDomain);

      var option = {
        openID: 'http://1tj.woniu.com/_u.gif',
        requestUrl: 'http://1tj.woniu.com/_t.gif',
        eventUrl: "http://1tj.woniu.com/_e.gif",
        domainName: location.hostname,
        mediaKey: pro ? "mr_" + pro + "_" : (function () {
          /*
                   * 濯掍粙
                   * 鏍规嵁鍩熷悕鍔犲瘑 绋嶅井鍔犲瘑涓�,- -md5鍔犲瘑绠楁硶澶暱浜�
                   * wwww.qq.com 姝ｅ垯杩囧悗 鍓╀笅 www 鍔犲瘑杩囧悗:mr_119119119_
                   * vip.woniu.com 姝ｅ垯杩囧悗 鍓╀笅 vip
                   * 鏍规嵁瀛愬煙鍚嶅敮涓€鍐檆ookie
                   * */
          var hostname = location.hostname, key = [];
          var a = hostname.replace(/\.([^\.]+)\.([^\.]+)$/, '');
          for (var i = 0, len = a.length; i < len; i++) {
            key.push(a.charCodeAt(i));
          }
          return "mr_" + key.join('') + "_";
        })(),
        tsKey: pro ? "1TJ_S_P" : '1TJ_S',
        nowAddMouth: function (n) {
          var d = new Date(), m = d.getMonth();
          d.setMonth(m + n * 1);
          return d;
        },
        getNaid: function () {
          var naid = util.getCookie('naid');
          var useInfo = util.getCookie('GWP_CommonUserInfo');
          if (!!useInfo) {
            naid = useInfo.split('&&')[1] + '_';
          }
          return naid;
        }
      };
      option.cookieDomain = pro == 0 ? option.domainName : rootDomain;
      option.now6Mouth = option.nowAddMouth(6);

      var enterTime = nowTime;
      //"http://tech.qq.com/a/20140606/006794.htm".referDomain  涓� tech.qq.com
      var referDomain = doc.referrer && doc.referrer.match('\/\/([^\/]+)')[1] ? doc.referrer.match('\/\/([^\/]+)')[1] : "";
      //鐭晥cookie 褰撳墠涓诲煙鍚嶈韩浠絚ookie
      var TJ_S = util.getCookie(option.tsKey);//鐭晥鏋渃ookie
      //mr_XXX_1TJ_enter  杩涘叆褰撳墠瀛愬煙鍚嶇殑鐨勯〉闈㈡椂闂�
      var TJ_enter = util.getCookie(option.mediaKey + '1TJ_enter') * 1;
      //mr_XXX_1TJ_key  濯掍粙绫诲瀷 1_1231221312    key_鏃堕棿
      var mr_type = util.getCookie(option.mediaKey + "1TJ_key");
      var mr_value = util.getCookie(option.mediaKey + "1TJ_value");
      var mr_cookie = mr_type ? {
        key: mr_type.split('_')[0] * 1,
        enterTime: mr_type.split('_')[1] * 1,
        value: mr_value
      } : {};

      /**
       *鏀瑰彉鐭晥cookie modify jiyj 閫氱敤閲囬泦鑴氭湰鐭晥cookies瑙勫垯鏀瑰彉锛屽彉鎴愪細璇濈骇 鍏ㄥ煙鍚嶅叡浜�
       **/
      var changeIden = function () {
        util.setCookie(option.tsKey, enterTime, '', '/', option.cookieDomain);
        util.setCookie(option.mediaKey + '1TJ_enter', enterTime, '', '/', option.cookieDomain);
      };
      /**
       *鏀瑰彉鍏抽敭瀛�, 渚濊禆cookie WNAD_wd
       *  */
      var changeKW = function (mr, time) {
        if (mr.key == 1 || mr.key == 2) {
          var kw = util.getCookie('WNAD_wd');
          if (mr.key == 2) {
            kw = /(q=[^&]+)/.test(doc.referrer) ? RegExp.$1 : /(query=[^&]+)/.test(doc.referrer) ? RegExp.$1 : /(wd=[^&]+)/.test(doc.referrer) ? RegExp.$1 : "";
          }
          util.setCookie(option.mediaKey + 'k', kw, time || "", '/', option.cookieDomain);
        }
      };
      /**
       *鏀瑰彉濯掍粙, 渚濊禆cookie WNAD_wd
       *  */
      var changeMr = function (mr) {
        util.setCookie(option.mediaKey + "1TJ_key", mr.key + "_" + enterTime, option.now6Mouth, '/', option.cookieDomain);
        util.setCookie(option.mediaKey + "1TJ_value", mr.value, option.now6Mouth, '/', option.cookieDomain);
        changeIden();
        changeKW(mr, option.now6Mouth);
      };

      getWNCookie(function (obj) {
        util.setCookie("WNAD_wd", obj.w_wd, '', '/', rootDomain);
        util.setCookie("WNAD_ud", obj.w_ud, '', '/', rootDomain);
        util.setCookie("WNAD_sd", obj.w_sd, '', '/', rootDomain);
        util.setCookie("WNAD_td", obj.w_td, '', '/', rootDomain);

        if (!TJ_S || !TJ_enter) { //cookie涓㈠け
          changeIden();
        } else {
          //30鍒嗛挓鍚庯紝鎴栬€呰繃澶�
          if ((enterTime - TJ_enter > 30 * 60 * 1000) || (new Date().getDate() - new Date(TJ_enter).getDate() > 0)) {
            changeIden();
          } else {
            if (pro) {
              if (pro != pid) {
                changeIden();
              }
            }
          }
        }

        /**
         *鍒拌揪鏂伴〉闈㈢殑鏃跺€欒绠楀獟浠嬪€�  褰撳墠椤甸潰鐨勫獟浠� 锛岃繖杈逛笉鍋氬獟浠嬬殑鏀瑰彉
         *  */
        var mr = (function () {
          var sek = /(bing.com|google.com|baidu.com|sogou.com|soso.com|yahoo.com|so.com)/;
          var adk = /(dd.woniu.com|dd.snailgame.com|dd.playsnail.com)/;
          var ink = /(snail.com|woniu.com)/;
          if (obj.w_wd && obj.w_wd != '-') {
            return {
              key: 1,
              value: adk.test(referDomain) ? referDomain : '' //鐧惧害绛変腑闂磋烦杞�
            }
          }
          if (referDomain && referDomain != location.hostname) {
            return {
              key: sek.test(referDomain) ? 2 : adk.test(referDomain) ? 1 : ink.test(referDomain) ? 5 : 4,
              value: referDomain
            }
          }
          return { key: 3, value: '' };
        })();

        if (!mr_cookie.key || mr_cookie.key == 3) {
          changeMr(mr);
        } else {
          if (mr.key == 1) {
            if (mr_cookie.key != -1) {
              changeMr(mr);
            }
          } else if (mr.key == 2) {
            if (mr_cookie.key > 2) {
              changeMr(mr);
            } else if (mr_cookie.key == 2) {
              if (referDomain != mr_value && referDomain != location.hostname) {//鏉ユ簮鍙樺寲
                changeMr(mr);
              } else {
                changeKW(mr);//鏇存柊鍏抽敭瀛�
              }
            }
          } else if (mr.key == 4 || mr.key == 5) {
            var _time = mr_cookie.enterTime;
            if (mr_cookie.key == 2) {
              if (enterTime - _time > 30 * 60 * 1000) {
                changeMr(mr);
              }
            } else if (mr_cookie.key == 4 || mr_cookie.key == 5) {
              if (referDomain != mr_value && referDomain != location.hostname) {
                changeMr(mr);
              }
            }
          }
        }

        util.sendRequest(option.openID, function () {
          var data = {
            lh: encodeURIComponent(location.href) || "",
            re: encodeURIComponent(doc.referrer.replace(/&/ig, "%26").replace(/\|/ig, "%7C")) || "",
            mt: util.getCookie(option.mediaKey + "1TJ_key").split('_')[0],
            kw: (function () {
              var _k = util.getCookie(option.mediaKey + "k");
              if (!!_k) {
                if (_k.indexOf('=') > -1) {
                  return _k.split('=')[1];
                } else {
                  return _k;
                }
              } else {
                return '';
              }
            })(),
            mk: referDomain || "",
            scT: util.getCookie(option.tsKey),
            mr: util.getCookie(option.mediaKey + "1TJ_value"),
            // nd: option.getNaid(),
            nd: isStone == 1? uid: option.getNaid(),
            w: screen.width + "*" + screen.height,
            t: encodeURIComponent(doc.title) || "",
            c: doc.characterSet || "",
            f: util.flashVesion,
            y: navigator.language || navigator.browserLanguage || "",
            //浜嬩欢娴�
            j: jz_flow_id

          };

          util.bindClickAction(function () {
            data.l = this.getAttribute('wn_tj_click_href');//瓒呴摼鎺ヨ璺宠浆鐨勭洰鏍囬〉
            data.e = this.getAttribute('wn_tj_click_excel');//excel閲岄潰鐨勮鍒�   鏍囩
            data.n = this.getAttribute('wn_tj_click_id');//鍦堝瓙ID 鍥剧墖鍦板潃 鍏宠仈key
            data.gd = this.getAttribute('wn_tj_click_gameId');
            data.b = isStone;
            util.sendRequest(option.eventUrl + "?" + util.obj2Str(data));
          });

          var orderNo = (function () {
            if (win.jQuery) {
              return jQuery('.sx_ul02:eq(0) span:eq(1)').html();
            } else if (doc.querySelector) {
              var node = doc.querySelector('.sx_ul02');
              if (node) {
                node = node.querySelectorAll('span')[1];
                if (node) return node.innerHTML;
              }
            } else {
              var ta = doc.getElementsByClassName("sx_ul02");
              if (ta.length > 0) {
                var tb = ta[0].getElementsByTagName("span");
                if (tb.length > 1) {
                  return tb[1].innerHTML;
                }
              }
            }
          })();

          util.sendRequest(option.requestUrl + "?" + util.obj2Str(data)
            + '&' + util.obj2Str({
              g: util.getCookie('WNAD_wd'),
              sd: util.getCookie('WNAD_sd'),
              td: util.getCookie('WNAD_td'),
              u: util.getCookie(option.tsKey),
              o: orderNo || '',
              b: isStone
            })
          );
        });
      })
    })();
  })(document, this);
},1000)
