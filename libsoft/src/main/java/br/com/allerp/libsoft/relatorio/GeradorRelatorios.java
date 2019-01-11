package br.com.allerp.libsoft.relatorio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.file.File;

import br.com.allerp.libsoft.entity.user.Bibliotecario;
import br.com.allerp.libsoft.service.user.BibliotecarioService;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class GeradorRelatorios implements Serializable {

	private static final long serialVersionUID = 2484344328902455202L;

	private InputStream relInStream;

	private byte[] geraPDF(HashMap<String, Object> parametros, String nomeRel, List<Bibliotecario> bibliotecarios) {

		String path = nomeRel + ".jasper";

		try {
			File arquivoRel = new File(GeradorRelatorios.class.getResource(path).toURI());

			relInStream = arquivoRel.inputStream();
			// preenche o relatorio
			return JasperRunManager.runReportToPdf(relInStream, parametros,
					new JRBeanCollectionDataSource(bibliotecarios));

		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public byte[] relatorioUser(HashMap<String, Object> parametros, String nomeRel,
			List<Bibliotecario> bibliotecarios) {
		return geraPDF(parametros, nomeRel, bibliotecarios);
	}

	public byte[] geraExcel(String planName, List<Bibliotecario> bibliotecarios) {

		String[] columns = { "Nome", "Acesso", "E-mail", "CPF" };

		// Create a Workbook
		Workbook workbook = new XSSFWorkbook();

		/*
		 * CreationHelper helps us create instances of various things like DataFormat,
		 * Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way
		 */
		// CreationHelper createHelper = workbook.getCreationHelper();

//		// Create Cell Style for formatting Date
//        CellStyle dateCellStyle = workbook.createCellStyle();
//        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		// Create a Sheet
		Sheet sheet = workbook.createSheet("Usuários Cadastrados");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.ORANGE.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		// Create a Row
		Row headerRow = sheet.createRow(0);

		// Create cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 1;
		for (Bibliotecario bibliotecario : bibliotecarios) {
			Row row = sheet.createRow(rowNum++);

			row.createCell(0).setCellValue(bibliotecario.getNome());
			row.createCell(1).setCellValue(bibliotecario.getUserAccess());
			row.createCell(2).setCellValue(bibliotecario.getEmail());
			row.createCell(3).setCellValue(bibliotecario.getCpf());
		}

		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		ByteArrayOutputStream excel = new ByteArrayOutputStream();
		try {
			workbook.write(excel);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				workbook.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return excel.toByteArray();
	}
}
