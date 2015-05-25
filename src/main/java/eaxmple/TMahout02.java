package eaxmple;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.RandomUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by chongyu on 9/1/14.
 */
public class TMahout02 {

    public static void main(String[] args) throws IOException, TasteException {

        String path = TMahout02.class.getResource("/").getPath() + "db/ua.base";
        System.out.println("path:"+path);

        //-配置并评估一个推荐程序-//
        RandomUtils.useTestSeed();
        DataModel model = new FileDataModel(new File(path));
        RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
        RecommenderBuilder builder = new RecommenderBuilder() {
            @Override
            public Recommender buildRecommender(DataModel dataModel) throws TasteException {
                UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
                //UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, dataModel);

                UserNeighborhood neighborhood =
                        new NearestNUserNeighborhood(2, similarity, dataModel);

                return new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
            }
        };
        double result = evaluator.evaluate(builder, null, model, 0.9, 1.0);
        System.out.println(result);

    }

}
