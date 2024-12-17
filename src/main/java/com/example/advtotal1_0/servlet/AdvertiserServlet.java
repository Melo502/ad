package com.example.advtotal1_0.servlet;

import com.example.advtotal1_0.dao.adType.AdTypeDao;
import com.example.advtotal1_0.dao.adType.AdTypeDaoImpl;
import com.example.advtotal1_0.pojo.Ad;
import com.example.advtotal1_0.pojo.AdType;
import com.example.advtotal1_0.service.ad.AdService;
import com.example.advtotal1_0.service.ad.AdServiceImpl;
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
                case "getAdContent" :
                    this.getAdContent(req, resp);
                    break;
                case "postAd":
                    this.postAd(req, resp);
                    break;
                default:
                    // 默认操作或错误处理
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "未知的操作方法！");
                    break;
            }
        } else {
            // 如果没有指定method参数，可以默认展示广告列表
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

        // 获取表单参数
        int advertiserId = 1; // 实际应用中应从登录用户信息获取
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String url = req.getParameter("url");
        String typeIdParam = req.getParameter("typeId");
        String typeKeyWords = req.getParameter("typeKeyWords");
        String status = req.getParameter("status");

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
            imageUrl = "images/" + uniqueFileName;
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

        // 获取表单参数
        String idParam = req.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            req.setAttribute("error", "未指定广告ID！");
            req.getRequestDispatcher("jsp/advertiser/edit_ad.jsp").forward(req, resp);
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            int advertiserId = 1; // 实际应用中应从登录用户信息获取
            String title = req.getParameter("title");
            String description = req.getParameter("description");
            String url = req.getParameter("url");
            String typeIdParam = req.getParameter("typeId");
            String typeKeyWords = req.getParameter("typeKeyWords");
            String status = req.getParameter("status");

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
                ad.setAdvertiserId(advertiserId);
                ad.setTitle(title);
                ad.setDescription(description);
                ad.setUrl(url);
                ad.setTypeId(typeId);
                ad.setStatus(status);
                ad.setTypeKeyWords(typeKeyWords);
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
        List<Ad> adList = null;  // 获取所有广告
        try {
            adList = adService.getAllAds();
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
                req.setAttribute("message", "该广告已被停用，请选择其他广告或重新启用。");
                req.getRequestDispatcher("advertiserServlet.do?method=showAdPlacementPage").forward(req, resp);
                return;
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
                    "http://localhost:8080/js/ad_display.js?adIds=%s",
                    adIdStrBuilder.toString()
            );
            String adScript = String.format("<script id='adScript' src='%s'></script>", scriptSrc);

            // 将广告代码片段传递给结果页面
            req.getServletContext().setAttribute("adScripts", adScript);
            req.setAttribute("website", website);

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

}

