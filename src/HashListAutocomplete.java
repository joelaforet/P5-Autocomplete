import java.util.*;

public class HashListAutocomplete implements Autocompletor {

    private static final int MAX_PREFIX = 10;
    private Map<String, List<Term>> myMap;
    private int mySize;

    public HashListAutocomplete(String[] terms, double[] weights) {
        if (terms == null || weights == null) {
            throw new NullPointerException("One or more arguments null");
        }
        initialize(terms,weights);
    }



    @Override
    public List<Term> topMatches(String prefix, int k) {
        if(prefix.length()>MAX_PREFIX) prefix = prefix.substring(0,MAX_PREFIX);
        if(!myMap.containsKey(prefix)) return new ArrayList<>();

        List<Term> all = myMap.get(prefix);
        List<Term> ret = all.subList(0, Math.min(k, all.size()));

        return ret;
    }

    @Override
    public void initialize(String[] terms, double[] weights) {
        myMap = new HashMap<>();
        for (int i=0;i< terms.length;i++) {
            String sub = terms[i];
            if(sub.length()>MAX_PREFIX) sub=terms[i].substring(0, MAX_PREFIX);

            for(int k=0;k<=sub.length();k++) {
                String a =sub.substring(0,k);
                myMap.putIfAbsent(a, new ArrayList<Term>());
                myMap.get(a).add(new Term(terms[i], weights[i]));
            }
        }

        for (String key:myMap.keySet()) {
            Collections.sort(myMap.get(key), Comparator.comparing(Term::getWeight).reversed());
        }

    }

    @Override
    public int sizeInBytes() {

        if (mySize == 0) {
            for(String s: myMap.keySet()) {
                mySize += BYTES_PER_CHAR*s.length();
            }

            for(Term t : myMap.get("")) {
                mySize += BYTES_PER_DOUBLE +
                        BYTES_PER_CHAR*t.getWord().length();
            }

        }
        return mySize;
    }

}
