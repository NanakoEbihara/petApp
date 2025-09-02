package service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import dao.HealthRecordDAO;
import dto.HealthRecordDTO;

public class HealthRecordService {

    private HealthRecordDAO dao = new HealthRecordDAO();

    // --- 今日のレコードを取得（取得のみ、挿入しない）---
    public HealthRecordDTO getTodayRecordOnly(int petId) throws SQLException {
        List<HealthRecordDTO> allRecords = dao.selectByPetId(petId);
        Date today = new Date(System.currentTimeMillis());
        for (HealthRecordDTO record : allRecords) {
            if (today.equals(record.getRecordDate())) {
                return record;
            }
        }
        return null; // 今日のレコードがなければ null
    }

    // --- 今日のレコードを取得（なければ作成） ---
    public HealthRecordDTO getTodayRecord(int petId) throws SQLException {
        HealthRecordDTO record = getTodayRecordOnly(petId);
        if (record == null) {
            Date today = new Date(System.currentTimeMillis());
            HealthRecordDTO newRecord = new HealthRecordDTO(petId, today);
            newRecord.setMealAmount("普通"); // デフォルト値
            dao.insert(newRecord);
            return newRecord;
        }
        return record;
    }

    // --- ペットIDで全件取得 ---
    public List<HealthRecordDTO> getAllRecords(int petId) throws SQLException {
        return dao.selectByPetId(petId);
    }

    // --- IDで1件取得 ---
    public HealthRecordDTO getRecordById(int id) throws SQLException {
        return dao.selectById(id);
    }

    // --- 更新 ---
    public int updateRecord(HealthRecordDTO record) throws SQLException {
        return dao.update(record);
    }

    // --- 新規作成 ---
    public int insertRecord(HealthRecordDTO record) throws SQLException {
        return dao.insert(record);
    }

    // --- 削除 ---
    public int deleteRecord(int id) throws SQLException {
        return dao.delete(id);
    }
}
