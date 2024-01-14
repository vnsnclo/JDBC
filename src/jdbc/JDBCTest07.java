package jdbc;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 怎么避免SQL注入？
 *  SQL注入的根本原因是：先进行了字符串的拼接，然后再进行的编译。
 *
 *  java.sql.Statement接口的特点：先进行字符串的拼接，然后再进行sql语句的编译。
 *     优点：使用Statement可以进行sql语句的拼接。
 *     缺点：因为拼接的存在，导致可能给不法分子机会。导致SQL注入。
 *
 *  java.sql.PreparedStatement接口的特点：先进行SQL语句的编译，然后再进行sql语句的传值。
 *     优点：避免SQL注入。
 *     缺点：没有办法进行sql语句的拼接，只能给sql语句传值。
 *
 *     PreparedStatement预编译的数据库操作对象。
 */
public class JDBCTest07 {
    public static void main(String[] args) {

        // 初始化一个界面，可以让用户输入用户名和密码
        Map<String,String> userLoginInfo = initUI();

        // 连接数据库验证用户名和密码是否正确
        boolean ok = checkNameAndPwd(userLoginInfo.get("loginName"), userLoginInfo.get("loginPwd"));

        System.out.println(ok ? "登录成功" : "登录失败");
    }

    /**
     * 验证用户名和密码
     * @param loginName 登录名
     * @param loginPwd 登录密码
     * @return true表示登录成功，false表示登录失败
     */
    private static boolean checkNameAndPwd(String loginName, String loginPwd) {
        boolean ok = false; //默认是登录失败的！
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            //1、注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            //2、获取连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bjpowernode","root","0000");
            //3、获取预编译的数据库操作对象
            // 注意：一个问号?是一个占位符，一个占位符只能接收一个“值/数据”
            // 占位符 ? 两边不能有单引号。 '?' 这样是错误的。
            String sql = "select * from t_user where login_name = ? and login_pwd = ?";
            stmt = conn.prepareStatement(sql); // 此时会发送sql给DBMS，进行sql语句的编译
            // 给占位符?传值
            // JDBC中所有下标都是从1开始。
            // 怎么解决SQL注入的：即使用户信息中有sql关键字，但是不参加编译就没事。
            stmt.setString(1, loginName); // 1代表第1个问号 ?
            stmt.setString(2, loginPwd); // 2代表第2个问号 ?
            //4、执行SQL
            rs = stmt.executeQuery(); //这个方法不需要将sql语句传递进去。不能是这样：rs = stmt.executeQuery(sql);
            //5、处理查询结果集
            if(rs.next()){
                ok = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //6、释放资源
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(stmt != null){
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
        return ok;
    }

    /**
     * 初始化界面，并且接收用户的输入。
     * @return 存储登录名和登录密码的Map集合。
     */
    private static Map<String,String> initUI() {
        System.out.println("欢迎使用该系统，请输入用户名和密码进行身份认证.");
        Scanner s = new Scanner(System.in);
        System.out.print("用户名：");
        String loginName = s.nextLine(); //接收用户输入，一次接收一行！
        System.out.print("密码：");
        String loginPwd = s.nextLine();

        //将用户名和密码放到Map集合中。
        Map<String,String> userLoginInfo = new HashMap<>();
        userLoginInfo.put("loginName",loginName);
        userLoginInfo.put("loginPwd",loginPwd);

        // 返回Map
        return userLoginInfo;
    }
}