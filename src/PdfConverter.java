import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;



public class PdfConverter {

	public static void main(String[] args) throws DocumentException,
			IOException, com.lowagie.text.DocumentException {
		PdfConverter pc = new PdfConverter();
		try {
			pc.createPdf();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This function takes a JSON as input which is stored in the local system
	 * which can be replaced by an API call once integrated with the uptake
	 * environment
	 * 
	 */
	public Procedure createProcedure() throws IOException, ParseException {

		FileReader reader = new FileReader("test.json");
		JSONParser jsonParser = new JSONParser();
		JSONObject procedureObject = (JSONObject) jsonParser.parse(reader);
		Procedure procedure = Procedure.JSONToProcedureObject(procedureObject);
		return procedure;
	}

	public void createPdf() throws DocumentException, IOException,
			ParseException, com.lowagie.text.DocumentException {
		Procedure procedure = createProcedure();
		OutputStream out = new BufferedOutputStream(new FileOutputStream(
				new File("generatedPDF.pdf")));
		System.out.println("Generating the PDF..");

		Document doc = new Document();
		StringBuilder stepString = new StringBuilder();
		stepString.append("<h1>" + procedure.getName() + "</h1>");
		FileOutputStream fos = new FileOutputStream(
				new File("generatedPDF.pdf"));
		PdfWriter pdfWriter = PdfWriter.getInstance(doc, fos);
		InputStream is;
		doc.open();
		List<Steps> steps = procedure.getSteps();

		for (Steps step : steps) {
			stepString.append(step.getDetails());
		}

		Image img = null;
		while (stepString.length() > 0) {
			if (stepString.toString().contains("<img")) {
				String html = stepString.substring(0,
						stepString.indexOf("<img"));
				String restHtml = stepString.substring(stepString
						.indexOf("<img"));
				String imageTag = restHtml.substring(restHtml.indexOf("<img"),
						restHtml.indexOf(".jpeg") + 5);
				String imgSrc = imageTag.substring(
						imageTag.indexOf("src=") + 5,
						imageTag.indexOf(".jpeg") + 5);

				// Initialize the image
				img = Image.getInstance(imgSrc);

				// Rendering HTML into a pdf
				is = new ByteArrayInputStream(html.getBytes());
				XMLWorkerHelper.getInstance().parseXHtml(pdfWriter, doc, is);

				// Adding Image to the PDF in place
				doc.add(img);

				// Resetting the HTML String to a new point
				stepString.delete(0, stepString.indexOf(".jpeg") + 5);
				// Setting Image to null
				img = null;
			} else {
				is = new ByteArrayInputStream(stepString.toString().getBytes());
				XMLWorkerHelper.getInstance().parseXHtml(pdfWriter, doc, is);

				stepString.delete(0, stepString.length());
			}
		}
		doc.newPage();
		System.out
				.println("PDF generation complete. Please check the output folder.");
		doc.close();
		out.close();
	}
}