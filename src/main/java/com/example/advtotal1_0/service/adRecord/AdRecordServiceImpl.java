package com.example.advtotal1_0.service.adRecord;

import com.example.advtotal1_0.dao.ad.AdDao;
import com.example.advtotal1_0.dao.ad.AdDaoImpl;
import com.example.advtotal1_0.dao.adRecord.AdRecordDao;
import com.example.advtotal1_0.dao.adRecord.AdRecordDaoImpl;
import com.example.advtotal1_0.pojo.AdRecord;
import com.example.advtotal1_0.service.Dto.AdDetailAggregation;
import com.example.advtotal1_0.service.Dto.OverallAggregation;

import java.util.*;

public class AdRecordServiceImpl implements AdRecordService {
    private AdRecordDao adRecordDao;
    private AdDao adDao;

    public AdRecordServiceImpl() {
        this.adDao = new AdDaoImpl();
        adRecordDao = new AdRecordDaoImpl();
    }

    // 处理广告点击记录
    public boolean recordAdClick(int adId, Double income, String website) throws Exception {
        // 生成广告点击的收益
        boolean flag = false;
        int row = 0;
        // 创建广告记录实体
        AdRecord adRecord = new AdRecord();
        adRecord.setAdId(adId);
        adRecord.setIncome(income);
        adRecord.setClickTime(new Date()); // 当前时间为点击时间
        adRecord.setWebsite(website);
        row = adRecordDao.insertAdRecord(adRecord);
        if(row > 0) {
            flag = true;
        }
        return flag;
    }

    public List<Integer> getAdIdsByWebsite(String website) throws Exception {
        return adRecordDao.getAdIdsByWebsite(website);
    }

    @Override
    public List<AdRecord> getAllAdRecords() throws Exception {
        return adRecordDao.getAllAdRecords();
    }

    @Override
    public List<AdRecord> getAdRecordsFiltered(Date startDate, Date endDate, Double minIncome, Double maxIncome, String website) throws Exception {
        return adRecordDao.getAdRecordsFiltered(startDate, endDate, minIncome, maxIncome, website);
    }


    @Override
    public List<String> getAllWebsites() throws Exception {
        return adRecordDao.getAllWebsites();
    }

    @Override
    public List<AdDetailAggregation> getAdDetailAggregations0(String type, String website, Date startDate, Date endDate) throws Exception {
        System.out.println("agg1"+adRecordDao.getAdDetailAggregations0(type, website, startDate, endDate));
        return adRecordDao.getAdDetailAggregations0(type, website, startDate, endDate);
    }

    @Override
    public OverallAggregation getOverallAggregation0(String type, String website, Date startDate, Date endDate) throws Exception {
        List<AdDetailAggregation> adDetailAggregations = adRecordDao.getAdDetailAggregations0(type, website, startDate, endDate);
        int totalClicks = adDetailAggregations.stream().mapToInt(AdDetailAggregation::getTotalClicks).sum();
        double totalIncome = adDetailAggregations.stream().mapToDouble(AdDetailAggregation::getTotalIncome).sum();
        System.out.println("over"+new OverallAggregation(totalClicks, totalIncome));
        return new OverallAggregation(totalClicks, totalIncome);
    }

    @Override
    public List<AdDetailAggregation> getAdDetailAggregations(int advertiserId, String type, String website, Date startDate, Date endDate) throws Exception {
        try {
            System.out.println("service.Aggregations"+adRecordDao.getAdDetailAggregationsByAdvertiserId(advertiserId, type, website, startDate, endDate));
            return adRecordDao.getAdDetailAggregationsByAdvertiserId(advertiserId, type, website, startDate, endDate);
        } catch (Exception e) {
            throw new Exception("获取广告详细聚合数据失败", e);
        }
    }

    @Override
    public OverallAggregation getOverallAggregation(int advertiserId, String type, String website, Date startDate, Date endDate) throws Exception {
        try {
            System.out.println("service.overallAggregations"+adRecordDao.getOverallAggregationByAdvertiserId(advertiserId, type, website, startDate, endDate));
            return adRecordDao.getOverallAggregationByAdvertiserId(advertiserId, type, website, startDate, endDate);
        } catch (Exception e) {
            throw new Exception("获取总体聚合数据失败", e);
        }
    }

    @Override
    public List<String> getAllWebsites(int advertiserId) throws Exception {
        try {
            return adRecordDao.getAllWebsites(advertiserId);
        } catch (Exception e) {
            throw new Exception("获取网站列表失败", e);
        }
    }

    @Override
    public List<AdRecord> getAdRecordsFiltered(int advertiserId, Date startDate, Date endDate, Double minIncome, Double maxIncome, String website) throws Exception {
        try {
            return adRecordDao.getAdRecordsFiltered(advertiserId, startDate, endDate, minIncome, maxIncome, website);
        } catch (Exception e) {
            throw new Exception("获取广告记录失败", e);
        }
    }

}
