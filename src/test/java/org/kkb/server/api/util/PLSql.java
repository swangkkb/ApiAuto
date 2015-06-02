package org.kkb.server.api.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ws.wang on 15-1-28.
 */
public class PLSql implements OptDB {
    private static String  url="jdbc:postgresql://192.168.30.56:5432/";
    private static String driver="org.postgresql.Driver";
    private static String username="postgres";
    private static String password="123456";


    @Override
    public Connection getCon(String driver, String url, String username, String password) {
        if (driver == null || driver.isEmpty()) {
            return null;
        } else if (url == null || url.isEmpty() || url.equalsIgnoreCase("")) {
            return null;
        } else if (username == null || username.equalsIgnoreCase("") || username.isEmpty()) {
            return null;
        } else if (password == null || password.equalsIgnoreCase("") || password.isEmpty()) {
            return null;
        }

        try {
            Class.forName(driver).newInstance();
            Connection con = DriverManager.getConnection(url, username, password);
            return con;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void closeCon(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static ResultSet select(String sql,String dbName){
        Connection connection;
        try {
            OptDB plsql = new PLSql();
            //connection = plsql.getCon("org.postgresql.Driver", "jdbc:postgresql://192.168.30.46:5432/"+dbName, "postgres", "123456");
            connection = plsql.getCon(driver, url+dbName, username, password);
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void update(String sql) {

    }

    public static int add(String sql,String dbName) {
        try {

            OptDB plsql = new PLSql();
           // Connection connection = plsql.getCon("org.postgresql.Driver", "jdbc:postgresql://192.168.30.46:5432/"+dbName, "postgres", "123456");
            Connection connection = plsql.getCon(driver, url+dbName, username, password);
            Statement st = connection.createStatement();
            int num = st.executeUpdate(sql);
            return num;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int delete(String sql,String dbName) {
        OptDB plsql = new PLSql();
       // Connection connection = plsql.getCon("org.postgresql.Driver", "jdbc:postgresql://192.168.30.46:5432/"+dbName, "postgres", "123456");
        Connection connection = plsql.getCon(driver, url+dbName, username, password);
        Statement st = null;
        try {
            st = connection.createStatement();
            int num = st.executeUpdate(sql);
            return num;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) throws SQLException {
       /* int addNum=add("insert into sale_students(school_id,sale_name,no) values (7,'swang','123456')","cms_production");
        System.out.println();*/

        ResultSet rs=select("select * from sale_students where sale_name='swang' and no='123456'","cms_production");
        List list=new ArrayList();
        while (rs.next())
        {
            System.out.print(rs.getInt(1));
            list.add(rs.getInt( 1 ));
        }
        System.out.println();
    }
     /*public static boolean add(Connection connection, String sql) throws SQLException {
        Statement st = connection.createStatement();
        int num = st.executeUpdate(sql);

        boolean bol = num == 1 ? true : false;
        return bol;
    }

    public static boolean del(Connection connection, String sql) throws SQLException {
        Statement st = connection.createStatement();
        int num = st.executeUpdate(sql);

        boolean bol = num == 1 ? true : false;
        return bol;
    }

    public static boolean update(Connection connection, String sql) throws SQLException {
        Statement st = connection.createStatement();
        int num = st.executeUpdate(sql);

        boolean bol = num == 1 ? true : false;
        return bol;
    }*/
}
