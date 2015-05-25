package eaxmple;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
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
public class TMahout03 {

    public static void main(String[] args) throws IOException, TasteException {

        String path = TMahout03.class.getResource("/").getPath() + "db/ua.base";
        System.out.println("path:"+path);

        //-查准率和查全率评估的配置与运行-//
        //-每个用户的阈值=该用户的平均偏好值+标准方差-//
        RandomUtils.useTestSeed();
        DataModel model = new FileDataModel(new File(path));

        RecommenderIRStatsEvaluator irStatsEvaluator  = new GenericRecommenderIRStatsEvaluator();
        RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
            @Override
            public Recommender buildRecommender(DataModel model)
                    throws TasteException {
                UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
                UserNeighborhood neighborhood =
                        new NearestNUserNeighborhood(2, similarity, model);
                return
                        new GenericUserBasedRecommender(model, neighborhood, similarity);
            }
        };
        IRStatistics stats = irStatsEvaluator.evaluate(recommenderBuilder, null, model, null, 2,
                GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD,1.0);
        System.out.println(stats.getPrecision());
        System.out.println(stats.getRecall());

    }

}
