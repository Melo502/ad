package com.example.advtotal1_0.service.ad;



import com.example.advtotal1_0.dao.ad.AdDao;
import com.example.advtotal1_0.dao.ad.AdDaoImpl;
import com.example.advtotal1_0.pojo.Ad;

import java.sql.SQLException;
import java.util.List;

public class AdServiceImpl implements AdService {

    private AdDao adDao = new AdDaoImpl();

    @Override
    public int createAd(Ad ad) throws Exception {
        return adDao.createAd(ad);
    }

    @Override
    public Ad getAdById(int id) throws Exception {
        return adDao.getAdById(id);
    }

    @Override
    public int updateAd(Ad ad) throws Exception {
        return adDao.updateAd(ad);
    }

    @Override
    public int deleteAd(int id) throws Exception {
        return adDao.deleteAd(id);
    }

    @Override
    public List<Ad> getAllAds() throws Exception {
        return adDao.getAllAds();
    }

    @Override
    public List<Ad> getAdsByIds(List<Integer> adIds) throws Exception {
        return adDao.getAdsByIds(adIds);
    }

    @Override
    public List<Ad> getAdsByTypeKeyWords(String typeKeyWords) throws Exception {
        return adDao.findAdsByTypeKeyWords(typeKeyWords);
    }

    @Override
    public boolean deployAds(List<Integer> adIds) throws Exception {
        int rows = adDao.updateAdStatusBatch(adIds, "活跃");
        return rows > 0;
    }

    @Override
    public List<Ad> searchAdsByTitle(String searchQuery) throws Exception {
        return adDao.searchAdsByTitle(searchQuery);
    }

    @Override
    public List<String> getAllAdTypes() throws Exception {
        return adDao.getAllAdTypes();
    }

    @Override
    public List<Ad> getAdsByAdvertiserId(int advertiserId) throws Exception {
        try {
            return adDao.getAdsByAdvertiserId(advertiserId);
        } catch (Exception e) {
            throw new Exception("获取广告列表失败", e);
        }
    }

    @Override
    public List<String> getAllAdTypes(int advertiserId) throws Exception {
        try {
            return adDao.getAllAdTypes(advertiserId);
        } catch (Exception e) {
            throw new Exception("获取广告类型失败", e);
        }
    }

    @Override
    public List<Ad> searchAdsByTitle(int advertiserId, String title) throws Exception {
        try {
            return adDao.searchAdsByTitle(advertiserId, title);
        } catch (Exception e) {
            throw new Exception("根据标题搜索广告失败", e);
        }
    }
}
