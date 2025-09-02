package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.User;
import dto.HealthRecordDTO;
import dto.PetDTO;
import service.HealthRecordService;
import service.PetRegisterService;

@WebServlet("/healthRecord")
public class HealthRecordController extends HttpServlet {

    private HealthRecordService healthRecordService = new HealthRecordService();
    private PetRegisterService petRegisterService = new PetRegisterService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            // ユーザーIDでペット一覧取得
            List<PetDTO> petList = petRegisterService.getPetsByUserId(user.getId());
            req.setAttribute("petList", petList);

            // petIdをリクエストパラメータから取得、なければ最初のペット
            int petId = req.getParameter("petId") != null
                    ? Integer.parseInt(req.getParameter("petId"))
                    : (petList.isEmpty() ? 0 : petList.get(0).getId());

            // 今日のレコードを取得（取得のみ、挿入しない）
            HealthRecordDTO todayRecord = healthRecordService.getTodayRecordOnly(petId);

            // 全レコード取得
            List<HealthRecordDTO> allRecords = healthRecordService.getAllRecords(petId);

            req.setAttribute("petId", petId);
            req.setAttribute("todayRecord", todayRecord);
            req.setAttribute("allRecords", allRecords);
            req.setAttribute("todayDate", new java.sql.Date(System.currentTimeMillis()).toString());

            req.getRequestDispatcher("/jsp/healthRecord.jsp").forward(req, resp);

        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "データ取得エラー");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        int petId = Integer.parseInt(req.getParameter("petId"));

        try {
            if ("insert".equals(action)) {
                // 新規追加
                String recordDate = req.getParameter("recordDate");
                String mealAmount = req.getParameter("mealAmount");

                HealthRecordDTO newRecord = new HealthRecordDTO();
                newRecord.setPetId(petId);
                newRecord.setRecordDate(java.sql.Date.valueOf(recordDate));
                newRecord.setMealAmount(mealAmount);

                healthRecordService.insertRecord(newRecord);

            } else if ("update".equals(action)) {
                // 更新
                List<HealthRecordDTO> records = healthRecordService.getAllRecords(petId);
                for (HealthRecordDTO record : records) {
                    String mealAmount = req.getParameter("mealAmount_" + record.getId());
                    if (mealAmount != null && !mealAmount.equals(record.getMealAmount())) {
                        record.setMealAmount(mealAmount);
                        healthRecordService.updateRecord(record);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "データ更新エラー");
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/healthRecord?petId=" + petId);
    }
}
