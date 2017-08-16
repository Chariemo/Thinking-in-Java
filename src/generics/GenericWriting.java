package generics;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Charley on 2017/8/16.
 */
public class GenericWriting {

    static List<Sub> subs = new ArrayList<>();
    static List<Sup> sups = new ArrayList<>();

    static <T> void writeExact(List<T> list, T item) {

        list.add(item);
    }

    static <T> void writeWithWildCard(List<? super T> list, T item) {

        list.add(item);
    }

    public static void main(String[] args) {

        Sub sub = new Sub();

        writeExact(subs, sub);
        writeExact(sups, sub);

        writeWithWildCard(subs, sub);
        writeWithWildCard(sups, sub);

    }
}

class Sup {

}

class Sub extends Sup {

}
