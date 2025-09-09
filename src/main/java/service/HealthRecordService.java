package service;

import java.sql.SQLException;
import java.util.List;

import dao.HealthRecordDAO;
import dto.HealthRecordDTO;

public class HealthRecordService {

    private HealthRecordDAO dao = new HealthRecordDAO();

    public List<HealthRecordDTO> getAllRecords(int petId) throws SQLException {
        return dao.selectByPetId(petId);
    }

    public HealthRecordDTO getRecordById(int id) throws SQLException {
        return dao.selectById(id);
    }

    public void insertRecord(HealthRecordDTO record) throws SQLException {
        dao.insert(record);
    }

    public void updateRecord(HealthRecordDTO record) throws SQLException {
        dao.update(record);
    }

    public void deleteRecord(int recordId) throws SQLException {
        dao.delete(recordId);
    }

    public HealthRecordDTO getTodayRecordOnly(int petId) throws SQLException {
        List<HealthRecordDTO> records = dao.selectByPetId(petId);
        java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
        for (HealthRecordDTO r : records) {
            if (r.getRecordDate().equals(today)) {
                return r;
            }
        }
        return null;
    }
    
    public void deleteItem(int itemId) {
    	// do this later.
    	return;
    }
}