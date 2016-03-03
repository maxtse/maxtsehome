package com.max.tse.db.jdbc;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 15-12-27
 * Time: 下午10:53
 * To change this template use File | Settings | File Templates.
 */
public class SearchServlet extends HttpServlet {

    private JdbcUtils jdbcUtils;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        //获取参数
        String start_time = request.getParameter("start-time");
        String end_time = request.getParameter("end-time");
        String nYear = request.getParameter("nYear");
        int nYearNum = getNYearNum(nYear);
        String city = request.getParameter("city");
        String department = request.getParameter("department");

        //获取统计结果
        int internshipCountThisYear = JdbcUtils.executeYearInternshipCount();//计算今年实习的人数
        int notInternshipCountThisYear = JdbcUtils.executeYearNotInternshipCount();//计算今年没有实习的人数
        int notInternshipLastYear = JdbcUtils.
                executeLastYearNotInternshipCount(start_time + "00:00:00", end_time + "00:00:00");//前一年在此时间段没有实习的学生
        Map<String, Integer> nYearCompanyInternshipCount = JdbcUtils.executeLastYearNotInternshipCount(nYearNum);//获取过去n年中每个公司招收的实习生人数
        int oneAreaInternshipCount = JdbcUtils.executeOneAreaInternshipCount(city, department);//获取某区域内实习生个数
        Map<String, Integer> allAreaInternshipCount = JdbcUtils.executeAllAreaInternshipCount();//获取所有区域的实习生个数
        Map<String, Integer> hasInternshipCompany = JdbcUtils.executeInternshipCompany();//获取至少一个实习生的公司

        //设置返回结果
        request.setAttribute("internshipCountThisYear", internshipCountThisYear);
        request.setAttribute("notInternshipCountThisYear", notInternshipCountThisYear);
        request.setAttribute("notInternshipLastYear", notInternshipLastYear);
        request.setAttribute("nYearCompanyInternshipCount", nYearCompanyInternshipCount);
        request.setAttribute("oneAreaInternshipCount", oneAreaInternshipCount);
        request.setAttribute("allAreaInternshipCount", allAreaInternshipCount);
        request.setAttribute("hasInternshipCompany", hasInternshipCompany);

        //页面返回
        response.sendRedirect("index.jsp");
        request.getRequestDispatcher("index.jsp").forward(request, response);
        super.doGet(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    private int getNYearNum(String nYear) {
        try {
            return Integer.valueOf(nYear);
        } catch (Exception e) {
            return 0;
        }
    }

    private void mockData(HttpServletRequest request) {
        request.setAttribute("internshipCountThisYear", 1);
        request.setAttribute("notInternshipCountThisYear", 38);
        request.setAttribute("notInternshipLastYear", 2);
        request.setAttribute("nYearCompanyInternshipCount", mockCompanyInternshipCount());
        request.setAttribute("oneAreaInternshipCount", 38);
        request.setAttribute("allAreaInternshipCount", mockAreaInternshipCount());
        request.setAttribute("hasInternshipCompany", mockHasInternShipCompany());
    }

    private  Map<String, Integer> mockCompanyInternshipCount() {
        Map<String, Integer> result = newHashMap();
        result.put("搜狗科技有限公司", 0);
        result.put("华为科技有限公司", 0);
        result.put("百度科技有限公司", 38);
        result.put("途牛科技有限公司", 0);
        result.put("美团科技有限公司", 3);
        result.put("360科技有限公司", 0);
        result.put("新东方教育集团", 0);
        result.put("腾讯科技有限公司", 0);
        result.put("去哪儿科技有限公司", 0);
        result.put("搜狐科技有限公司", 0);
        result.put("阿里巴巴科技有限公司", 0);
        result.put("小米科技有限公司", 36);
        result.put("携程科技有限公司", 0);
        return result;
    }

    private Map<String, Integer> mockHasInternShipCompany() {
        Map<String, Integer> result = newHashMap();
        result.put("百度科技有限公司", 38);
        result.put("小米科技有限公司", 36);
        result.put("美团科技有限公司", 3);
        return result;
    }
    private Map<String, Integer> mockAreaInternshipCount() {
        Map<String, Integer> result = newHashMap();
        result.put("深圳-南山区", 0);
        result.put("南京-玄武", 0);
        result.put("上海-长宁区", 0);
        result.put("深圳-龙岗区", 0);
        result.put("杭州-余杭区", 0);
        result.put("北京-朝阳区", 38);
        result.put("北京-海淀区", 38);
        return result;
    }

    private  <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<K, V>();
    }

    private  <T> List<T> newArrayList() {
        return new ArrayList<T>();
    }
}
