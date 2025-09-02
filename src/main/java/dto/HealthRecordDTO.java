package dto;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class HealthRecordDTO {
    private int id;
    private int petId;
    private Date recordDate;
    private String mealAmount;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // 任意項目（食事量・元気・嘔吐など）をまとめるリスト
    private List<HealthRecordItemDTO> items = new ArrayList<>();

    // --- constructors ---
    public HealthRecordDTO() {}

    public HealthRecordDTO(int petId, Date recordDate) {
        this.petId = petId;
        this.recordDate = recordDate;
    }

    // --- getters/setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPetId() { return petId; }
    public void setPetId(int petId) { this.petId = petId; }

    public Date getRecordDate() { return recordDate; }
    public void setRecordDate(Date recordDate) { this.recordDate = recordDate; }
    
    public String getMealAmount() { return mealAmount; }
    public void setMealAmount(String mealAmount) { this.mealAmount = mealAmount; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    public List<HealthRecordItemDTO> getItems() { return items; }
    public void setItems(List<HealthRecordItemDTO> items) { this.items = items; }
}