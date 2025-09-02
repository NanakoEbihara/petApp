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
import service.UserRegisterService;

@WebServlet("/registerConfirm")
public class RegisterConfirmController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	request.setCharacterEncoding("UTF-8");
		
	String loginId =request.getParameter("loginId");
	String password =request.getParameter("password");
	String name =request.getParameter("name");
	
	User user = new User(loginId,password,name);
	//新規登録したいユーザがDBに既存しているかチェック 
		UserRegisterService userRegister = new UserRegisterService();
		boolean result = false;
		try {
			result = userRegister.userEntryDo(user);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	//UserRegisterService の userEntryDo メソッドを呼び、DBへの登録を実行。登録成功なら result は true
	
	//ユーザが存在しない場合はresultにtrueが格納されている
	if(result == true) {
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/registerDone.jsp");
		rd.forward(request,response);
	}else {
		//新規会員登録に失敗した場合は、エラーメッセージを用意して会員登録画面へ戻す
		request.setAttribute("registerError","新規会員登録に失敗しました。");
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/register.jsp");
		rd.forward(request,response); 
		//登録成功したら registerDone.jsp に進み、登録完了を表示。失敗したら、エラーメッセージをセットして元の登録画面に戻す。
		}
	}
}
