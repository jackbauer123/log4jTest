package log4jdbc.test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	App app = new App();
    	app.test1();
    }
    
    

    public void test1(){
        String sql = "update emp t set t.desc=? where t.id=?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            //conn.setAutoCommit(false);
            ps = conn.prepareStatement(sql);
            ps.setString(1, "kkkkk12");
            ps.setInt(2, 2);
            ps.executeUpdate(); 
            throw new RuntimeException("测试事务一致");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (Exception e) {
        	System.out.println("kkk");
        	try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws IOException,SQLException,ClassNotFoundException {
        String driverClassName = null;
        String jdbcUrl = null;
        String user = null;
        String password = null;
        // 读取类路径下的配置文件
        InputStream in = getClass().getClassLoader().getResourceAsStream(
                "db.properties");
        Properties properties = new Properties();
        properties.load(in);
        driverClassName = properties.getProperty("driverClassName");
        jdbcUrl = properties.getProperty("url4");
        user = properties.getProperty("username");
        password = properties.getProperty("password");
        Class.forName(driverClassName);
        // 连接信息
        Properties info = new Properties();
        info.put("user", user);
        info.put("password", password);
        // 获取连接
        Connection connection = DriverManager.getConnection(jdbcUrl, user,
                password);
        return connection;
    }
    
}
