package com.nfcat.papermc.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nfcat.papermc.configs.MybatisConfig;
import com.nfcat.papermc.entity.Shopentity;
import com.nfcat.papermc.entity.Shopobj;
import com.nfcat.papermc.mapper.ShopMapper;
import com.nfcat.papermc.mapper.ShopobjMapper;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.units.qual.C;

import java.util.List;

public class MocShopUtils {
    public static Boolean addshop(CommandSender sender,Shopentity shop){
        ShopMapper mapper = MybatisConfig.getSqlSession().getMapper(ShopMapper.class);
        QueryWrapper<Shopentity> wrapper = new QueryWrapper<>();
        Shopentity shopentity = new Shopentity();
        shopentity.setShopName(shop.getShopName());
        wrapper.setEntity(shopentity);
        Shopentity shopentity1 = mapper.selectOne(wrapper);
        if (shopentity1==null) {
            int insert = mapper.insert(shop);
            if (insert==1){
                sender.sendMessage("店铺创建成功");
                return true;
            }else {
                sender.sendMessage("创建失败，请联系管理员");
                return false;
            }
        }else {
            sender.sendMessage("店铺名称重复");
            return false;
        }
    }
    public static Boolean delshop(CommandSender sender,Shopentity shop){
        ShopMapper mapper = MybatisConfig.getSqlSession().getMapper(ShopMapper.class);
        QueryWrapper<Shopentity> wrapper = new QueryWrapper<>();
        Shopentity shopentity = new Shopentity();
        shopentity.setShopName(shop.getShopName());
        wrapper.setEntity(shopentity);
        Shopentity shopentity1 = mapper.selectOne(wrapper);
        if (shopentity1!=null) {
            int delete = mapper.delete(wrapper);
            if (delete==1){
                sender.sendMessage("店铺注销成功");
                return true;
            }else {
                sender.sendMessage("注销失败，请联系管理员");
                return false;
            }
        }else {
            sender.sendMessage("店铺不存在");
            return false;
        }
    }
    public static boolean addshopobj(CommandSender sender, Shopobj shopobj){
        ShopobjMapper mapper = MybatisConfig.getSqlSession().getMapper(ShopobjMapper.class);
        QueryWrapper<Shopobj> w = new QueryWrapper<>();
        w.setEntity(shopobj);
        if (mapper.selectOne(w)==null) {
            int insert = mapper.insert(shopobj);
            if (insert == 1) {
                sender.sendMessage("添加成功");
                return true;
            } else {
                sender.sendMessage("添加失败，请联系管理员");
                return false;
            }
        }else {
            sender.sendMessage("商品已存在");
            return false;
        }
    }
    public static boolean delshopobj(CommandSender sender,Shopobj shopobj){
        ShopobjMapper mapper = MybatisConfig.getSqlSession().getMapper(ShopobjMapper.class);
        QueryWrapper<Shopobj> wrapper = new QueryWrapper<>();
        wrapper.setEntity(shopobj);
        if (mapper.selectOne(wrapper)!=null) {
            int delete = mapper.delete(wrapper);
            if (delete==1){
                sender.sendMessage("删除成功");
                return true;
            }else {
                sender.sendMessage("发生错误，请联系管理员");
                return false;
            }
        }else {
            sender.sendMessage("商品不存在");
            return false;
        }
    }
    public static Object fandshopobj(CommandSender sender,Shopobj shopobj){
        ShopobjMapper mapper = MybatisConfig.getSqlSession().getMapper(ShopobjMapper.class);
        QueryWrapper<Shopobj> wrapper = new QueryWrapper<>();
        wrapper.setEntity(shopobj);
        List<Shopobj> shopobjs = mapper.selectList(wrapper);
        return shopobjs;

    }

}
