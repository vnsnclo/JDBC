package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
JDBC默认情况下对事务是怎么处理的？
    模拟一下银行账户转账操作，A账户向B账户转账10000元。
    从A账户减去10000，向B账户加上10000.
    必须同时成功，或者同时失败！

    转账是需要：执行两条update语句的。

JDBC默认情况下支持自动提交：
    什么叫做自动提交呢？
        只要执行一条DML语句就自动提交一次。

在实际开发中必须将JDBC的自动提交机制关闭掉，改成手动提交。
当一个完整的事务结束之后，再提交。
    conn.setAutoCommit(false); 关闭自动提交机制。
    conn.commit(); 手动提交
    conn.rollback(); 手动回滚
 */
public class JDBCTest12 {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            // 1、注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 2、获取连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bjpowernode","root","0000");
            // 开启事务：将自动提交机制关闭掉。
            conn.setAutoCommit(false);

            // 3、获取预编译的数据库操作对象
            String sql = "update t_act set balance = ? where actno = ?";
            ps = conn.prepareStatement(sql);

            //给?传值
            ps.setDouble(1, 10000);
            ps.setString(2, "A");
            int count = ps.executeUpdate(); //更新成功之后表示更新1条，返回1

            //Thread.sleep(1000 * 10);
            // 模拟异常！！！！
            //String s = null;
            //s.toString();

            // 给?传值
            ps.setDouble(1, 10000);
            ps.setString(2, "B");
            count += ps.executeUpdate(); //再次更新1条再返回1

            System.out.println(count == 2 ? "转账成功" : "转账失败！");

            // 代码能够执行到此处，说明上面的代码没有出现任何异常，表示都成功了，手动提交。
            // 手动提交，事务结束。
            conn.commit();

        } catch (Exception e) {
            // 出现异常的话，为了保险起见，这里要回滚！
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();
        } finally {
            // 6、释放资源
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
