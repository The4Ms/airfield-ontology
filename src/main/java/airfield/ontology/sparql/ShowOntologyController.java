package airfield.ontology.sparql;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import airfield.ontology.ModelProvider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryParseException;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.shared.Lock;
import com.hp.hpl.jena.sparql.resultset.JSONOutput;

@WebServlet(displayName = "ShowOntologyController", urlPatterns = { "/ontologyFile" })
public class ShowOntologyController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		ModelProvider.MODEL.write(response.getOutputStream());
		response.getOutputStream().flush();
	}
}
