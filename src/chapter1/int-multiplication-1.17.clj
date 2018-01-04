
(use 'clojure.test
     'clojure.test.ext)

(defn mult-recursive [a b]
  (if (zero? b)
    0
    (+ a (mult-recursive a (dec b)))))

(defn fast-mult-iterative [a b]
  (letfn [(double [x] (* x 2))
          (halve [x] (/ x 2))]
    (loop [r 0, a a, count b]
      (cond
        (= count 0) r
        (even? count) (recur r (double a) (halve count))
        :else (recur (+ a r) a (dec count))))))

(defmultitest t
  (fn [f] (are [x y z] (= (f x y) z)
                       2 4 8
                       4 2 8
                       3 4 12
                       4 3 12
                       4 5 20
                       7 8 56))
  mult-recursive
  fast-mult-iterative)