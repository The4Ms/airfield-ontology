package airfield.ontology.visualization.schema;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import airfield.ontology.ModelProvider;
import airfield.ontology.utils.VisJsGraph;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet(displayName = "OntologyController", urlPatterns = { "/schema" })
public class SchemaController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		OntologySchema sc = new OntologySchema(ModelProvider.MODEL);
		VisJsGraph g =  sc.buildSchemaGraph().toVisJsGraph();
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonObj = mapper.writeValueAsString(g);

		response.setContentType("application/json");
		PrintWriter writer = response.getWriter();
		writer.print(jsonObj);
		writer.close();
	}
}
