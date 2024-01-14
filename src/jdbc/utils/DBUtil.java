package jdbc.utils;

import java.sql.*;
import java.util.ResourceBundle;

/**
 * 数据库工具类，便于JDBC的代码编写。
 */
public class DBUtil {

    // 工具类中的构造方法一般都是私有化的，为什么？
    // 构造方法私有化是为了防止new对象，为什么要防止new对象？
    // 因为工具类中的方法都是静态的，不需要new对象，直接使用“类名.”的方式调用。
    // Suppresses default constructor, ensuring non-instantiability.
    private DBUtil(){}

    // 类加载时绑定属性资源文件
    private static ResourceBundle bundle = ResourceBundle.getBundle("resources/db");

    // 注册驱动
    static {
        try {
            Class.forName(bundle.getString("driver"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接对象
     * @return 新的连接对象
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        String url = bundle.getString("url");
        String user = bundle.getString("user");
        String password = bundle.getString("password");
        Connection conn = DriverManager.getConnection(url,user,password);
        return conn;
    }

    /**
     * 释放资源
     * @param conn 连接对象
     * @param stmt 数据库操作对象
     * @param rs 查询结果集
     */
    public static void close(Connection conn, Statement stmt, ResultSet rs){
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