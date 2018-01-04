
(use 'clojure.test
     'clojure.test.ext)

(defn f-recursive [n]
  (cond
    (< n 3) n
    :else (+ (f-recursive (- n 1))
             (* 2 (f-recursive (- n 2)))
             (* 3 (f-recursive (- n 3))))))

(defn f-iterative-inner-defn [n]
  (letfn [(helper [r pp ppp count]
            (if (= count n)
              r
              (recur (+ r (* 2 pp) (* 3 ppp)) r pp (inc count))))]
    (if (< n 3)
      n
      ;;      f(3) f(2) f(1) count
      (helper 4    2    1    3))))

(defn f-iterative-loop-recur [n]
  (if (< n 3)
    n
    ;;       f(3)    f(2)     f(1)       count
    (loop [r 4,   pp 2,   ppp 1,   count 3]
      (if (= count n)
        r
        (recur (+ r (* 2 pp) (* 3 ppp)) r pp (inc count))))))

(defmultitest values
  (fn [f] (are [x y] (= (f x) y)
                     0 0
                     1 1
                     2 2
                     3 4
                     5 25
                     6 59))
  f-recursive
  f-iterative-inner-defn
  f-iterative-loop-recur)