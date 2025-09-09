package dto;
//DTOはデータベースから取得した値を保持すること

import java.sql.Timestamp;
//SQLの日付時刻型を使うためのインポート
//SQLの TIMESTAMP 型が必要な理由は「いつ作成されたか（登録日時など）」を正確に記録・管理するため

public class UserDTO {
	//ユーザー情報をまとめて保持するDTOクラス
	
	private int id;
	private String loginId;
	private String password;
	private String name;
	private Timestamp createdAt;
	//ユーザー情報の各項目
	
	//引数なしコンストラクタ,何も値を渡さずにオブジェクトを生成できるようにするため
	public UserDTO() {}
	
	//引数ありコンストラクタ,loginId・password・name をセットしてオブジェクトを作る時に使う
		public UserDTO(String loginId, String password, String name) {
			this.loginId = loginId;
			this.password = password;
			this.name = name;
		}
		
		//Javaでは、変数は private（外から触れない） にして、
		//外部からアクセスさせるときは getter / setter を使うのが基本です

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getLoginId() {
			return loginId;
		}

		public void setLoginId(String loginId) {
			this.loginId = loginId;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Timestamp getCreateAt() {
			return createdAt;
		}

		public void setCreatedAt(Timestamp createAt) {
			this.createdAt = createAt;
		}
}
