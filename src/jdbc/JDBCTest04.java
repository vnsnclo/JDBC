package jdbc;

import java.sql.*;

/*
注册驱动的第二种方式：类加载注册

mysql的厂家写的类：
class com.mysql.jdbc.Driver {
    static {
		try {
			java.sql.DriverManager.registerDriver(new Driver());
		} catch (SQLException E) {
			throw new RuntimeException("Can't register driver!");
		}
	}
}
 */
public class JDBCTest04 {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            // 1、注册驱动
            // oracle数据库: Class.forName("oracle.jdbc.driver.OracleDriver");
            Class.forName("com.mysql.jdbc.Driver");
            // 2、获取连接
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/bjpowernode?useUnicode=true&characterEncoding=utf8","root","0000");
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