# Asymptotics Problems Solutions

For questions 1-7, state whether the statement is true or false. For question 8, give a runtime bound.

## Q1 `B.put(K, V)` $\in O (\log N)$

False. Worst case is $\Theta (N)$, for a spindly BST

## Q2 `B.put(K, V)` $\in \Theta (\log N)$

False. Worst case is $\Theta (N)$

## Q3 `B.put(K, V)` $\in \Theta (N)$

False. Best case is $\Theta (\log (N))$, for a bushy BST

## Q4 `B.put(K, V)` $\in O (N)$

True

## Q5 `B.put(K, V)` $\in O (N^2)$

True (remember that big-O need not be tight unless otherwise specified!)

## Q6 For a fixed key `C` not equal to `K`, both `B.containsKey(C)` and `B.containsKey(K)` run in $\Omega (\log N)$

False. In the best case, these nodes are near the root. Note that if C and K are randomly chosen, then this is True in the amortized case, since the depth of the average node in the tree is given by the sum $\sum_{h=1}^{\lg N} \frac{h2^h}{N} \in \Theta(\log N)$

## Q7 (This question is quite difficult.) Let `b` be a `Node` of a `BSTMap`, and two subtrees rooted at `root`, called `left` and `right`. Further, assume the method `numberOfNodes(Node p)` returns the number of nodes $(M)$ of the subtree rooted at `p` and runs in $\Theta (M)$ time. What is the running time, in both the worst and best case, of `mystery(b.root, z)`, assuming `1 <= z < numberOfNodes(b.root)`?

Hint: See if you can work out what `mystery` does first, then see how it accomplishes it.

```Java
public Key mystery(Node b, int z) {
    int numLeft = numberOfNodes(b.left);
    if (numLeft == z - 1) {
        return b.key;
    } else if (numLeft > z) {
        return mystery(b.left, z);
    } else {
        return mystery(b.right, z - numLeft - 1);
    }
}
```

`mystery` finds the zth largest element in the BST (indexed from 1). It does this by calculating the number of elements in the left subtree, then going to the right or left subtree depending on whether we have too many elements in the left subtree or not enough. Note that `numLeft` will take proportional time to the number of nodes in the left subtree, so the amount of work in one recursive subcall is $\Theta (\text{number of nodes in left subtree})$.

There are no conditions on the tree, so we must consider when the tree is bushy, left-spindly, and right-spindly. The best-case will be when the root of the tree is exactly the `z`th largest element, in which case it will take $\Theta (\text{number of nodes in left subtree})$ time (because there is no recursive call).

* Bushy Tree: There are $\frac {N}{2} - 1$ nodes in the left subtree, so the runtime is $\Theta (N)$ in the best case (when `z == N / 2`).
* Spindly Tree (Left): There are $N - 1$ nodes in the left subtree, so the runtime is $\Theta (N)$ in the best case (when `z == N`).
* Spindly Tree (Right): There are $0$ nodes in the left subtree, so the runtime is $\Theta (1)$ in the best case (when `z == 1`).

Hence the best-case runtime is $\Theta (1)$.

The worst case occurs when we have to traverse all the way down to the leaves of the tree.

