package mhsn.kmly.yusf.salh;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hp.hpl.jena.query.ResultSet;

@WebServlet(displayName = "SparqlController", urlPatterns = { "/sparql/" })
public class SparqlController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String query = convertStreamToString(request.getInputStream());
		
		Object res = SparqlHandlerProvider.getSparqlHandler().excuteQuery(query);
		
		System.out.println(res.toString());
	}
	static String convertStreamToString(java.io.InputStream is) {
	    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
}
