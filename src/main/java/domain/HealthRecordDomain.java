// HealthRecordDomain.java
package domain;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class HealthRecordDomain {
    private int id;
    private int petId;
    private Date recordDate;
    private String mealAmount;
    private String memo;
    
	private Timestamp createdAt;
    private Timestamp updatedAt;
    private List<HealthRecordItemDomain> items;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPetId() {
		return petId;
	}
	public void setPetId(int petId) {
		this.petId = petId;
	}
	public Date getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}
	public String getMealAmount() {
		return mealAmount;
	}
	public void setMealAmount(String mealAmount) {
		this.mealAmount = mealAmount;
	}
    public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public Timestamp getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
	public List<HealthRecordItemDomain> getItems() {
		return items;
	}
	public void setItems(List<HealthRecordItemDomain> items) {
		this.items = items;
	}

    
}
