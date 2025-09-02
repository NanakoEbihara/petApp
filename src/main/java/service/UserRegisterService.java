//ユーザー登録処理を担当するサービス層のクラス
//録前の重複確認,実際のDB登録処理
package service;

import java.sql.SQLException;

import dao.UserDAO;
import domain.User;
import dto.UserDTO;

public class UserRegisterService {
	public boolean userEntryConfirm(User user) throws SQLException {
		//userEntryConfirmメソッド誕生！！
		//User:クラス名（型名）domain.User を指す。user:そのメソッドの引数名（変数名）
		
		//DBにユーザーがすでに存在するかチェック
		UserDAO userDAO = new UserDAO();
		//データベースにアクセスするための DAO インスタンスを作成。
		UserDTO userDTO = userDAO.selectByLoginId(user.getLoginId());
		
		//ユーザーが存在しない場合→登録内容確認画面へ
		if(userDTO == null) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean userEntryDo(User user) throws SQLException {
		//userEntryDoメソッド誕生！！実際にユーザー情報をDBに登録する処理
		UserDAO userDAO = new UserDAO();
		UserDTO dto = new UserDTO(user.getLoginId(),user.getPassword(),user.getName());
		//ドメインモデル（User）から DTO に変換して登録準備
		
		
		int result = userDAO.insert(dto); //insert メソッドを実行してDBに登録
		if(result == 1) { //戻り値 result は、成功なら 1、失敗なら 0
			return true;
		}else {
			return false;
		}
	}
}