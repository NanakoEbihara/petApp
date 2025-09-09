package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.User;
import dto.PetDTO;
import service.PetRegisterService;

@WebServlet("/home")
public class HomeController extends HttpServlet {
    private PetRegisterService service = new PetRegisterService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if(user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        List<PetDTO> petList = service.getPetsByUserId(user.getId());
        request.setAttribute("petList", petList);
        request.setAttribute("user", user);
        request.getRequestDispatcher("/jsp/home.jsp").forward(request, response);
    }
}
