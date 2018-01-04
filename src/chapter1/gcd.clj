
(use 'clojure.test 'clojure.test.ext)

(defn gcd-iterative [a b]
  (if (zero? b)
    a
    (recur b (rem a b))))


(defmultitest t
  (fn [f] (are [x y z] (= (f x y) z)
                       28 16 4
                       16 28 4))
  gcd-iterative)