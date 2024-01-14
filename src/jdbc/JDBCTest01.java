package jdbc;
/*
	JDBC编程六步

		1、注册驱动
			（通知java程序我们即将要连接的是哪个品牌的数据库）

		2、获取数据库连接
			（java进程和mysql进程，两个进程之间的通道开启了）
			（java进程可能在北京，mysql进程在上海）

		3、获取数据库操作对象
			这个对象很重要，用这个对象执行SQL的。

		4、执行SQL语句
			执行CRUD操作

		5、处理查询结果集
			如果第四步是select语句，才有这个第五步

		6、释放资源
			关闭所有的资源（因为JDBC毕竟是进程之间的通信，占用很多资源的，需要关闭！）
*/
import java.sql.DriverManager;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.Connection; // 连接接口interface。
import java.sql.Statement;

public class JDBCTest01{
    public static void main(String[] args){

        Connection conn = null;
        Statement stmt = null;

        try{
            // 1、注册驱动
            // com.mysql.jdbc.Driver是mysql数据库厂家写的，实现了java.sql.Driver接口。
            // 如果是oracle数据库的话，类名就不一样了：oracle.jdbc.driver.OracleDriver
            Driver driver = new com.mysql.jdbc.Driver(); // mysql的。
            // Driver driver = new oracle.jdbc.driver.OracleDriver(); // oracle的。
            DriverManager.registerDriver(driver);

            // 2、获取数据库连接
			/*
				什么是URL？
					统一资源定位符
				任何一个URL都包括：
					协议+IP地址+端口号port+资源名
					http://192.168.100.2:8888/abc

				协议：
					是一个提前规定好的数据传输格式。通信协议有很多：http、https.....
					在传送数据之前，提前先商量好数据传送的格式。
					这样对方接收到数据之后，就会按照这个格式去解析，拿到有价值的数据。

				IP地址：
					网络当中定位某台计算机的。

				PORT端口号：
					定位这台计算机上某个服务的。

				资源名：
					这个服务下的某个资源。

				jdbc:mysql://			这是java程序和mysql通信的协议。
				localhost				这是本机IP地址，本机IP地址还可以写成：127.0.0.1
				3306						mysql数据库端口号
				bjpowernode				mysql数据库的名称

				jdbc:mysql://192.168.111.123:3306/abc

				如果是oracle数据库的话：
					oracle:jdbc:thin:@localhost:1521:bjpowernode
					oracle:jdbc:thin:@  这是oracle和java的通信协议
					localhost			  这是本机IP地址。
					1521					  oracle默认端口
					bjpowernode			  oracle中数据库的名字

				localhost和127.0.0.1都是本机IP地址。死记硬背。

			*/
            String url = "jdbc:mysql://localhost:3306/bjpowernode?useUnicode=true&characterEncoding=utf8";
            String user = "root";
            String password = "0000";
            conn = DriverManager.getConnection(url,user,password);

            //输出连接对象的内存地址
            // com.mysql.jdbc.JDBC4Connection@2aaf7cc2
            // com.mysql.jdbc.JDBC4Connection类实现了java.sql.Connection接口。
            // 实际上我们后续的开发不需要关心底层具体是哪个对象，因为面向接口编程。
            //System.out.println(conn);

            // 3、获取数据库操作对象
            stmt = conn.createStatement();
            //System.out.println(stmt);

            // 通过一个连接对象Connection是可以创建多个Statement对象的
            //Statement stmt2 = conn.createStatement();
            //System.out.println(stmt2);

            // 4、执行SQL语句
            // insert delete update

            /*String insertSql = "insert into dept(deptno,dname,loc) values(50, '销售部', '北京')";
            // Statement接口中的executeUpdate方法专门来执行DML语句的。
            // 该方法的返回值表示：影响了数据库表中的总记录条数！
            int count = stmt.executeUpdate(insertSql);
            System.out.println(count); // 1*/

			/*String updateSql = "update dept set dname = '人事部', loc = '天津' where deptno = 50";
			int count = stmt.executeUpdate(updateSql);
			System.out.println(count);  // 1*/

			String deleteSql = "delete from dept where deptno = 50";
			int count = stmt.executeUpdate(deleteSql);
			System.out.println(count);


        }catch(SQLException e){
            e.printStackTrace();
        } finally{
			// 6、先释放Statement，再释放Connection
			// 分别进行try..catch处理
			// 放到finally中关闭
			if(stmt != null){
				try{
					stmt.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
			if(conn != null){
				try{
					conn.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		}

    }
}

