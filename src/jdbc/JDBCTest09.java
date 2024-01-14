package jdbc;

import java.sql.*;
import java.util.Scanner;
/*
使用Statement。
 */
public class JDBCTest09 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("请输入desc或asc【desc表示降序，asc表示升序】:");
        // 用户输入的
        String orderKey = s.next(); // desc

        // 先使用PreparedStatement
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            // 1、注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 2、获取连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bjpowernode","root","0000");
            // 3、获取预编译的数据库操作对象
            stmt = conn.createStatement();
            // 4、执行sql语句
            String sql = "select ename,sal from emp order by sal " + orderKey;
            rs = stmt.executeQuery(sql);
            // 5、处理查询结果集
            while(rs.next()){
                String ename = rs.getString("ename");
                String sal = rs.getString("sal");
                System.out.println(ename + "," + sal);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}