
(use 'clojure.test
     'clojure.test.ext)

;; stackoverflow at [2 25000]
;; ok at [2 20000]
(defn expt-recursive [b n]
  (if (zero? n)
    1N
    (* b (expt-recursive b (dec n)))))

(defn expt-iterative [b n]
  (loop [r 1N count n]
         (if (zero? count)
           r
           (recur (* b r) (dec count)))))

;; ok at [2 100000]!
(defn fast-expt-recursive [b n]
  (cond
    (zero? n) 1N
    (even? n) (let [v (fast-expt-recursive b (/ n 2))] (* v v))
    :else (* b (fast-expt-recursive b (dec n)))))

;; http://community.schemewiki.org/?sicp-ex-1.16
;; Consider that (helper a b n) = a * b^n.
;; Hence, if (even? n), then (helper a b n) = (helper a (* b b) (/ n 2))
(defn fast-expt-iterative [b n]
  (loop [r 1N, b (bigint b), count n]
    (cond
      (zero? count) r
      (even? count) (recur r (* b b) (/ count 2))
      :else (recur (* b r) b (dec count)))))

(defmultitest t
  (fn [f]
    (are [x y z] (= (f x y) z)
                 2 3 8
                 2 10 1024
                 3 10 59049
                 4 10 1048576))
  expt-recursive
  expt-iterative
  fast-expt-recursive
  fast-expt-iterative)