package eaxmple;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.similarity.precompute.example.GroupLensDataModel;

import java.io.File;
import java.io.IOException;

/**
 * Created by chongyu on 9/1/14.
 */
public class TMahout07 {

    public static void main(String[] args) throws IOException, TasteException {

        String path = TMahout07.class.getResource("/").getPath() + "db/ratings.dat";
        System.out.println("path:"+path);

        //-对简单推荐引擎进行评估-//
        DataModel model = new GroupLensDataModel(new File(path));
        RecommenderEvaluator evaluator =
                new AverageAbsoluteDifferenceRecommenderEvaluator();
        RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
            @Override
            public Recommender buildRecommender(DataModel model) throws TasteException {
                UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
                UserNeighborhood neighborhood =
                        new NearestNUserNeighborhood(100, similarity, model);
                return new GenericUserBasedRecommender(model, neighborhood, similarity);
            }
        };
        double score = evaluator.evaluate(recommenderBuilder, null, model, 0.95, 0.05);
        System.out.println(score);

    }

}
