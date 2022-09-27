package com.nfcat.papermc.sql.manager;

import com.nfcat.papermc.Main;
import com.nfcat.papermc.configs.MybatisConfig;
import com.nfcat.papermc.sql.entry.NfMcUser;
import com.nfcat.papermc.sql.mapper.NfMcUserMapper;
import com.nfcat.papermc.utils.NfUtils;
import org.apache.ibatis.session.SqlSession;

public class UserSQLManager {

    /**
     * 增加金币 可负数
     *
     * @param username username
     * @param addGold  (+/-)gold
     * @return true/false
     */
    public static boolean addGold(String username, Long addGold) {
        username = username.toLowerCase().trim();

        SqlSession sqlSession = MybatisConfig.getSqlSession();
        NfMcUserMapper mapper = sqlSession.getMapper(NfMcUserMapper.class);
        int n = mapper.addGold(username, addGold);
        sqlSession.close();
        return n > 0;
    }

    /**
     * 查询用户数据,不包括info
     *
     * @param username username
     * @return NfMcUser
     */
    public static NfMcUser selectUser(String username) {
        username = username.toLowerCase().trim();

        SqlSession sqlSession = MybatisConfig.getSqlSession();
        NfMcUserMapper mapper = sqlSession.getMapper(NfMcUserMapper.class);
        NfMcUser user = mapper.selectUser(username);
        sqlSession.close();
        return user;
    }

    /**
     * 查询用户info字段(请使用JSON格式进行处理，判断是否NULL)
     *
     * @param username username
     * @return String
     */
    public static String selectUserOtherInfo(String username) {
        username = username.toLowerCase().trim();

        SqlSession sqlSession = MybatisConfig.getSqlSession();
        NfMcUserMapper mapper = sqlSession.getMapper(NfMcUserMapper.class);
        String s = mapper.selectUserOtherInfo(username);
        sqlSession.close();
        return s;
    }

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return true/false
     */
    public static boolean login(String username, String password) {
        username = username.toLowerCase().trim();
        password = NfUtils.pwdEncrypt(password.trim(), Main.configProp.getProperty("salt", ""));

        SqlSession sqlSession = MybatisConfig.getSqlSession();
        NfMcUserMapper mapper = sqlSession.getMapper(NfMcUserMapper.class);
        boolean b = mapper.login(username, password);
        sqlSession.close();
        return b;
    }

    /**
     * 注册
     *
     * @param username 用户名
     * @param password 密码
     * @return true/false
     */
    public static boolean register(String username, String password) {
        username = username.toLowerCase().trim();
        password = NfUtils.pwdEncrypt(password.trim(), Main.configProp.getProperty("salt", ""));

        SqlSession sqlSession = MybatisConfig.getSqlSession();
        NfMcUserMapper mapper = sqlSession.getMapper(NfMcUserMapper.class);
        int i = mapper.insertSelective(new NfMcUser().setMcName(username).setPassword(password));
        sqlSession.close();
        return i != 0;
    }

    /**
     * 修改密码
     *
     * @param username    用户名
     * @param oldPassword 原密码
     * @param password    新密码
     * @return true/false
     */
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
