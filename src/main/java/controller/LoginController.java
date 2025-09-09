package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.User;
import service.UserLoginService;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	//doGetメソッドによって最初にユーザーに表示するlogin.jspへフォワード
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		RequestDispatcher rd =request.getRequestDispatcher("/jsp/login.jsp");
			rd.forward(request, response);
	}
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
			request.setCharacterEncoding("UTF-8");
			
			//リクエストパラメータを取得
			String loginId =request.getParameter("loginId");
			String password =request.getParameter("password");
			
			//UserLoginServiceのloginCheckメソッドにパラメータを渡す
			UserLoginService loginService = new UserLoginService();
			User user = null;
			try {
				user = loginService.loginCheck(loginId, password);
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			
			if(user != null) {
				//ログイン成功した場合はセッションスコープにDomeinを保存
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				RequestDispatcher rd = request.getRequestDispatcher("/jsp/home.jsp");
						rd.forward(request, response);
			}else {
				//ログインIDやパスワードが間違っていた場合はリクエストスコープにエラーメッセージを格納
				request.setAttribute("loginError","ログインIDまたはパスワードが間違っています.");

				RequestDispatcher rd =request.getRequestDispatcher("/jsp/login.jsp");
								rd.forward(request, response);
			}
	}
}
