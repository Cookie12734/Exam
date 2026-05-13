package scoremanager;
//a
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import tool.Action;

public class LoginAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res)
			throws Exception {

		req.getRequestDispatcher("/scoremanager/login.jsp").forward(req, res);
	}
}
