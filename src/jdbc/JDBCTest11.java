package jdbc;

import java.sql.*;

/*
使用PreparedStatement做模糊查询
 */
public class JDBCTest11 {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 1、注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 2、获取连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bjpowernode","root","0000");
            // 3、获取预编译的数据库操作对象(找出名字中含有O的)
            /*String sql = "select ename from emp where ename like '%?%'"; //这样写错误的！
            ps = conn.prepareStatement(sql);
            ps.setString(1, "O");*/

            // 重点是占位符该怎么写！！！
            String sql = "select ename from emp where ename like ?"; //这样是对的！
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%O%");

            // 4、执行SQL语句
            rs = ps.executeQuery();
            while(rs.next()){
                System.out.println(rs.getString("ename"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 6、释放资源
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