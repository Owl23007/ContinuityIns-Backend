package org.ContinuityIns.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import jakarta.websocket.Endpoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class AliOssUtil {
    @Value("${aliyun.oss.endpoint}")
    private  String endPoint;

    @Value("${aliyun.oss.bucketName}")
    private  String bucketName;

    @Value("${aliyun.oss.accessKeyId}")
    private  String accessKeyId;

    @Value("${aliyun.oss.accessKeySecret}")
    private  String accessKeySecret;


    // 节点域名
    private  final String EndPoint = endPoint;
    // 存储空间名
    private  final String Bucket_Name = bucketName;
    // 访问密钥id
    private  final String ACCESS_KEY_ID = accessKeyId;
    // 访问密钥key
    private  final String ACCESS_KEY_SECRET = accessKeySecret;

    public Map<String, String> generatePolicy(String dir, long maxSize) {
        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
        try {
            long expireTime = 30; // 30分钟过期
            long expireEndTime = System.currentTimeMillis() + expireTime * 60 * 1000;
            Date expiration = new Date(expireEndTime);

            PolicyConditions policyConditions = new PolicyConditions();
            policyConditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, maxSize);
            policyConditions.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
            String postPolicy = ossClient.generatePostPolicy(expiration, policyConditions);
            String signature = ossClient.calculatePostSignature(postPolicy);

            Map<String, String> respMap = new HashMap<>();
            respMap.put("accessid", accessKeyId);
            respMap.put("policy", postPolicy);
            respMap.put("signature", signature);
            respMap.put("dir", dir);
            respMap.put("host", "https://" + bucketName + "." + endPoint);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));
            return respMap;
        } finally {
            ossClient.shutdown();
        }
    }
}
