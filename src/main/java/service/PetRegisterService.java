package service;

import java.util.List;

import dao.PetDAO;
import dto.PetDTO;

public class PetRegisterService {

    private PetDAO petDAO;
    
    public PetRegisterService() {
    	petDAO = new PetDAO();
    }

    // 新規ペット登録
    public int registerPet(PetDTO pet) {
        return petDAO.insert(pet);
    }

    // ペット情報編集
    public int editPet(PetDTO pet) {
        return petDAO.edit(pet);
    }

    // ユーザーIDからペット一覧を取得
    public List<PetDTO> getPetsByUserId(int userId) {
        return petDAO.selectByUserId(userId);
    }

    // ペット1件取得
    public PetDTO getPet(int petId) {
        return petDAO.selectById(petId);
    }
}
