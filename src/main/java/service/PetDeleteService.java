package service;

import java.sql.SQLException;

import dao.PetDAO;
import dto.PetDTO;

public class PetDeleteService {

    public boolean petDeleteDo(int petId) throws SQLException {
        PetDAO petDAO = new PetDAO();
        PetDTO dto = new PetDTO();
        dto.setId(petId);

        int result = petDAO.delete(dto);
        if(result == 1) {
			return true;
		}else {
			return false;
    }
}}
