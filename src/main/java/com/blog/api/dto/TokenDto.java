package com.blog.api.dto;

import lombok.Data;

@Data
public class TokenDto {

    public Token getAccess_token() {
        return access_token;
    }

    public void setAccess_token(Token access_token) {
        this.access_token = access_token;
    }

    public Token getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(Token refresh_token) {
        this.refresh_token = refresh_token;
    }

    private Token access_token;

    private Token refresh_token;

    @Data
    public static class Token {
        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public long getExpire() {
            return expire;
        }

        public void setExpire(long expire) {
            this.expire = expire;
        }

        private String token;
        private long expire;
    }
}
