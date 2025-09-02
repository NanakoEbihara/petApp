package service;
//ログインチェックを行うサービスクラス。
//コントローラーから呼び出され、データベースとのやり取りやログイン成功可否の判定を担当。


import java.sql.SQLException;

import dao.UserDAO;
import domain.User;
import dto.UserDTO;

public class UserLoginService {

	public User loginCheck(String loginId, String password) throws SQLException {
		//コントローラーなどから送られてきたユーザーの loginIdとpassword
		UserDAO userDAO = new UserDAO();
		//DAOを使って、データベースにアクセスする準備。
		//Controllerから受け取ったパラメーターをDAOのメソッドへ渡す
		UserDTO userDTO = userDAO.selectByLoginId(loginId);
		//入力されたログインIDでデータベースを検索し、該当するユーザー情報を取得
		//DAOでのselect成功時はUserDTOにログインしたユーザー情報が格納されている
		//ユーザーが入力した値とDBの値が等しいかチェック
		//入力されたログインIDでデータベースを検索し、該当するユーザー情報を取得。

		if(userDTO != null && userDTO.getPassword().equals(password))
		{
			//等しい場合,DTOの情報をDomainに移行
			User user = new
			User(userDTO.getLoginId(),userDTO.getPassword(),userDTO.getName());
			user.setId(userDTO.getId());
			return user;
			
		}
		
			return null;
		}
}
