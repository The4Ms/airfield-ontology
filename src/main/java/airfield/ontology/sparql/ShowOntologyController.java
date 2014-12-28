package airfield.ontology.sparql;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import airfield.ontology.ModelProvider;

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
