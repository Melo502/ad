package com.example.advtotal1_0.dao.adType;



import com.example.advtotal1_0.pojo.AdType;

import java.util.List;

public interface AdTypeDao {
    /**
     * 获取所有广告类型
     * @return 广告类型列表
     * @throws Exception
     */
    List<AdType> getAllAdTypes() throws Exception;
}
