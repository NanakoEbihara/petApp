package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.PetDTO;

public class PetDAO extends BaseDAO {

    // 通常のペット一覧（アーカイブ除外）
    public List<PetDTO> selectByUserId(int userId) {
        List<PetDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            String sql = "SELECT * FROM pets WHERE user_id = ? AND is_deleted = false ORDER BY id DESC";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            while (rs.next()) {
                PetDTO dto = mapRowToPetDTO(rs);
                list.add(dto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
            // conn は TransactionManager が管理している場合は閉じない
        }

        return list;
    }

    // ペットIDで1件取得
    public PetDTO selectById(int petId) {
        PetDTO dto = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            String sql = "SELECT * FROM pets WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, petId);
            rs = ps.executeQuery();

            if (rs.next()) {
                dto = mapRowToPetDTO(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        return dto;
    }

    // 新規登録
    public int insert(PetDTO pet) {
        int result = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        TransactionManager tm = null;

        try {
            conn = getConnection();
            tm = new TransactionManager(conn);

            String sql = "INSERT INTO pets (user_id, name, species, breed, birth_date, gender, weight_kg, photo_path, created_at, is_deleted) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, pet.getUserId());
            ps.setString(2, pet.getName());
            ps.setString(3, pet.getSpecies());
            ps.setString(4, pet.getBreed());
            ps.setDate(5, pet.getBirthDate());
            ps.setString(6, pet.getGender());
            ps.setDouble(7, pet.getWeightKg());
            ps.setString(8, pet.getPhotoPath());
            ps.setBoolean(9, false);

            result = ps.executeUpdate();
            tm.commit();

        } catch (SQLException e) {
            if (tm != null) tm.rollback();
            e.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (tm != null) tm.close();  // connもここで閉じる
        }

        return result;
    }

    // 編集
    public int edit(PetDTO pet) {
        int result = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        TransactionManager tm = null;

        try {
            conn = getConnection();
            tm = new TransactionManager(conn);

            String sql = "UPDATE pets SET name = ?, species = ?, breed = ?, birth_date = ?, gender = ?, weight_kg = ?, photo_path = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, pet.getName());
            ps.setString(2, pet.getSpecies());
            ps.setString(3, pet.getBreed());
            ps.setDate(4, pet.getBirthDate());
            ps.setString(5, pet.getGender());
            ps.setDouble(6, pet.getWeightKg());
            ps.setString(7, pet.getPhotoPath());
            ps.setInt(8, pet.getId());

            result = ps.executeUpdate();
            tm.commit();

        } catch (SQLException e) {
            if (tm != null) tm.rollback();
            e.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (tm != null) tm.close();
        }

        return result;
    }

    // 論理削除
    public int logicalDelete(int petId) {
        int result = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        TransactionManager tm = null;

        try {
            conn = getConnection();
            tm = new TransactionManager(conn);

            String sql = "UPDATE pets SET is_deleted = true, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, petId);

            result = ps.executeUpdate();
            tm.commit();

        } catch (SQLException e) {
            if (tm != null) tm.rollback();
            e.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (tm != null) tm.close();
        }

        return result;
    }

    // 共通処理: ResultSet → PetDTO
    private PetDTO mapRowToPetDTO(ResultSet rs) throws SQLException {
        PetDTO dto = new PetDTO();
        dto.setId(rs.getInt("id"));
        dto.setUserId(rs.getInt("user_id"));
        dto.setName(rs.getString("name"));
        dto.setSpecies(rs.getString("species"));
        dto.setBreed(rs.getString("breed"));
        dto.setBirthDate(rs.getDate("birth_date"));
        dto.setGender(rs.getString("gender"));
        dto.setWeightKg(rs.getDouble("weight_kg"));
        dto.setPhotoPath(rs.getString("photo_path"));
        dto.setCreatedAt(rs.getTimestamp("created_at"));
        dto.setUpdatedAt(rs.getTimestamp("updated_at"));
        dto.setIsDeleted(rs.getBoolean("is_deleted"));
        return dto;
    }

//ここからが追加分（登録削除機能）
    public int delete(PetDTO dto) throws SQLException {
        int result = 0;
        Connection conn = getConnection();
        TransactionManager tm = new TransactionManager(conn);

        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM pets WHERE id = ?");
            ps.setInt(1, dto.getId());
            result = ps.executeUpdate();
            tm.commit();
        } catch (SQLException e) {
            tm.rollback();
            e.printStackTrace();
        } finally {
            tm.close();
        }
        return result;
    }
}

