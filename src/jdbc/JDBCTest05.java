package jdbc;

import java.sql.*;
import java.util.ResourceBundle;

/*
思想：
    将连接数据库的可变化的4条信息写到配置文件中。
    以后想连接其他数据库的时候，可以直接修改配置文件，不用修改java程序。
    四个信息是什么？
        driver
        url
        user
        password
 */
public class JDBCTest05 {
    public static void main(String[] args) {

        //资源绑定器
        ResourceBundle bundle = ResourceBundle.getBundle("resources/db");
        // 通过属性配置文件拿到信息
        String driver = bundle.getString("driver");
        String url = bundle.getString("url");
        String user = bundle.getString("user");
        String password = bundle.getString("password");

        System.out.println(driver);
        System.out.println(url);
        System.out.println(user);
        System.out.println(password);

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            // 1、注册驱动
            Class.forName(driver);
            // 2、获取连接
            conn = DriverManager.getConnection(url,user,password);
            // 3、获取数据库操作对象
            stmt = conn.createStatement();
            // 4、执行SQL语句
            String sql = "select a.ename as '员工',b.ename as '领导' from emp a left join emp b on a.mgr = b.empno";
            rs = stmt.executeQuery(sql);
            // 5、处理查询结果集
            while(rs.next()){
                String ename = rs.getString("员工");
                String lname = rs.getString("领导");
                System.out.println(ename + "," + lname);
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
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}