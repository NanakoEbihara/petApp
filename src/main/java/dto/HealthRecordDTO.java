package dto;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class HealthRecordDTO {
    private int id;
    private int petId;
    private Date recordDate;
    private String mealAmount;
    private int genkiLevel;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private List<HealthRecordItemDTO> items; // 追加項目

    // --- getter / setter ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPetId() { return petId; }
    public void setPetId(int petId) { this.petId = petId; }

    public Date getRecordDate() { return recordDate; }
    public void setRecordDate(Date recordDate) { this.recordDate = recordDate; }

    public String getMealAmount() { return mealAmount; }
    public void setMealAmount(String mealAmount) { this.mealAmount = mealAmount; }
    
    public int getGenkiLevel() { return genkiLevel; }
    public void setGenkiLevel(int genkiLevel) { this.genkiLevel = genkiLevel; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    public List<HealthRecordItemDTO> getItems() { return items; }
    public void setItems(List<HealthRecordItemDTO> items) { this.items = items; }
}
