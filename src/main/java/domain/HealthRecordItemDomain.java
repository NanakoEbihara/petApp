package domain;

import dto.HealthRecordItemDTO;

public class HealthRecordItemDomain {
    private HealthRecordItemDTO dto;  // DTO の型名を修正

    public HealthRecordItemDomain(HealthRecordItemDTO dto) {
        this.dto = dto;
    }

    public String getName() {
        return dto.getName();
    }

    public String getValue() {
        return dto.getValue();
    }

    public String getType() {
        return dto.getType();
    }

    // 例: 注意値判定
    public boolean isAlert() {
        if ("食事".equals(dto.getName()) || "元気".equals(dto.getName())) {
            return "×".equals(dto.getValue()) || "なし".equals(dto.getValue());
        }
        if ("嘔吐".equals(dto.getName())) {
            return "〇".equals(dto.getValue()); // 嘔吐は〇が注意
        }
        return false;
    }

    public HealthRecordItemDTO getDto() {
        return dto;
    }
}