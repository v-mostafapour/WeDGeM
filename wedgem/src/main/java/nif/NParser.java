package nif;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.nlp2rdf.parser.Document;
import org.nlp2rdf.parser.EntityMention;
import org.nlp2rdf.parser.NIFParser;

import utils.Utils;

public class NParser {
	final static Logger LOG = Logger.getLogger(NParser.class);

	public static void main(String[] args) {
		new NParser().getRDFDocument(new Utils().WICKER_PARK_NIF_TTL);
	}

	public List<EntityMention> getEntityList(String text) {
		List<EntityMention> entityList = new ArrayList<EntityMention>();
		try {
			Document documentFromNIFString = NIFParser.getDocumentFromNIFString(text);

			entityList = documentFromNIFString.getEntities();
			for (EntityMention entityMention : entityList) {
				LOG.info(entityMention.getMention() + "-" + entityMention.getBeginIndex());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return entityList;
	}

	public String getRDFDocument(String filePath) {
		String text = "EMPTY";
		try {
			text = new ResourceLoader().getContent(filePath);

			LOG.info("RDF document is retrieved." + text);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}

	public String getText(String filePath) {
		String text = "EMPTY";
		try {
			text = new ResourceLoader().getContent(filePath);

			Document documentFromNIFString = NIFParser.getDocumentFromNIFString(text);
			text = documentFromNIFString.getText();
			LOG.info("Text:" + text);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}

}