package org.ContinuityIns.utils;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
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

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class AliOssUtil {

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
    private DefaultAcsClient stsClient;

    @PostConstruct
    public void init() {
        ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
        stsClient = new DefaultAcsClient(
                DefaultProfile.getProfile("cn-shenzhen", accessKeyId, accessKeySecret)
        );
    }

    /**
     * 获取STS Token
     *
     * @param path    上传路径
     * @param maxSize 文件大小限制
     * @return STS Token
     */
    public Map<String, Object> getSTSToken(String path,int maxSize) {
        try {
            // 1. 创建AssumeRole请求
            AssumeRoleRequest request = new AssumeRoleRequest();
            request.setRoleArn(roleArn);
            request.setRoleSessionName("aliyun_oss_session");
            request.setDurationSeconds(3600L);

            // 2. 调用阿里云STS服务获取凭证
            AssumeRoleResponse response = stsClient.getAcsResponse(request);
            AssumeRoleResponse.Credentials credentials = response.getCredentials();

            log.debug("STS Token生成成功{}", credentials.getAccessKeyId());

            // 3. 构建返回结果
            return getObjectMap(path, maxSize, credentials);

        } catch (ClientException e) {
            // 记录详细错误信息
            log.error("STS Token生成失败: {}", e.getMessage(), e);
            throw new RuntimeException("STS凭证生成失败，请检查阿里云配置", e);
        }
    }

    /**
     * 获取上传文件的参数
     *
     * @param path     上传路径
     * @param maxSize  文件大小限制
     * @param credentials STS凭证
     * @return 参数Map
     */
    private Map<String, Object> getObjectMap(String path, int maxSize, AssumeRoleResponse.Credentials credentials) {
        Map<String, Object> result = new HashMap<>();
        result.put("accessKeyId", credentials.getAccessKeyId());
        result.put("accessKeySecret", credentials.getAccessKeySecret());
        result.put("securityToken", credentials.getSecurityToken());
        result.put("expiration", credentials.getExpiration());
        result.put("path", path);
        result.put("maxSize", maxSize);
        result.put("bucketName", bucketName);
        result.put("endPoint", endPoint);
        return result;
    }


    @PreDestroy
    public void destroy() {
        if (ossClient != null) {
            ossClient.shutdown();
        }
    }
}