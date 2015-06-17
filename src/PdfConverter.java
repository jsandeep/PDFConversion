import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.itextpdf.text.DocumentException;
import com.lowagie.text.Document;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.pdf.PdfWriter;

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

		FileReader reader = new FileReader(
				"C:/Users/Sandeep J/Desktop/procedure1.json");
		JSONParser jsonParser = new JSONParser();
		JSONObject procedureObject = (JSONObject) jsonParser.parse(reader);
		Procedure procedure = Procedure.JSONToProcedureObject(procedureObject);
		return procedure;
	}

	public void createPdf() throws DocumentException, IOException,
			ParseException, com.lowagie.text.DocumentException {
		Procedure procedure = createProcedure();
		OutputStream out = new BufferedOutputStream(
				new FileOutputStream(new File(
						"C:/Users/Sandeep J/Desktop/Procedure PDFs/Test2.pdf")));
		System.out.println("Generating the PDF..");
		Document doc = new Document();

		String str = "";
		str += "<h1>" + procedure.getName() + "</h1>";
		HTMLWorker ht = new HTMLWorker(doc);

		PdfWriter.getInstance(doc, new FileOutputStream(new File(
				"C:/Users/Sandeep J/Desktop/Procedure PDFs/Test2.pdf")));
		doc.open();
		List<Steps> steps = procedure.getSteps();
		for (Steps step : steps) {
			str += step.getDetails();
			str += "<br>";

		}
		System.out.println(str);
		ht.parse(new StringReader(str));
		doc.newPage();
		System.out
				.println("PDF generation complete. Please check the output folder.");
		doc.close();
		out.close();

		// try {
		//
		// final FopFactory fopFactory = FopFactory.newInstance(new File(".")
		// .toURI());
		// FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
		// // Construct fop with desired output format
		// Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent,
		// out);
		//
		// // Setup XSLT
		// TransformerFactory factory = TransformerFactory.newInstance();
		// Transformer transformer = factory.newTransformer(new StreamSource(
		// "C:/Users/Sandeep J/Desktop/sampleXslt"));
		//
		// List<Steps> steps = procedure.getSteps();
		// for (Steps step : steps) {
		// str += step.getDetails();
		// }
		// // Setup input for XSLT transformation
		// Source src = new StreamSource(str);
		//
		// // Resulting SAX events (the generated FO) must be piped through to
		// // FOP
		// Result res = new SAXResult(fop.getDefaultHandler());
		// System.out.println("jifhduifhdi");
		// // Start XSLT transformation and FOP processing
		// transformer.transform(src, res);
		// System.out.println("here");
		// } catch (FOPException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (TransformerConfigurationException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (TransformerException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } finally {
		// out.close();
		// }
	}
}
// FOP Test

// FopFactory fopFactory = FopFactory.newInstance(new File(
// "C:/Users/Sandeep J/Desktop/NewTest.json"));
//
// Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);
// TransformerFactory factory = TransformerFactory.newInstance();
// Transformer transformer = factory.newTransformer();
// String str = "";
// List<Steps> steps = procedure.getSteps();
// for (Steps step : steps) {
// str += step.getDetails();
// }
// Source src = new StreamSource(new StringReader(str));
// Result res = new SAXResult(fop.getDefaultHandler());
// transformer.transform(src, res);
// } catch (SAXException e) {
// // TODO Auto-generated catch block
// e.printStackTrace();
// } catch (TransformerException e) {
// // TODO Auto-generated catch block
// e.printStackTrace();
// } finally {
// out.close();
// }
// FLYING SAUCER Test

// StringBuilder string = new StringBuilder();
// string.append("<html>");
// string.append("<head>");
// System.out.println(procedure.getName());
// string.append("<h1>" + procedure.getName() + "</h1>");
// string.append("</head>");
// string.append("<body>");
// List<Steps> steps = procedure.getSteps();
// for (Steps step : steps) {
// string.append(step.getDetails() + "<br>");
// string.append("</br>");
// }
// string.append("</img>");
// string.append("</body>");
// string.append("</html>");
//
// ITextRenderer renderer = new ITextRenderer();
// renderer.setDocumentFromString(string.toString());
// renderer.layout();
// renderer.createPDF(file);
// System.out
// .println("PDF generation complete. Please check the output folder.");
// file.close();
//

// XMLWorker TEST

// String string = new String();
// string += ("<h1>" + procedure.getName() + "</h1>");
// Document document = new Document(PageSize.A4, 36, 36, 54, 54);
// PdfWriter writer = PdfWriter.getInstance(document, file);
//
// HeaderFooter event = new HeaderFooter();
// writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
// writer.setPageEvent(event);
// document.open();
// List<Steps> steps = procedure.getSteps();
// for (Steps step : steps) {
// string += (step.getDetails() + "<br>");
// }
// InputStream is = new ByteArrayInputStream(string.toString()
// .getBytes());
// XMLWorkerHelper.getInstance().parseXHtml(writer, document, is);
//
// System.out
// .println("PDF generation complete. Please check the output folder.");
// document.close();
// file.close();
// } catch (Exception e) {
// e.printStackTrace();
// }

