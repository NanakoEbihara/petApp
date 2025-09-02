package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dto.HealthRecordDTO;
import dto.HealthRecordItemDTO;

public class HealthRecordDAO extends BaseDAO {

    // --- 1件取得 ---
    public HealthRecordDTO selectById(int id) throws SQLException {
        String sql = "SELECT * FROM health_records WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapToDTO(rs, conn);
                }
            }
        }
        return null;
    }

    // --- ペットIDで全件取得 ---
    public List<HealthRecordDTO> selectByPetId(int petId) throws SQLException {
        List<HealthRecordDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM health_records WHERE pet_id = ? ORDER BY record_date DESC";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, petId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapToDTO(rs, conn));
                }
            }
        }
        return list;
    }

    // --- 新規登録 ---
    public int insert(HealthRecordDTO dto) throws SQLException {
        int result = 0;
        String sql = "INSERT INTO health_records (pet_id, record_date, meal_amount, created_at) " +
                     "VALUES (?, ?, ?, CURRENT_TIMESTAMP)";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, dto.getPetId());
                ps.setDate(2, dto.getRecordDate());
                ps.setString(3, dto.getMealAmount());
                result = ps.executeUpdate();

                // 自動採番ID取得
                int recordId = 0;
                try (ResultSet rsKeys = ps.getGeneratedKeys()) {
                    if (rsKeys.next()) {
                        recordId = rsKeys.getInt(1);
                    }
                }

                // items 登録
                insertItems(dto.getItems(), recordId, conn);

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
        return result;
    }

    // --- 更新 ---
    public int update(HealthRecordDTO dto) throws SQLException {
        int result = 0;
        String sql = "UPDATE health_records SET record_date = ?, meal_amount = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setDate(1, dto.getRecordDate());
                ps.setString(2, dto.getMealAmount());
                ps.setInt(3, dto.getId());
                result = ps.executeUpdate();
            }

            // items 更新（全削除して再登録）
            try (Connection conn2 = getConnection()) {
                deleteItemsByRecordId(dto.getId(), conn);
                insertItems(dto.getItems(), dto.getId(), conn);
            }

            conn.commit();
        } catch (SQLException e) {
            throw e;
        }

        return result;
    }

    // --- 削除 ---
    public int delete(int id) throws SQLException {
        int result = 0;

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            try {
                // items 削除
                deleteItemsByRecordId(id, conn);

                // record 削除
                String sql = "DELETE FROM health_records WHERE id = ?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, id);
                    result = ps.executeUpdate();
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
        return result;
    }

    // --- ヘルパーメソッド ---
    private HealthRecordDTO mapToDTO(ResultSet rs, Connection conn) throws SQLException {
        HealthRecordDTO dto = new HealthRecordDTO();
        dto.setId(rs.getInt("id"));
        dto.setPetId(rs.getInt("pet_id"));
        dto.setRecordDate(rs.getDate("record_date"));
        dto.setMealAmount(rs.getString("meal_amount"));
        dto.setCreatedAt(rs.getTimestamp("created_at"));
        dto.setUpdatedAt(rs.getTimestamp("updated_at"));
        return dto;
    }

    private void insertItems(List<HealthRecordItemDTO> items, int recordId, Connection conn) throws SQLException {
        if (items == null || items.isEmpty()) return;
        String sql = "INSERT INTO health_records (name, value, type) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (HealthRecordItemDTO item : items) {
                ps.setString(1, item.getName());
                ps.setString(2, item.getValue());
                ps.setString(3, item.getType());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteItemsByRecordId(int recordId, Connection conn) throws SQLException {
        String sql = "DELETE FROM health_records WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, recordId);
            ps.executeUpdate();
        }
    }
}
