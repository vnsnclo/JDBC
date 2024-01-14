package jdbc;

import java.sql.*;
import java.util.Scanner;

/**
 * 需求：用户在控制台上输入desc则降序，输入asc则升序
 * 思考一下：这个应该选择Statement还是PreparedStatement呢？
 *  选Statement，因为PreparedStatement只能传值，不能进行sql语句的拼接。
 */
public class JDBCTest08 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("请输入desc或asc【desc表示降序，asc表示升序】:");
        // 用户输入的
        String orderKey = s.next(); // desc

        // 先使用PreparedStatement
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 1、注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 2、获取连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bjpowernode","root","0000");
            // 3、获取预编译的数据库操作对象
            String sql = "select ename,sal from emp order by sal ?";
            ps = conn.prepareStatement(sql);
            // 给?传值
            ps.setString(1, orderKey);
            // 4、执行sql语句
            rs = ps.executeQuery();
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
            if (ps != null) {
                try {
                    ps.close();
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