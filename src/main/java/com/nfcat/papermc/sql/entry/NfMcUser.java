/*
 * This file is generated by MybatisHelperPro plug-in. For details, please see：
 *
 * https://gitee.com/lsl-gitee/LDevKit/tree/master/MybatisHelperPro
 * or
 * https://plugins.jetbrains.com/plugin/15913-mybatishelperpro
 */
package com.nfcat.papermc.sql.entry;

import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;

/**
 * @author MybatisHelperPro
 * @since 2022/09/27 15:57
 */
@Data
@Accessors(chain = true)
public class NfMcUser implements Serializable {
    private static final long serialVersionUID = 1L;
    /**     * mc用户名     */    private String mcName;
    /**     * 对接云服务id     */    private String cloudNanoId;
    /**     * 密码     */    private String password;
    /**     * 金币     */    private Long gold;
    /**     * 水晶     */    private Long crystal;
    /**     * 信息     */    private String info;
}