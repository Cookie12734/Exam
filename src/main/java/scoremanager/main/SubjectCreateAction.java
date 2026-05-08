package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectCreateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // webapp直下からの絶対パス（/から始まるパス）に変更します
        // これにより、webapp/scoremanager/main/subject_create.jsp を正確に参照します
        req.getRequestDispatcher("/scoremanager/subject_create.jsp").forward(req, res);
    }
}	