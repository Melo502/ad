package com.example.advtotal1_0.servlet;

import com.example.advtotal1_0.dao.adType.AdTypeDao;
import com.example.advtotal1_0.dao.adType.AdTypeDaoImpl;
import com.example.advtotal1_0.pojo.Ad;
import com.example.advtotal1_0.pojo.AdRecord;
import com.example.advtotal1_0.pojo.AdType;
import com.example.advtotal1_0.pojo.User;
import com.example.advtotal1_0.service.Dto.AdDetailAggregation;
import com.example.advtotal1_0.service.Dto.OverallAggregation;
import com.example.advtotal1_0.service.ad.AdService;
import com.example.advtotal1_0.service.ad.AdServiceImpl;
import com.example.advtotal1_0.service.adRecord.AdRecordService;
import com.example.advtotal1_0.service.adRecord.AdRecordServiceImpl;
import com.example.advtotal1_0.util.StaticData;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;


@WebServlet("/advertiserServlet.do")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 15    // 15 MB
)
public class AdvertiserServlet extends HttpServlet {

    private AdService adService = new AdServiceImpl();
    private AdTypeDao adTypeDao = new AdTypeDaoImpl();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method != null) {
            switch (method) {
                case "viewAd":
                    this.viewAd(req, resp);
                    break;
                case "createAd":
                    this.showCreateAdForm(req, resp);
                    break;
                case "doCreateAd":
                    this.createAd(req, resp);
                    break;
                case "viewAdDetail":
                    this.viewAdDetail(req, resp);
                    break;
                case "editAd":
                    this.showEditAdForm(req, resp);
                    break;
                case "doEditAd":
                    this.editAd(req, resp);
                    break;
                case "deleteAd":
                    this.deleteAd(req, resp);
                    break;
                case "showAdPlacementPage":
                    this.showAdPlacementPage(req, resp);
                    break;
                case "placeAd":
                    placeAd(req, resp);
                    break;
                case "getAdContent":
                    this.getAdContent(req, resp);
                    break;
                case "postAd":
                    this.postAd(req, resp);
                    break;
                case "logAdClick":
                    this.logAdClick(req, resp);
                    break;
                case "viewAdRecords":
                    this.viewAdRecords(req, resp);
                    break;
                case "viewAdRecordsSelect":
                    this.viewAdRecordsSelect(req, resp);
                    break;
                case "viewAdDetails":
                    this.viewAdDetails(req, resp);
                case "searchAds":
                    this.searchAds(req, resp);
                    break;
                case "adAnalysis":
                    this.adAnalysis(req, resp);
                    break;
                default:

                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "未知的操作方法！");
                    break;
            }
        } else {
            this.viewAd(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /**
     * 显示广告列表
     */
    private void viewAd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Ad> adList = new ArrayList<>();
        User currentUser = (User) req.getSession().getAttribute(StaticData.ONLINE_USER);
        int advertiserId = currentUser.getId();
        try {
            adList = adService.getAdsByAdvertiserId(advertiserId);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "无法获取广告列表！");
        }
        req.setAttribute("adList", adList);
        req.getRequestDispatcher("jsp/advertiser/ad_list.jsp").forward(req, resp);
    }

    /**
     * 显示创建广告表单
     */
    private void showCreateAdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<AdType> typeList = adTypeDao.getAllAdTypes();
            req.setAttribute("typeList", typeList);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "无法获取广告类型列表！");
        }
        req.getRequestDispatcher("jsp/advertiser/create_ad.jsp").forward(req, resp);
    }

    /**
     * 处理创建广告
     */
    private void createAd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置请求编码
        req.setCharacterEncoding("UTF-8");
        User currentUser = (User) req.getSession().getAttribute(StaticData.ONLINE_USER);

        // 获取表单参数

        int advertiserId = currentUser.getId();
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String url = req.getParameter("url");
        String typeIdParam = req.getParameter("typeId");
        String typeKeyWords = req.getParameter("typeKeyWords");
        String status = req.getParameter("status");
        Double price = Double.valueOf(req.getParameter("price"));

        int typeId = 0;
        try {
            typeId = Integer.parseInt(typeIdParam);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            req.setAttribute("error", "无效的广告类型！");
            this.showCreateAdForm(req, resp);
            return;
        }

        // 处理文件上传
        String imageUrl = null;
        Part filePart = req.getPart("image");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            // 生成唯一文件名，避免冲突
            String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
            // 定义上传路径
            String uploadPath = getServletContext().getRealPath("") + File.separator + "images";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();
            // 保存文件
            String filePath = uploadPath + File.separator + uniqueFileName;
            filePart.write(filePath);
            // 构建 imageUrl（根据实际情况调整）
            imageUrl = "advertise/images/" + uniqueFileName;
        }

        // 创建 Ad 对象
        Ad ad = new Ad();
        ad.setAdvertiserId(advertiserId);
        ad.setTitle(title);
        ad.setDescription(description);
        ad.setUrl(url);
        ad.setTypeId(typeId);
        ad.setStatus(status);
        ad.setTypeKeyWords(typeKeyWords);
        ad.setImageUrl(imageUrl);
        ad.setPrice(price);

        try {
            adService.createAd(ad);
            // 重定向到广告列表页面
            resp.sendRedirect("advertiserServlet.do?method=viewAd");
        } catch (Exception e) {
            e.printStackTrace();
            // 处理异常，例如显示错误页面
            req.setAttribute("error", "创建广告失败！");
            this.showCreateAdForm(req, resp);
        }
    }

    /**
     * 显示广告详情
     */
    private void viewAdDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            try {
                int id = Integer.parseInt(idParam);
                Ad ad = adService.getAdById(id);
                if (ad != null) {
                    req.setAttribute("ad", ad);
                } else {
                    req.setAttribute("error", "广告不存在！");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                req.setAttribute("error", "无效的广告ID！");
            } catch (Exception e) {
                e.printStackTrace();
                req.setAttribute("error", "无法获取广告详情！");
            }
        } else {
            req.setAttribute("error", "未指定广告ID！");
        }
        req.getRequestDispatcher("jsp/advertiser/view_ad.jsp").forward(req, resp);
    }

    /**
     * 显示编辑广告表单
     */
    private void showEditAdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            try {
                int id = Integer.parseInt(idParam);
                Ad ad = adService.getAdById(id);
                if (ad != null) {
                    req.setAttribute("ad", ad);
                    // 加载广告类型列表
                    List<AdType> typeList = adTypeDao.getAllAdTypes();
                    req.setAttribute("typeList", typeList);
                } else {
                    req.setAttribute("error", "广告不存在！");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                req.setAttribute("error", "无效的广告ID！");
            } catch (Exception e) {
                e.printStackTrace();
                req.setAttribute("error", "无法获取广告信息！");
            }
        } else {
            req.setAttribute("error", "未指定广告ID！");
        }
        req.getRequestDispatcher("jsp/advertiser/edit_ad.jsp").forward(req, resp);
    }

    /**
     * 处理编辑广告
     */
    private void editAd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置请求编码
        req.setCharacterEncoding("UTF-8");
        User currentUser = (User) req.getSession().getAttribute(StaticData.ONLINE_USER);

        // 获取表单参数
        String idParam = req.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            req.setAttribute("error", "未指定广告ID！");
            req.getRequestDispatcher("jsp/advertiser/edit_ad.jsp").forward(req, resp);
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            int advertiserId = currentUser.getId();
            String title = req.getParameter("title");
            String description = req.getParameter("description");
            String url = req.getParameter("url");
            String typeIdParam = req.getParameter("typeId");
            String typeKeyWords = req.getParameter("typeKeyWords");
            String status = req.getParameter("status");
            Double price = Double.valueOf(req.getParameter("price"));

            int typeId = 0;
            try {
                typeId = Integer.parseInt(typeIdParam);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                req.setAttribute("error", "无效的广告类型！");
                this.showEditAdForm(req, resp);
                return;
            }

            // 处理文件上传
            String imageUrl = null;
            Part filePart = req.getPart("image");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                // 生成唯一文件名，避免冲突
                String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
                // 定义上传路径
                String uploadPath = getServletContext().getRealPath("") + File.separator + "images";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdir();
                // 保存文件
                String filePath = uploadPath + File.separator + uniqueFileName;
                filePart.write(filePath);
                // 构建 imageUrl（根据实际情况调整）
                imageUrl = "advertise/images/" + uniqueFileName;
            }

            // 获取现有广告
            Ad ad = adService.getAdById(id);
            if (ad != null) {
                ad.setAdvertiserId(advertiserId);
                ad.setTitle(title);
                ad.setDescription(description);
                ad.setUrl(url);
                ad.setTypeId(typeId);
                ad.setStatus(status);
                ad.setTypeKeyWords(typeKeyWords);
                ad.setPrice(price);

                if (imageUrl != null) {
                    ad.setImageUrl(imageUrl);
                }

                adService.updateAd(ad);
                // 重定向到广告列表页面
                resp.sendRedirect("advertiserServlet.do?method=viewAd");
            } else {
                req.setAttribute("error", "广告不存在！");
                req.getRequestDispatcher("jsp/advertiser/edit_ad.jsp").forward(req, resp);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            req.setAttribute("error", "无效的广告ID！");
            req.getRequestDispatcher("jsp/advertiser/edit_ad.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "修改广告失败！");
            req.getRequestDispatcher("jsp/advertiser/edit_ad.jsp").forward(req, resp);
        }
    }

    /**
     * 处理删除广告
     */
    private void deleteAd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            try {
                int id = Integer.parseInt(idParam);
                adService.deleteAd(id);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                req.setAttribute("error", "无效的广告ID！");
            } catch (Exception e) {
                e.printStackTrace();
                req.setAttribute("error", "删除广告失败！");
            }
        } else {
            req.setAttribute("error", "未指定广告ID！");
        }
        // 重定向回广告列表页面
        resp.sendRedirect("advertiserServlet.do?method=viewAd");
    }

    // 显示投放广告页面
    private void showAdPlacementPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Ad> adList = null;
        User currentUser = (User) req.getSession().getAttribute(StaticData.ONLINE_USER);
        int advertiserId = currentUser.getId();
        try {
            adList = adService.getAdsByAdvertiserId(advertiserId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        req.setAttribute("adList", adList);
        req.getRequestDispatcher("jsp/advertiser/ad_place.jsp").forward(req, resp);
    }

    // 处理投放广告请求
    private void placeAd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置CORS头，允许跨域请求
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");

        // 获取参数
        String website = req.getParameter("website");
        String[] adIdParams = req.getParameterValues("adIds"); // 多选广告ID

        if (website == null || website.isEmpty() || adIdParams == null || adIdParams.length == 0) {
            req.setAttribute("message", "目标网站和广告至少选择一项。");
            req.getRequestDispatcher("advertiserServlet.do?method=showAdPlacementPage").forward(req, resp);
            return;
        }

        // 转换广告ID为整数列表
        List<Integer> adIds = new ArrayList<>();
        for (String adIdStr : adIdParams) {
            try {
                int adId = Integer.parseInt(adIdStr);
                adIds.add(adId);
            } catch (NumberFormatException e) {
                // 忽略无效的广告ID
                System.err.println("无效的广告ID: " + adIdStr);
            }
        }

        if (adIds.isEmpty()) {
            req.setAttribute("message", "没有有效的广告ID。");
            req.getRequestDispatcher("advertiserServlet.do?method=showAdPlacementPage").forward(req, resp);
            return;
        }

        try {
            // 获取广告对象列表
            List<Ad> ads = adService.getAdsByIds(adIds);

            if (ads.isEmpty()) {
                req.setAttribute("message", "没有有效的广告ID。");
                req.getRequestDispatcher("advertiserServlet.do?method=showAdPlacementPage").forward(req, resp);
                return;
            }
            boolean success = adService.deployAds(adIds);

            if (!success) {
                req.setAttribute("message", "批量投放广告失败。");
            }

            // 生成广告ID的逗号分隔字符串
            StringBuilder adIdStrBuilder = new StringBuilder();
            for (int i = 0; i < ads.size(); i++) {
                adIdStrBuilder.append(ads.get(i).getId());
                if (i < ads.size() - 1) {
                    adIdStrBuilder.append(",");
                }
            }

            // 生成单个广告脚本标签，包含adIds数组
            String scriptSrc = String.format(
                    "http://localhost:8080/advertise/js/ad_display.js?adIds=%s",
                    adIdStrBuilder.toString()
            );
            String adScript = String.format("<script id='adScript' src='%s'></script>", scriptSrc);

            // 将广告代码片段传递给结果页面
            req.getServletContext().setAttribute("adScripts", adScript);
            req.getSession().setAttribute("website", website);

            // 将JavaScript代码作为响应返回给前端
            req.getRequestDispatcher("jsp/advertiser/ad_place_result.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", "服务器错误。");
            req.getRequestDispatcher("advertiserServlet.do?method=showAdPlacementPage").forward(req, resp);
        }
    }

    private void getAdContent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置CORS头，允许跨域请求
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");

        try {
            // 使用 ObjectMapper 解析 JSON 请求体
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> requestBody = objectMapper.readValue(req.getInputStream(), Map.class);

            // 从 JSON 中获取广告ID字符串
            String adIdsString = (String) requestBody.get("adIds");
            if (adIdsString == null || adIdsString.trim().isEmpty()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "广告ID不能为空，请提供有效的广告ID！");
                return;
            }

            // 将广告ID字符串转换为整数列表
            Set<Integer> adIds = new HashSet<>();
            try {
                String[] adIdArray = adIdsString.split(",");
                for (String idStr : adIdArray) {
                    adIds.add(Integer.parseInt(idStr.trim())); // 转换为整数并去重
                }
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "广告ID格式不正确，请提供逗号分隔的数字！");
                return;
            }

            // 获取广告内容列表
            List<Ad> ads = new ArrayList<>();
            for (Integer adId : adIds) {
                try {
                    Ad ad = adService.getAdById(adId);
                    if (ad != null) {
                        ads.add(ad);
                    } else {
                        System.out.println("广告ID未找到: " + adId);
                    }
                } catch (Exception e) {
                    System.err.println("获取广告失败，广告ID: " + adId);
                    e.printStackTrace();
                }
            }

            // 返回广告列表JSON
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            if (!ads.isEmpty()) {
                String jsonResponse = objectMapper.writeValueAsString(ads);
                resp.getWriter().write(jsonResponse);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "未找到任何广告信息！");
            }
        } catch (Exception e) {
            System.err.println("处理广告请求时出错: " + e.getMessage());
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "加载广告失败，请稍后再试。");
        }
    }

    void postAd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置CORS头，允许跨域请求
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");

        // 从ServletContext中获取adScripts属性
        String adScript = (String) req.getServletContext().getAttribute("adScripts");

        // 如果adScript为空，可能是没有设置，做相应处理
        if (adScript == null) {
            resp.getWriter().write("广告脚本未设置。");
            return;
        }

        // 输出日志，查看adScript内容
        System.out.println(adScript);

        // 设置响应内容类型
        resp.setContentType("text/html;charset=utf-8");

        // 将广告脚本输出到响应中
        PrintWriter out = resp.getWriter();
        out.println(adScript);
    }

    void logAdClick(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");

        // 获取广告ID和点击来源网站信息
        String website = (String) req.getSession().getAttribute("website");

        // 读取请求体中的 JSON 数据并解析
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = req.getReader().readLine()) != null) {
            requestBody.append(line);
        }

        // 解析 JSON 数据为 Map
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> requestData = objectMapper.readValue(requestBody.toString(), Map.class);

        // 获取广告 ID，考虑类型不同的情况
        Object adIdObj = requestData.get("adId");

        if (adIdObj != null) {
            int adId = 0;
            if (adIdObj instanceof String) {
                // 如果是 String 类型，进行转换
                String adIdString = (String) adIdObj;
                if (!adIdString.isEmpty()) {
                    try {
                        adId = Integer.parseInt(adIdString);
                    } catch (NumberFormatException e) {
                        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        resp.getWriter().write("广告ID无效");
                        return;
                    }
                } else {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("广告ID不能为空");
                    return;
                }
            } else if (adIdObj instanceof Integer) {
                // 如果是 Integer 类型，直接赋值
                adId = (Integer) adIdObj;
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("广告ID类型不正确");
                return;
            }

            // 记录广告点击的业务逻辑
            AdRecordService adRecordService = new AdRecordServiceImpl();
            AdService adService = new AdServiceImpl();
            try {
                Ad ad = adService.getAdById(adId);
                if (ad != null) {
                    Double income = ad.getPrice();
                    adRecordService.recordAdClick(adId, income, website);
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.getWriter().write("广告点击已记录");
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("广告未找到");
                }
            } catch (Exception e) {
                e.printStackTrace();
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("服务器内部错误");
            }
        } else {
            // 广告 ID 缺失，返回失败响应
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("广告ID缺失");
        }
    }

    // 查看广告投放记录
    void viewAdRecords(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AdRecordService adRecordService = new AdRecordServiceImpl();
        User currentUser = (User) request.getSession().getAttribute(StaticData.ONLINE_USER);
        int userId = currentUser.getId();
        List<AdRecord> adRecords = null;
        try {
            adRecords = adRecordService.getAdRecordsFiltered(userId,null,null,null,null,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("adRecords", adRecords);
        request.getRequestDispatcher("jsp/advertiser/view_records.jsp").forward(request, response);
    }

    // 查看广告详情
    private void viewAdDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String adIdStr = request.getParameter("id");

        if (adIdStr != null) {
            try {
                int adId = Integer.parseInt(adIdStr);
                Ad ad = null;
                try {
                    ad = adService.getAdById(adId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (ad != null) {
                    request.setAttribute("ad", ad);
                } else {
                    request.setAttribute("error", "广告详情不存在。");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "无效的广告ID。");
            }
        } else {
            request.setAttribute("error", "广告ID未提供。");
        }
        request.getRequestDispatcher("jsp/advertiser/viewAdDetails.jsp").forward(request, response);
    }

    private void viewAdRecordsSelect(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 获取筛选参数
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        String minIncomeStr = request.getParameter("minIncome");
        String maxIncomeStr = request.getParameter("maxIncome");
        String website = request.getParameter("website");
        User currentUser = (User) request.getSession().getAttribute(StaticData.ONLINE_USER);
        int userId = currentUser.getId();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate = null;
        Double minIncome = null;
        Double maxIncome = null;

        // 解析日期参数
        try {
            if (startDateStr != null && !startDateStr.trim().isEmpty()) {
                startDate = sdf.parse(startDateStr);
            }
            if (endDateStr != null && !endDateStr.trim().isEmpty()) {
                // 为结束日期设置为当天的最后一刻
                Date tempEndDate = sdf.parse(endDateStr);
                endDate = new Date(tempEndDate.getTime() + 24 * 60 * 60 * 1000 - 1);
            }
        } catch (Exception e) {
            // 解析失败，设置错误消息
            request.setAttribute("error", "无效的日期格式。请使用正确的日期格式。");
        }

        // 解析收入参数
        try {
            if (minIncomeStr != null && !minIncomeStr.trim().isEmpty()) {
                minIncome = Double.parseDouble(minIncomeStr);
            }
            if (maxIncomeStr != null && !maxIncomeStr.trim().isEmpty()) {
                maxIncome = Double.parseDouble(maxIncomeStr);
            }
        } catch (NumberFormatException e) {
            // 解析失败，设置错误消息
            request.setAttribute("error", "无效的收益格式。请使用有效的数字。");
        }

        // 调用Service层获取筛选后的广告记录
        List<AdRecord> adRecords = null;
        AdRecordService adRecordService = new AdRecordServiceImpl();
        try {
            adRecords = adRecordService.getAdRecordsFiltered(userId,startDate, endDate, minIncome, maxIncome, website);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "获取广告投放记录失败。");
        }

        // 设置广告记录到请求属性
        request.setAttribute("adRecords", adRecords);

        System.out.println(adRecords + "selectadRecords");
        // 转发到JSP页面
        request.getRequestDispatcher("jsp/advertiser/view_records.jsp").forward(request, response);
    }

    // 搜索广告记录 by Ad title
    private void searchAds(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchQuery = request.getParameter("searchQuery");
        User currentUser = (User) request.getSession().getAttribute(StaticData.ONLINE_USER);
        int userId = currentUser.getId();
        List<Ad> adList = new ArrayList<>();
        try {
            adList = adService.searchAdsByTitle(userId,searchQuery);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "无法获取广告列表！");
        }
        request.setAttribute("adList", adList);
        request.getRequestDispatcher("jsp/advertiser/ad_list.jsp").forward(request, response);
    }

    private void adAnalysis(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取参数，如广告类型、网站、开始时间和结束时间
        String type = request.getParameter("type");
        String website = request.getParameter("website");
        String start = request.getParameter("startDate");
        String end = request.getParameter("endDate");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        User currentUser =(User) request.getSession().getAttribute(StaticData.ONLINE_USER);
        int userId = currentUser.getId();


        Date startDate = null;
        Date endDate = null;

        try {
            if (start != null && !start.isEmpty()) {
                startDate = sdf.parse(start);
            }
            if (end != null && !end.isEmpty()) {
                endDate = sdf.parse(end);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 处理日期解析错误，可以设置默认值或返回错误信息
            request.setAttribute("error", "日期格式错误，请使用YYYY-MM-DD格式。");
            request.getRequestDispatcher("jsp/advertiser/error.jsp").forward(request, response);
            return;
        }

        // 如果未提供日期，则默认查询全部
        if (startDate == null) {
            startDate = new Date(0); // 1970-01-01
        }
        if (endDate == null) {
            endDate = new Date(); // 当前时间
        }

        AdService adService = new AdServiceImpl();
        AdRecordService adRecordService = new AdRecordServiceImpl();
        try {
            // 获取每个广告的聚合数据
            List<AdDetailAggregation> adAggregations = adRecordService.getAdDetailAggregations(userId,type, website, startDate, endDate);
            // 获取总体聚合数据
            OverallAggregation overallAggregation = adRecordService.getOverallAggregation(userId,type, website, startDate, endDate);

            // 获取所有广告类型和网站列表，用于下拉菜单
            List<String> adTypes = adService.getAllAdTypes(userId);
            List<String> websites = adRecordService.getAllWebsites(userId);

            // 将数据设置到请求属性中
            request.setAttribute("adAggregations", adAggregations);
            request.setAttribute("overallAggregation", overallAggregation);
            request.setAttribute("adTypes", adTypes);
            request.setAttribute("websites", websites);
            request.setAttribute("selectedType", type);
            request.setAttribute("selectedWebsite", website);
            request.setAttribute("startDate", sdf.format(startDate));
            request.setAttribute("endDate", sdf.format(endDate));

            // 转发到JSP页面
            request.getRequestDispatcher("jsp/advertiser/ad_analysis.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            // 设置错误信息并转发到错误页面
            request.setAttribute("error", "广告分析数据加载失败：" + e.getMessage());
            request.getRequestDispatcher("jsp/advertiser/error.jsp").forward(request, response);
        }

    }
}

