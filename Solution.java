
import java.util.ArrayDeque;
import java.util.Deque;

public class Solution {

    /*
    Examples for peopleDirectlyHiddenByThisPerson.
    
    heights = {some other heights ..., 5, 1, 2, 3, 4, 8, ... some other people}
    
    person height = 5, peopleDirectlyHiddenByThisPerson = 4 i.e. {1, 2, 3, 4} 
    are directly hidden by the height of 5. The people with the heights {1, 2, 3, 4} 
    can not be seen from any person preceding the person with height 5, regardless of their height.
    
    This is as per the problem statement!! This is not a given physical reality because 
    if the people preceding the person with height 5 have sufficiently large heights, 
    then it could be possible to see the people with heights {1, 2, 3, 4} from positions 
    preceding the position of the person with height of 5.    
    
    person height = 1, peopleDirectlyHiddenByThisPerson = 0 i.e. 2 is higher than 1 
    person height = 2, peopleDirectlyHiddenByThisPerson = 0 i.e. 3 is higher than 2  
    person height = 3, peopleDirectlyHiddenByThisPerson = 0 i.e. 4 is higher than 3
    person height = 4, peopleDirectlyHiddenByThisPerson = 0 i.e. 8 is higher than 4
     */
    private record Person(int height, int index, int peopleDirectlyHiddenByThisPerson) {}

    private static final int MAX_HEIGHT = (int) Math.pow(10, 5);

    public int[] canSeePersonsCount(int[] heights) {
        int[] countSeenPersons = new int[heights.length];
        Deque<Person> monotonicStack = new ArrayDeque<>();
        Person sentinel = new Person(1 + MAX_HEIGHT, heights.length - 1, 0);
        monotonicStack.addFirst(sentinel);

        for (int i = heights.length - 1; i >= 0; --i) {
            int peopleOutOfSightFromCurrentPosition = 0;
            int additionalPeopleOutOfSightSeenFromPositionsAtSmallerIndexes = 0;

            while (!monotonicStack.isEmpty() && monotonicStack.peekFirst().height < heights[i]) {
                peopleOutOfSightFromCurrentPosition += monotonicStack.peekFirst().peopleDirectlyHiddenByThisPerson;
                ++additionalPeopleOutOfSightSeenFromPositionsAtSmallerIndexes;
                monotonicStack.pollFirst();
            }

            countSeenPersons[i] = monotonicStack.peekFirst().index - i - peopleOutOfSightFromCurrentPosition;
            int peopleDirectlyHiddenByThisPerson = additionalPeopleOutOfSightSeenFromPositionsAtSmallerIndexes
                    + peopleOutOfSightFromCurrentPosition;
            monotonicStack.addFirst(new Person(heights[i], i, peopleDirectlyHiddenByThisPerson));
        }

        return countSeenPersons;
    }
}
