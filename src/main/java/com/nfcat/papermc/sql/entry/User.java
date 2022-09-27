package com.nfcat.papermc.sql.entry;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class User {
    @JSONField(name = "mc_name")
    private String mcName;
    private String password;
    private long gold;
    @JSONField(name = "cloud_nano_id")
    private String cloudNanoId;
    private long nmb;
}
