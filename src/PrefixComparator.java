import java.util.Comparator;

/**
 * Factor pattern for obtaining PrefixComparator objects
 * without calling new. Users simply use
 *
 *     Comparator<Term> comp = PrefixComparator.getComparator(size)
 *
 * @author owen astrachan
 * @date October 8, 2020
 */
public class    PrefixComparator implements Comparator<Term> {

    private int myPrefixSize; // size of prefix

    /**
     * private constructor, called by getComparator
     * @param prefix is prefix used in compare method
     */
    private PrefixComparator(int prefix) {
        myPrefixSize = prefix;
    }


    /**
     * Factory method to return a PrefixComparator object
     * @param prefix is the size of the prefix to compare with
     * @return PrefixComparator that uses prefix
     */
    public static PrefixComparator getComparator(int prefix) {
        return new PrefixComparator(prefix);
    }


    @Override
    /**
     * Use at most myPrefixSize characters from each of v and w
     * to return a value comparing v and w by words. Comparisons
     * should be made based on the first myPrefixSize chars in v and w.
     * @return < 0 if v < w, == 0 if v == w, and > 0 if v > w
     */
    public int compare(Term v, Term w) {
        // change this to use myPrefixSize as specified,
        // replacing line below with code

        //return -1 if v<w
        //return 0 if v == w
        //return 1 if v > w

        String vWord = v.getWord();
        String wWord = w.getWord();

        if(vWord.length() < myPrefixSize && wWord.length() >= myPrefixSize){
            String tempW = wWord.substring(0,vWord.length());

            if(vWord.compareTo(tempW) > 0){
                return 1;
            }
            if(vWord.compareTo(tempW) <0){
                return -1;
            }

            return -1;
        }

        String subV;
        String subW;

        if(vWord.length() < myPrefixSize){
            subV = vWord;
        }else{
            subV = vWord.substring(0,myPrefixSize);
        }

        if(wWord.length() < myPrefixSize){
            subW = wWord;
        }else{
            subW = wWord.substring(0,myPrefixSize);
        }

        if(subV.compareTo(subW) > 0){
            return 1;
        }
        if(subV.compareTo(subW) <0){
            return -1;
        }
        return 0;


    }

    @Override
    public Comparator<Term> reversed() {
        return Comparator.super.reversed();
    }
}
