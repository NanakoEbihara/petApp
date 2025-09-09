package dto;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class PetDTO {
    private int id;
    private int userId;
    private String name;
    private String species;
    private String breed;
    private Date birthDate;
    private String gender;
    private double weightKg;
    private String photoPath;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private boolean isDeleted;  // アーカイブ用フラグ

    // 追加: 健康記録リスト
    private List<HealthRecordDTO> healthRecords;

    // getter / setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }

    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }

    public Date getBirthDate() { return birthDate; }
    public void setBirthDate(Date birthDate) { this.birthDate = birthDate; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public double getWeightKg() { return weightKg; }
    public void setWeightKg(double weightKg) { this.weightKg = weightKg; }

    public String getPhotoPath() { return photoPath; }
    public void setPhotoPath(String photoPath) { this.photoPath = photoPath; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    public boolean getIsDeleted() { return isDeleted; }
    public void setIsDeleted(boolean isDeleted) { this.isDeleted = isDeleted; }

    public List<HealthRecordDTO> getHealthRecords() { return healthRecords; }
    public void setHealthRecords(List<HealthRecordDTO> healthRecords) { this.healthRecords = healthRecords; }
}