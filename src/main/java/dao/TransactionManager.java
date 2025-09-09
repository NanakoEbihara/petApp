package dao;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    private Connection conn;

    public TransactionManager(Connection conn) {
        this.conn = conn;
        try {
            conn.setAutoCommit(false); 
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // コミット
    public void commit() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ロールバック
    public void rollback() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 接続クローズ
    public void close() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
