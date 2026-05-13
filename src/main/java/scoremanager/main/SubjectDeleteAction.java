package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import tool.Action;

public class SubjectDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //  一覧画面から受け取る
        String subjectCd = request.getParameter("cd");

        //  JSPへ渡す
        request.setAttribute("cd", subjectCd);

        //  削除確認画面へフォワード
        request.getRequestDispatcher("/scoremanager/subject_delete.jsp").forward(request, response);
    }
}
