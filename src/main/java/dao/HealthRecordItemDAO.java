package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dto.HealthRecordItemDTO;

public class HealthRecordItemDAO extends BaseDAO {

    // --- recordId に紐づく全件取得 ---
    public List<HealthRecordItemDTO> selectByRecordId(int recordId) throws SQLException {
        List<HealthRecordItemDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM health_record_items WHERE record_id = ? ORDER BY id";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, recordId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HealthRecordItemDTO item = new HealthRecordItemDTO();
                    item.setId(rs.getInt("id"));
                    item.setRecordId(rs.getInt("record_id"));
                    item.setName(rs.getString("name"));
                    item.setValue(rs.getString("value"));
                    item.setType(rs.getString("type"));
                    item.setUnit(rs.getString("unit"));
                    item.setCreatedAt(rs.getTimestamp("created_at"));
                    item.setUpdatedAt(rs.getTimestamp("updated_at"));
                    list.add(item);
                }
            }
        }
        return list;
    }

    // --- 新規登録 ---
    public int insert(HealthRecordItemDTO item) throws SQLException {
        String sql = "INSERT INTO health_record_items (record_id, name, value, type, unit, created_at) " +
                     "VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, item.getRecordId());
            ps.setString(2, item.getName());
            ps.setString(3, item.getValue());
            ps.setString(4, item.getType());
            ps.setString(5, item.getUnit());

            int result = ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    item.setId(rs.getInt(1));
                }
            }

            return result;
        }
    }

    // --- 更新 ---
    public int update(HealthRecordItemDTO item) throws SQLException {
        String sql = "UPDATE health_record_items SET name = ?, value = ?, type = ?, unit = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, item.getName());
            ps.setString(2, item.getValue());
            ps.setString(3, item.getType());
            ps.setString(4, item.getUnit());
            ps.setInt(5, item.getId());

            return ps.executeUpdate();
        }
    }

    // --- 削除 ---
    public int delete(int id) throws SQLException {
        String sql = "DELETE FROM health_record_items WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate();
        }
    }

    // --- recordId に紐づく全削除 ---
    public int deleteByRecordId(int recordId) throws SQLException {
        String sql = "DELETE FROM health_record_items WHERE record_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, recordId);
            return ps.executeUpdate();
        }
    }
}
