# Overview
his README is about notes on Algorithms courses from [coursera - Divide and Conquer, Sorting and Searching, and Randomized Algorithms](https://www.coursera.org/learn/algorithms-divide-conquer?specialization=algorithms). This file uses LaTeX.

However, problems in this repository are from text books Algorithm Illuminated.

## Implementation Struggles
probelm 1.6 Karatsuba multiplication

# Divide and Conquer, Sorting and Searching, and Randomized Algorithms
## week 1 - Merge sort and worst case analysis
Merge sort is a typical example of divide & conquer while it is a better version of selection, insertion, and bubble sort. Merge sort is a recursive algorithm spliting half and merge. 
###### merge sort overview
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
###### sudo code during merge
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
Seeing from above, there are $` 4l + 2 \leq 6l `$ operations to merge two $` l/2 `$ length arrays. So, the work done by a level j subproblem is $` 2^j \times 6n/2^j = 6n `$. Summing all levels' work, $` (m + 1)6n = (\log_2 n + 1)6n = 6n\log_2 n + 6n `$

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
###### comparison process of n numbers
```text
n/2 pairs: (○, ○), (○, ○), ... , (○, ○), (○, ○)
n/4 pairs: (○, ○), ... , (○, ○)
...
1 pair: (○, ○)
```
The number of comparisons above to get the largest number is    
```math
n + 1 
```
Additionally, throughout the proccess, a list of numbers defeated by the largest number is collected. Since the largest number competes in every level of the tournament, the size of this list is the number of levels, i.e., $` \log_2 n `$. The number of comparisons needed to find the second-largest number from this list is derived from the above as $` \log_2 n - 1 `$.

Thus, the total number of comparisons is:
```math 
n + \log_2 n - 2 
```

## week 1 - Asymptotic analysis
###### Asymptotic Notation in Seven Words
>  supress constant factors (system dependent) and lower-order terms (irrelevant for large inputs)

###### Big-O Notation
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

###### Big-Omega Notation
$` T(n) = \Omega(f(n)) `$ if and only if there exist positive constants $` c `$ and $` n_0 `$ such that $` T(n) \geq c \cdot f(n) `$ for all $` n \geq n_0 `$.

###### Big-Theta Notation
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
