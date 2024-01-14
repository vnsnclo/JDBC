package jdbc;
/*
	处理查询结果集

	提醒一下：
		JDBC中所有的下标都是从1开始。
*/
import java.sql.*;

public class JDBCTest02{
    public static void main(String[] args){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            // 1、注册驱动
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            // 2、获取连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bjpowernode","root","0000");
            // 3、获取数据库操作对象
            stmt = conn.createStatement();
            // 4、执行SQL语句
            // JDBC当中的sql语句不需要以“;”结尾
            String sql = "select empno,ename as hehe,sal from emp order by sal desc";
            // 执行查询语句是这个方法：executeQuery
            // ResultSet就是查询结果集对象，查询的结果都在这个对象当中。
            rs = stmt.executeQuery(sql);

            // 5、处理查询结果集
            // 目前没什么好处理的，直接把结果集中的数据遍历输出吧。
			/*
				ResultSet对象中有以下数据：
				+-------+--------+---------+
				| empno | ename  | sal     |
				+-------+--------+---------+
				|  7839 | KING   | 5500.00 |
				|  7788 | SCOTT  | 3300.00 |
				|  7902 | FORD   | 3300.00 |
				|  7566 | JONES  | 3272.50 |
				|  7698 | BLAKE  | 3135.00 |
				|  7782 | CLARK  | 2695.00 |
				|  7499 | ALLEN  | 1760.00 |
				|  7844 | TURNER | 1650.00 |
				|  7934 | MILLER | 1430.00 |
				|  7654 | MARTIN | 1375.00 |
				|  7521 | WARD   | 1375.00 |
				|  7876 | ADAMS  | 1210.00 |
				|  7900 | JAMES  | 1045.00 |
				|  7369 | SMITH  |  880.00 |
				+-------+--------+---------+
				调用ResultSet接口中相应的方法来遍历结果集
			*/
			/*
			boolean has = rs.next(); //光标向前移动一位
			if(has){
				// 条件成立表示光标指向的行有记录
				// 取当前行的第1个值
				String empno = rs.getString(1); // 注意：getString()这个方法是不管底层数据库表中是什么类型，统一都以String形式返回。
				// 取当前行的第2个值
				String ename = rs.getString(2);
				// 取当前行的第3个值
				String sal = rs.getString(3);
				System.out.println(empno + "," + ename + "," + sal);
			}

			has = rs.next();
			if(has){
				String empno = rs.getString(1);
				String ename = rs.getString(2);
				String sal = rs.getString(3);
				System.out.println(empno + "," + ename + "," + sal);
			}
			*/

            while(rs.next()){
                // 根据下标来取值的，并且都是以String类型取出的！
				/*
				String empno = rs.getString(1);
				String ename = rs.getString(2);
				String sal = rs.getString(3);
				System.out.println(empno + "," + ename + "," + sal);
				*/

                // 根据下标来取值的，以特定类型取出！！！
				/*
				int empno = rs.getInt(1);
				String ename = rs.getString(2);
				double sal = rs.getDouble(3);
				System.out.println(empno + "," + ename + "," + (sal + 100));
				*/

                // 根据查询结果的列名可以取吗？
                // 以后这种方式是常用的，健壮。
                int empno = rs.getInt("empno"); // empno并不是字段的名称，是查询结果的列名。
                //String ename = rs.getString("ename");
                String ename = rs.getString("hehe"); //是查询结果的列名，不是表格中的字段名。
                double sal = rs.getDouble("sal");
                System.out.println(empno + "," + ename + "," + (sal + 100));

            }

        }catch(SQLException e){
            e.printStackTrace();
        } finally{
            // 6、释放资源
            //先关闭ResultSet，再关闭Statement，最后关闭Connection
            if(rs != null){
                try{
                    rs.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
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