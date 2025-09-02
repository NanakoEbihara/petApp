package service;

import java.sql.SQLException;

import dao.UserDAO;
import domain.User; //ユーザー情報（ログインID、パスワード、名前など）を保持する User クラスを使用
import dto.UserDTO;

public class UserDeleteService {
	public boolean userDeleteDo(User user) throws SQLException {

		//削除したいユーザーのloginIdをDTOに格納
		UserDAO userDAO = new UserDAO();
		UserDTO dto = new UserDTO(user.getLoginId(),null,null);
		int result = userDAO.delete(dto);
		
		if(result == 1) {
			return true;
		}else {
			return false;
		}
	}

}
