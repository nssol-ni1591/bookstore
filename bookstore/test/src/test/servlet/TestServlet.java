package test.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import test.logic.HelloLogic;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Autowired @Qualifier("HelloLogicBId") HelloLogic logic;
	//@Autowired HelloLogic logic;	// NG

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestServlet() {
        super();
        System.out.println("called TestServlet<init>");
    }

    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    	System.out.println("called TestServlet.init");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain;charset=utf-8");
		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.getWriter().println();

		response.getWriter().append("hello1: ").append(logic.hello1());
		response.getWriter().println();

		response.getWriter().append("hello2: ").append(logic.hello2());
		response.getWriter().println();

		response.getWriter().append("hello3: ").append(logic.hello3());
		response.getWriter().println();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void setLogic(HelloLogic logic) {
		System.out.println("setLogic: logic=" + logic);
		this.logic = logic;
	}
}
