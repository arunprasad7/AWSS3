package com.poc.aws.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationConfig {

    public final CMMS cmms = new CMMS();

    public static class CMMS {

        public static class AWS {

            private String access_key_id;
            private String secret_access_key;
            private String bucket;
            private String region;
            public String getAccess_key_id() {
                return access_key_id;
            }
            public void setAccess_key_id(String access_key_id) {
                this.access_key_id = access_key_id;
            }
            public String getSecret_access_key() {
                return secret_access_key;
            }
            public void setSecret_access_key(String secret_access_key) {
                this.secret_access_key = secret_access_key;
            }
            public String getBucket() {
                return bucket;
            }
            public void setBucket(String bucket) {
                this.bucket = bucket;
            }
            public String getRegion() {
                return region;
            }
            public void setRegion(String region) {
                this.region = region;
            }
        }

        private AWS aws;

        public AWS getAws() {
            return aws;
        }
        public void setAws(AWS aws) {
            this.aws = aws;
        }

    }

    public CMMS getCMMS() {
        return cmms;
    }
}
