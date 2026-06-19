
import kotlin.math.pow

class Solution {

    /*
    Examples for peopleDirectlyHiddenByThisPerson.

    heights = {some other heights ..., 5, 1, 2, 3, 4, 8, ... some other people}

    person height = 5, peopleDirectlyHiddenByThisPerson = 4 i.e. {1, 2, 3, 4}
    are directly hidden by the height of 5. The people with the heights {1, 2, 3, 4}
    can not be seen from any person preceding the person with height 5, regardless of their height.

    This is as per the problem statement!! This is not a given physical reality because
    if the people preceding the person with height 5 have sufficiently large heights,
    then it could be possible to see the people with heights {1, 2, 3, 4} from positions
    preceding the position of the person with height of 5.

     person height = 1, peopleDirectlyHiddenByThisPerson = 0 i.e. 2 is higher than 1
     person height = 2, peopleDirectlyHiddenByThisPerson = 0 i.e. 3 is higher than 2
     person height = 3, peopleDirectlyHiddenByThisPerson = 0 i.e. 4 is higher than 3
     person height = 4, peopleDirectlyHiddenByThisPerson = 0 i.e. 8 is higher than 4
     */

    private data class Person(val height: Int, val index: Int, val peopleDirectlyHiddenByThisPerson: Int)

    private companion object {
        val MAX_HEIGHT = (10.00).pow(5.0).toInt()
    }

    fun canSeePersonsCount(heights: IntArray): IntArray {
        val countSeenPersons = IntArray(heights.size)
        val monotonicStack = mutableListOf<Person>()
        val sentinel = Person(1 + MAX_HEIGHT, heights.size - 1, 0)
        monotonicStack.add(sentinel)

        for (i in heights.size - 1 downTo (0)) {
            var peopleOutOfSightFromCurrentPosition = 0
            var additionalPeopleOutOfSightSeenFromPositionsAtSmallerIndexes = 0

            while (monotonicStack.isNotEmpty() && monotonicStack.last().height < heights[i]) {
                peopleOutOfSightFromCurrentPosition += monotonicStack.last().peopleDirectlyHiddenByThisPerson
                ++additionalPeopleOutOfSightSeenFromPositionsAtSmallerIndexes
                monotonicStack.removeLast()
            }

            countSeenPersons[i] = monotonicStack.last().index - i - peopleOutOfSightFromCurrentPosition
            val peopleDirectlyHiddenByThisPerson = additionalPeopleOutOfSightSeenFromPositionsAtSmallerIndexes +
                                                   peopleOutOfSightFromCurrentPosition
            monotonicStack.add(Person(heights[i], i, peopleDirectlyHiddenByThisPerson))
        }

        return countSeenPersons
    }
}
