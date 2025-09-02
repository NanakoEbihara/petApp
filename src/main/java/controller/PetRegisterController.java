package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.User;
import dto.PetDTO;
import service.PetRegisterService;

@WebServlet("/petRegister")
public class PetRegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PetRegisterService service = new PetRegisterService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String mode = request.getParameter("mode");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            request.setAttribute("errorMessage", "ログインしてください");
            request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
            return;
        }

        PetDTO pet = new PetDTO();
        pet.setUserId(user.getId());
        pet.setName(request.getParameter("name"));
        pet.setSpecies(request.getParameter("species"));
        pet.setBreed(request.getParameter("breed"));
        pet.setGender(request.getParameter("gender"));
        if(request.getParameter("birthDate") != null && !request.getParameter("birthDate").isEmpty()) {
            pet.setBirthDate(java.sql.Date.valueOf(request.getParameter("birthDate")));
        }
        if(request.getParameter("weight") != null && !request.getParameter("weight").isEmpty()) {
            pet.setWeightKg(Double.parseDouble(request.getParameter("weight")));
        }

        if ("confirm".equals(mode)) {
            request.setAttribute("pet", pet);
            request.getRequestDispatcher("/jsp/petRegisterConfirm.jsp").forward(request, response);
        } else if ("register".equals(mode)) {
            int result = service.registerPet(pet);
            if(result > 0) {
                request.setAttribute("pet", pet);
                request.getRequestDispatcher("/jsp/petRegisterDone.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "登録に失敗しました");
                request.getRequestDispatcher("/jsp/petRegister.jsp").forward(request, response);
            }
        } else {
            request.getRequestDispatcher("/jsp/petRegister.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/petRegister.jsp").forward(request, response);
    }
}
