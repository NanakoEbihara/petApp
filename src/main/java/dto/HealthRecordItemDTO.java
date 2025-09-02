package dto;

public class HealthRecordItemDTO {
    private int id;
    private int recordId;   // どの健康記録に紐づくか
    private String name;    // 項目名（例：食事、元気、嘔吐）
    private String value;   // 入力値（例：多、普通、少ない、×、〇、数字、自由入力）
    private String type;    // 入力タイプ（select, text, numberなど）

    // 引数なしコンストラクタ
    public HealthRecordItemDTO() {}

    // 引数ありコンストラクタ
    public HealthRecordItemDTO(int recordId, String name, String value, String type) {
        this.recordId = recordId;
        this.name = name;
        this.value = value;
        this.type = type;
    }

    // --- getters/setters ---
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
}
