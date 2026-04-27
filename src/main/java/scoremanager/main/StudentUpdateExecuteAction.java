package scoremanager.main;

import bean.Student;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 1. JSPから送られてきた値を受け取る
		int entYear = Integer.parseInt(req.getParameter("ent_year"));
		String studentNo = req.getParameter("no");
		String studentName = req.getParameter("name");
		String classNum = req.getParameter("class_num");
		String isAttendStr = req.getParameter("is_attend"); // チェックボックス

		// 2. 在学フラグの判定（チェックが入っていれば true）
		boolean isAttend = false;
		if (isAttendStr != null) {
			isAttend = true;
		}

		// 3. Beanに値をセット
		Student student = new Student();
		student.setEntYear(entYear);
		student.setStudentNo(studentNo);
		student.setStudentName(studentName);
		student.setClassNum(classNum);
		student.setAttend(isAttend);

		// 4. DAOを使ってDBを更新
		StudentDao sDao = new StudentDao();
		sDao.save(student); // saveメソッドが内部で「あれば更新、なければ登録」をしてくれるはずです

		// 5. 完了したら一覧画面へ「リダイレクト」する
		// フォワードではなくリダイレクトを使うことで、再読み込みによる二重登録を防げます
		res.sendRedirect("StudentList.action");
	}
}