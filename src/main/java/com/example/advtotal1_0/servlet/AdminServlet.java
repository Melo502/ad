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
import com.example.advtotal1_0.service.user.UserService;
import com.example.advtotal1_0.service.user.UserServiceImpl;
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


@WebServlet("/adminServlet.do")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 15    // 15 MB
)
public class AdminServlet extends HttpServlet {

    private AdService adService = new AdServiceImpl();
    private AdTypeDao adTypeDao = new AdTypeDaoImpl();
    private UserService userService=new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method != null) {
            switch (method) {
                case "viewAd":
                    this.viewAd(req, resp);
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
                case "listUser":
                    this.listUsers(req, resp);
                    break;
                case "showAddUser":
                    this.showAddUserForm(req, resp);
                    break;
                case "showEditUser":
                    this.showEditUserForm(req,resp);
                    break;
                case "deleteUser":
                    this.deleteUser(req,resp);
                    break;
                case "addUser"  :
                    this.addUser(req, resp);
                    break;
                case "editUser"  :
                    this.editUser(req, resp);
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
        try {
            adList = adService.getAllAds();
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "无法获取广告列表！");
        }
        req.setAttribute("adList", adList);
        req.getRequestDispatcher("jsp/admin/ad_list.jsp").forward(req, resp);
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
        req.getRequestDispatcher("jsp/admin/view_ad.jsp").forward(req, resp);
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
        req.getRequestDispatcher("jsp/admin/edit_ad.jsp").forward(req, resp);
    }

    /**
     * 处理编辑广告
     */
    private void editAd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置请求编码
        req.setCharacterEncoding("UTF-8");
                // 获取表单参数
        String idParam = req.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            req.setAttribute("error", "未指定广告ID！");
            req.getRequestDispatcher("jsp/advertiser/edit_ad.jsp").forward(req, resp);
            return;
        }


        try {

            int id = Integer.parseInt(idParam);
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
                imageUrl = "images/" + uniqueFileName;
            }

            // 获取现有广告
            Ad ad = adService.getAdById(id);
            if (ad != null) {
                ad.setAdvertiserId(ad.getAdvertiserId());
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
                resp.sendRedirect("adminServlet.do?method=viewAd");
            } else {
                req.setAttribute("error", "广告不存在！");
                req.getRequestDispatcher("jsp/admin/edit_ad.jsp").forward(req, resp);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            req.setAttribute("error", "无效的广告ID！");
            req.getRequestDispatcher("jsp/admin/edit_ad.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "修改广告失败！");
            req.getRequestDispatcher("jsp/admin/edit_ad.jsp").forward(req, resp);
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
        resp.sendRedirect("adminServlet.do?method=viewAd");
    }

    void viewAdRecords(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AdRecordService adRecordService = new AdRecordServiceImpl();
        List<AdRecord> adRecords = null;
        try {
            adRecords = adRecordService.getAllAdRecords();
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("adRecords", adRecords);
        request.getRequestDispatcher("jsp/admin/view_records.jsp").forward(request, response);
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
        request.getRequestDispatcher("jsp/admin/viewAdDetails.jsp").forward(request, response);
    }

    private void viewAdRecordsSelect(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 获取筛选参数
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        String minIncomeStr = request.getParameter("minIncome");
        String maxIncomeStr = request.getParameter("maxIncome");
        String website = request.getParameter("website");

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
            adRecords = adRecordService.getAdRecordsFiltered(startDate, endDate, minIncome, maxIncome, website);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "获取广告投放记录失败。");
        }

        // 设置广告记录到请求属性
        request.setAttribute("adRecords", adRecords);

        // 转发到JSP页面
        request.getRequestDispatcher("jsp/admin/view_records.jsp").forward(request, response);
    }

    // 搜索广告记录 by Ad title
    private void searchAds(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchQuery = request.getParameter("searchQuery");
        List<Ad> adList = new ArrayList<>();
        try {
            adList = adService.searchAdsByTitle(searchQuery);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "无法获取广告列表！");
        }
        request.setAttribute("adList", adList);
        request.getRequestDispatcher("jsp/admin/ad_list.jsp").forward(request, response);
    }

    private void adAnalysis(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取参数，如广告类型、网站、开始时间和结束时间
        String type = request.getParameter("type");
        String website = request.getParameter("website");
        String start = request.getParameter("startDate");
        String end = request.getParameter("endDate");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


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
            request.getRequestDispatcher("jsp/admin/error.jsp").forward(request, response);
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
            List<AdDetailAggregation> adAggregations = adRecordService.getAdDetailAggregations(type, website, startDate, endDate);
            // 获取总体聚合数据
            OverallAggregation overallAggregation = adRecordService.getOverallAggregation(type, website, startDate, endDate);

            // 获取所有广告类型和网站列表，用于下拉菜单
            List<String> adTypes = adService.getAllAdTypes();
            List<String> websites = adRecordService.getAllWebsites();

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
            request.getRequestDispatcher("jsp/admin/ad_analysis.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            // 设置错误信息并转发到错误页面
            request.setAttribute("error", "广告分析数据加载失败：" + e.getMessage());
            request.getRequestDispatcher("jsp/admin/error.jsp").forward(request, response);
        }

    }


    // 列出所有用户
    private void listUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> userList = null;
        try {
            userList = userService.getAllUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("userList", userList);
        request.getRequestDispatcher("jsp/admin/user_list.jsp").forward(request, response);
    }

    // 显示添加用户表单
    private void showAddUserForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("jsp/admin/user_add.jsp").forward(request, response);
    }

    // 添加用户
    private void addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取表单参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String userType = request.getParameter("userType");

        // 基本验证
        if(username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                userType == null || userType.trim().isEmpty()){
            request.setAttribute("error", "请填写所有必填字段。");
            request.getRequestDispatcher("jsp/admin/error.jsp").forward(request, response);
            return;
        }

        // 创建用户对象
        User user = new User();
        user.setUsername(username.trim());
        user.setPassword(password.trim());
        user.setUserType(userType.trim());

        // 添加用户
        int result = 0;
        try {
            result = userService.addUser1(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(result > 0){
            response.sendRedirect(request.getContextPath() + "/adminServlet.do?method=listUser");
        } else {
            request.setAttribute("error", "添加用户失败。");
            request.getRequestDispatcher("jsp/admin/error.jsp").forward(request, response);
        }
    }

    // 显示编辑用户表单
    private void showEditUserForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if(idStr == null || idStr.trim().isEmpty()){
            request.setAttribute("error", "用户ID不能为空。");
            request.getRequestDispatcher("jsp/admin/error.jsp").forward(request, response);
            return;
        }

        int id = Integer.parseInt(idStr);
        User user = null;
        try {
            user = userService.getUserById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(user == null){
            request.setAttribute("error", "未找到该用户。");
            request.getRequestDispatcher("jsp/admin/error.jsp").forward(request, response);
            return;
        }

        request.setAttribute("user", user);
        request.getRequestDispatcher("jsp/admin/user_edit.jsp").forward(request, response);
    }

    // 更新用户
    private void editUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取表单参数
        String idStr = request.getParameter("id");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String userType = request.getParameter("userType");

        // 基本验证
        if(idStr == null || idStr.trim().isEmpty() ||
                username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                userType == null || userType.trim().isEmpty()){
            request.setAttribute("error", "请填写所有必填字段。");
            request.getRequestDispatcher("jsp/admin/error.jsp").forward(request, response);
            return;
        }

        int id = Integer.parseInt(idStr);
        User user = null;
        try {
            user = userService.getUserById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(user == null){
            request.setAttribute("error", "未找到该用户。");
            request.getRequestDispatcher("jsp/admin/error.jsp").forward(request, response);
            return;
        }

        // 更新用户对象
        user.setUsername(username.trim());
        user.setPassword(password.trim());
        user.setUserType(userType.trim());

        // 更新用户
        int result = 0;
        try {
            result = userService.updateUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(result > 0){
            response.sendRedirect(request.getContextPath() + "/adminServlet.do?method=listUser");
        } else {
            request.setAttribute("error", "更新用户失败。");
            request.getRequestDispatcher("jsp/admin/error.jsp").forward(request, response);
        }
    }

    // 删除用户
    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if(idStr == null || idStr.trim().isEmpty()){
            request.setAttribute("error", "用户ID不能为空。");
            request.getRequestDispatcher("jsp/admin/error.jsp").forward(request, response);
            return;
        }

        int id = Integer.parseInt(idStr);
        int result = 0;
        try {
            result = userService.deleteUser(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(result > 0){
            response.sendRedirect(request.getContextPath() + "/adminServlet.do?method=listUser");
        } else {
            request.setAttribute("error", "删除用户失败。");
            request.getRequestDispatcher("jsp/admin/error.jsp").forward(request, response);
        }
    }
}

