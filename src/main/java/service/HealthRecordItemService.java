package service;

import java.sql.SQLException;
import java.util.List;

import dao.HealthRecordDAO;
import dto.HealthRecordDTO;
import dto.HealthRecordItemDTO;

public class HealthRecordItemService {

    private HealthRecordDAO dao = new HealthRecordDAO();

    // recordId から items を取得
    public List<HealthRecordItemDTO> getItemsByRecordId(int recordId) throws SQLException {
        return dao.selectById(recordId).getItems();
    }

    // item をまとめて更新（record 更新時に使う）
    public void saveItems(int recordId, List<HealthRecordItemDTO> items) throws SQLException {
        HealthRecordDTO record = dao.selectById(recordId);
        if (record != null) {
            record.setItems(items);
            dao.update(record);
        }
    }
}
