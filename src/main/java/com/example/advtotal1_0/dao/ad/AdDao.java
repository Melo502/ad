package com.example.advtotal1_0.dao.ad;



import com.example.advtotal1_0.pojo.Ad;
import com.example.advtotal1_0.pojo.AdType;

import java.util.List;

public interface AdDao {
    int createAd(Ad ad) throws Exception;
    Ad getAdById(int id) throws Exception;
    int updateAd(Ad ad) throws Exception;
    int deleteAd(int id) throws Exception;
    List<Ad> getAllAds() throws Exception;
    List<Ad> getAdsByIds(List<Integer> adIds) throws Exception;
    List<Ad> findAdsByTypeKeyWords(String typeKeyWords) throws Exception;
    int updateAdStatusBatch(List<Integer> adIds, String status) throws Exception;
    List<Ad> searchAdsByTitle(String searchQuery) throws Exception;
    List<String> getAllAdTypes() throws Exception;
    List<Ad> getAdsByAdvertiserId(int advertiserId) throws Exception;
    List<String> getAllAdTypes(int advertiserId) throws Exception;
    List<Ad> searchAdsByTitle(int advertiserId, String title) throws Exception;
    List<Ad> getAdByTitle(String website, String searchTitle) throws Exception;
    List<Ad> getAllAdsByWebsite(String website) throws Exception;
    List<String> getAllAdTypes(String website) throws Exception;
}
