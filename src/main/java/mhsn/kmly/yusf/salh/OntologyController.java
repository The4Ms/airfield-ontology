package mhsn.kmly.yusf.salh;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryParseException;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.shared.Lock;
import com.hp.hpl.jena.sparql.resultset.JSONOutput;

@WebServlet(displayName = "OntologyController", urlPatterns = { "/ontology" })
public class OntologyController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// Not the public variable, either this or use a get/set for each variable
	public class DummyClass implements Serializable {
		public int x;
	}
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		
		DummyClass obj = new DummyClass();
		obj.x = 5;
		
		
		// Convert the java object into a JSON object here
		ObjectMapper mapper = new ObjectMapper();
		String jsonObj = mapper.writeValueAsString(obj);
		
		// Returning a normal graph so that you can see it
		jsonObj = "{\"nodes\":[{\"id\":1,\"label\":\"circle\",\"shape\":\"circle\",\"group\":\"group_x\"},{\"id\":2,\"label\":\"ellipse\",\"shape\":\"ellipse\",\"group\":\"group_x\"},{\"id\":3,\"label\":\"database\",\"shape\":\"database\",\"group\":\"group_x\"},{\"id\":4,\"label\":\"box\",\"shape\":\"box\",\"group\":\"group_x\"}],\"edges\":[{\"from\":3,\"to\":1,\"style\":\"arrow\"},{\"from\":1,\"to\":4,\"style\":\"dash-line\"},{\"from\":1,\"to\":2,\"style\":\"arrow-center\"}]}";

		response.setContentType("application/json");
		PrintWriter writer = response.getWriter();
		writer.print(jsonObj);
		writer.close();
	}
}
