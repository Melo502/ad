package com.example.advtotal1_0.service.adPlacement;

import com.example.advtotal1_0.pojo.AdPlacement;

import java.util.List;

public interface AdPlacementService {
    /**
     * 保存广告投放记录
     * @param adPlacement 广告投放对象
     * @return 是否保存成功
     */
    boolean placeAd(AdPlacement adPlacement);

    /**
     * 根据广告ID获取最新的目标网站
     * @param adId 广告ID
     * @return 最新的目标网站URL，如果没有找到则返回null
     */
    String getLatestTargetWebsiteByAdId(int adId);

    /**
     * 根据广告ID获取所有广告投放记录
     * @param adId 广告ID
     * @return 广告投放记录列表
     */
    List<AdPlacement> getAdPlacementsByAdId(int adId);
}
