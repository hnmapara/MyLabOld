package com.example.java;

/**
 * Created by mapara on 12/4/14.
 */
/*
you can try using this code to start and run some of your own tests
make sure that you do NOT submit this code otherwise your tests will fail.
also beware that iterators do not necessarily have to come from a backing list.
take time with your corner cases.
anonymous inner classes might help although you don't have to use it.
NO need to support delete operation
*/
import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.Iterator;
        import java.util.List;


public class FlattenIterator {
    public static void main(String args[]) throws Exception {
        List<Iterator<String>> lists = new ArrayList<Iterator<String>>();
        lists.add(Arrays.<String>asList().iterator());
        lists.add(Arrays.asList("a", "b", "c").iterator());
        lists.add(null);
        lists.add(Arrays.<String>asList().iterator());
        lists.add(Arrays.asList("d", "e").iterator());
        lists.add(Arrays.<String>asList().iterator());

        Iterator<Iterator<String>> iters = lists.iterator();
        Iterator<String> flattened = flatten(iters);
        while (flattened.hasNext()) {
            System.out.println(flattened.next());
        }
    }

    /** @return Iterator which flattens the given arguments */
    public static Iterator<String> flatten(final Iterator<Iterator<String>> iters) {

        return new Iterator<String>() {
            Iterator<String> currentIterator;
            public boolean hasNext() {
                if (currentIterator == null) {
                    while (iters.hasNext()){
                        currentIterator = iters.next();
                        if(currentIterator != null && currentIterator.hasNext())  {
                            return currentIterator.hasNext();
                        }
                    }
                    return false;

                } else {
                    if(currentIterator.hasNext()) {
                        return true;
                    } else {
                        while (iters.hasNext()){
                            currentIterator = iters.next();
                            if(currentIterator != null && currentIterator.hasNext())  {
                                return currentIterator.hasNext();
                            }
                        }
                        return false;
                    }
                }
            }

            public String next() {
                return currentIterator.next();
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}