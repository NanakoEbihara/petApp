package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import validation.Validation;


@WebServlet("/edit") //このサーブレットがどのURLでアクセスされるかを指定するパス。
public class EditController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/edit.jsp");
		rd.forward(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		//文字化けしないように、リクエストの文字エンコーディングを UTF-8 に設定
	
//リクエストパラメータを取得(フォームで送信された「パスワード」「名前」の入力値を受取)
	String editPassword = request.getParameter("editPassword");
	
//リクエストパラメータを取得
	String editName = request.getParameter("editName");
	
//バリデーション
	Validation validation = new Validation();
	validation.isBlank("パスワード",editPassword);
	validation.isBlank("お名前",editName);
	validation.length("パスワード",editPassword,2,10);
	validation.length("お名前",editName,1,10);

	//入力エラーがあった場合
	if(validation.hasErrorMsg()) { //バリデーションエラーがあれば true を返し
		request.setAttribute("errorMsg",validation.getErrorMsgList());
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/edit.jsp");
		rd.forward(request,response);
	}else {
		//変更後の値をリクエストスコープへ保存して確認画面へ
		request.setAttribute("editName",editName);
		request.setAttribute("editPassword",editPassword);
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/editConfirm.jsp");
		rd.forward(request,response); 
	}
}
}

