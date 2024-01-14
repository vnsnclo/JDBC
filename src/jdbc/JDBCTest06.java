package jdbc;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/*
模拟用户登录

用户名：fdsa
密码：fdsa' or '1'='1
select * from t_user where login_name = 'fdsa' and login_pwd = 'fdsa' or '1'='1'
登录成功

以上随意输入一个用户名和密码，登录成功了，这种现象被称为SQL注入现象！

导致SQL注入的根本原因是什么？怎么解决？
    导致SQL注入的根本原因是：用户不是一般的用户，用户是懂得程序的，输入的用户名信息以及密码信息中
    含有SQL语句的关键字，这个SQL语句的关键字和底层的SQL语句进行“字符串拼接”，导致原SQL语句的含义
    被扭曲了。最最最最最最主要的原因是：用户提供的信息参与了SQL语句的编译。

    主要因素是：这个程序是先进行的字符串的拼接，然后再进行SQL语句的编译，正好被注入。
 */
public class JDBCTest06 {
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
        Statement stmt = null;
        ResultSet rs = null;
        try {
            //1、注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            //2、获取连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bjpowernode","root","0000");
            //3、获取数据库操作对象
            stmt = conn.createStatement();
            //4、执行SQL
            String sql = "select * from t_user where login_name = '" + loginName + "' and login_pwd = '" + loginPwd + "'";
            System.out.println(sql);
            // 程序执行到此处，才会将以上的sql语句发送到DBMS上。DBMS进行sql语句的编译。
            rs = stmt.executeQuery(sql);
            //5、处理查询结果集
            // 如果以上sql语句中用户名和密码是正确的，那么结果集最多也就查询出一条记录，所以以下不需要while循环，if就够了！
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