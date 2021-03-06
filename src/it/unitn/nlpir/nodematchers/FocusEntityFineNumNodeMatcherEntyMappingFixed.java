package it.unitn.nlpir.nodematchers;

import it.unitn.nlpir.projectors.MatchedNode;
import it.unitn.nlpir.types.NER;
import it.unitn.nlpir.types.QuestionClass;
import it.unitn.nlpir.types.QuestionFocus;
import it.unitn.nlpir.types.Token;
import it.unitn.nlpir.util.NERUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.uima.jcas.JCas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uimafit.util.JCasUtil;

import edu.stanford.nlp.trees.Tree;

/**
 * This class performs a hard match on the tokens of the type specified by the
 * {@matchingTokenTextType} passed in the constructor.
 * By default matching is done on the lemmas.
 * 
 */
public class FocusEntityFineNumNodeMatcherEntyMappingFixed implements NodeMatcher {
	private final static Logger logger = LoggerFactory.getLogger(FocusEntityFineNumNodeMatcherEntyMappingFixed.class);
	
	private final static String defaultRelTag = "REL";
	
	private MatchingStrategy strategy;
	private String relTag;
	private boolean typedRelTag;

	public FocusEntityFineNumNodeMatcherEntyMappingFixed(MatchingStrategy strategy) {
		this(strategy, defaultRelTag, false);
	}
	
	public FocusEntityFineNumNodeMatcherEntyMappingFixed(MatchingStrategy strategy, boolean typedRelTag) {
		this(strategy, defaultRelTag, typedRelTag);
	}
	
	public FocusEntityFineNumNodeMatcherEntyMappingFixed(MatchingStrategy strategy, String relTag, boolean typedRelTag) {
		this.strategy = strategy;
		this.relTag = relTag;
		this.typedRelTag = typedRelTag;
	}

	@Override
	public List<MatchedNode> getMatches(JCas questionCas, JCas documentCas, Tree questionTree,
			Tree documentTree) {
		List<Token> qTokens = new ArrayList<>();
		for (Token qToken : JCasUtil.select(questionCas, Token.class)) {
			qTokens.add(qToken);
		}
		List<Token> docTokens = new ArrayList<>();
		for (Token docToken : JCasUtil.select(documentCas, Token.class)) {
			docTokens.add(docToken);
		}

		// Get an index of question leaf nodes
		Tree[] qLeafNodes = buildTokenId2TreeLeafIndex(qTokens, questionTree);
		Tree[] docLeafNodes = buildTokenId2TreeLeafIndex(docTokens, documentTree);

		String questionClass = JCasUtil.selectSingle(questionCas, QuestionClass.class)
				.getQuestionClass();

		List<String> qClassRelatedNERTypes = NERUtil
				.resolveFineNumQuestionCategory2EntityClassTypesEntyFixed(questionClass);
		HashSet<String> nerTypes = new HashSet<>(qClassRelatedNERTypes);

		String focusTag;
		if (this.typedRelTag) {
			focusTag = String.format("%s-FOCUS-%s", this.relTag, questionClass);
		} else {
			focusTag = String.format("%s-FOCUS", this.relTag);
		}

		List<MatchedNode> matches = new ArrayList<>();

		QuestionFocus questionFocus = JCasUtil.selectSingle(questionCas, QuestionFocus.class);
		List<Token> qTokenFocus = JCasUtil.selectCovered(Token.class, questionFocus);

		if (qTokenFocus.isEmpty()) {
			logger.info("Failed to recover focus token for the sentence: {}", questionCas.getDocumentText());
			return matches;
		}
		Token qFocus = qTokenFocus.get(0);
		Tree qFocusNode = qLeafNodes[qFocus.getId()];
		if (qFocusNode != null) {
			//strategy.doMatching(questionTree, qFocusNode, matches, focusTag);

			// Add REL nodes from the candidate answer.
			for (NER ner : JCasUtil.select(documentCas, NER.class)) {
				if (nerTypes.contains(ner.getNERtype())) {
					List<Token> tokens = JCasUtil.selectCovered(documentCas, Token.class, ner);
					for (Token token : tokens) {
						int id = token.getId();
						Tree node = docLeafNodes[id];
						if (node != null) {
							strategy.doMatching(documentTree, node, matches, focusTag);
						}
					}
				}
			}
		}

		return matches;
	}

	private Tree[] buildTokenId2TreeLeafIndex(List<Token> tokens, Tree tree) {
		Tree[] leafNodes = new Tree[tokens.size()];
		for (Tree qLeaf : tree.getLeaves()) {
			Integer qTokenId;
			try {
				qTokenId = Integer.parseInt(qLeaf.nodeString());
			} catch (NumberFormatException e) {
				continue;
			}
			leafNodes[qTokenId] = qLeaf;
		}
		return leafNodes;
	}
}
