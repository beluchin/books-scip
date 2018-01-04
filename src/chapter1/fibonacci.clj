(use 'clojure.test
     'clojure.test.ext)

(defn fibos [] (map first (iterate (fn [[a b]] [(+ a b) a]) [0 1])))

;; computes (unnecessarily) one more Fib number
(defn fib-iterative [n]
  (loop [a 1, b 0, count n]
    (if (= count 0)
      b
      (recur (+ a b) a (dec count)))))

;; does not compute the extra number
(defn fib-iterative-2 [n]
  (cond
    (= n 1) 1
    (= n 0) 0
    :else (loop [a 1, b 0, count (dec n)]
            (if (zero? count)
              a
              (recur (+ a b) a (dec count))))))

(defn fib-tree-recursive [n]
  (cond (= n 0) 0
        (= n 1) 1
        :else (+ (fib-tree-recursive (- n 1))
                 (fib-tree-recursive (- n 2)))))

(defmultitest test-implementations
  (fn [fib]
    (are [x y] (= (fib x) y)
               0 0
               1 1
               2 1
               3 2
               4 3
               5 5
               6 8
               7 13))
  fib-iterative
  fib-iterative-2
  fib-tree-recursive)







