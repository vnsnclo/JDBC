package jdbc;

import jdbc.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
在当前事务中对job='MANAGER'的记录进行查询并锁定，使用行级锁机制。
 */
public class JDBCTest14 {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 获取连接
            conn = DBUtil.getConnection();
            // 开启事务
            conn.setAutoCommit(false);
            // 执行
            String sql = "select ename,sal from emp where empno = ? for update";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, 7369);
            rs = ps.executeQuery();
            while(rs.next()){
                System.out.println(rs.getString("ename") + "," + rs.getDouble("sal"));
            }

            //睡眠
            Thread.sleep(1000 * 20);

            // 提交事务
            conn.commit();
        } catch (SQLException e) {
            // 回滚事务
            if(conn != null){
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
    }
}