package jdbc;

import java.sql.*;

/*
使用IDEA工具开发第一个JDBC程序
 */
public class JDBCTest03 {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            // 注册驱动
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            // 获取连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bjpowernode","root","0000");
            // 获取数据库操作对象
            stmt = conn.createStatement();
            // 执行SQL
            String sql = "select e.ename,d.dname from emp e join dept d on e.deptno = d.deptno";
            rs = stmt.executeQuery(sql);
            // 处理查询结果集
            while(rs.next()){
                String ename = rs.getString("ename");
                String dname = rs.getString("dname");
                System.out.println(ename + "," + dname);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 释放资源
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