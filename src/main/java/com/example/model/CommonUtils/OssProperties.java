package com.example.model.CommonUtils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "tanhua.oss")
public class OssProperties {

    private String accessKey;
    private String secret;
    private String bucketName;
    private String url; //域名
    private String endpoint;
}
