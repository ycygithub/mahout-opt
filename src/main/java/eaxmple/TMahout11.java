package eaxmple;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.recommender.svd.ALSWRFactorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.precompute.example.GroupLensDataModel;

import java.io.File;
import java.io.IOException;

/**
 * Created by chongyu on 9/1/14.
 */
public class TMahout11 {

    public static void main(String[] args) throws IOException, TasteException {

        String path = TMahout11.class.getResource("/").getPath() + "db/ratings.dat";
        System.out.println("path:"+path);

        //-基于奇异值分解的推荐算法-//
        DataModel model = new GroupLensDataModel(new File(path));
        RecommenderEvaluator evaluator =
                new AverageAbsoluteDifferenceRecommenderEvaluator();
        RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
            @Override
            public Recommender buildRecommender(DataModel model) throws TasteException {
                return new SVDRecommender(model, new ALSWRFactorizer(model,10,0.05,10));
            }
        };
        double score = evaluator.evaluate(recommenderBuilder, null, model, 0.95, 0.05);
        System.out.println(score);

    }

}
