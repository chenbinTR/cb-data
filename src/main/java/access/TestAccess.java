package access;

import java.sql.*;

public class TestAccess {
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    private PreparedStatement ps;

    public TestAccess() {
    }

    public void connectAccessFile() {
        try {
            String surl = "jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ=E:\\DICT_DATA\\中文-副本\\SpiderResult.mdb";
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            conn = DriverManager.getConnection(surl, null, null);
            System.out.println("连接成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addData(String friend, String msg) {
        String sql = "INSERT INTO message(friend,msg) VALUES(?,?)";
        try {
            System.out.println("执行数据加入操作...");
            ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, friend);
            ps.setString(2, msg);
            ps.executeUpdate();
            System.out.println("数据加入成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getData() {
        try {
            stmt = (Statement) conn.createStatement();
            rs = (ResultSet) stmt.executeQuery("SELECT * FROM message");
            while (rs.next()) {
                System.out.println("执行了");
                String friend = rs.getString(1);
                String msg = rs.getString(2);
                System.out.println(friend);
                System.out.println(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TestAccess ta = new TestAccess();
        ta.connectAccessFile();
        ta.getData();
    }
}