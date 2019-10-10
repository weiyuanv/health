package com.wei.common;

/**
 * @version V1.0
 * @author: weiyuan
 * @date: 2019/10/10 14:40
 * @description:
 */

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;

public class QiniuTest {
    @Test
    public void upload(){

        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "2Pae2aICzB_XK-QtxzQqFIP0QzYPdyfP0AZ2-KZu";
        String secretKey = "qO_vO9FpkgP6rtxztUiVBUjer0BavM7tOhEL3zfO";
        String bucket = "health-83";
        //需要上传的文件地址，如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "e:\\girl1.jpg";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;

        Auth auth = Auth.create(accessKey, secretKey);
        //进行授权认证
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }

    }
}
