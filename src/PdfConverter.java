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

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
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

	// Function to create header and footer
	public void onEndPage(PdfWriter writer, Document document) {
		PdfContentByte canvas = writer.getDirectContent();
		Rectangle rect = writer.getBoxSize("art");
		rect.setBorder(Rectangle.BOX);
		rect.setBorderWidth(50); // a width of 5 user units
		rect.setBorderColor(BaseColor.BLACK); // a red border
		rect.setUseVariableBorders(true); // the full width will be visible
		canvas.rectangle(rect);
		// ColumnText.showTextAligned(writer.getDirectContent(),
		// Element.ALIGN_RIGHT, new Phrase("My static header text"),
		// rect.getRight(), rect.getTop(), 0);
		ColumnText.showTextAligned(writer.getDirectContent(),
				Element.ALIGN_CENTER, new Phrase(String.format("page %d", 1)),
				(rect.getLeft() + rect.getRight()) / 2, rect.getBottom() - 18,
				0);
	}

	public void createPdf() throws DocumentException, IOException,
			ParseException, com.lowagie.text.DocumentException {
		Procedure procedure = createProcedure();
		OutputStream out = new BufferedOutputStream(new FileOutputStream(
				new File("generatedPDF.pdf")));
		System.out.println("Generating the PDF..");

		Document doc = new Document(PageSize.A4, 120, 54, 54, 54);
		StringBuilder stepString = new StringBuilder();
		stepString.append("<h1>" + procedure.getName() + "</h1>");
		FileOutputStream fos = new FileOutputStream(
				new File("generatedPDF.pdf"));
		PdfWriter pdfWriter = PdfWriter.getInstance(doc, fos);
		Rectangle footer = new Rectangle(125, 25, 560, 800);
		pdfWriter.setBoxSize("footer", footer);
		Rectangle header = new Rectangle(175, 25, 560, 805);
		pdfWriter.setBoxSize("header", header);

		HeaderFooterPageEvent event = new HeaderFooterPageEvent();
		pdfWriter.setPageEvent(event);

		InputStream is;

		// Adding Metadata properties
		PdfConverter.addMetaData(doc, procedure);

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
				doc.newPage();
				stepString.delete(0, stepString.length());

			}
		}

		System.out
				.println("PDF generation complete. Please check the output folder.");
		doc.close();
		out.close();
	}

	// iText allows to add metadata to the PDF which can be viewed in your Adobe
	// Reader
	// under File -> Properties
	private static void addMetaData(Document document, Procedure procedure) {
		document.addTitle(procedure.getName());
		document.addAuthor(procedure.getDetails().getAuthorName());
	}
}