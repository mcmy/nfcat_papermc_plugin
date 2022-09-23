package com.nfcat.spigotmc.config;

import lombok.Data;

@Data
public class Config{

    private Datasource datasource;

    @Data
    public static class Datasource {
        private String driverClassName;
        private String username;
        private String password;
        private String url;
    }
}
