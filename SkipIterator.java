class SkipIterator implements Iterator<Integer> {
    private final HashMap<Integer, Integer> map;
    private final Iterator<Integer> it;
    //next element of skip iterator
    private final int nextEl;
    public SkipIterator(Iterator<Integer> it){
        map = new HashMap<>();
        this.it = it;
        advance();
    }
    public boolean hasNext(){
        return nextEl != null;
    }
    public Integer next(){
        if(!hasNext()) throw new RuntimeException("empty");
        int temp = nextEl;
        advance();    //for putting the next pointer at valid next location
        return temp; 
    }
    
    public void skip(Integer val){
        if(!hasNext()) throw new RuntimeException("empty");
        if(val == nextEl){
            advance();  //skip if pointing at the value itself
        }else{
            map.put(val, map.getOrDefault(val, 0) + 1);
        }
    }
    //used for putting nextEl pointer of skip iterator at the next valid el in my native iterator
    private void advance(){
        //find new nextEl of skip iterator
        //first the previous next element can be declared null
        nextEl = null;
        while(nextEl == null && it.hasNext()){
            int el = it.next(); //it moves the next pointer of my native iterator to next location
            if(map.containsKey(el)){
                //reduce skip count
                map.put(el, map.get(el) -  1);
                map.remove(el, 0);
            }else{
                nextEl = el;
            }
        }
    }
}

// "static void main" must be defined in a public class.
public class Main {
    
    public static void main(String[] args) {
        SkipIterator itr = new SkipIterator(Arrays.asList(2,3,5,6,7,5,-1,5,10).iterator());
    
        System.out.println(itr.hasNext());  //true
        System.out.println(itr.next());     //return 2
        itr.skip(5);
        System.out.println(itr.next());  //return 3
        System.out.println(itr.next());  //return 6 because 5 should be skipped
        System.out.println(itr.next());  //return 5
        itr.skip(5);
        itr.skip(5);
        System.out.println(itr.next());  //return 7
        System.out.println(itr.next());  //return -1
        System.out.println(itr.next());  //return 10
        System.out.println(itr.hasNext());  //false;
    }
}
