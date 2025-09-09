// HealthRecordDAO.java
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

    // --- record取得 ---
    public HealthRecordDTO selectById(int id) throws SQLException {
        String sql = "SELECT * FROM health_records WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    HealthRecordDTO dto = mapToDTO(rs);
                    dto.setItems(selectItemsByRecordId(id, conn));
                    return dto;
                }
            }
        }
        return null;
    }

    public List<HealthRecordDTO> selectByPetId(int petId) throws SQLException {
        List<HealthRecordDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM health_records WHERE pet_id=? ORDER BY record_date DESC";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, petId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HealthRecordDTO dto = mapToDTO(rs);
                    dto.setItems(selectItemsByRecordId(dto.getId(), conn));
                    list.add(dto);
                }
            }
        }
        return list;
    }

    // --- item取得 ---
    private List<HealthRecordItemDTO> selectItemsByRecordId(int recordId, Connection conn) throws SQLException {
        List<HealthRecordItemDTO> items = new ArrayList<>();
        String sql = "SELECT * FROM health_record_items WHERE record_id=? ORDER BY id";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
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
                    items.add(item);
                }
            }
        }
        return items;
    }

    // --- insert ---
    public int insert(HealthRecordDTO dto) throws SQLException {
        String sql = "INSERT INTO health_records (pet_id, record_date, meal_amount, genki_level, memo, created_at) VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
        int recordId = 0;
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            conn.setAutoCommit(false);
            ps.setInt(1, dto.getPetId());
            ps.setDate(2, dto.getRecordDate());
            ps.setString(3, dto.getMealAmount());
            ps.setInt(4, dto.getGenkiLevel());
            ps.setString(5, dto.getMemo());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) recordId = rs.getInt(1);
            }
            insertItems(dto.getItems(), recordId, conn);
            conn.commit();
        }
        return recordId;
    }

    // --- update ---
    public void update(HealthRecordDTO dto) throws SQLException {
        String sql = "UPDATE health_records SET record_date=?, meal_amount=?, genki_level=?, memo=?, updated_at=CURRENT_TIMESTAMP WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            ps.setDate(1, dto.getRecordDate());
            ps.setString(2, dto.getMealAmount());
            ps.setInt(3, dto.getGenkiLevel());
            ps.setString(4,  dto.getMemo());
            ps.setInt(5, dto.getId());
            ps.executeUpdate();

            // itemsは削除して再登録
            deleteItemsByRecordId(dto.getId(), conn);
            insertItems(dto.getItems(), dto.getId(), conn);

            conn.commit();
        }
    }

    // --- delete ---
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM health_records WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    
    private void insertItems(List<HealthRecordItemDTO> items, int recordId, Connection conn) throws SQLException {
        if (items == null || items.isEmpty()) return;
        String sql = "INSERT INTO health_record_items (record_id, name, value, type, unit) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (HealthRecordItemDTO item : items) {
                ps.setInt(1, recordId);
                ps.setString(2, item.getName());
                ps.setString(3, item.getValue());
                ps.setString(4, item.getType() != null ? item.getType() : "text");
                ps.setString(5, item.getUnit());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
 // --- items ---
    private void deleteItemsByRecordId(int recordId, Connection conn) throws SQLException {
        String sql = "DELETE FROM health_record_items WHERE record_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, recordId);
            ps.executeUpdate();
        }
    }



    private HealthRecordDTO mapToDTO(ResultSet rs) throws SQLException {
        HealthRecordDTO dto = new HealthRecordDTO();
        dto.setId(rs.getInt("id"));
        dto.setPetId(rs.getInt("pet_id"));
        dto.setRecordDate(rs.getDate("record_date"));
        dto.setMealAmount(rs.getString("meal_amount"));
        dto.setGenkiLevel(rs.getInt("genki_level"));
        dto.setMemo(rs.getString("memo"));
        dto.setCreatedAt(rs.getTimestamp("created_at"));
        dto.setUpdatedAt(rs.getTimestamp("updated_at"));
        return dto;
    }
}
