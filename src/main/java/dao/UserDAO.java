package dao;
// このクラスは dao パッケージにあります（データアクセスを扱う場所）

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.UserDTO;

public class UserDAO extends BaseDAO {
//UserDAO クラスは BaseDAO を継承
	
	public UserDTO selectByLoginId(String loginId) throws SQLException {
		UserDTO dto = null;
		//selectByLoginId(String loginId) メソッド
		//引数としてログインIDを受け取り、そのIDに一致するユーザー情報をDBから取得するメソッド
		//いまの UserDTO dto; の行だけでは、「箱を用意した」だけで、中身は null のまま
	
		Connection conn = getConnection();
		
		try {
			PreparedStatement ps = conn.prepareStatement(
					"SELECT * FROM users WHERE login_id = ?");
			ps.setString(1, loginId);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) { //rs は ResultSet オブジェクトで、SQLの検索結果（表形式のデータ）を持っている
				//next() は、その結果の 次の行 にカーソルを移動するメソッド
				dto = new UserDTO();
				//このように「中身を作ってから」じゃないと使えない。
				dto.setId(rs.getInt(1));
				dto.setLoginId(rs.getString(2));
				dto.setPassword(rs.getString(3));
				dto.setName(rs.getString(4));
				dto.setCreatedAt(rs.getTimestamp(5));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return dto;
		//dto型で返す
	}
	//ここからが追加分
	public int insert(UserDTO dto) throws SQLException {
		int result = 0;
		//データベースへ接続
	Connection conn = getConnection();
		//トランザクション処理を開始
	TransactionManager tm = new TransactionManager(conn);
	//トランザクションを設定し、ロールバック・コミットの制御等を行う準備
	
	try {
		PreparedStatement ps = conn.prepareStatement
				("INSERT INTO users(login_id, password,name)VALUES(?,?,?)");
		//SQLの準備・実行する
		ps.setString(1,dto.getLoginId());
		ps.setString(2,dto.getPassword());
		ps.setString(3,dto.getName());
		//1番目の ? に文字列を入れる
		
		//DBへのinsertが成功した件数がint型で返却される
		result = ps.executeUpdate();
		//SELECT文を実行して結果をResultSetで受け取る
		tm.commit();
		//問題なければ commit()
	}catch(SQLException e) {
		tm.rollback(); ////例外発生時はロールバックして、エラーログを出力
		e.printStackTrace();
	}
		tm.close();
		return result; //最後にトランザクションを閉じて、登録件数を返す。

	}
	//ここからが追加分
	public int edit(UserDTO dto) throws SQLException {
		int result = 0;
		
		//データベースへ接続
		Connection conn = getConnection();
		//トランザクション処理を開始
		TransactionManager tm = new TransactionManager(conn);
		//データベースへ接続
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE users SET password = ?, name = ? WHERE login_id = ?");
			ps.setString(1,dto.getPassword());
			ps.setString(2,dto.getName());
			ps.setString(3,dto.getLoginId());
			
			//DBへのupdateが成功した件数がint型で返却される
			result = ps.executeUpdate();
			tm.commit();
		}catch(SQLException e) {
			tm.rollback();
			e.printStackTrace();
		}
		tm.close();
		return result;
		}
	
	//ここからが追加分（登録削除機能）
	public int delete(UserDTO dto) throws SQLException {
		int result = 0;
		
		//データベースへ接続(DB接続を取得（どこかに定義された `getConnection()` メソッドが呼ばれる）)
		Connection conn = getConnection();
		//トランザクション処理を開始
		TransactionManager tm = new TransactionManager(conn);
		//TransactionManagerは自作、複数のDB処理を一つのまとまった操作として管理するためにある
		
		//データベースへ接続
		try {
			PreparedStatement ps = conn.prepareStatement
					("DELETE FROM users WHERE login_id = ?");
			ps.setString(1, dto.getLoginId());
		//DBへのdeleteが成功した件数がint型で返却される
			result = ps.executeUpdate();
					tm.commit(); //削除成功したので、トランザクションをコミット（確定）
			}catch(SQLException e) {
				tm.rollback();
				e.printStackTrace();
			}
			tm.close();
			return result;
			}
	}
