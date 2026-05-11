package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import tool.Action;

public class SubjectDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 1. 一覧画面から受け取る（パラメータ名は "cd"）
        String subjectCd = request.getParameter("cd");

        // ※科目名も必要な場合は、SubjectDAOを使って科目コードからDB検索を行うか、
        // 一覧画面のリンクにあらかじめ &name=... のように付与する必要があります。

        // 2. JSPへ渡す
        request.setAttribute("cd", subjectCd);

        // 3. 削除確認画面へフォワード（絶対パスでwebapp直下から指定）
        request.getRequestDispatcher("/scoremanager/subject_delete.jsp").forward(request, response);
    }
}
