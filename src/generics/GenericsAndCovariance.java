package generics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Charley on 2017/8/17.
 */
public class GenericsAndCovariance {

    public static void main(String[] args) {

        List<? extends Sup> elist = new ArrayList<Sub>();
//        elist.add(new Sub());
        elist.add(null);

        List<? super Sub> slist = new ArrayList<Sup>();
        slist.add(new Sub());
//        slist.add(new Sup());
        slist.add(new Sub2());
        Sub sub = (Sub) slist.get(0);
    }
}

class Sub2 extends Sub {

}