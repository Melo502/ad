package com.example.advtotal1_0.dao.adRecord;

import com.example.advtotal1_0.pojo.AdRecord;
import com.example.advtotal1_0.service.Dto.AdDetailAggregation;
import com.example.advtotal1_0.service.Dto.OverallAggregation;

import java.util.Date;
import java.util.List;

public interface AdRecordDao {
    int insertAdRecord(AdRecord adRecord) throws Exception;
    List<Integer> getAdIdsByWebsite(String website)  throws Exception;
    List<AdRecord> getAllAdRecords() throws Exception;
    List<AdRecord> getAdRecordsFiltered(Date startDate, Date endDate, Double minIncome, Double maxIncome, String website) throws Exception;
    List<String> getAllWebsites() throws Exception;
    List<AdDetailAggregation> getAdDetailAggregations(String type, String website, Date startDate, Date endDate) throws Exception;
    List<AdDetailAggregation> getAdDetailAggregationsByAdvertiserId(int advertiserId, String type, String website, Date startDate, Date endDate) throws Exception;
    OverallAggregation getOverallAggregationByAdvertiserId(int advertiserId, String type, String website, Date startDate, Date endDate) throws Exception;
    List<String> getAllWebsites(int advertiserId) throws Exception;
    List<AdRecord> getAdRecordsFiltered(int advertiserId, Date startDate, Date endDate, Double minIncome, Double maxIncome, String website) throws Exception;
}
