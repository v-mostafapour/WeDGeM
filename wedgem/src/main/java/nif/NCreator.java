package nif;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.nlp2rdf.bean.NIFBean;
import org.nlp2rdf.bean.NIFType;
import org.nlp2rdf.parser.EntityMention;

import model.Pair;
import regex.RegexPattern;

public class NCreator {
	final static Logger LOG = Logger.getLogger(NCreator.class);

	public NIFBean createElement(List<Pair<Integer, Integer>> allmatches3, String context, String typeUri) {
		NIFBean.NIFBeanBuilder entityBuilderFILM = new NIFBean.NIFBeanBuilder();
		List<String> typesFILM = new ArrayList<String>();
		typesFILM.add(typeUri);
		entityBuilderFILM.context(context, allmatches3.get(0).getFirst(), allmatches3.get(0).getSecond())
				.mention(new RegexPattern().FILM).beginIndex(allmatches3.get(0).getFirst())
				.endIndex(allmatches3.get(0).getSecond()).taIdentRef(typeUri).annotator("http://siege.ege.edu.tr/trex")
				.types(typesFILM);
		NIFBean entityBeanFILM = new NIFBean(entityBuilderFILM);
		return entityBeanFILM;
	}

	public NIFBean createContext(String text, String context) {
		NIFBean.NIFBeanBuilder contextBuilder = new NIFBean.NIFBeanBuilder();
		contextBuilder.context(context, 0, text.length()).mention(text).nifType(NIFType.CONTEXT);
		NIFBean beanContext = new NIFBean(contextBuilder);
		return beanContext;
	}

	public NIFBean createEntity(EntityMention mention, String context, String typeUri, String taIdentUri) {
		NIFBean.NIFBeanBuilder entityBuilderWP = new NIFBean.NIFBeanBuilder();
		List<String> types = new ArrayList<String>();
		types.add(typeUri);
		entityBuilderWP.context(context, mention.getBeginIndex(), mention.getEndIndex()).mention(mention.getMention())
				.beginIndex(mention.getBeginIndex()).endIndex(mention.getEndIndex()).taIdentRef(taIdentUri)
				.annotator("http://siege.ege.edu.tr/trex").types(types).referenceContext(mention.getReferenceContext());
		NIFBean entityBeanWP = new NIFBean(entityBuilderWP);
		return entityBeanWP;
	}

}
