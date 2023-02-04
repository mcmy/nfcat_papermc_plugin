package com.nfcat.papermc;

import com.nfcat.papermc.configs.MybatisConfig;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class MybatisTest {

    @Test
    public void testUserById() {
        SqlSession sqlSession = MybatisConfig.getSqlSession();
//        NfMcUserMapper mapper = sqlSession.getMapper(NfMcUserMapper.class);
//        NfMcUser user = mapper.queryOne("puppetk");
//        System.out.println(user);
        sqlSession.close();

    }
}
