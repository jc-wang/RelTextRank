package it.unitn.nlpir.features.presets;

import it.unitn.nlpir.features.FeatureSets;
import it.unitn.nlpir.features.builder.FeaturesBuilder;
import it.unitn.nlpir.features.providers.fvs.DependencyTripletsProvider;
import it.unitn.nlpir.features.providers.similarity.CosineSimilarity;
import it.unitn.nlpir.features.providers.similarity.PTKSimilarity;
import it.unitn.nlpir.features.providers.trees.old.RelConstituencyTreeProvider;
import it.unitn.nlpir.features.providers.trees.old.RelDependencyTreeProvider;
import it.unitn.nlpir.features.providers.trees.old.RelPhraseDependencyTreeProvider;
import it.unitn.nlpir.uima.TokenTextGetterFactory;

public class BaselineFeatures implements IVectorFeatureExtractor{

	@Override
	public FeaturesBuilder getFeaturesBuilder() {
		return new FeaturesBuilder()
		
		.extend(FeatureSets.buildBowFeatures())
		.extend(FeatureSets.buildOldKernelFeatures())
		
		.extend(FeatureSets.buildSimpleBowFeatures())
		.add(new CosineSimilarity(new DependencyTripletsProvider(
				TokenTextGetterFactory.LEMMA))) 
		.add(new PTKSimilarity(new RelConstituencyTreeProvider())) 
		.add(new PTKSimilarity(new RelDependencyTreeProvider()))
		.add(new PTKSimilarity(new RelPhraseDependencyTreeProvider()));
	}
}
