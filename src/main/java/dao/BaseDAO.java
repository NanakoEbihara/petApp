package dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * BaseDAO - provides database connection via JNDI DataSource.
 * All DAO classes can extend this class.
 */
public class BaseDAO {

    private static DataSource ds;

    static {
        try {
            InitialContext ctx = new InitialContext();
            // Look up the DataSource defined in Tomcat context.xml
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/mydb");
        } catch (NamingException e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError("Cannot initialize DataSource");
        }
    }

    /**
     * Get a new Connection from the DataSource.
     * The caller should close the connection after use (try-with-resources recommended).
     */
    protected Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}