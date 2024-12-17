package com.example.advtotal1_0.dao.ad;



import com.example.advtotal1_0.pojo.Ad;

import java.util.List;

public interface AdDao {
    /**
     * 创建一个新的广告
     * @param ad 广告对象
     * @return 成功插入的行数
     */
    int createAd(Ad ad) throws Exception;

    /**
     * 根据ID获取广告
     * @param id 广告ID
     * @return 广告对象
     */
    Ad getAdById(int id) throws Exception;

    /**
     * 更新广告信息
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
     * 根据id查找多个广告
     * @param adIds
     * @return
     * @throws Exception
     */
    List<Ad> getAdsByIds(List<Integer> adIds) throws Exception;

    /**
     * 根据广告类型代号查找广告
     * @param typeKeyWords 广告类型代号
     * @return 广告列表
     */
    public List<Ad> findAdsByTypeKeyWords(String typeKeyWords) throws Exception;
}
