package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.User;
import dto.HealthRecordDTO;
import dto.HealthRecordItemDTO;
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
            List<PetDTO> petList = petRegisterService.getPetsByUserId(user.getId());
            req.setAttribute("petList", petList);
         
            int petId = req.getParameter("petId") != null
                    ? Integer.parseInt(req.getParameter("petId"))
                    : (petList.isEmpty() ? 0 : petList.get(0).getId());
           
            // 選ばれたペットの名前を取得
            String petName = "";
            for (PetDTO pet : petList) {
                if (pet.getId() == petId) {
                    petName = pet.getName();
                    break;
                }
            }
            // JSP に渡す
            req.setAttribute("petId", petId);
            req.setAttribute("petName", petName);

            HealthRecordDTO todayRecord = healthRecordService.getTodayRecordOnly(petId);
            List<HealthRecordDTO> allRecords = healthRecordService.getAllRecords(petId);

            req.setAttribute("petId", petId);
            req.setAttribute("petName", petName);
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
    	req.setCharacterEncoding("UTF-8");
        int petId = Integer.parseInt(req.getParameter("petId"));

        try {
            // アイテム削除
            String deleteItemIdStr = req.getParameter("deleteItemId");
            if (deleteItemIdStr != null) {
                int deleteItemId = Integer.parseInt(deleteItemIdStr);
                healthRecordService.deleteItem(deleteItemId);
                resp.sendRedirect(req.getContextPath() + "/healthRecord?petId=" + petId);
                return;
            }

            // レコード削除
            String deleteRecordIdStr = req.getParameter("deleteRecordId");
            if (deleteRecordIdStr != null) {
                int deleteRecordId = Integer.parseInt(deleteRecordIdStr);
                healthRecordService.deleteRecord(deleteRecordId);
                resp.sendRedirect(req.getContextPath() + "/healthRecord?petId=" + petId);
                return;
            }

            String action = req.getParameter("action");
            if ("insert".equals(action)) {
                HealthRecordDTO record = new HealthRecordDTO();
                record.setPetId(petId);
                record.setRecordDate(java.sql.Date.valueOf(req.getParameter("recordDate")));
                record.setMealAmount(req.getParameter("mealAmount"));
                record.setGenkiLevel(Integer.parseInt(req.getParameter("genkiLevel")));
                record.setMemo(req.getParameter("memo"));

                List<HealthRecordItemDTO> items = new ArrayList<>();
                for (String param : req.getParameterMap().keySet()) {
                    if (param.startsWith("itemName_new_")) {
                        String idx = param.split("_")[2];
                        HealthRecordItemDTO item = new HealthRecordItemDTO();
                        item.setName(req.getParameter("itemName_new_" + idx));
                        item.setValue(req.getParameter("itemValue_new_" + idx));
                        item.setUnit(req.getParameter("itemUnit_new_" + idx));
                        items.add(item);
                    }
                }
                record.setItems(items);
                healthRecordService.insertRecord(record);

            } else if ("update".equals(action)) {
                List<HealthRecordDTO> records = healthRecordService.getAllRecords(petId);
                for (HealthRecordDTO record : records) {
                    String mealAmount = req.getParameter("mealAmount_" + record.getId());
                    if (mealAmount != null) {
                        record.setMealAmount(mealAmount);

                        List<HealthRecordItemDTO> items = new ArrayList<>();
                        for (String param : req.getParameterMap().keySet()) {
                            if (param.startsWith("itemName_" + record.getId() + "_")) {
                                String idx = param.split("_")[2];
                                HealthRecordItemDTO item = new HealthRecordItemDTO();
                                String idParam = "itemId_" + record.getId() + "_" + idx;
                                if (req.getParameter(idParam) != null) {
                                    item.setId(Integer.parseInt(req.getParameter(idParam)));
                                }
                                item.setName(req.getParameter("itemName_" + record.getId() + "_" + idx));
                                item.setValue(req.getParameter("itemValue_" + record.getId() + "_" + idx));
                                item.setUnit(req.getParameter("itemUnit_" + record.getId() + "_" + idx));
                                items.add(item);
                            }
                        }
                        record.setItems(items);
                        healthRecordService.updateRecord(record);
                    }
                    record.setGenkiLevel(Integer.parseInt(req.getParameter("genkiLevel_" + record.getId())));
                    record.setMemo(req.getParameter("memo_" + record.getId()));
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