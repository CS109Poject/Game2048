package com.CS109.game2048.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("CallToPrintStackTrace")
public class ConnectionUtil {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
//    private static final String HOST = "sql205.infinityfree.com";
//    private static final String DBNAME = "if0_36540288_mygame";
//    private static final String USER = "if0_36540288";
//    private static final String PASS = "G2h52lo1Us64XH";
//    private static final int PORT = 3306;
//    private static final String DB_URL="jdbc:mysql://"+HOST+":"+PORT+"/"+DBNAME;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test?" +
    "useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "MySQL190504";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public static void closeAll(Connection conn, PreparedStatement pstmt, ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (pstmt != null) {
            pstmt.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    public static void setParameters(PreparedStatement pstmt, Object[] params) throws SQLException {
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
        }
    }

    public static void executeUpdate(String sql, Object[] params) throws ClassNotFoundException, SQLException {

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            setParameters(pstmt, params);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeAll(conn, pstmt, null);
        }

    }

    public static List<String> executeQuery(String sql, Object[] params, String column) throws ClassNotFoundException, SQLException {

        List<String> data = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            setParameters(pstmt, params);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String value = rs.getString(column);
                data.add(value);
            }
            return data;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeAll(conn, pstmt, rs);
        }
    }

}
