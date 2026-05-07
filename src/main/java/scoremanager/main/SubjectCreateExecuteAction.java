package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		// 1. JSPの <input name="code"> と <input name="name"> から取得
		String subjectCd = req.getParameter("code");
		String subjectName = req.getParameter("name");

		// 2. Beanの作成
		Subject subject = new Subject();
		
		// --- ここが修正ポイント ---
		// Subject.java の定義に合わせて setSubjectCd, setSubjectName を使用
		subject.setSubjectCd(subjectCd);
		subject.setSubjectName(subjectName);
		
		// teacherの学校オブジェクトから学校コードを取得してセット
		subject.setSchoolCd(teacher.getSchool().getSchoolCd());
		// -----------------------

		// 3. DAOを使って保存
		SubjectDAO sDao = new SubjectDAO();
		sDao.save(subject);

		// 4. 次の画面へ（完了画面など）
		req.getRequestDispatcher("subject_create_done.jsp").forward(req, res);
	}
}