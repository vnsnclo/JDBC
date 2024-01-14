package jdbc;

import jdbc.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
在这个事务当中对job='MANAGER'的记录进行update操作。
 */
public class JDBCTest15 {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "update emp set sal = sal * 10 where empno = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, 7698);
            int count = ps.executeUpdate();
            System.out.println("更新了"+count+"条");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, null);
        }
    }
}