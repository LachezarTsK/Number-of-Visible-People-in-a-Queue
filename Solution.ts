
function canSeePersonsCount(heights: number[]): number[] {
    const MAX_HEIGHT = Math.pow(10, 5);
    const countSeenPersons: number[] = new Array(heights.length);
    const monotonicStack: Person[] = new Array();
    const sentinel = new Person(1 + MAX_HEIGHT, heights.length - 1, 0);
    monotonicStack.push(sentinel);

    for (let i = heights.length - 1; i >= 0; --i) {
        let peopleOutOfSightFromCurrentPosition = 0;
        let additionalPeopleOutOfSightSeenFromPositionsAtSmallerIndexes = 0;

        while (monotonicStack.length > 0 && monotonicStack[monotonicStack.length - 1].height < heights[i]) {
            peopleOutOfSightFromCurrentPosition += monotonicStack[monotonicStack.length - 1].peopleDirectlyHiddenByThisPerson;
            ++additionalPeopleOutOfSightSeenFromPositionsAtSmallerIndexes;
            monotonicStack.pop();
        }

        countSeenPersons[i] = monotonicStack[monotonicStack.length - 1].index - i - peopleOutOfSightFromCurrentPosition;
        const peopleDirectlyHiddenByThisPerson = additionalPeopleOutOfSightSeenFromPositionsAtSmallerIndexes
                                                 + peopleOutOfSightFromCurrentPosition;
        monotonicStack.push(new Person(heights[i], i, peopleDirectlyHiddenByThisPerson));
    }

    return countSeenPersons;
};

class Person {

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
    constructor(public height: number, public index: number, public peopleDirectlyHiddenByThisPerson: number) { }
}
