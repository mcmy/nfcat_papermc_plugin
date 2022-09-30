package com.nfcat.papermc.configs;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

//sqlSessionFactory -> sqlSession
public class MybatisConfig {
    private static SqlSessionFactory sqlSessionFactory;

    static {
        try (InputStream inputStream = MybatisConfig.class.getClassLoader().getResourceAsStream("mybatis/mybatis-config.xml")) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession(true);
    }
}

