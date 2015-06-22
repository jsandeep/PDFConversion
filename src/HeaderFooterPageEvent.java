import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderFooterPageEvent extends PdfPageEventHelper {
	int pageNumber = 0;
	String uptakeFooter = "C:\\Dev\\test\\PDFConversion\\footer.jpg";
	String uptakeHeader = "C:\\Dev\\test\\PDFConversion\\header.jpg";
	String uptakeImg = "C:\\Dev\\test\\PDFConversion\\uptake.jpg";

	public static final Font font = new Font(FontFamily.TIMES_ROMAN, 8);

	public void onStartPage(PdfWriter writer, Document document) {
		Rectangle rect = writer.getBoxSize("header");

		Image uptake = null;
		try {
			uptake = Image.getInstance(uptakeHeader);
			uptake.setAbsolutePosition(40f, 600f);
			// document.add(uptake);
		} catch (BadElementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ColumnText.showTextAligned(writer.getDirectContent(),
				Element.ALIGN_CENTER, new Phrase("PROCEDURE MANAGEMENT", font),
				rect.getLeft(), rect.getTop(), 0);
		pageNumber++;
	}

	public void onEndPage(PdfWriter writer, Document document) {
		Rectangle rect = writer.getBoxSize("footer");
		Image uptake = null;
		try {
			uptake = Image.getInstance(uptakeImg);
			uptake.setAbsolutePosition(40f, 0f);
			document.add(uptake);
		} catch (BadElementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ColumnText.showTextAligned(writer.getDirectContent(),
				Element.ALIGN_CENTER,
				new Phrase(String.format("%d", pageNumber), font),
				rect.getLeft(),
				rect.getBottom(), 0);
	}
}