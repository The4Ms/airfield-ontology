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

@WebServlet(displayName = "IndividController", urlPatterns = { "/individuals" })
public class IndividController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		OntologySchema sc = new OntologySchema(ModelProvider.MODEL);
		VisJsGraph g =  sc.buildIndividualsGraph().toVisJsGraph();
		
		// Convert the java object into a JSON object here
		ObjectMapper mapper = new ObjectMapper();
		String jsonObj = mapper.writeValueAsString(g);
		

		response.setContentType("application/json");
		PrintWriter writer = response.getWriter();
		writer.print(jsonObj);
		writer.close();
	}
}
