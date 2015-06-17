import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderFooter extends PdfPageEventHelper {
	int pageNumber = 0;
	Phrase header = new Phrase("Procedure Management");

	public void onStartPage(PdfWriter writer, Document document) {
		try {
			document.add(new Paragraph(""));
			document.add(Chunk.NEWLINE);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		pageNumber++;
	}

	public void onEndPage(PdfWriter writer, Document document) {
		Rectangle rect = writer.getBoxSize("art");
		ColumnText.showTextAligned(writer.getDirectContent(),
				Element.ALIGN_RIGHT, header, rect.getRight(), rect.getTop(), 0);

		ColumnText.showTextAligned(writer.getDirectContent(),
				Element.ALIGN_CENTER,
				new Phrase(String.format("page %d", pageNumber)),
				(rect.getLeft() + rect.getRight()) / 2, rect.getBottom() - 18,
				0);
	}
}
