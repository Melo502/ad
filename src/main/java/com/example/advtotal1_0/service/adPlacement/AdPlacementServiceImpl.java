package com.example.advtotal1_0.service.adPlacement;

import com.example.advtotal1_0.dao.adPlacement.AdPlacementDao;
import com.example.advtotal1_0.dao.adPlacement.AdPlacementDaoImpl;
import com.example.advtotal1_0.pojo.AdPlacement;

import java.util.List;

public class AdPlacementServiceImpl implements AdPlacementService {

    private AdPlacementDao adPlacementDAO = new AdPlacementDaoImpl();

    @Override
    public boolean placeAd(AdPlacement adPlacement) {
        return adPlacementDAO.saveAdPlacement(adPlacement);
    }

    @Override
    public String getLatestTargetWebsiteByAdId(int adId) {
        return adPlacementDAO.getLatestTargetWebsiteByAdId(adId);
    }

    @Override
    public List<AdPlacement> getAdPlacementsByAdId(int adId) {
        return adPlacementDAO.getAdPlacementsByAdId(adId);
    }
}
