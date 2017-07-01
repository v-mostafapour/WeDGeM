package main;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.nlp2rdf.NIF;
import org.nlp2rdf.bean.NIFBean;
import org.nlp2rdf.nif21.impl.NIF21;
import org.nlp2rdf.parser.EntityMention;

import model.Pair;
import nif.NCreator;
import nif.NParser;
import regex.RegexFinder;
import regex.RegexPattern;
import utils.Utils;

public class TripleExtractor {
	final static Logger LOG = Logger.getLogger(TripleExtractor.class);

	public static void main(String[] args) {

		// get text
		String text = new NParser().getText(new Utils().WICKER_PARK_NIF_TTL);

		// get Entities
		String rdfDocument = new NParser().getRDFDocument(new Utils().WICKER_PARK_NIF_TTL);
		List<EntityMention> entityList = new NParser().getEntityList(rdfDocument);

		// Wicker park
		EntityMention mention = entityList.get(0);
		System.out.println("wikP=" + mention.getMention());
		// pattern extractor ("is a" and "film") from text

		// get spans of each textPattern in the Text in the form
		// span(startIndex, endIndex)
		List<Pair<Integer, Integer>> allmatches2 = new RegexFinder().getAllMatchesSpans(text, new RegexPattern().IS_A);
		List<Pair<Integer, Integer>> allmatches3 = new RegexFinder().getAllMatchesSpans(text, new RegexPattern().FILM);

		if (!allmatches2.isEmpty() && !allmatches3.isEmpty()) {

			String context = mention.getReferenceContext().split("#")[0];
			context.replaceAll("http://example.org/", "http://siege.ege.edu.tr/");

			// for the text of mention
			NIFBean beanContext = new NCreator().createContext(text, context);

			// WP
			NIFBean entityBeanWP = new NCreator().createEntity(mention, context, "http://dbpedia.org/ontology/Film",
					"http://dbpedia.org/resource/Wicker_Park_(film)");

			// ISA
			NIFBean entityBeanISA = new NCreator().createElement(allmatches2, context,
					"http://www.w3.org/1999/02/22-rdf-syntax-ns#type");

			// FILM
			NIFBean entityBeanFILM = new NCreator().createElement(allmatches3, context,
					"http://dbpedia.org/ontology/Film");

			List<NIFBean> beans = new ArrayList<NIFBean>();

			beans.add(entityBeanWP);
			beans.add(entityBeanISA);
			beans.add(entityBeanFILM);
			beans.add(beanContext);

			NIF nif = new NIF21(beans);
			LOG.info("Turtle format: ");
			LOG.info(nif.getTurtle());

		}

	}
}
