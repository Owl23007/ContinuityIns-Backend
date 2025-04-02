package org.ContinuityIns.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class AliOssUtil {
    @Value("${CDNPoint}")
    private String CDNPoint;

    @Value("${aliyun.oss.endpoint}")
    private String endPoint;

    @Value("${aliyun.oss.bucketName}")
    private String bucketName;

    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.roleArn}")
    private String roleArn;

    private OSS ossClient;

    @PostConstruct
    public void init() {
        ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
    }

    @PreDestroy
    public void destroy() {
        if (ossClient != null) {
            ossClient.shutdown();
            log.info("OSS 客户端已关闭");
        }
    }

    public String getCDNUrl(String objectName) {
        return CDNPoint + "/" + objectName;
    }

    /**
     * 生成签名URL（适用于上传或下载文件）
     *
     * @param objectName 文件在OSS中的路径（例如：exampledir/exampleobject.png）
     * @param expireTime URL的有效时间（单位：秒）
     * @param method     HTTP方法（"PUT"表示上传，"GET"表示下载）
     * @return 签名URL
     */
    public String generatePresignedUrl(String objectName, int expireTime, String method) {
        try {
            GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, objectName);
            request.setMethod(com.aliyun.oss.HttpMethod.valueOf(method));
            request.setExpiration(new Date(System.currentTimeMillis() + expireTime * 1000L));
            URL url = ossClient.generatePresignedUrl(request);
            return url.toString();
        } catch (Exception e) {
            log.error("生成签名URL失败", e);
            throw new RuntimeException("生成签名URL失败", e);
        }
    }

    /**
     * 生成Post Policy（适用于限制文件大小、类型等）
     *
     * @param dir         文件上传的路径前缀
     * @param fileName    文件名
     * @param expireTime  Post Policy的有效时间（单位：秒）
     * @param minFileSize 文件最小大小（单位：字节）
     * @param maxFileSize 文件最大大小（单位：字节）
     * @param contentTypes 允许的文件类型（例如：["image/jpeg", "image/png"]）
     * @return 包含Post Policy参数的Map
     */
    public Map<String, String> generatePostPolicy(String dir, String fileName, int expireTime, long minFileSize, long maxFileSize, String[] contentTypes) {
        try {
            // 创建策略条件
            PolicyConditions conditions = new PolicyConditions();
            conditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, minFileSize, maxFileSize);
            conditions.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
            conditions.addConditionItem(PolicyConditions.COND_KEY, fileName);
            for (String contentType : contentTypes) {
                conditions.addConditionItem(PolicyConditions.COND_CONTENT_TYPE, contentType);
            }
            // 设置过期时间
            Date expiration = new Date(System.currentTimeMillis() + expireTime * 1000L);

            // 生成临时访问凭证
            Map<String, String> credentials = getSTSToken(roleArn, "oss-post", expireTime);
            log.debug("临时访问凭证：{}", credentials);

            // 创建OSS客户端
            OSS tempOssClient = new OSSClientBuilder().build(endPoint, credentials.get("accessKeyId"), credentials.get("accessKeySecret"));
            log.debug("临时访问连接：{}", tempOssClient);

            // 生成Post Policy
            String policy = tempOssClient.generatePostPolicy(expiration, conditions);
            String encodedPolicy = Base64.getEncoder().encodeToString(policy.getBytes(StandardCharsets.UTF_8));
            String signature = tempOssClient.calculatePostSignature(policy);

            // 构造返回结果
            Map<String, String> result = new HashMap<>();
            result.put("OSSAccessKeyId", credentials.get("accessKeyId"));
            result.put("OSSAccessKeySecret", credentials.get("accessKeySecret"));
            result.put("securityToken", credentials.get("securityToken"));
            result.put("policy", encodedPolicy);
            result.put("signature", signature);
            result.put("dir", dir);
            result.put("host", "https://" + bucketName + "." + endPoint);
            result.put("expire", String.valueOf(expiration.getTime()));

            log.debug("Post Policy：{}", result);
            return result;
        } catch (Exception e) {
            log.error("生成Post Policy失败", e);
            throw new RuntimeException("生成Post Policy失败", e);
        }
    }

    /**
     * 获取临时访问凭证（STS Token）
     *
     * @param roleArn         角色ARN
     * @param roleSessionName 角色会话名称
     * @param durationSeconds 凭证有效时间（单位：秒）
     * @return 临时访问凭证
     */
    public Map<String, String> getSTSToken(String roleArn, String roleSessionName, int durationSeconds) {
        try {
            DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
            DefaultAcsClient client = new DefaultAcsClient(profile);

            AssumeRoleRequest request = new AssumeRoleRequest();
            request.setRoleArn(roleArn);
            request.setRoleSessionName(roleSessionName);
            request.setDurationSeconds((long) durationSeconds);

            AssumeRoleResponse response = client.getAcsResponse(request);
            AssumeRoleResponse.Credentials credentials = response.getCredentials();

            Map<String, String> result = new HashMap<>();
            result.put("accessKeyId", credentials.getAccessKeyId());
            result.put("accessKeySecret", credentials.getAccessKeySecret());
            result.put("securityToken", credentials.getSecurityToken());
            result.put("expiration", credentials.getExpiration());
            return result;
        } catch (ClientException e) {
            log.error("获取STS Token失败", e);
            throw new RuntimeException("获取STS Token失败", e);
        }
    }
}
