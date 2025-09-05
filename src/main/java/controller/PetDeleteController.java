package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.PetDAO;
import dto.PetDTO;
import service.PetDeleteService;

@WebServlet("/delete")
public class PetDeleteController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // GETパラメータから petId を取得
        String petIdStr = request.getParameter("petId");
        if (petIdStr != null) {
            int petId = Integer.parseInt(petIdStr);
            PetDAO petDAO = new PetDAO();
            PetDTO pet = petDAO.selectById(petId); // DBからペット情報取得
            request.setAttribute("pet", pet);       // JSPで ${pet} として使える
        }

        // 確認画面へフォワード
        RequestDispatcher rd = request.getRequestDispatcher("/jsp/petdeleteConfirm.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // フォームから送られたペットIDを取得
        int petId = Integer.parseInt(request.getParameter("deleteId"));

        PetDeleteService deleteService = new PetDeleteService();
        boolean result = false;

        try {
            // サービスクラスで削除処理
            result = deleteService.petDeleteDo(petId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (result) { // 削除成功
            // ペット情報だけ消す場合、セッション破棄は不要
            // 完了画面へフォワード
            RequestDispatcher rd = request.getRequestDispatcher("/jsp/petdeleteDone.jsp");
            rd.forward(request, response);
        } else { // 削除失敗
            request.setAttribute("deleteError", "登録情報の削除に失敗しました");
            // 確認画面に戻す
            RequestDispatcher rd = request.getRequestDispatcher("/jsp/petdeleteConfirm.jsp");
            rd.forward(request, response);
        }
    }
}
