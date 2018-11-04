package haur.haurrankingandroid.pdf;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import haur.haurrankingandroid.RankingAppContext;
import haur.haurrankingandroid.domain.DivisionRanking;
import haur.haurrankingandroid.domain.DivisionRankingRow;
import haur.haurrankingandroid.domain.Ranking;
import haur.haurrankingandroid.util.DataFormatUtils;

/**
 * Created by Jarno on 10.10.2018.
 */

public class PdfGenerator {

	private static Font h1Font = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
	private static Font h2Font = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
	private static Font tableHeaderFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	private static Font defaultFont = new Font(Font.FontFamily.TIMES_ROMAN, 12);
	private static Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	private static Ranking ranking;
	private static Document doc;
	private static boolean firstPage = true;

	private static final String TAG = PdfGenerator.class.getCanonicalName();

	public static Uri generatePdf(Ranking rankingData, String dirPath) {
		try {
			ranking = rankingData;
			File file = getFile(dirPath);

			doc = new Document(PageSize.A4, 50, 50, 90, 120);

			PdfWriter pdfWriter = PdfWriter.getInstance(doc, new FileOutputStream(file.getAbsoluteFile()));
			Footer footer = new Footer(DataFormatUtils.dateToString(ranking.getDate()),
					ranking.getTotalResultsCount(), ranking.getCompetitorsWithRank(),
					ranking.getValidClassifiersCount());
			pdfWriter.setPageEvent(footer);

			doc.open();
			doc.addCreator(
					"Haur Ranking application using iText Java " +
							"Library v. 5.0.6 by iText Software under AGPL License.");
			doc.addAuthor("Haukilahden Urheiluampujat");
			doc.addCreationDate();

			doc.add(getTitleParagraph());

			for (DivisionRanking divisionRanking : ranking.getDivisionRankings()) {
				footer.setShowFooterOnPage(true);
				doc.add(getDivisionRankingParagraph(divisionRanking));
			}
			doc.close();
			return Uri.fromFile(file);

		}
		catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
			return null;
		}
	}

	private static File getFile(String dirPath) {
		File file = null;
		try {
			String fileName = "haur_ranking_" + DataFormatUtils.dateToString(new Date()).replace(".", "-");
			if (dirPath == null) dirPath = "/sdcard/HaurRanking/temp/";

			File dir = new File(dirPath);

			if (!dir.exists()) {
				dir.mkdirs();
			}
			String filePath = dirPath + fileName + ".pdf";

			file = new File(filePath);
			if (!file.exists()) {
				file.createNewFile();
			}
		}
		catch (IOException e) {
			Log.e(TAG, e.getMessage(), e);
		}
		return file;
	}
	private static Paragraph getDivisionRankingParagraph(DivisionRanking divisionRanking) {

		Paragraph divisionRankingPara = new Paragraph();
		divisionRankingPara.setSpacingBefore(40);
		divisionRankingPara.setAlignment(Element.ALIGN_CENTER);
		Paragraph titlePara = new Paragraph(new Chunk(divisionRanking.getDivision().toString().toUpperCase() + " ", h2Font));
		titlePara.setSpacingAfter(20);
		titlePara.setAlignment(Element.ALIGN_CENTER);
		divisionRankingPara
				.add(titlePara);

		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(80);
		table.setHorizontalAlignment(Element.ALIGN_CENTER);
		try {

			table.setWidths(new float[]{40, 170, 70, 70, 80});

			// Write table header row

			PdfPCell cell = new PdfPCell(new Paragraph(new Chunk("Sija", tableHeaderFont)));
			cell.setPadding(5);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);

			cell = new PdfPCell(new Paragraph(new Chunk("Nimi", tableHeaderFont)));
			cell.setPadding(5);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);

			cell = new PdfPCell(new Paragraph(new Chunk("%", tableHeaderFont)));
			cell.setPadding(5);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);

			cell = new PdfPCell(new Paragraph(new Chunk("HF-ka", tableHeaderFont)));
			cell.setPadding(5);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);

			String resultsColumnHeader = "Tuloksia*";

			if (firstPage == true) {
				resultsColumnHeader += "*";
				firstPage = false;
			}
			cell = new PdfPCell(new Paragraph(new Chunk(resultsColumnHeader, tableHeaderFont)));
			cell.setPadding(5);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);

			int position = 1;
			for (DivisionRankingRow row : divisionRanking.getRows()) {
				Font rowFont;
				if (row.isImprovedResult()) {
					rowFont = boldFont;

				} else
					rowFont = defaultFont;
				String positionString = "--";
				if (row.isRankedCompetitor())
					positionString = position + ".";
				cell = new PdfPCell(new Paragraph(new Chunk(positionString, rowFont)));
				cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
				cell.setPadding(5);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell);

				cell = new PdfPCell(new Paragraph(new Chunk(
						row.getCompetitor().getFirstName() + " " + row.getCompetitor().getLastName(), rowFont)));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPadding(5);
				cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
				table.addCell(cell);

				String percentageString = "--";
				if (row.isRankedCompetitor())
					percentageString = DataFormatUtils
							.formatTwoDecimalNumberToString(DataFormatUtils.round(row.getResultPercentage(), 2)) + " %";
				cell = new PdfPCell(new Paragraph(new Chunk(percentageString, rowFont)));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(5);
				cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
				table.addCell(cell);

				String averageHfString = "--";
				if (row.isRankedCompetitor())
					averageHfString = DataFormatUtils
							.formatTwoDecimalNumberToString(DataFormatUtils.round(row.getHitFactorAverage(), 2));
				cell = new PdfPCell(new Paragraph(new Chunk(averageHfString, rowFont)));
				cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
				cell.setPadding(5);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				cell = new PdfPCell(new Paragraph(new Chunk(String.valueOf(row.getResultsCount()), rowFont)));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(5);
				cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
				table.addCell(cell);

				position++;
			}

			boolean oddRow = true;
			for (PdfPRow row : table.getRows()) {
				boolean lastRow = table.getRows().indexOf(row) == table.getRows().size() - 1;
				for (PdfPCell tableCell : row.getCells()) {
					tableCell.setBackgroundColor(oddRow ? BaseColor.LIGHT_GRAY : BaseColor.WHITE);
					if (lastRow) {
						tableCell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
					}
				}
				oddRow = !oddRow;
			}

		}
		catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
		}
		divisionRankingPara.add(table);
		return divisionRankingPara;

	}

	private static Paragraph getTitleParagraph() {
		try {
			Paragraph para = new Paragraph();
			para.setSpacingAfter(20);

			PdfPTable table = new PdfPTable(new float[]{20, 80});

			InputStream is = RankingAppContext.getAppContext().getAssets().open("haur_logo.png");
			Bitmap bmp = BitmapFactory.decodeStream(is);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
			Image image = Image.getInstance(stream.toByteArray());

			PdfPCell cell = new PdfPCell();
			cell.setBorder(Rectangle.NO_BORDER);
			cell.addElement(image);
			table.addCell(cell);
			cell = new PdfPCell();
			cell.addElement(new Chunk("HAUR Ranking - " + DataFormatUtils.dateToString(new Date()) + "*", h1Font));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingLeft(15);
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
			para.add(table);
			return para;
		}
		catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
			return null;
		}
	}
}
