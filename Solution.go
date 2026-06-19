
package main

import "math"

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
type Person struct {
    height                           int
    index                            int
    peopleDirectlyHiddenByThisPerson int
}

var MAX_HEIGHT = int(math.Pow(10.0, 5.0))

func canSeePersonsCount(heights []int) []int {
    countSeenPersons := make([]int, len(heights))
    monotonicStack := []Person{}
    sentinel := Person{1 + MAX_HEIGHT, len(heights) - 1, 0}
    monotonicStack = append(monotonicStack, sentinel)

    for i := len(heights) - 1; i >= 0; i-- {
        peopleOutOfSightFromCurrentPosition := 0
        additionalPeopleOutOfSightSeenFromPositionsAtSmallerIndexes := 0

        for len(monotonicStack) > 0 && monotonicStack[len(monotonicStack) - 1].height < heights[i] {
            peopleOutOfSightFromCurrentPosition += monotonicStack[len(monotonicStack) - 1].peopleDirectlyHiddenByThisPerson
            additionalPeopleOutOfSightSeenFromPositionsAtSmallerIndexes++
            monotonicStack = monotonicStack[:len(monotonicStack) - 1]
        }

        countSeenPersons[i] = monotonicStack[len(monotonicStack) - 1].index - i - peopleOutOfSightFromCurrentPosition
        peopleDirectlyHiddenByThisPerson := additionalPeopleOutOfSightSeenFromPositionsAtSmallerIndexes + 
                                            peopleOutOfSightFromCurrentPosition
        monotonicStack = append(monotonicStack, Person{heights[i], i, peopleDirectlyHiddenByThisPerson})
    }

    return countSeenPersons
}
