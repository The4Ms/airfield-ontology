package mhsn.kmly.yusf.salh;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.sparql.resultset.JSONOutput;

@WebServlet(displayName = "SparqlController", urlPatterns = { "/sparql" })
public class SparqlController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String query = request.getParameter("query");

		ResultSet res = (ResultSet)SparqlHandlerProvider.getSparqlHandler()
				.excuteQuery(query);

		JSONOutput s = new JSONOutput();
		
		String jsonRes = s.asString(res);
		
		PrintWriter writer = response.getWriter();
		writer.print(jsonRes.length() == 0 ? "No data found" : jsonRes); 
		writer.close();
		
	}
}
