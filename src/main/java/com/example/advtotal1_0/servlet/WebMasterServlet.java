// src/main/java/com/example/advtotal1_0/servlet/WebmasterServlet.java

package com.example.advtotal1_0.servlet;

import com.example.advtotal1_0.pojo.Ad;
import com.example.advtotal1_0.pojo.AdRecord;
import com.example.advtotal1_0.pojo.User;
import com.example.advtotal1_0.service.Dto.AdDetailAggregation;
import com.example.advtotal1_0.service.Dto.OverallAggregation;
import com.example.advtotal1_0.service.ad.AdService;
import com.example.advtotal1_0.service.ad.AdServiceImpl;
import com.example.advtotal1_0.service.adPlacement.AdPlacementService;
import com.example.advtotal1_0.service.adPlacement.AdPlacementServiceImpl;
import com.example.advtotal1_0.service.adRecord.AdRecordService;
import com.example.advtotal1_0.service.adRecord.AdRecordServiceImpl;
import com.example.advtotal1_0.util.StaticData;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/webmasterServlet.do")
public class WebMasterServlet extends HttpServlet {

    private AdService adService = new AdServiceImpl();
    private AdRecordService adRecordService = new AdRecordServiceImpl();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action != null) {
            switch (action) {
                case "ad_list":
                    this.viewAdList(req, resp);
                    break;
                case "ad_records":
                    this.viewAdRecords(req, resp);
                    break;
                case "ad_analysis":
                    this.viewAdAnalysis(req, resp);
                    break;
                default:
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "未知的操作方法！");
                    break;
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "缺少操作参数！");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /**
     * 广告列表页面：显示广告信息，并允许根据标题搜索
     */
    private void viewAdList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取网站信息，假设网站名称存储在Session中
        String website = (String) req.getSession().getAttribute("userWebSiteName");
        if (website == null || website.isEmpty()) {
            req.setAttribute("error", "未设置网站信息！");
            req.getRequestDispatcher("jsp/webMaster/error.jsp").forward(req, resp);
            return;
        }

        // 获取搜索参数
        String searchTitle = req.getParameter("searchTitle");
        List<Ad> ads;
        try {
            if (searchTitle != null && !searchTitle.trim().isEmpty()) {
                // 根据标题搜索
                ads = adService.getAdByTitle(website, searchTitle.trim());
                req.setAttribute("searchTitle", searchTitle.trim());
            } else {
                // 显示所有广告
                ads = adService.getAllAdsByWebsite(website);
            }
            req.setAttribute("ads", ads);
            req.getRequestDispatcher("jsp/webMaster/ad_list.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "无法获取广告列表！");
            req.getRequestDispatcher("jsp/webMaster/error.jsp").forward(req, resp);
        }
    }

    /**
     * 广告点击记录页面：显示广告点击记录，并允许筛选
     */
    private void viewAdRecords(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取网站信息，假设网站名称存储在Session中
        String website = (String) req.getSession().getAttribute("userWebSiteName");
        if (website == null || website.isEmpty()) {
            req.setAttribute("error", "未设置网站信息！");
            req.getRequestDispatcher("jsp/webMaster/error.jsp").forward(req, resp);
            return;
        }

        // 获取筛选参数
        String advertiserIdStr = req.getParameter("advertiserId");
        String startDateStr = req.getParameter("startDate");
        String endDateStr = req.getParameter("endDate");
        String minIncomeStr = req.getParameter("minIncome");
        String maxIncomeStr = req.getParameter("maxIncome");

        int advertiserId = 0;
        Double minIncome = null;
        Double maxIncome = null;
        Date startDate = null;
        Date endDate = null;

        // 获取广告主ID
        if (advertiserIdStr != null && !advertiserIdStr.trim().isEmpty()) {
            try {
                advertiserId = Integer.parseInt(advertiserIdStr.trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                req.setAttribute("error", "无效的广告主ID。");
                req.getRequestDispatcher("jsp/webMaster/error.jsp").forward(req, resp);
                return;
            }
        }

        // 获取最小收益
        if (minIncomeStr != null && !minIncomeStr.trim().isEmpty()) {
            try {
                minIncome = Double.parseDouble(minIncomeStr.trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                req.setAttribute("error", "无效的最小收益。");
                req.getRequestDispatcher("jsp/webMaster/error.jsp").forward(req, resp);
                return;
            }
        }

        // 获取最大收益
        if (maxIncomeStr != null && !maxIncomeStr.trim().isEmpty()) {
            try {
                maxIncome = Double.parseDouble(maxIncomeStr.trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                req.setAttribute("error", "无效的最大收益。");
                req.getRequestDispatcher("jsp/webMaster/error.jsp").forward(req, resp);
                return;
            }
        }

        // 获取日期参数
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (startDateStr != null && !startDateStr.trim().isEmpty()) {
                startDate = sdf.parse(startDateStr.trim());
            }
            if (endDateStr != null && !endDateStr.trim().isEmpty()) {
                Date tempEndDate = sdf.parse(endDateStr.trim());
                endDate = new Date(tempEndDate.getTime() + 24 * 60 * 60 * 1000 - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "日期格式错误，请使用YYYY-MM-DD格式。");
            req.getRequestDispatcher("jsp/webMaster/error.jsp").forward(req, resp);
            return;
        }

        try {

            List<AdRecord> records = adRecordService.getAdRecordsFiltered(advertiserId, startDate, endDate, minIncome, maxIncome, website);
            req.setAttribute("records", records);
            req.setAttribute("advertiserId", advertiserId);
            req.setAttribute("startDate", startDateStr);
            req.setAttribute("endDate", endDateStr);
            req.setAttribute("minIncome", minIncomeStr);
            req.setAttribute("maxIncome", maxIncomeStr);
            req.getRequestDispatcher("jsp/webMaster/ad_records.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "无法获取广告点击记录！");
            req.getRequestDispatcher("jsp/webMaster/error.jsp").forward(req, resp);
        }
    }

    /**
     * 广告分析页面：显示聚合数据并可视化
     */
    private void viewAdAnalysis(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 获取网站信息，假设网站名称存储在Session中
        String website = (String) req.getSession().getAttribute("userWebSiteName");
        if (website == null || website.isEmpty()) {
            req.setAttribute("error", "未设置网站信息！");
            req.getRequestDispatcher("jsp/webMaster/error.jsp").forward(req, resp);
            return;
        }

        // 获取筛选参数
        String advertiserIdStr = req.getParameter("advertiserId");
        String startDateStr = req.getParameter("startDate");
        String endDateStr = req.getParameter("endDate");
        String type = req.getParameter("type");

        int advertiserId = 0;
        Date startDate = null;
        Date endDate = null;

        // 获取广告主ID
        if (advertiserIdStr != null && !advertiserIdStr.trim().isEmpty()) {
            try {
                advertiserId = Integer.parseInt(advertiserIdStr.trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                req.setAttribute("error", "无效的广告主ID。");
                req.getRequestDispatcher("jsp/webMaster/error.jsp").forward(req, resp);
                return;
            }
        }

        // 获取日期参数
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (startDateStr != null && !startDateStr.trim().isEmpty()) {
                startDate = sdf.parse(startDateStr.trim());
            }
            if (endDateStr != null && !endDateStr.trim().isEmpty()) {
                Date tempEndDate = sdf.parse(endDateStr.trim());
                endDate = new Date(tempEndDate.getTime() + 24 * 60 * 60 * 1000 - 1); // 设置为当天的最后一刻
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "日期格式错误，请使用YYYY-MM-DD格式。");
            req.getRequestDispatcher("jsp/webMaster/error.jsp").forward(req, resp);
            return;
        }

        try {
            // 获取聚合数据
            OverallAggregation aggregation = adRecordService.getOverallAggregation(advertiserId, type, website ,startDate, endDate);
            req.setAttribute("aggregation", aggregation);

            System.out.println("servlet:overallaggregation"+aggregation);
            // 获取详细聚合数据
            List<AdDetailAggregation> adDetails = adRecordService.getAdDetailAggregations(advertiserId, type, website, startDate, endDate);
            req.setAttribute("adDetails", adDetails);

            System.out.println("servlet:aggregation"+adDetails);
            // 获取所有广告类型以填充筛选下拉菜单
            List<String> adTypes = adService.getAllAdTypes(website);
            req.setAttribute("adTypes", adTypes);

            // 传递筛选参数到 JSP 页面，以便在表单中显示当前筛选条件
            req.setAttribute("advertiserId", advertiserIdStr);
            req.setAttribute("startDate", startDateStr);
            req.setAttribute("endDate", endDateStr);
            req.setAttribute("type", type);

            // 转发到分析页面
            req.getRequestDispatcher("jsp/webMaster/ad_analysis.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "无法获取广告分析数据！");
            req.getRequestDispatcher("jsp/webMaster/error.jsp").forward(req, resp);
        }
    }
}
