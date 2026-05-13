package scoremanager.main;
//a
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import tool.Action;

public class LogoutAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		//ローカル変数の宣言 1
		String url = "";
		HttpSession session=req.getSession();

		if (session.getAttribute("user") != null) {
			session.invalidate();
		}

		url = "logout.jsp";
		req.getRequestDispatcher(url).forward(req, res);
	}

}
