(ns chapter1.sqrt)

;; 1.2
(/ (+ 5 4 (- 2 (- 3 (+ 6 (/ 4 5)))))
   (* 3 (- 6 2) (- 2 7)))

;; 1.3
(defn sum-square-of-larger-two [& args]
  ;;
  #_(apply + (map #(* % %) (take 2 (reverse (sort args)))))
  ;; with thread-last operator
  (->> args
       sort                                                 ; or (sort)
       reverse                                              ; or (reverse)
       (take 2)
       (map #(* % %))
       (apply +)))

;; sqrt-iter
(defn sqrt-iter
  ([x] (sqrt-iter 1.0 x))
  ([guess x]
   (let [abs (fn [n] (max n (- n)))
         avg (fn [a b] (/ (+ a b) 2))
         improve (fn [guess] (avg guess (/ x guess)))

          ; bad for small (.001) and large numbers (10^13 - stackoverflow).
          ; Recall that Clojure does not do tail recursive call
          ; optimizations due to limitations of the JVM.
          ;good-enough? (fn [guess x] (< (abs (- (* guess guess) x)) 0.001))
          ; ---
          ; still bad for small numbers (.001) but does not crash for large
          ; numbers (10^14)
         good-enough? (fn [guess] (< (abs (- (improve guess) guess))
                                     (* guess 0.001)))]


     (if (good-enough? guess)
       guess
       (sqrt-iter (improve guess) x)))))

