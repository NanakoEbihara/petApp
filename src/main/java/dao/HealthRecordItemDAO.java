package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.HealthRecordItemDTO;

public class HealthRecordItemDAO extends BaseDAO {

    // record_id で全件取得
    public List<HealthRecordItemDTO> selectByRecordId(int recordId) throws SQLException {
        List<HealthRecordItemDTO> items = new ArrayList<>();
        Connection conn = getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM health_record_items WHERE record_id = ?");
            ps.setInt(1, recordId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                HealthRecordItemDTO item = new HealthRecordItemDTO();
                item.setId(rs.getInt("id"));
                item.setRecordId(rs.getInt("record_id"));
                item.setName(rs.getString("name"));
                item.setValue(rs.getString("value"));
                item.setType(rs.getString("type"));
                items.add(item);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    // 新規登録
    public int insert(HealthRecordItemDTO item) throws SQLException {
        int result = 0;
        Connection conn = getConnection();
        TransactionManager tm = new TransactionManager(conn);

        try {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO health_record_items (record_id, name, value, type) VALUES (?, ?, ?, ?)");
            ps.setInt(1, item.getRecordId());
            ps.setString(2, item.getName());
            ps.setString(3, item.getValue());
            ps.setString(4, item.getType());

            result = ps.executeUpdate();
            ps.close();
            tm.commit();
        } catch (SQLException e) {
            tm.rollback();
            e.printStackTrace();
        }

        tm.close();
        return result;
    }

    // 更新
    public int update(HealthRecordItemDTO item) throws SQLException {
        int result = 0;
        Connection conn = getConnection();
        TransactionManager tm = new TransactionManager(conn);

        try {
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE health_record_items SET name = ?, value = ?, type = ? WHERE id = ?");
            ps.setString(1, item.getName());
            ps.setString(2, item.getValue());
            ps.setString(3, item.getType());
            ps.setInt(4, item.getId());

            result = ps.executeUpdate();
            ps.close();
            tm.commit();
        } catch (SQLException e) {
            tm.rollback();
            e.printStackTrace();
        }

        tm.close();
        return result;
    }

    // 削除
    public int delete(int id) throws SQLException {
        int result = 0;
        Connection conn = getConnection();
        TransactionManager tm = new TransactionManager(conn);

        try {
            PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM health_record_items WHERE id = ?");
            ps.setInt(1, id);

            result = ps.executeUpdate();
            ps.close();
            tm.commit();
        } catch (SQLException e) {
            tm.rollback();
            e.printStackTrace();
        }

        tm.close();
        return result;
    }

    // record_id に紐づく items を一括削除
    public int deleteByRecordId(int recordId) throws SQLException {
        int result = 0;
        Connection conn = getConnection();
        TransactionManager tm = new TransactionManager(conn);

        try {
            PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM health_record_items WHERE record_id = ?");
            ps.setInt(1, recordId);

            result = ps.executeUpdate();
            ps.close();
            tm.commit();
        } catch (SQLException e) {
            tm.rollback();
            e.printStackTrace();
        }

        tm.close();
        return result;
    }
}

