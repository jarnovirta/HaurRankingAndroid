package haur.haurrankingandroid.pdf;

import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class Footer extends PdfPageEventHelper {
	private boolean showFooterOnPage = false;
	private static Font footerFont = new Font(Font.FontFamily.TIMES_ROMAN, 11);
	private static String rankingDateString;
	private static int totalResultsCount;
	private static int competitorsWithRank;
	private static int validClassifiers;

	public Footer(String date, int results, int competitors, int classifiers) {
		rankingDateString = date;
		totalResultsCount = results;
		competitorsWithRank = competitors;
		validClassifiers = classifiers;
	}

	@Override
	public void onEndPage(PdfWriter writer, Document document) {
		List<Phrase> lines = new ArrayList<Phrase>();
		String thirdLine;
		if (document.getPageNumber() == 1) {
			String firstLine = "* Tulokset huomioitu " + rankingDateString + " saakka. Ranking-sija "
					+ competitorsWithRank + " henkilöllä. Sijaa parantaneet lihavoituina.";
			String secondLine = "   Huomioituja luokitteluohjelmia " + validClassifiers + " (väh. 2 tulosta/ohjelma)"
					+ ", joissa yhteensä " + totalResultsCount + " tulosta.";

			lines.add(new Phrase(firstLine, footerFont));
			lines.add(new Phrase(secondLine, footerFont));
			thirdLine = "**";
		} else
			thirdLine = "*";
		if (showFooterOnPage) {
			thirdLine += " Vähintään 4 tulosta vaaditaan sijoitukseen ja 8 viimeisintä huomioidaan.";
			lines.add(new Phrase(thirdLine, footerFont));
			showFooterOnPage = false;
		}
		int[] offset = new int[] { 40, 55, 75 };
		PdfContentByte cb = writer.getDirectContent();
		int i = 0;
		for (Phrase line : lines) {
			ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, line, 100, document.bottom() - offset[i++], 0);
		}
	}

	public void setShowFooterOnPage(boolean showFooterOnPage) {
		this.showFooterOnPage = showFooterOnPage;
	}
}
