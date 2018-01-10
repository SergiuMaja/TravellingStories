package ro.tuc.travellingstories.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import ro.tuc.travellingstories.repositories.StoryRepository;
import ro.tuc.travellingstories.repositories.UserRepository;
import ro.tuc.travellingstories.util.constants.Constant;

@Service
public class ReportService {
	
	private static Log LOGGER = LogFactory.getLog(ReportService.class);
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	StoryRepository storyRepository;

	public ByteArrayInputStream getPdfReport() {
		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		try {
			PdfPTable table = new PdfPTable(2);
			table.setWidthPercentage(60);
			
			PdfPCell cell;
			
			cell = new PdfPCell(new Phrase(Constant.NR_REGISTERED_USERS));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase(String.valueOf(userRepository.count())));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase(Constant.NR_STORIES));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase(String.valueOf(storyRepository.count())));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			
			PdfWriter.getInstance(document, out);
			document.open();
			document.add(table);
			
			document.close();
		} catch (Exception e) {
			LOGGER.error("Something went wrong trying to generate the report " + e);
		}
		
		return new ByteArrayInputStream(out.toByteArray());
	}

}
