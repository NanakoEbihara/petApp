package domain;

import java.text.SimpleDateFormat;
//日付を "yyyy年MM月dd日hh時mm分" のような文字列に変換するためのクラスを読み込んでいる。
import java.util.Date;
//日付と時刻を扱うためのクラス

public class User {
	
	private int id;
	private String loginId;
	private String password;
	private String name;
	private Date createdAt;
	private String createdAtStr;
	//これらはUserクラスの属性を表している
	
	public User() {}//引数なしのデフォルトコンストラクタ
	
	public User(String loginId, String password, String name) {
		this.loginId = loginId;
		this.password = password;
		this.name = name;
		//ログインID・パスワード・名前を指定して新しいユーザーを作る時に使うコンストラクタ
	}

	//データを取得するためのメソッド → get〇〇()
	//データを設定するためのメソッド → set〇〇()
	
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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
		this.createdAtStr = new SimpleDateFormat("yyyy年MM月dd日hh時mm分").format(createdAt);
	}

	public String getCreatedAtStr() {
		return createdAtStr;
	}

}
