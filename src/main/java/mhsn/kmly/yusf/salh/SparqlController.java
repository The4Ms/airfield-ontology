package mhsn.kmly.yusf.salh;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.shared.Lock;
import com.hp.hpl.jena.sparql.resultset.JSONOutput;

@WebServlet(displayName = "SparqlController", urlPatterns = { "/sparql" })
public class SparqlController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String query = request.getParameter("query");

		OntModel model = ModelProvider.MODEL;

		model.enterCriticalSection(Lock.WRITE);
		
		try {
			
			QueryExecution c = QueryExecutionFactory.create(query, model);

			if(c.getQuery().isSelectType()){
				ResultSet res = c.execSelect();

				JSONOutput s = new JSONOutput();

				String jsonRes = s.asString(res);

				PrintWriter writer = response.getWriter();
				writer.print(jsonRes);
				writer.close();
			}
			else if(c.getQuery().isAskType()){
				boolean res = c.execAsk();

				PrintWriter writer = response.getWriter();
				writer.print(res);
				writer.close();
			}
			else{
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			}
			
		} finally {
			model.leaveCriticalSection();
		}

	}
}
