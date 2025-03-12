# Overview
his README is about notes on Algorithms courses from [coursera - Divide and Conquer, Sorting and Searching, and Randomized Algorithms](https://www.coursera.org/learn/algorithms-divide-conquer?specialization=algorithms). This file uses LaTeX.

However, problems in this repository are from text books Algorithm Illuminated.

## Implementation Struggles
- problem 1.6 Karatsuba multiplication   
- theorem 5.5 why it is $` 2^k `$   
- problem 6.3 DSelect for the weighted median   

# Divide and Conquer, Sorting and Searching, and Randomized Algorithms
## week 1 - Merge sort and worst case analysis
Merge sort is a typical example of divide & conquer while it is a better version of selection, insertion, and bubble sort. Merge sort is a recursive algorithm spliting half and merge. 
#### merge sort overview
```text
        ○        <-- Level 0
      /   \
    ○       ○    <-- Level 1
   / \     / \
  ○   ○   ○   ○  <-- Level 2
 / \ / \ / \ / \
  ...
 (Further splitting continues)
  ...
●  ●  ●  ●  ●  ●  ●  ●  <-- Level m (Base case, individual elements)
```
where $` m = \log_2 n `$ which means the number of levels is $` m + 1 = \log_2 n + 1 `$

At the j level there are $` 2^j `$ subproblems of array size $` n/2^j `$.
#### pseudo code during merge
```text
i := 0
j := 0
for k = 0, ... , l
    if (C[i] <= D[j])
        A[k] = C[i]
        i++
    else
        A[k] = D[j]
        j++
```
Seeing from above, there are $` 4l + 2 \leq 6l `$ operations to merge two $` \frac{l}{2} `$ length arrays. So, the work done by a level j subproblem is $` 2^j \times \frac{6n}{2^j} = 6n `$. Summing all levels' work, $` (m + 1)6n = (\log_2 n + 1)6n = 6n\log_2 n + 6n `$

Worst case analysis mathematically tractable as opposed to analysis needing domain knowleges  (average case analysis, benchmarks). Also, constants depend on machine dependent aspects and ignoring them loses very little predictive power.

### Problem 1.3
Merge k sorted arrays (each of size n) sequentially: first two, then merge the result with the third, then the fourth, and so on. What is the total running time?

```math
2n + 3n + 4n + \dots + kn = n \sum_{i=2}^{k} i = n \left(\frac{k(k+1)}{2} - 1\right)
```

Ignoring lower-order terms and constants, the running time is approximately: $` k^2 n `$  

### Problem 1.4
Merge k sorted arrays (each of size n) by pairing them recursively until one sorted array of size kn remains. What is the total running time?

Each level of merging requires approximately $` kn `$ operations, and the number of levels is about $` \log_2 k `$. Thus, the total running time is roughly: $` kn \log_2 k `$  

### Problem 1.5
Given an unsorted array of n distinct numbers (n as a power of 2), design an algorithm to find the second-largest number using at most $` n + \log_2 n - 2 `$ comparisons. [Hint: Consider the comparisons left after finding the largest number.] 

- Is a recursive algorithm necessary?
- The largest number, by the nature of the comparison process, must have defeated the second-largest number.
#### comparison process of n numbers
```text
n/2 pairs: (○, ○), (○, ○), ... , (○, ○), (○, ○)
n/4 pairs: (○, ○), ... , (○, ○)
...
1 pair: (○, ○)
```
The number of comparisons above to get the largest number is    
```math
n - 1 
```
Additionally, throughout the proccess, a list of numbers defeated by the largest number is collected. Since the largest number competes in every level of the tournament, the size of this list is the number of levels, i.e., $` \log_2 n `$. The number of comparisons needed to find the second-largest number from this list is derived from the above as $` \log_2 n - 1 `$.

Thus, the total number of comparisons is:
```math 
n + \log_2 n - 2 
```

## week 1 - Asymptotic analysis
#### Asymptotic Notation in Seven Words
>  supress constant factors (system dependent) and lower-order terms (irrelevant for large inputs)

#### Big-O Notation
$` T(n) = O(f(n)) `$ if and only if there exist positive constants $` c `$ and $` n_0 `$ such that $` T(n) \leq c \cdot f(n) `$ for all $` n \geq n_0 `$.

Claim: If $` T(n) = a_k n^k + \cdot\cdot\cdot + a_1 n + a_0 `$ then $` T(n) = O(n^k) `$   
```math
\begin{align}
T(n) \leq |a_k|n^k + \cdot\cdot\cdot + |a_1| n^k + |a_0| n^k   \\
= (|a_k| + \cdot\cdot\cdot + |a_1| + |a_0|) n^k   \\
= c \cdot n^k
\end{align}
```

Claim: Let $` k \geq 1 `$  be a positive integer and define $` T(n) = n^k `$. Then $` T(n) `$ is not $` O(n^{k-1}) `$.   
Assume $` T(n) = O(n^{k-1}) `$. Then, there exist positive constants $` c `$ and $` n_0 `$ such that $` n^k \leq c \cdot n^{k-1} `$ for all $` n \geq n_0 `$.   

```math
\begin{align}
n^k \leq c \cdot n^{k-1} \\
\Rightarrow n \leq c 
\end{align}
```

Choosing $` n = c + 1 `$ contradicts this inequality.

#### Big-Omega Notation
$` T(n) = \Omega(f(n)) `$ if and only if there exist positive constants $` c `$ and $` n_0 `$ such that $` T(n) \geq c \cdot f(n) `$ for all $` n \geq n_0 `$.

#### Big-Theta Notation
$` T(n) = \Theta(f(n)) `$ if and only if there exist positive constants $` c_1 `$, $` c_2 `$, and $` n_0 `$ such that $` c_1 \cdot f(n) \leq T(n) \leq c_2 \cdot f(n) `$ for all $` n \geq n_0 `$.   

### Quiz 2.5
Let $` T(n) = \frac{1}{2} n^2 + 3n `$. Then, below are true.
```math
\begin{align}
T(n) = \Omega(n) \\
T(n) = \Theta(n^2) \\
T(n) = O(n^3)
\end{align}
```

Big-O notation is used mostly because alorithm designers take care more of upper bounds.   

Claim: $` 2^{n+10} = O(2^n) `$   
```math
\begin{align}
2^{n+10} = 2^{10} \cdot 2^n \\ 
\leq c \cdot 2^n
\end{align}
```
Taking $` c = 2^{10} `$ this inequality holds for all $` n \geq 1 `$, which means $` n_0 `$ could be 1.   
Claim: $` 2^{10n} `$ is not $` O(2^n) `$   
Assume $` 2^{10n} = O(2^n) `$. Then, there exist positive constants $` c `$ and $` n_0 `$ such that $` 2^{10n} \leq c \cdot 2^n `$ for all $` n \geq n_0 `$.
```math
\begin{align}
2^{10n} \leq c \cdot 2^n \\
\Rightarrow 2^{9n} \leq c
\end{align}
```
As $` n `$ goes to infinity being greater than a constant, this inequality does not hold.   
Claim: $` max(f(n), g(n)) = \Theta(f(n) + g(n)) `$ for every pair of functions from the positive integers to the nonnegative real numbers $` f(n) `$ and $` g(n) `$.   
```math
\begin{align}
max(f(n), g(n)) \leq f(n) + g(n) \\
2 \cdot max(f(n), g(n)) \geq f(n) + g(n) \\
\Rightarrow \frac{1}{2} (f(n) + g(n)) \leq max(f(n), g(n)) \leq f(n) + g(n)
\end{align}
```

### Problem 2.1
Let $` f `$ and $` g `$ be non-decreasing real-valued functions defined on the positive integers, with $` f(n) `$ and $` g(n) `$ at least 1 for all $` n \geq 1 `$. Assume that $` f(n) = O(g(n)) `$, and let $` c `$ be a positive constant. Is $` f(n) \cdot log_2 f(n)^c = O(g(n) \cdot log_2 g(n)) `$?   
Yes. By Assumption in the problem, there exist positive $` k `$ and $` n_0 `$ such that $` f(n) \leq k \cdot g(n) `$ for all $` n \geq n_0 `$. So,   
```math
\begin{align}
f(n)log_2 f(n) \leq k \cdot g(n)log_2 f(n) \\   
\leq klog_2 k \cdot g(n) + k \cdot g(n)log_2 g(n) \\   
\therefore f(n)log_2 f(n) = O(g(n)) + O(g(n)log_2 g(n))  \\
= O(g(n)log_2 g(n))
\end{align}
```

### Problem 2.2
Assume again two positive non-decreasing functions $` f `$ and $` g `$ such that $` f(n) = O(g(n)) `$. Is $` 2^{f(n)} = O(2^{g(n)}) `$?  
No.    
For this to be true, we would need to show that there exist positive constants $` c `$ and $` n_0 `$ such that: 
```math
2^{f(n)} \leq c \cdot 2^{g(n)} 
```
for all $` n \geq n_0 `$.   
By the assumption $` f(n) = O(g(n)) `$, there exist positive constants $` k `$ and $` n_0 `$ such that $` f(n) \leq k \cdot g(n) `$ for all $` n \geq n_0 `$.   
```math
2^{f(n)} \leq 2^{k \cdot g(n)}
```
Since when $` k > 1 `$, $` (2^{g(n)})^k `$ grows asymptotically *faster* than any constant multiple of $` 2^{g(n)} `$, it contradicts the Big-O requirement.

## week 2 - DIVIDE & CONQUER ALGORITHMS
#### Inversions
Let A be an array of distinct integers. The number of inversions in A is the number of pairs (i, j) of array indices such that $` i < j `$ and $` A[i] > A[j] `$.   
A left inversion is an inversion that occurs within the first half of the array, while a right inversion occurs within the second half. A split inversion is an inversion between two elements, one from the left half and one from the right half.

Since sorting is a for-free primitive of O(nlogn), mergesort is used.

#### Lemma 3.1
Let A be an array, and let C and D be the sorted versions of the first and second halves of A, respectively. An element x from the first half of A and an element y from the second half of A form a split inversion if and only if, in the Merge subroutine with inputs C and D, y is copied to the output array before x.    
Proof:   
Consider the merge step in Merge Sort, where two sorted arrays C and D are merged into a single sorted array.   
If x (from C) is copied to the output array before y (from D), then $` x \leq y `$, which means no inversion.   
If y (from D) is copied to the output array before x (from C), then $` y < x `$, which means x and y are a (split) inversion.   
```text
C (first half)  = [1, 3, 5]
D (second half) = [2, 4, 6]

C: [  ] [3] [5]  
D: [2] [4] [6]  
Output: [1] [ ] [ ] [ ] [ ] [ ]

C: [ ] [3] [5]  
D: [  ] [4] [6]                 -> 2 is copied to the output and C has [3, 5] whose length is the number of split inversions: 2.
Output: [1] [2] [ ] [ ] [ ] [ ]
 
...
(Further splitting continues)

C: [ ] [ ] [ ]  
D: [ ] [ ] [6]  
Output: [1] [2] [3] [4] [5] [ ]
```
The Merge subroutine runs in O(n), and since there are $` O(log n) `$ levels of recursion, the overall running time is $` O(n log n) `$.

#### Strassen's Subcubic Matrix Multiplication Algorithm
According to the Master Method from a later chapter, decomposed matrix multiplication's running time is O(n^3) which is unsatisfactory since it is the same as the trivial algorithm. Strassen's algorithm reduces the number of recursive calls from 8 to 7, making its running time even lower than cubic time.   
Let X and Y be two n×n matrices, partitioned into four $` \frac{n}{2} \times \frac{n}{2} `$ submatrices:
```text
    X = [ A  B ]      Y = [ E  F ]
        [ C  D ]          [ G  H ]
then,

    X × Y = [ P1 + P4 - P5 + P7    P3 + P5           ]
            [ P2 + P4              P1 + P3 - P2 + P6 ]

Where the seven recursive multiplications are:

    P1 = (A + D) × (E + H)
    P2 = (C + D) × E
    P3 = A × (F - H)
    P4 = D × (G - E)
    P5 = (A + B) × H
    P6 = (C - A) × (E + F)
    P7 = (B - D) × (G + H)
```

#### Closest Pair
Let $` P_x `$ be the array of points sorted by the x-axis and $` P_y `$ be the array sorted by the y-axis.   
A left or right pair is the closest pair of points both of which are located in the left half or right half solely, respectively. A split pair consists of two closest points, one from the left half and the other from the right half.   
ClosestPair algorithm consist of three parts: 
1. ClosestLeftPair: Finds the closest pair in the left half.
2. ClosestRightPair: Finds the closest pair in the right half.
3. ClosestSplitPair: Determines whether the closest pair is split across both halves.   

The minimum distance between ClosestLeftPair and ClosestRightPair, denoted as $` \delta `$, is passed to ClosestSplitPair to check whether a closer split pair exists, i.e., if the distance between the split pair is less than $` \delta `$.   
- Key Idea: Perform a brute-force search over a restricted number of point pairs.

ClosestSplitPair subroutine definition:   
1. Compute $` \bar{x} `$, the largest x-coordinate from the left half of $` P_x `$, which represents the median x-coordinate of the entire array.
2. Construct $` S_y `$ from $` P_y `$ by filtering points whose **x-coordinates** lie in the interval $` (x - \delta, x + \delta) `$ 
3. Perform a brute-force search on $` S_y `$, which contains at most six points, to determine the closest pair.
4. Return the closest pair among ClosestLeftPair, ClosestRightPair, and ClosestSplitPair      

#### Lemma 3.3
In the ClosestSplitPair subroutine, suppose (p, q) is a split pair with $` d(p, q) < \delta `$ where $` \delta `$ is the smallest distance between a left pair or a right pair of points. Then:
- (a) p and q will be included in the set $` S_y `$;
- (b) at most six points of $` S_y `$ have a y-coordinate in between those of p and q.   

Proof (a):  
Since (p, q) is a split pair, one of them, say p, is from the left half and the other, say q, from the right half. Assume $` p = (x_1, y_1) `$ and $` q = (x_2, y_2) `$ and let $` \bar{x} `$ be the rightmost x-coordinate from the left half. Then, since p is from the left half and q from the right, $` x_1 \leq \bar{x} < x_2 `$. Also, by assumpiton $` d(p, q) < \delta `$,
```math
\begin{align}
\delta > \sqrt{(x_1 - x_2)^2 + (y_1 - y_2)^2} \\ 
\geq max\{(x_1 - x_2)^2, (y_1 - y_2)^2\} \\   
= max\{|x_1 - x_2|, |y_1 - y_2|\} \\
\Rightarrow |x_1 - x_2|, |y_1 - y_2| < \delta
\end{align}
```
The below drawing follows from combining the fact $` x_1 \leq \bar{x} < x_2 `$ and $` |x_1 - x_2| < \delta `$   
```text
                         <----- ≤ δ ----->
--------------|---------|- -------|--------|---------|------------→ x
            x̅ - δ      x1       x̅       x2      x̅ + δ
               <---------------- 2δ --------------->
```
Recall $` S_y `$ consists of all points within $` \bar{x} - \delta \leq x ≤ \bar{x} + \delta `$. Since p and q satisfy $` x_1, x_2 \in (\bar{x} - \delta, \bar{x} + \delta) `$, they must be included in $` S_y `$.   
Proof (b):   
Assume a split pair $` p = (x_1, y_1) `$ and $` q = (x_2, y_2) `$ with $` d(p, q) < \delta `$ where $` \delta `$ is the smallest distance of a pair from the left or right half. In the process of *Proof (a)* it is shown $` |y_1 - y_2| < \delta `$. For convenience, assume q has the smallest y-coordinate in $` S_y `$. Below does drawing follow
```text
         p                  b
        +●---+----+----+----●
    δ/2 |    |    |    |a   |  
        +----+----+----●----+  
    δ/2 |    |    |  q |    |  
--------+----+----+--●-+----+--------→ x
      x̅ - δ       x̅       x̅ + δ
             ←δ/2→

```
To show a box has at most one point, we would show contradictory when proving two points being in a box. If a box has two points, the farthest distance between them is $` \sqrt{2} \cdot \delta/2 `$. This is smaller than $` \delta `$. By assumption, $` \delta `$ is the smallest possible distance between any two points when considering only pairs within the left half or within the right half. However, within a single box whose side is $` \delta/2 `$, two points can have a distance of at most $` \sqrt{2} \cdot \delta/2 `$, which is strictly less than $` \delta `$, leading to a contradiction. In conclusion, each box has at most one point, which means at most six points has a y-coordinate in between (p, q).   
#### Corollary 3.4
When the closest pair is a split pair, then the ClosestSplitPair subroutine returns it.   
Proof:   
Let (p,q) be the closest pair and a split pair. By definition, $` d(p,q) < \delta `$. By Lemma 3.3 (a), $` (p, q) \in S_y `$​, and since The ClosestSplitPair subroutine performs a brute-force search on $` S_y `$, it will consider (p, q). According to Lemma 3.3 (b), there are at most six points in $` S_y `$ with y-coordinates between those of p and q. Since (p,q) is the closest, the subroutine must return it.   

#### Theorem 3.5
For every set P of $` n \geq 2 `$ in the plane, the ClosestPair algorithm correctly computes the closest pair of P in the plane and runs in O(nlogn).   
Proof:   
It is known that preprocessing, i.e. - sorting based on x-coordinates and y-coordinates - runs in O(nlogn). Also, the recursive structure follows that of MergeSort, dividing the input in half at each step. In each recursive call, if the closest pair is a left pair, the ClosestLeftPair subroutine finds and returns it in O(n). Similarly, if the closest pair is a right pair, the ClosestRightPair subroutine does the same in O(n). Finally, if the closest pair is a split pair, Corollay 3.4 ensures that the ClosestSplitPair subroutine finds it in O(n). Since the algorithm returns the smallest distance among these three candidates, the correct closest pair is always selected. The recurrence follows the MergeSort pattern, leading to an overall time complexity of O(nlogn).    

### Problem 3.4
Given an $` n \times n `$ grid where each cell contains a distinct number, a local minimum is defined as a cell whose value is smaller than all of its adjacent neighbors (top, bottom, left, and right). Design an efficient algorithm using the divide-and-conquer approach to locate any local minimum. Your algorithm should achieve a time complexity of O(n) in terms of pairwise comparisons. 

## week 2 - Master Method
The Master Method is used to evaluate the upper bound of an algorithm. It is based on a recurrence relation. Let T(n) represent the total time for the algorithm on an input of size n. Then, the standard recurrence format is:
```math
\begin{align}
\text{Base case: T(n) is at most a constant for all sufficiently small n.} \\
\text{General case: for larger values of n,} \ T(n) \leq a \cdot T(n/b) + O(n^d) \\ 
a = \text{number of recursive calls} \\
b = \text{input size shrinkage factor} \\
d = \text{exponent in running time of the combine step} \\
\end{align}
```
The Master Method applies only if all subproblems are of equal size, and a, b, and d are constants (i.e., independent of n). In this course, Linear-time selection does not strictly follow the Master Method due to varying subproblem sizes.

#### Theorem 4.1
If T(n) is defined by a standard recurrence, with parameters $` a \geq 1, \ b > 1, \text{ and } d \geq 0 `$, then   
$` \begin{equation} T(n) = \begin{cases}O(n^d log n) ,& \text{if } a = b^d \\ O(n^d) ,& \text{if } a < b^d \\ O(n^{log_b a}) ,& \text{if } a > b^d\end{cases}\end{equation} `$   
Proof:   
By the standard recurrence form: $` T(n) \leq a \cdot T(n/b) + c \cdot n^d `$ where c is a constant, at level j there are $` a^j `$ subproblems of size $` n/b^j `$. So,
```math
\begin{align}
\text{work at level j } \leq a^j \times c \cdot [n/b^j]^d \\
= c \cdot n^d \times [a/b^d]^j \\ 
\Rightarrow \ \text{sum of work from all levels } \\ 
\leq \sum_{j=0}^{\log_b n}c \cdot n^d \times [a/b^d]^j
\end{align}   
```
This is a geometric series with ratio $` [a/b^d] `$. So, when $` a = b^d `$, $` T(n) = O(n^{d}\log_b n) `$. Also, by computing sum of a geometric series, if $` a < b^d `$ then $` T(n) = n^d `$ while if $` a > b^d `$ then $` T(n) = O(n^{\log_b a}) `$.   

Applying the Master Method to MergeSort, $` d = 1 `$ has been shown already. Likewise, $` d = 1 `$ for divide-and-conquer multiplications.

### Quiz 4.2
A binary search of a sorted array has $` a = 1, b = 2, c = 0 `$. So, the running time bound is $` O(\log n) `$.   

### Quiz 4.3
Strassen's Subcubic Matrix Multiplication differs from the naive divide-and-conquer approach in the number of recursive calls: $` a = 7 `$ vs. $` a = 8 `$. For both methods, $` b = 2 `$ because the input matrix shrinks from $` n \times n `$ to $` \frac{n}{2} \times \frac{n}{2} `$. Although the number of entries decreases from $` n^2 `$ to $` \frac{n^2}{4} `$, the recurrence is written as T(n), not $` T(n^2) `$, so $` b \neq 4 `$. The additional matrix operations outside of recursion take $` O(n^2) `$ time, meaning $` d = 2 `$. This is because computing each submatrix of the result involves additions of $` \frac{n}{2} \times \frac{n}{2} `$ matrices, and each matrix has $` \frac{n^2}{4} `$ entries.   

The recurrence $` T(n) \leq 2 \cdot T(\frac{n}{2})  + O(n^2) `$ has a counterintuitive result: $` T(n) = O(n^2) `$. At first glance, it might be mistakenly expected to be $` T(n) = O(n^{2} \log n) `$ by analogy with MergeSort's recurrence $` T(n) \leq 2 \cdot T(\frac{n}{2}) + O(n) `$, which gives $` T(n) = O(n\log n) `$.

### Problem 4.5
Suppose the running time T(n) of an algorithm is bounded by recurrence with T(n) = 1 and $` T(n) \leq T(\lfloor n^{1/2} \rfloor) + 1 `$ for $` n > 1 `$. Show  $` O(\log\log n) `$ is an upper bound of $` T(n) `$, i.e. $` T(n) = O(\log\log n) `$.   
Solution:   
Notice that T(n) is not defined by a standard recurrence form. Since the number of subroutines is 1 and the combine step takes constant running time, $` \text{work at level j } \leq  \text{number of subproblems } \times \text{work per subproblem } = 1 \times 1`$
```math
\begin{align}
n \ \ \ \ \ \ \ \text{<-- Level 0} \\
| \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \  \\ 
n^{\frac{1}{2}} \ \ \ \ \ \ \text{<-- Level 1} \\
| \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \  \\ 
n^{\frac{1}{4}} \ \ \ \ \ \ \text{<-- Level 2} \\
| \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \  \\ 
... \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \  \\ 
| \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \ \  \\ 
1 \ \ \ \ \ \ \text{<-- Level m} \\
\end{align}
```
m should be found such that $` \lim\limits_{n\to \infty} n^{(\frac{1}{2})^m}  = 1`$. Taking log on both sides of the equality, we have $` \lim\limits_{n\to \infty} (\frac{1}{2})^m \log n = 0 `$. This means that $` (\frac{1}{2})^m `$ decreases much faster than how $` \log n `$ grows. So, 
```math
\begin{align}
(\frac{1}{2})^m = O(\frac{1}{\log n}) \\
\Rightarrow \ m \log \frac{1}{2} = O(\log \frac{1}{\log n}) \\
\Rightarrow \ -m \log 2 = O(-\log \log n) \\
\Rightarrow \ m = O(\frac{1}{\log 2} \cdot \log \log n) \\ 
\therefore \ m = O(\log \log n) \\ 
\end{align}
```
Since $` \sum_{j=0}^{m}1 = O(\log\log n)`$, $` T(n) = O(\log\log n) `$.   

## week 3 - QuickSort
#### quick sort overview
QuickSort is an in-place sorting algorithm, unlike MergeSort, which requires additional space. This makes QuickSort preferable for many programming libraries as their default sorting algorithm. Key idea of QuickSort is:
- Pivot
  - Partition
- Swap

QuickSort picks a pivot and rearranges other elements to the left and right of the pivot (partition), according to comparison between an element and the pivot (the one smaller than the pivot goes to the left and bigger to the right). During partition, sorting does not happen on the subproblems. It is only the pivot which goes to the rightful place. The partition step runs in O(n) time, and unlike MergeSort, QuickSort does not have an explicit combine step, and split the problem into left and right subproblems around the pivot.   
Proof: Correctness   
To prove this by mathematical induction, let P(n) denote for every input array of length n, QuickSort algorithm sorts the input array in order. For n = 1, the array is trivially sorted. Assume for n = k, QuickSort correctly sorts any input array of size k. Now, P(k+1) needs to be shown. For n = k+1, QuickSort selects a pivot and partitions the array into two subarrays:
```text
An input of length k+1
┌───────────────────────────┬─────┌───────────────────────────┐
│             pivot >       │pivot│      > pivot              │
└───────────────────────────┴─────└───────────────────────────┘
<---- at most k length ---->      <---- at most k length ---->
```
By the assumption, the left and right partitions (each of at most k elements) are correctly sorted. Since the pivot is placed in its rightful position, the entire array of length k+1 is sorted. Hence, P(k+1) holds.   

#### QuickSort
QuickSort algorithm takes three inputs:
- A: an input array of n length
- l: left endpoint (for initial execution, l = 0)
- r: right endpoint   

Also here, the choice of a pivot and the partition subroutine remain abstract.
```text
Given
         ┌─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┐
array A  │     │ ... │     │     │     │ ... │     │     │
         └─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┘
index:      0          l-1    l                       r

Choose a pivot p, and swap it with the left endpoint.
         ┌─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┐
         │     │ ... │     │  p  │ ... │     │     │     │
         └─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┘
            0          l-1    l                       r

Execute partition on the subarray from index l to r.
         ┌─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┐
         │     │ ... │     │  p  │ ... │     │     │     │
         └─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┘
            0          l-1    l                       r
                            <----- partition target ---->

Recursively call QuickSort excluding the pivot.
               ┌─────┬─────┬─────┬─────┬─────┬─────┬─────┐
               │     │ ... │     │  p  │     │ ... │     │
               └─────┴─────┴─────┴─────┴─────┴─────┴─────┘
                <-- QuickSort -->       <-- QuickSort -->
```

#### Partition
The partition subroutine returns an index of the pivot after placing the pivot at a rightful location. 
At the start of the partition subroutine, the pivot is placed at the beginning of the array. This specifically applies to the first call to Partition, where the pivot is initially placed at index 0.
```text
Initialization
                     ┌─────┬─────┬─────┬─────┬─────┬─────┬─────┐
partition target     │  p  │     │ ... │     │     │     │     │
                     └─────┴─────┴─────┴─────┴─────┴─────┴─────┘
index:                       i,j

All done before swap and return step
                     ┌─────┬─────┬─────┬─────┬─────┬─────┬─────┐
                     │  p  │ ... │     │  k  │     │ ... │     │
                     └─────┴─────┴─────┴─────┴─────┴─────┴─────┘
                                                i           j
                            <----  p >  ----> <----  p <  ---->

Swap the pivot with the right most element smaller than the pivot.
                     ┌─────┬─────┬─────┬─────┬─────┬─────┬─────┐
                     │  k  │ ... │     │  p  │     │ ... │     │
                     └─────┴─────┴─────┴─────┴─────┴─────┴─────┘
                                                i
                      <----  p >  ---->       <----  p <  ---->
```
After the processes are finished in the figure above, the partition subroutine returns a rightful location of the pivot.   

It is a choice of pivot which determines efficiency of the QuickSort algorithm. If an input array is already sorted and also if the pivot is always the first element, then QuickSort algorithms run in $` O(n^2) `$. To be away from this situation, choice of a pivot is randomized.   

Master Method does not apply to QuickSort because pivot is randomly chosen and subproblems are of different sizes.   

#### Theorem 5.1
For every input array of length $` n \geq 1 `$, the average running time of randomized QuickSort is $` O(n \log n) `$.   
Proof:   
This is implied by Lemma 5.2 and Theorem 5.3.

#### Lemma 5.2
There is a constant $` a > 0 `$ such that for every input array A of length at least 2 and every pivot sequence $` \omega `$, $` RT(\omega) \leq a \cdot C(\omega) `$.   
- RT: running time of QuickSort.
- C: total number of comparisons made by QuickSort.   

Proof:   
Each QuickSort call consists of a partition step followed by two recursive calls. The partition step involves comparing each element to the pivot, performing a constant amount of extra work per comparison, such as swapping elements. Thus, the total work done in a partition step is at most a constant multiple of the number of comparisons made. Summing over all partitions,
```math
RT(\omega) = \sum_{\text{all partitions}} (\text{constant} \cdot \text{number of comparisons in that partition} + \text{constant}).
```
Since the total number of comparisons across all partitions is precisely $` C(\omega) `$, for some constants $` a' `$ and $` b' `$ this equations is expressed as:
```math
\begin{align}
RT(\omega) = a' \cdot C(\omega) + b' \\
\Rightarrow  RT(\omega) \leq a \cdot C(\omega)
\end{align}
```
by choosing sufficiently large $` a `$.   

This means that the possible number of comparisons between two arbitrarily chosen elements matter to determine the running time of QuickSort.

#### Theorem 5.3
For every input array of length $` n \geq 1 `$, the expected number of comparisons between input array elements in randomized QuickSort is at most $` 2 \cdot (n-1)\ln n = O(n\log n) `$.   
Proof:   
Let $` z_i `$ and $` z_j `$ denote the ith and jth smallest elements of the input array. Define $` X_{ij}(\omega) `$ as the number of comparisons between $` z_i `$ and $` z_j `$ in QuickSort when the pivots are specifed by $` \omega `$ for every fixed choice of pivots $` \omega `$. The total number of comparisons made by QuickSort is expressed as:
```math
\begin{align}
C(\omega) = \sum_{i=1}^{n-1}\sum_{j=i+1}^{n} X_{ij}(\omega) \\
\Rightarrow E(C) = \sum_{i=1}^{n-1}\sum_{j=i+1}^{n} E(X_{ij}) \\
= \sum_{i=1}^{n-1}\sum_{j=i+1}^{n} \Pr(X_{ij}=1). \\
= \sum_{i=1}^{n-1}\sum_{j=i+1}^{n} \frac{2}{j - i + 1} \ \ \ \ \ \text{ by Lemma 5.4.}
\end{align}
```
This is bounded:
```math
\begin{align}
\sum_{i=1}^{n-1}\sum_{j=i+1}^{n} \frac{2}{j - i + 1} \leq \sum_{i=1}^{n-1} 2 \cdot (\frac{1}{2} + \frac{1}{3} + \cdot \cdot \cdot + \frac{1}{n}) \\
= 2 \cdot (n-1)\sum_{j=2}^{n}\frac{1}{j} \\
\leq 2 \cdot (n-1) \int_{1}^{n} \frac{1}{x} \,dx \\
= 2 \cdot (n-1) \ln n = O(n \log n)
\end{align}
```

#### Lemma 5.4
If $` z_i `$ and $` z_j `$ denote the ith and jth smallest elements of the input array, with $` i < j `$, then, $` \Pr [z_i,z_j \text{ get compared in randomized QuickSort}] = \frac{2}{j - i + 1} `$    
Proof:   
Considering the fact that QuickSort's recursive calls on the left and right of the pivot, the pivot should not be between $` z_i `$ and $` z_j `$ for $` z_i `$ and $` z_j `$ to be compared eventually. Therefore,
```math
\begin{align}
\Pr [z_i,z_j \text{ get compared at some point in randomized QuickSort}] \\
= \Pr [z_i \text{ or } z_j \text{ is chosen as a pivot before any of } z_{i+1}, ..., z_{j-1}] \\
= \frac{2}{j - i + 1} \\
\end{align}
```
because randomized QuickSort chooses a pivot at uniformly random.      

#### Theorem 5.5
There is a constant $` c > 0 `$ such that, for every $` n \geq 1 `$, every comparison-based sorting algorithm performs at least $` c \cdot n \log_2 n `$ operations on some length-n input array.   
- A comparison-based sorting algorithm orders elements using only pairwise comparisons, without accessing or manipulating values directly. Examples of non-comparison-based sorting algorithms are: BucketSort, CountingSort, and RadixSort.    

Proof:   
Assume a comparison-based sorting algorithm which does at most k comparisons. Through this algorithm, there are $` 2^k `$ number of possible execution paths such as below:
```text
             Compare A[1], A[2]
            /                   \
    A[1] < A[2]               A[1] > A[2]
       /       \                 /       \
A[2] < A[3]   A[2] > A[3]   A[1] < A[3]  A[1] > A[3]
      ...         ...          ...          ...
```
This algorithm can distinguish between at most different $` 2^k `$ inputs and execute in at most $` 2^k `$ different ways. An input array of length n could be arranged in $` n! `$ ways. So, to differentiate all possible arrangements, k should satisfy $` 2^k \geq n! `$. By the fact that the first n/2 elements of $` n! `$ is greater than $` n/2 `$:
```math
\begin{align}
2^k \geq n! \geq (\frac{n}{2})^{n/2} \\
\Rightarrow k \geq n/2 \cdot(\log_2 n - 1) \\
= \Omega(n \log n)
\end{align}
```

### Problem 5.1
Choose elements which could have been the pivot element.
```text
┌─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┐
│  3  │  1  │  2  │  4  │  5  │  8  │  7  │  6  │  9  │
└─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┘
```
Not 7. After partition is over all elements on the left of the pivot are smaller than the pivot.   

### Problem 5.5
Extend the $` \Omega(n \log n) `$ lower bound from Theorem 5.5 to apply also to the expected running time of randomized comparison-based sorting algorithms.   
The running time of a sorting algorithm depends on the number of comparison operations. Even with randomness, the number of comparisons needed to distinguish between different input arrays remains the same. This is because, regardless of random choices, the number of comparison operations must be sufficient to distinguish between all $` n! `$ possible permutations of the array.

## week 4 - Linear-Time Selection
- RSelect: randomized selection. O(n)   
- DSelect: deterministic selection. O(n)  

While both run in O(n) time, RSelect is typically faster in practice because DSelect has a larger constant factor. Additionally, DSelect requires more memory than RSelect since execution of DSelect is not in-place.   

After sorting an input array, it is easy to get ith smallest value, but this is not linear time but $` O(n\log n) `$.
#### RSelect overview
- Choose a pivot randomly.
- Execute partition process of QuickSort.
- Choose one and only one subarray to recurse on depending on comparison between the pivot location and i.
```text
Choice of a recursion:       i ∈ [left part]         i ∈ [right part]      
                            ─────────────────       ─────────────────    
                           ┌─────┬─────┬─────┬─────┬─────┬─────┬─────┐
                           │     │ ... │     │  p  │     │ ... │     │
                           └─────┴─────┴─────┴─────┴─────┴─────┴─────┘
index:                                          k
                            <----  p >  ---->       <----  p <  ---->
```
The running time of RSelect depends on how good a chosen pivot is.   

#### Theorem 6.1
For every input array of length $` n \geq 1 `$, the average running time of RSelect is O(n). 

Proof:   
Since there is only one recursive call to RSelect, to track the size of the subarray on a recursive call phase j is used. 
- Phase j: progress of a recursive call to RSelect in relation with the length of its subarray. 
  - If the length of its subarray is between $` n \cdot (\frac{3}{4})^{j+1} `$ and $` n \cdot (\frac{3}{4})^j `$, the recursive call is in phase j.    
- $` X_j `$: number of phase-j recursive calls   

Because RSelect's running time is O(n) outside of its recursive call (partition), there is a constant $` c > 0 `$ such that for every input array of length n, RSelect performs at most cn operations outside of its recursive call. So, in each phase-j recursive call RSelect performs at most $` c \cdot (\frac{3}{4})^j \cdot n `$ operations. So the total running time is:
```math
\begin{align}
\text{running time of RSelect} \leq \sum_{j \geq 0} X_j \cdot c \cdot  (\frac{3}{4})^j \cdot n \\
= cn \sum_{j \geq 0} (\frac{3}{4})^j \cdot X_j \\
\Rightarrow E[\text{running time of RSelect}] \leq cn \sum_{j \geq 0} (\frac{3}{4})^j \cdot E[X_j] \\
\end{align}
```

By corollary 6.5, 
```math
\begin{align}
E[\text{running time of RSelect}] = cn \sum_{j \geq 0}(\frac{3}{4})^jE[X_j] \leq 2cn\sum_{j \leq 0}(\frac{3}{4})^j \\
= 8cn \ \ \ \ \ \ \ \ \ \ \\
\therefore E[\text{running time of RSelect}] = O(n) \\
\end{align}
```

#### Proposition 6.2
If a phase-j recursive call chooses an approximate median, then the next recursive call belongs to phase j+1 or later.   
Proof:   
- Approximate median: an element greater than at least 25% of the other elements in the subarray and less than at least 25% of the other elements   

By definition of an approximate median, given that an approximate median has been chosen, the subarray of the next recursive call is sized at most 75%, i.e. $` \frac{3}{4} `$
```text
     25%                           25%
 ───────────                   ───────────    
┌─────┬─────┬─────┬─────┬─────┬─────┬─────┐
│     │ ... │     │     │     │ ... │     │
└─────┴─────┴─────┴─────┴─────┴─────┴─────┘
             ─────────────────
       possible approximate medians
```
#### Proposition 6.3
A call to RSelect chooses an approximate median with probability at least 50%.   
Proof:   
This follows directrly from the uniform randomness of the pivot choice, and also by the definition of an approximate median.   

#### Proposition 6.4
For each phase j, $` E[X_j] \leq E[N] `$.   
- N: number of tries of tossing a coin till it is heads   
  - Rather than dealing with the complexities of $` X_j `$​ , we compare it to N, which follows a straightforward coin-flipping process.

Proof:   
It is to be shown
```math
\begin{align}
E[X_j] = E[X_j | O_j] \cdot Pr(O_j) + E[X_j | O_j^\complement] \cdot Pr(O_j^\complement) \\
\leq E[N] \\
\end{align}
```
where $` O_j `$ denotes the event that phase j actually occurs. $` O_j^\complement `$ happens if the chosen median reduces the subarray size more than 25% (for example, getting the exact median).    
Here, $` E[X_j | O_j^\complement] \cdot Pr(O_j^\complement) = 0 `$ because:   
```math
\begin{align}
E[X_j | O_j^\complement] = \sum_{x \geq 0} x \cdot Pr(X_j = x | O_j^\complement) \\
= 0 \cdot Pr(X_j = 0 | O_j^\complement) + \sum_{x > 0} x \cdot Pr(X_j = x | O_j^\complement) \\
= 0 \cdot 1 + \sum_{x > 0} x \cdot 0 \ \ \ \because Pr(X_j = 0 | O_j^\complement) = 1 \text{ by definition of } O_j \\ 
\therefore E[X_j | O_j^\complement] = 0.
\end{align}
```
So, $` E[X_j] = E[X_j | O_j] \cdot Pr(O_j) `$.    

Now, by showing $` E[X_j | O_j] \cdot Pr(O_j) \leq E[N] `$ the proof is done. By the fact that there is only single recursive call, if any, to RSelect in each phase: 
```math
\begin{align}
E[X_j | O_j] = \sum_{j \geq 0} 1 \cdot Pr(X_j = 1 | O_j) \cdot Pr(O_j) \\
= \sum_{j \geq 0} Pr(O_j) \ \ \ \because Pr(X_j = 1 | O_j) = 1
\end{align}
```
Also, $` E[N] = \sum_{n = 0}^{\infty} n \cdot Pr(N = n) `$. Putting altogether, the inequality is expressed as:
```math
\begin{align}
E[X_j | O_j] \leq E[N] \\
\Rightarrow \sum_{j \geq 0} Pr(O_j) \leq \sum_{n = 0}^{\infty} n \cdot Pr(N = n) \\
= \sum_{n = 0}^{\infty} n \cdot (\frac{1}{2})^n \\
\end{align}
```
By proposition 6.2 and 6.3, $` Pr(O_j) `$ reduces at least of a factor of 50% whereas the right-hand side's is exactly 50%. So the inequality holds.   

#### Corollary 6.5
For every j, $` E[X_j] \leq 2 `$.   
Proof:   
This follows by the fact that $` E[N] = \sum_{n = 0}^{\infty} n \cdot (\frac{1}{2})^n = 2 `$ and proposition 6.4. $` \sum_{n = 0}^{\infty} n \cdot (\frac{1}{2})^n = 2 `$ because
```math
\begin{align}
\sum_{n=0}^{\infty} x^n = \frac{1}{1-x} \text{ , for } |x| < 1 \\ 
\Rightarrow \sum_{n=0}^{\infty} n \cdot x^{n-1} = \frac{1}{(1-x)^2} \\
\Rightarrow \sum_{n=1}^{\infty} n \cdot x^n = \frac{x}{(1-x)^2} \\
\text{By applying } x = \frac{1}{2}, \\ 
\sum_{n = 1}^{\infty} n \cdot (\frac{1}{2})^n = \frac{\frac{1}{2}}{(1 -\frac{1}{2})^2} = 2 \\
\therefore E[N] = 2 \\
\end{align}
```

#### DSelect pseudo code
- Input: array A of $` n \geq 1 `$ distinct numbers, and an integer $` i \in \set{1, 2, ..., n} `$.
- Output: the ith order statistic of A.
```text
 1  if n = 1 then
 2      return A[1]
 3  for h := 1 to n/5 do
 4      C[h] := middle element from the hth group of 5
 5  p := DSelect(C, n/10)
 6  partition A around p
 7  j := p's position in partitioned array
 8  if j = i then
 9      return p
10  else if j > i then
11      return DSelect(first part of A, i)
12  else
13      return DSelect(second part of A, i - j)
```
This algorithm is identical to RSelect except lines 3 to 5, where the pivot is chosen deterministically using the median-of-medians method rather than randomly.

### Quiz 6.3
A single call to DSelect has 2 recursive calls not 1. The first occurs when finding the median-of-medians while the second happens after partitioning.      

#### Theorem 6.6
For every input array of length $` n \geq 1 `$, the running time of DSelect is O(n).   
Proof:   
DSelect is expressed in a recurrence format of: 
```math
T(n) \leq T(\frac{1}{5}n) + T(\frac{7}{10}n) + O(n).
```
- $` T(\frac{1}{5}n) `$: operations in line 5 in the pseudo code
- $` T(\frac{7}{10}n) `$: operations in line eiter 11 or 13 by lemma 6.7
- O(n): running time bound of sorting each of $` \frac{n}{5} `$ arrays with length 5, more exactly $` \Theta(n) `$.   

$` T(n) \leq l \cdot n `$ to be shown for some constant $` l > 0 `$ through mathematical induction. Choose $` l = 10c `$. For the base case, T(1) = 1. This holds for $` c \geq 1 `$, $` T(1) \leq l`$. Assume $` T(k) \leq l \cdot k `$ for $` k < n `$. So,
```math
\begin{align}
T(n) \leq T(\frac{1}{5}n) + T(\frac{7}{10}n) + cn \\
\leq l \cdot \frac{1}{5}n + l \cdot \frac{7}{10}n + cn \\
= (\frac{9}{10}l + c)n = 10cn \\
\therefore T(n) \leq l \cdot n \\
\end{align}
```
Thus, T(n) = O(n).

#### Lemma 6.7
For every input array of length $` n \geq 2 `$, the subarray passed to the recursive call in line 11 or 13 of DSelect has length at most $` \frac{7}{10}n `$.   
Proof:   
Let $` k = \frac{n}{5} `$ denote the number of groups of size 5. Also let x_i denote the median of each group where $` i \in \set {1, 2, ..., \frac{n}{5}} `$. Then, the median-of-medians is x_k/2 if k is even; otherwise, it is ⌈k/2⌉. By the drawing below, the median-of-medians is greater than at least 30% of elements in the input array.
```text
              | Group 1 |   ...   |Group k/2|   ...   | Group k |
              |---------|---------|---------|---------|---------|
              | greater |   ...   | greater |   ...   | greater |
              | greater |   ...   | greater |   ...   | greater |
          ┌─  |   x_1   |   ...   |  x_k/2  |   ...   |  x_n/5  |
3/5 rows  │   | smaller |   ...   | smaller |   ...   | smaller |
          │   | smaller |   ...   | smaller |   ...   | smaller |
          │
          └─────────────────────────────────┘
                         1/2 columns
3/5 * 1/2 = 30%
```
Similarly, the median-of medians is less than at least 30% of elements in the input array. So, the median-of-medians, i.e. the pivot, lies between a range of excluding left and right 30% of the array such as below:
```text
  30% of n                       30% of n
 ───────────                   ───────────
┌─────┬─────┬─────┬─────┬─────┬─────┬─────┐
│     │ ... │     │     │     │ ... │     │
└─────┴─────┴─────┴─────┴─────┴─────┴─────┘
             <- pivot range ->
```
Thus, the subarray passed to the recursive call in line 11 or 13 is guaranteed to have at most $` \frac{7}{10}n `$.

### Problem 6.3
Suppose an unsorted input array of length n consisting of distinct elements $` x_i `$ for $` i \in \set{1,2,..., n} `$. Each element $` x_i `$ has a positive weight $` w_i `$, and let $` W = \sum_{i = 1}^{n} w_i `$. Using DSelect, find an element $` x_k `$ such that  $` \sum_{x_i < x_k} w_i \leq \frac{W}{2} `$ and also $` \sum_{x_i > x_k} w_i  \leq \frac{W}{2} `$. Observe that there is either one or two such elements.   
- Input
  - A: the input array of length n
  - W: $` \sum_{i=1}^{n} w_i `$
  - D_L: total discarded weights of elements that are less than the elements of the subarray; initialized as 0
  - D_R: total discarded weights of elements that are greater than the elements of the subarray; initialized as 0
```text
 1  function WeightedMedian(A, W, D_L, D_R)
 2      if n = 1 then
 3          return A[0]
 4      p := DSelect(A, n/2)  // Compute unweighted median
 5      Partition A around p
 6      Compute W_L and W_R, the total weight of elements on each side of p
 7      if W_L + D_L ≤ W/2 and W_R + D_R ≤ W/2 then
 8          return p
 9      else if W_L + D_L > W/2 then
10          return WeightedMedian(left part of A, W, D_L, D_R + W_R + weight of p)
11      else         // Since the total weight is W, both sides cannot each have weight > W/2.
12          return WeightedMedian(right part of A, W, D_L + W_L + weight of p, D_R)
```
This algorithm is represented by a recurrence format:
```math
\begin{align}
T(n) \leq T(\frac{n}{2}) + O(n) \\
\therefore T(n) = O(n) \ \text{ by Master Method.}
\end{align}
```

# References
- Tim Roughgarden. (2018)  Algorithms Illuminated Part 1 (1st ed.). Soundlikeyourself Publishing, LLC
