package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.User;
import dto.PetDTO;
import service.PetRegisterService;

@WebServlet("/PetSelect")
public class PetSelectController extends HttpServlet {

    private PetRegisterService petRegisterService = new PetRegisterService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // ログインユーザー取得
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // ユーザーのペット一覧を取得
		List<PetDTO> petList = petRegisterService.getPetsByUserId(user.getId());
		req.setAttribute("petList", petList);

		// JSP にフォワード
		req.getRequestDispatcher("/jsp/petSelect.jsp").forward(req, resp);
    }
}
