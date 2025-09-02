package validation;
//正しい内容ではない場合の対応、ユーザーに知らせる機能を持ったモデル

import java.util.ArrayList;
import java.util.List;

public class Validation {

//エラーメッセージを保持するString型のerrorMsgListという名前のリスト
	private List<String> errorMsgList;
	
	public Validation() {
		//コンストラクタ。newしたときに9行目のリストを初期化
		this.errorMsgList = new ArrayList<>();
	}
	//エラーメッセージが１つでも存在するかどうかチェックするメソッド
	public boolean hasErrorMsg() {
		if(errorMsgList.size() > 0) {
			return true;
		}else {
			return false;
		}
	}
	//指定されたテキストがnull（何も入ってない状態）
	//または空文字かどうかをチェック
	//nullが空文字ならエラーメッセージを追加
	public void isBlank(String textName, String text) {
		if(text== null || text.isEmpty()) {
			this.errorMsgList.add(textName + "が入力されていません");
			
		}
	}
	//テキストの長さが最小値以外かどうかをチェック。超えている場合はエラーメッセージを追加
		public void length(String textName, String text, int min, int max) {
			if(text == null || text.length() < min || text.length()> max) {
				this.errorMsgList.add(textName + "は、"+ min + "文字以上,"
						+ max + "文字以下で入力して下さい");
			}
		}
		//テキストの長さが最大値かどうかをチェック。超えている場合はエラーメッセージを追加
		public void length(String textName, String text, int max) {
			if(text == null || text.length() > max) {
				this.errorMsgList.add(textName + "は、"+ max + "文字以上,"
						+ max + "文字以下で入力して下さい");
			}
		}
		//エラーメッセージをリストに追加
		public void addErrorMsg(String msg) {
			errorMsgList.add(msg);
		}
		
		//保持しているエラーメッセージのリストを返す
		public List<String> getErrorMsgList(){
			return errorMsgList;
		}
}
