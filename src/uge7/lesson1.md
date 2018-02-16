# Exercise 1
2.1-1

Original array:  
A = <31, 41, 59, 26, 41, 58>  
First number is already in the correct position, so no need to move that.  
Look at index 2, 41 is more than 31, therefore this is correctly positioned.  
Look at index 3, 59 is more than 41, therefore this is already correctly positioned.  
Look at index 4. 26 is less than 59, therefore we need to move it back one spot:  
A = <31, 41, 26, 59, 41, 58>   
Also smaller than 41  
A = <31, 26, 41, 59, 41, 58>   
Also smaller than 31  
A = <26, 31, 41, 59, 41, 58>  
Look at index 5. 41 is less than 59, therefore we need to move it back one spot:  
A = <26, 31, 41, 41, 59, 58>
41 is not less than 41, therefore we do not need additional moves.  
Look at index 6, 58 is less than 59, therefore we need to move it back one spot.  
A = <26, 31, 41, 41, 58, 59>  
58 is not less than 41, therefore the array is now sorted. 

# Exercise 2
2.1-3

```
getIndex(A, v):
    for i = 1 to A.length
        if A[i] = v:
            return i
    return nil
```

# Exercise 3
2.2-3

On average half the array needs to be checked, given a run time of `O(n/2)`. 
Worst case is having to search through all elements, giving `O(n)`. 
Best case is the target element being element 1, giving `O(1)`. 

# Exercise 4
2.3-5
    
```
getIndex(A,v):
    index = A.length / 2
    searchSize = index
    while searchSize >= 1:
        if A[index] = v:
            return index
        searchSize /= 2
        if A[index] < v:
            index += searchSize
        if A[index] > v:
            index -= searchSize
    return nil
```

# Exercise 5
2.3-6

We cannot, as still have to move all the elements afterwards to new positions, 
which we have to do in linear time. If the list is a linkedList, then we can't 
do a binary search, as we can't jump to any random index in `O(1)`, but rather 
only `O(n)`. 

# Exercise 6
2.3-1

Initial array:  
A = <3, 41, 52, 26, 38, 57, 9, 49>  
Split the array in smaller arrays:  
<3><41><52><26><38><57><9><49>  
Merge each array with the next:  
<3, 41>, <26, 52>, <38, 57>, <9, 49>  
Merge again the next levels:  
<3, 26, 41, 52>, <9, 38, 49, 57>  
And again, the last level:  
<3, 9, 26, 38, 41, 49, 52, 57>  

# Exercise 7
2.3-2 

```
// We are assuming all arrays start at index 0

merge(A,p,q,r):
    n1 = q - p + 1
    n2 = r - q
    let L and R be new arrays of size n1 and n2 respectively
    for i = 1 to n1:
        L[i] = A[p + i - 1]
    for j = 1 to n2:
        R[j] = A[q + j]
        
    i = j = 1
    for k = p to r:
        if L.length = i:
            A[k] = R[j]
            j++
            continue
        if R.length = j:
            A[k] = L[i]
            i++
            continue
        if L[i] <= R[j]:
            A[k] = L[i]
            i++
        else:
            A[k] = R[j]
            j++
```

# Exercise 8
2-4 

## a 
Inversions:  
(3,4)
(1,5)
(2,5)
(3,5)
(4,5)

## b
The array in reverse order.  
It has ((n-1)n)/2 inversions. 

## c
It's the exact same, as insertion sort has to do one action for each inversion. 
If the same end index appears multiple times, then it means more movement has to 
be done to get the element to the target index. Therefore the number of inversions
equal the running time of insertion sort. 

# Exercise 9
2.3-7

Start by starting the array using e.g. mergesort.  
For each element in the array, subtract the value from x, call this v. 
Using binary search, attempt to find element v. If v exists, then there exists 
two elements whose sum is x. If v can't be found, then proceed to the next index,
and retry the process. 

