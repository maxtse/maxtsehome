package com.max.tse.db.jdbc;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.max.tse.common.utils.DateFormatUtils;
import com.max.tse.headfirst.decorator.io.InputTest;
import com.max.tse.headfirst.proxy.gumball.State;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 15-12-27
 * Time: 下午12:31
 * To change this template use File | Settings | File Templates.
 */
public class JdbcUtils {

    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    public static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/internship_management?useUnicode=true&characterEncoding=utf-8";

    public static final String JDBC_USERNAME = "root";//数据库用户名

    public static final String JDBC_PASSWORD = "mysql";//数据库密码

    public static Connection getConnection() throws Exception{
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
        return con;
    }

    /**
     * 1.计算今年实习的人数
     * */
    public static int executeYearInternshipCount(){
        Connection con = null;
        Statement statement = null;

        //CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        try {
            con = getConnection();
            statement = con.createStatement();
            String sql = "select year_internship_student_count from statistical_table1 limit 1";
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            closeConnection(con);
        }
        return 0;
    }

    /**
     * 2.计算今年没有实习的人数
     * */
    public static int executeYearNotInternshipCount(){
        Connection con = null;
        //CallableStatement callableStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            con = getConnection();
            statement = con.createStatement();
            String sql = "select year_not_internship_student_count from statistical_table1 limit 1";
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                return resultSet.getInt(1);
            }
            /*callableStatement = con.prepareCall("{call year_not_internship_pro()}");
            callableStatement.execute();*/

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            closeConnection(con);
        }
        return 0;
    }

    /**
     * 3.前一年在此时间段没有实习的学生 year_company_internship_count
     * */
    public static int executeLastYearNotInternshipCount(String startDate, String endDate){
        Connection con = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        try {
            con = getConnection();
            callableStatement = con.prepareCall("{call period_not_internship(?,?,?)}");
            callableStatement.setDate(1, new Date(DateFormatUtils.convertLong(startDate).getTime()));
            callableStatement.setDate(2, new Date(DateFormatUtils.convertLong(endDate).getTime()));

            callableStatement.registerOutParameter(3, Types.INTEGER);
            callableStatement.execute();
            return  callableStatement.getInt(3);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            closeResultSet(resultSet);
            closeStatement(callableStatement);
            closeConnection(con);
        }

    }


    /**
     * 4.获取过去n年中每个公司招收的实习生人数 参数：n
     * */
    public static Map<String, Integer> executeLastYearNotInternshipCount(int n){
        Connection con = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        Map<String, Integer> result = new HashMap<String, Integer>();
        try {
            con = getConnection();
            callableStatement = con.prepareCall("{call year_company_internship_count(?)}");
            callableStatement.setInt(1, n);
            callableStatement.execute();

            resultSet = callableStatement.getResultSet();
            while (resultSet.next()) {
                result.put(resultSet.getString(1), resultSet.getInt(2));
            }
            return result;
        } catch (Exception e) {
            return new HashMap<String, Integer>();
        } finally {
            closeResultSet(resultSet);
            closeStatement(callableStatement);
            closeConnection(con);
        }
    }

    /**
     * 5.获取某区域内实习生个数
     * @param
     * */
    public static int executeOneAreaInternshipCount(String city, String department){
        Connection con = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        try {
            con = getConnection();
            callableStatement = con.prepareCall("{call get_area_internship_count(?,?,?)}");

            callableStatement.setString(1, city);
            callableStatement.setString(2, department);
            callableStatement.registerOutParameter(3, Types.INTEGER);
            callableStatement.execute();

            return callableStatement.getInt(3);
        } catch (Exception e) {
            return 0;
        } finally {
            closeResultSet(resultSet);
            closeStatement(callableStatement);
            closeConnection(con);
        }
    }
    /**
     * 6.获取所有区域的实习生个数
     * @return
     * k:city-department
     * v:count
     * */
    public static Map<String, Integer> executeAllAreaInternshipCount(){
        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Map<String, Integer> result = newHashMap();
        try {
            con = getConnection();
            String sql = "select city,department,internship_count from statistical_table2";
            statement = con.createStatement();
            resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                result.put(resultSet.getString(1) + "-" + resultSet.getString(2), resultSet.getInt(3));
            }
            return result;

        } catch (Exception e) {
            return newHashMap();
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            closeConnection(con);
        }
    }
    /**
     * 7.获取至少一个实习生的公司
     * @return
     * k:city-department
     * v:count
     * */
    public static Map<String, Integer> executeInternshipCompany(){
        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Map<String, Integer> result = newHashMap();
        try {
            con = getConnection();
            String sql = "select company_name,internship_count from statistical_table3 where internship_count > 0";
            statement = con.createStatement();
            resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                result.put(resultSet.getString(1), resultSet.getInt(2));
            }
            return result;

        } catch (Exception e) {
            return newHashMap();
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            closeConnection(con);
        }
    }

    private static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<K, V>();
    }

    private static <T> List<T> newArrayList() {
        return new ArrayList<T>();
    }





    public static void closeConnection(Connection con) {

        try {
            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeStatement(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeResultSet(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main (String[] args) {
        System.out.println(executeYearNotInternshipCount());
        System.out.println(executeYearInternshipCount());
        System.out.println(executeLastYearNotInternshipCount("2015-12-09 00:00:00", "2015-12-31 00:00:00"));
        System.out.println(executeLastYearNotInternshipCount(2));
        int count1 = executeOneAreaInternshipCount("北京", "海淀区");
        System.out.println(count1);
        System.out.println(executeInternshipCompany());
        System.out.println(executeAllAreaInternshipCount());

    }



}
