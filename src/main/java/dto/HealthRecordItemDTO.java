package dto;

import java.sql.Timestamp;

public class HealthRecordItemDTO {
    private int id;
    private int recordId;
    private String name;     // 項目名
    private String value;    // 入力値
    private String type;     // text, number, boolean
    private String unit;     // ml, g, 個, など
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // --- getter / setter ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getRecordId() { return recordId; }
    public void setRecordId(int recordId) { this.recordId = recordId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
    
	}
    