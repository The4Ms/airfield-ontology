package mhsn.kmly.yusf.salh;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(displayName = "SparqlController", urlPatterns = { "/sparql" })
public class SparqlController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String query = request.getParameter("query");

		//System.out.println(query);
		
		Object res = SparqlHandlerProvider.getSparqlHandler().excuteQuery(query);
		
		System.out.println(res.toString());
		
	}
}
