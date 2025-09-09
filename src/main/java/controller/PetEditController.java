package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.PetDTO;
import service.PetRegisterService;

@WebServlet("/PetEdit")
public class PetEditController extends HttpServlet {
    private PetRegisterService service = new PetRegisterService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // URLパラメータから petId を取得
        String petIdStr = request.getParameter("id");
        if (petIdStr == null || petIdStr.isEmpty()) {
            // idがなければ選択画面に戻す
            response.sendRedirect(request.getContextPath() + "/PetEditSelect");
            return;
        }

        PetRegisterService prs = new PetRegisterService();
        
        int petId = Integer.parseInt(petIdStr);
        PetDTO pet = prs.getPet(petId);
        if (pet == null) {
            // 存在しないIDなら選択画面に戻す
            response.sendRedirect(request.getContextPath() + "/PetEditSelect");
            return;
        }

        // 編集フォームに表示するためのデータを渡す
        request.setAttribute("pet", pet);
        request.getRequestDispatcher("/jsp/petEdit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String mode = request.getParameter("mode");
        int id = Integer.parseInt(request.getParameter("id"));

        // 入力されたデータを取得
        String name = request.getParameter("name");
        String species = request.getParameter("species");
        String breed = request.getParameter("breed");
        String birthDate = request.getParameter("birthDate");
        String gender = request.getParameter("gender");
        String weight = request.getParameter("weight");

        // DTOにセット
        PetDTO pet = new PetDTO();
        pet.setId(id);
        pet.setName(name);
        pet.setSpecies(species);
        pet.setBreed(breed);
        pet.setGender(gender);
        if (birthDate != null && !birthDate.isEmpty()) {
            pet.setBirthDate(java.sql.Date.valueOf(birthDate));
        }
        if (weight != null && !weight.isEmpty()) {
            pet.setWeightKg(Double.parseDouble(weight));
        }

        if ("confirm".equals(mode)) {
            // 確認画面へ
            request.setAttribute("pet", pet);
            request.getRequestDispatcher("/jsp/petEditConfirm.jsp").forward(request, response);

        } else if ("update".equals(mode)) {
            // 更新処理
            int result = service.editPet(pet);
            if (result > 0) {
                request.setAttribute("pet", pet);
                request.getRequestDispatcher("/jsp/petEditDone.jsp").forward(request, response);
            } else {
                request.setAttribute("pet", pet);
                request.setAttribute("errorMessage", "更新に失敗しました");
                request.getRequestDispatcher("/jsp/petEdit.jsp").forward(request, response);
            }

        } else {
            // 不正なmodeなら編集選択画面に戻す
            response.sendRedirect(request.getContextPath() + "/PetEditSelect");
        }
    }
}