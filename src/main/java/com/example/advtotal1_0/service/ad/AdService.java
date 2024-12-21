package com.example.advtotal1_0.service.ad;



import com.example.advtotal1_0.pojo.Ad;

import java.sql.SQLException;
import java.util.List;

public interface AdService {
    /**
     * 创建广告
     * @param ad 广告对象
     * @return 成功创建的行数
     */
    int createAd(Ad ad) throws Exception;

    /**
     * 根据ID获取广告
     * @param id 广告ID
     * @return 广告对象
     */
    Ad getAdById(int id) throws Exception;

    /**
     * 更新广告
     * @param ad 广告对象
     * @return 成功更新的行数
     */
    int updateAd(Ad ad) throws Exception;

    /**
     * 删除广告
     * @param id 广告ID
     * @return 成功删除的行数
     */
    int deleteAd(int id) throws Exception;

    /**
     * 获取所有广告
     * @return 广告列表
     */
    List<Ad> getAllAds() throws Exception;

    /**
     * 根据广告ID列表获取广告对象
     * @param adIds 广告ID列表
     * @return 广告对象列表
     * @throws Exception
     */
    List<Ad> getAdsByIds(List<Integer> adIds) throws Exception;

    /**
     * 根据广告类型代号获取广告列表
     * @param typeKeyWords 广告类型
     * @return 广告列表
     * @throws SQLException
     */
   List<Ad> getAdsByTypeKeyWords(String typeKeyWords) throws Exception;

    /**
     * 批量投放广告，将多个广告状态设置为"活跃"
     * @param adIds 广告ID列表
     * @return 是否成功
     * @throws Exception
     */
    boolean deployAds(List<Integer> adIds) throws Exception;

    List<Ad> searchAdsByTitle(String searchQuery) throws Exception;
    List<String> getAllAdTypes() throws Exception;
    List<Ad> getAdsByAdvertiserId(int advertiserId) throws Exception;
    List<String> getAllAdTypes(int advertiserId) throws Exception;
    List<Ad> searchAdsByTitle(int advertiserId, String title) throws Exception;

}

