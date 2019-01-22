package br.com.allerp.allbanks.relatorio;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

import org.apache.wicket.util.file.File;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class GeradorRelatorio<T> implements Serializable {

	private static final long serialVersionUID = 4212487557666075231L;

	private InputStream relInStream;

	public byte[] geraPdf(HashMap<String, Object> parametros, String nomeRel, List<T> objects) {
		String path = nomeRel + ".jasper";

		try {
			File arquivoRel = new File(GeradorRelatorio.class.getResource(path).toURI());

			relInStream = arquivoRel.inputStream();

			return JasperRunManager.runReportToPdf(relInStream, parametros, new JRBeanCollectionDataSource(objects));

		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}

	public byte[] geraExcel() {
		return null;
	}

}
