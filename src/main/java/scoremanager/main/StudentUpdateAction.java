package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentUpdateAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 1. セッションからユーザー情報を取得
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		// 2. リクエストパラメーターから学生番号（no）を取得
		String studentNo = req.getParameter("no");

		// 3. DBから学生の現在の情報を取得
		StudentDao sDao = new StudentDao();
		Student student = sDao.get(studentNo); // 学生番号で検索

		// 4. 入学年度やクラスの選択肢を準備（新規登録と同じ）
		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear();
		List<Integer> entYearSet = new ArrayList<>();
		for (int i = year - 10; i < year + 1; i++) {
			entYearSet.add(i);
		}
		ClassNumDao cDao = new ClassNumDao();
		List<String> classList = cDao.filter(teacher.getSchool());

		// 5. リクエストにデータをセット
		req.setAttribute("student", student); // 取得した学生情報
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("class_num_set", classList);

		// 6. 変更画面（JSP）へフォワード（階層に注意！）
		req.getRequestDispatcher("../student_update.jsp").forward(req, res);
	}
}