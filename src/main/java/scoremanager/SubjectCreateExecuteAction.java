package scoremanager;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectCreateExecuteAction extends Action { 

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 1. 画面（JSP）から送られてきたパラメータを取得
        String cd = request.getParameter("cd");
        String name = request.getParameter("name");

        // セッションからログインユーザー（Teacher等）の情報を取得し、学校コードを特定する
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // インスタンスの準備
        SubjectDAO sDao = new SubjectDAO();
        Subject subject = new Subject();

        // 2. 未入力チェック
        if (cd == null || cd.isEmpty() || name == null || name.isEmpty()) {
            request.setAttribute("error", "科目コードと科目名を入力してください");
            request.setAttribute("cd", cd);
            request.setAttribute("name", name);
            request.getRequestDispatcher("subject_create.jsp").forward(request, response);
            return;
        }

        // 3. 重複チェック
        Subject exists = sDao.get(cd);
        if (exists != null) {
            request.setAttribute("error", "科目コードが重複しています");
            request.setAttribute("cd", cd);
            request.setAttribute("name", name);
            request.getRequestDispatcher("subject_create.jsp").forward(request, response);
            return;
        }

        // 4. DBへ保存（Subject.java の新しいメソッド名を使用）
        subject.setSubjectCd(cd);
        subject.setSubjectName(name);
        // ログインユーザーがいる場合、その学校コードをセット
        if (teacher != null) {
        	subject.setSchoolCd(teacher.getSchool().getSchoolCd());
        }
        
        sDao.save(subject);

        // 5. 完了画面を表示
        request.getRequestDispatcher("subject_create_done.jsp").forward(request, response);
    }
}