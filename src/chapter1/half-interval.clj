
(use 'clojure.test 'clojure.test.ext)

(defn average [x y] (/ (+ x y) 2))

(defn close-enough? [x y] (> 1 (Math/abs (double (- x y)))))

(defn find-0-half-interval
  "assumes (< (f neg) 0) and (< 0 (f pos))"
  [f neg pos]
  (loop [n neg, p pos]
    (let [midpoint (average n p)]
     (if (close-enough? n p)
       midpoint
       (cond
         (pos? (f midpoint)) (recur n midpoint)
         (neg? (f midpoint)) (recur midpoint p)
         :else midpoint)))))

(defn shorter
  "an attempt to make find-0-half-interval shorter.

  It is definitely shorter - the (cond ...) form was extracted into
  another function. Is it worth it?

  For one thing, at least the exercise helped me understand trampoline ..."
  [f neg pos]
  (letfn [(find-0 [n m p]
            (cond
              (pos? (f m)) (trampoline #(shorter f n m))
              (neg? (f m)) (trampoline #(shorter f m p))
              :else m))]
    (loop [n neg, p pos]
      (let [midpoint (average n p)]
        (if (close-enough? n p)
          midpoint
          (trampoline #(find-0 n midpoint p)))))))

(defmultitest t
  (fn [f] (is (= (f identity -5 100) 85/256)))
  find-0-half-interval
  shorter)