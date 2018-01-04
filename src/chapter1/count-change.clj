

(use 'clojure.test)

(defn count-change
  ([amount] (count-change amount [1, 5, 10, 25, 50]))
  ([amount coin-kinds]
   (cond
     (zero? amount) 1
     (< amount 0) 0
     (zero? (count coin-kinds)) 0
     :else (+ (count-change (- amount (last coin-kinds)) coin-kinds)
              (count-change amount (drop-last coin-kinds))))))

(deftest values
  (is (= 292 (count-change 100))))


