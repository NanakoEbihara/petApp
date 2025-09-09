//ビューとコントローラーの追加()
//ユーザーの新規登録画面を処理するコントローラー
package controller;

import java.io.IOException;
//IOException を使うためのインポート。ファイル入出力や通信時に発生するエラー処理で必要
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
//JSPなどの画面（View）に処理を転送する RequestDispatcher を使うため
import javax.servlet.ServletException;
//サーブレット処理中の例外（エラー）を扱うため。
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.User; //ユーザー情報（ログインID、パスワード、名前など）を保持する User クラスを使用
import service.UserRegisterService;//ユーザー登録に関する処理（DBチェック・登録など）を行うサービスクラス
import validation.Validation;//入力チェック（空欄、文字数など）を行うクラス


@WebServlet("/register") //このコントローラーは /register というURLにアクセスされたときに動作するサーブレットという意味。
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
	
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/register.jsp");
		///jsp/register.jsp に処理を渡す準備。 rd はそのためのオブジェクト。
			rd.forward(request, response);
		//実際に登録画面（register.jsp）へフォワード（処理の転送）します
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
	request.setCharacterEncoding("UTF-8");
		
	//リクエストパラメータを取得
	String loginId = request.getParameter("loginId");
	String password = request.getParameter("password");
	String name = request.getParameter("name");
		
	//バリデーションで入力値チェック
	Validation validation = new Validation();
	validation.isBlank("ユーザーID",loginId);
	validation.isBlank("パスワード",password);
	validation.isBlank("お名前",name);
	validation.length("ユーザーID",loginId,1,8);
	validation.length("パスワード",password,2,10);
	validation.length("お名前",name,1,10);

	//入力値にエラーがあった場合
	if(validation.hasErrorMsg()) {
		request.setAttribute("errorMsg",validation.getErrorMsgList());
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/register.jsp");
		rd.forward(request,response);
	}
	
	//ユーザ情報をDomainに格納
		User user = new User(loginId,password,name);
	//新規登録したいユーザがDBに既存しているかチェック 
		UserRegisterService registerService = new UserRegisterService();
		boolean result = false;
		try {
			result = registerService.userEntryConfirm(user);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	
	//ユーザが存在しない場合はresultにtrueが格納されている
	if(result == true) {
		//リクエストスコープにDomainを格納してフォワード
		request.setAttribute("user",user);
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/registerConfirm.jsp");
		rd.forward(request,response);
	}else {
		//既にユーザーが存在す場合はエラーメッセージを用意して新規登録画面へ戻す
		validation.addErrorMsg("入力いただいたID「"+loginId+ "」は既に使われています");
		request.setAttribute("errorMsg",validation.getErrorMsgList());
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/register.jsp");
		rd.forward(request,response);
		}
	}
}

