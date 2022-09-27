package com.nfcat.papermc.sql;

import com.nfcat.papermc.Main;
import com.nfcat.papermc.configs.MybatisConfig;
import com.nfcat.papermc.sql.entry.NfMcUser;
import com.nfcat.papermc.sql.mapper.NfMcUserMapper;
import com.nfcat.papermc.utils.NfUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class SQLManager {

    @Test
    public void t() {
        System.out.println(addGold("PuppetK", -50L));
    }

    public static boolean addGold(String username, Long addGold) {
        username = username.toLowerCase().trim();

        SqlSession sqlSession = MybatisConfig.getSqlSession();
        NfMcUserMapper mapper = sqlSession.getMapper(NfMcUserMapper.class);
        int n = mapper.addGold(username, addGold);
        sqlSession.close();
        return n > 0;
    }

    public static NfMcUser selectInfo(String username) {
        username = username.toLowerCase().trim();

        SqlSession sqlSession = MybatisConfig.getSqlSession();
        NfMcUserMapper mapper = sqlSession.getMapper(NfMcUserMapper.class);
        NfMcUser user = mapper.queryOne(username);
        sqlSession.close();
        return user;
    }

    public static boolean login(String username, String password) {
        username = username.toLowerCase().trim();
        password = NfUtils.pwdEncrypt(password.trim(), Main.configProp.getProperty("salt", ""));

        SqlSession sqlSession = MybatisConfig.getSqlSession();
        NfMcUserMapper mapper = sqlSession.getMapper(NfMcUserMapper.class);
        boolean b = mapper.login(username, password);
        sqlSession.close();
        return b;
    }

    public static boolean register(String username, String password) {
        username = username.toLowerCase().trim();
        password = NfUtils.pwdEncrypt(password.trim(), Main.configProp.getProperty("salt", ""));

        SqlSession sqlSession = MybatisConfig.getSqlSession();
        NfMcUserMapper mapper = sqlSession.getMapper(NfMcUserMapper.class);
        int i = mapper.insert(new NfMcUser().setMcName(username).setPassword(password));
        sqlSession.close();
        return i != 0;
    }

    public static boolean changepass(String username, String oldPassword, String password) {
        username = username.toLowerCase().trim();
        oldPassword = NfUtils.pwdEncrypt(oldPassword.trim(), Main.configProp.getProperty("salt", ""));
        password = NfUtils.pwdEncrypt(password.trim(), Main.configProp.getProperty("salt", ""));

        SqlSession sqlSession = MybatisConfig.getSqlSession();
        NfMcUserMapper mapper = sqlSession.getMapper(NfMcUserMapper.class);
        int i = mapper.changepass(username, oldPassword, password);
        sqlSession.close();
        return i != 0;
    }
}
