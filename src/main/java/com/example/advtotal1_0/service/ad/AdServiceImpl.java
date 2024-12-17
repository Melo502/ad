package com.example.advtotal1_0.service.ad;



import com.example.advtotal1_0.dao.ad.AdDao;
import com.example.advtotal1_0.dao.ad.AdDaoImpl;
import com.example.advtotal1_0.pojo.Ad;

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


    public List<Ad> getAdsByIds(List<Integer> adIds) throws Exception {
        return adDao.getAdsByIds(adIds);
    }
}
