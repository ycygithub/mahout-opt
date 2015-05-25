package eaxmple;


/**
 * Created by chongyu on 9/1/14.
 */
public class TMahout06 {

    public static void main(String[] args){

        //-基于用户的推荐程序-//

        //-The algorithm-//
        /*
        for every item i that u has no preference for yet
          for every other user v that has a preference for i
            compute a similarity s between u and v
            incorporate v's preference for i, weighted by s, into a running average
        return the top items, ranked by weighted average
        **/

        /*
        for every other user w
          compute a similarity s between u and w
          retain the top users, ranked by similarity, as a “neighborhood” n
        for every item i that some user in n has a preference for,
              but that u has no preference for yet
          for every other user v in n that has a preference for i
            compute a similarity s between u and v
            incorporate v's preference for i, weighted by s, into a running average
        **/

    }

}
