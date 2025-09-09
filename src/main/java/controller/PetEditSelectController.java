package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.User;  //dto.UserDTO じゃなくてこっち
import dto.PetDTO;
import service.PetRegisterService;

@WebServlet("/PetEditSelect")
public class PetEditSelectController extends HttpServlet {
    private PetRegisterService service = new PetRegisterService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // セッションからログイン中のユーザーを取得
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // ユーザーIDを使ってペット一覧を取得
        List<PetDTO> petList = service.getPetsByUserId(user.getId());
        request.setAttribute("petList", petList);

        // JSPへフォワード
        request.getRequestDispatcher("/jsp/petEditSelect.jsp").forward(request, response);
    }
}
