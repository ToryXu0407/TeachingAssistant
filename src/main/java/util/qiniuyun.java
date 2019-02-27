package util;

import annotation.UnCheckLogin;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

/**
 * @Author: toryxu
 * @Date: 2019/2/15 0015 12:33
 * @Version 1.0
 */
public class qiniuyun {
    /**
     * 获得七牛云token
     */
    public static String getToken(){
        String accessKey = "yrNSzGN_IG22aZGa686fKSOilJGXlRoRPUJkGIvx";
        String secretKey = "1JtRvtB0URA-M3S3lL4SmckJ1WwbiAylqyS4NqqI";
        //要上传的空间
        String bucket = "hellootryxu";
        //密钥配置
        Auth auth = Auth.create(accessKey, secretKey);
        String token = auth.uploadToken(bucket);
        return token;
    }

    /**
     * 根据图片路径上传图片
     * @return
     */
    public static String upload(String url,String fileName) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
//...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
//如果是Windows情况下，格式是 D:\\qiniu\\test.png
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key =fileName;
        String uptoken = getToken();
        try {
            Response response = uploadManager.put(url, key,uptoken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            String imageUrl ="http://oss.yiqihappy.top/"+fileName;
            return imageUrl;
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return null;
    }
}
