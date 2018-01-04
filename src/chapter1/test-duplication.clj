

;; https://stackoverflow.com/questions/47954895/unable-to-remove-duplication-when-composing-tests

(use 'clojure.test)

(defn foo1 [] ,,,)
(defn foo2 [] ,,,)
(defn foo3 [] ,,,)

(defn- test-impl [foo]
  (is (= ,,, (foo))))

(deftest test-all-impls
  (doseq [foo [foo1 foo2 foo3]] (test-impl foo)))

