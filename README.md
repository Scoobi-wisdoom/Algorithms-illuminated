# Overview
his README is about notes on Algorithms courses from [coursera - Divide and Conquer, Sorting and Searching, and Randomized Algorithms](https://www.coursera.org/learn/algorithms-divide-conquer?specialization=algorithms). This file uses LaTeX.

However, problems in this repository are from text books Algorithm Illuminated.

## Implementation Struggles
probelm 1.6 Karatsuba multiplication

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
#### sudo code during merge
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

# References
- Tim Roughgarden. (2018)  Algorithms Illuminated Part 1 (1st ed.). Soundlikeyourself Publishing, LLC
