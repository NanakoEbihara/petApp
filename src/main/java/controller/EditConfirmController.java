//editConfirmというURLにPOSTされたリクエストに応じて、ユーザー情報の更新処理を実行
package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.User;
import service.UserEditService;

@WebServlet("/editConfirm")
public class EditConfirmController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
	
		request.setCharacterEncoding("UTF-8");
		
		String loginId =request.getParameter("loginId");
		String password =request.getParameter("editPassword");
		String name =request.getParameter("editName");
		//リクエスト（フォーム）から送られてきた値を取得。
		//フォーム内の <input name="editPassword"> のような部分に対応。
		
		User user = new User(loginId,password,name);
		//更新後の会員情報をDomainに格納
		//「ログインID・パスワード・名前」という会員の情報をまとめて扱うためのオブジェクト
		
		UserEditService editService = new UserEditService();
		boolean result = false;
		try {
			result = editService.userEditDo(user);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		//★ユーザー編集サービスを呼び出し、DBに情報を更新しようとする。
		//更新に成功すると true が返ってくる
		//UserEditService の userEditDo メソッドを呼び、DBへの登録を実行。登録成功なら result は true
		
		//更新に成功した場合はresultにtrueが格納されている
		if(result == true) {
			RequestDispatcher rd = request.getRequestDispatcher("/jsp/editDone.jsp");
			rd.forward(request,response);
		}else {
			//会員情報の更新に失敗した場合は、エラーメッセージを用意して編集画面へ戻す
			request.setAttribute("editError","メンバー情報の更新に失敗しました。");
			RequestDispatcher rd = request.getRequestDispatcher("/jsp/edit.jsp");
			rd.forward(request,response); 
			//登録成功したら registerDone.jsp に進み、登録完了を表示。失敗したら、エラーメッセージをセットして元の編集画面に戻す。
			}
		}
	}

//DB更新に成功したら「編集完了画面」へ、失敗したら「編集画面」に戻す。
