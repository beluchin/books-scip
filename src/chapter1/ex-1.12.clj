
(use 'clojure.test)

;;
;; Pascal's triangle
;;                            level start
;;           1                0     0
;;         1   1              1     1
;;       1   2   1            2     3
;;     1   3   3   1          3     6
;;   1   4   6   4   1        4     10
;; 1   5   10  10  5   1      5     15
;; ...
;;

(def ^:private levels
  "the sequence of levels.
  A level is a map with keys :level and :start.
  The values are 0-based"
  (iterate
    (fn [{:keys [level start]}]
      (let [new-level (inc level)]
        {:level new-level, :start (+ start new-level)}))
    {:level 0, :start 0}))

(with-test
  (defn- level [n]
    "computes the level of the element e.g.
        0 => {:level 0, :start 0}
        1, 2 => {:level 1, :start 1}
        3, 4, 5 => {:level 2, :start 3 }
        6, 7, 8, 9 => {:level 3, :start 6}
        ...
        "
    (last (take-while #(>= n (:start %)) levels)))
  (are [x y] (= (level x) y)
             0 {:level 0, :start 0}
             1 {:level 1, :start 1}
             2 {:level 1, :start 1}
             3 {:level 2, :start 3}
             4 {:level 2, :start 3}
             5 {:level 2, :start 3}
             6 {:level 3, :start 6}
             7 {:level 3, :start 6}
             8 {:level 3, :start 6}
             9 {:level 3, :start 6}))

(defn- nth-element [n]
  (let [l (level n)
        offset (- n (:start l))
        at-edge? (or (zero? offset) (= (:level l) offset))]
    (if at-edge?
      1
      (let [prev-level-start (:start (nth levels (dec (:level l))))]
        (+ (nth-element (+ (dec offset) prev-level-start))
           (nth-element (+ offset prev-level-start)))))))

(def pascal-triangle-elements (map nth-element (range)))

(deftest elements
  (let [triangle
        [
         1
         1   1
         1   2   1
         1   3   3   1
         1   4   6   4   1
         1   5  10  10   5  1
         1   6  15  20  15  6  1
         ]]
    (is (= triangle (take (count triangle) pascal-triangle-elements)))))