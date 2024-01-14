package jdbc;

import jdbc.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
测试DBUtil工具类。
 */
public class JDBCTest13 {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 获取连接
            conn = DBUtil.getConnection();
            // 获取预编译的数据库操作对象
            String sql = "select ename,sal from emp where ename like ?";
            ps = conn.prepareStatement(sql);
            // 给?传值
            ps.setString(1, "A%");
            // 执行sql
            rs = ps.executeQuery();
            // 处理结果集
            while(rs.next()){
                System.out.println(rs.getString("ename") + "," + rs.getDouble("sal"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            DBUtil.close(conn, ps, rs);

            // 如果没有结果集对象，调用这个方法的时候第三个参数传null。
            //DBUtil.close(conn, ps, null);
        }
    }
}