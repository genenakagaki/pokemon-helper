(ns pokemon-helper.subs
  (:require
   [cljs.pprint :as pp]
   [pokemon-helper.db :as db]
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::lang
 (fn [db]
   (:lang db)))

(re-frame/reg-sub
 ::poketype-m
 (fn [db]
   (:poketype-m db)))

(defn poketype-label [type-kv language]
  (let [[name type] type-kv]
    {:name name
     :label (-> (:label type)
                (assoc :text (get-in type [:label :text language])))}))

(defn poketype-labels [poketype-m lang]
  (->> poketype-m
       (map #(poketype-label % lang))))

(comment
  (poketype-label (first (:poketype-m db/default-db)) :en)
  (poketype-labels (:poketype-m db/default-db) :en)
  )

(re-frame/reg-sub
 ::poketype-labels
 :<- [::poketype-m]
 :<- [::lang]
 (fn [[poketype-m lang] _]
   (poketype-labels poketype-m lang)))

(re-frame/reg-sub
 ::selected-poketype-names
 (fn [db]
   (:selected-poketype-names db)))

(defn selected-poketype? [name selected-names]
  (some? (->> selected-names
              (some #(= % name)))))

(comment
  (selected-poketype? :normal [])
  (selected-poketype? :normal [:normal])
  )

(re-frame/reg-sub
 ::selected-poketype?
 :<- [::selected-poketype-names]
 (fn [selected-names [_ name]]
   (selected-poketype? name selected-names)))

(defn poketype-select-options [labels selected-names]
  (->> labels
       (map #(assoc % :selected? (selected-poketype? (:name %) selected-names)))))

(comment
  (poketype-select-options (poketype-labels (:poketype-m db/default-db) :en) [:normal])
  )

(re-frame/reg-sub
 ::poketype-select-options
 :<- [::poketype-labels]
 :<- [::selected-poketype-names]
 (fn [[labels selected-names] _]
   (poketype-select-options labels selected-names)))

(defn poketype-effectiveness
  ([kw]
   (case kw
     :double 2
     :half 0.5
     :none 0))
  ([type name]
   (->> (:effectiveness type)
        (map (fn [[kw types]]
               (some #(when (= % name) (poketype-effectiveness kw)) types)))
        (map #(if (nil? %)
                1
                %))
        (apply *)))
  ([type name1 name2]
   (* (poketype-effectiveness type name1)
      (poketype-effectiveness type name2))))

(comment
  (poketype-effectiveness :double)
  (poketype-effectiveness :half)
  (poketype-effectiveness :none)

  (poketype-effectiveness (:normal (:poketype-m db/default-db)) :ghost)
  (poketype-effectiveness (:normal (:poketype-m db/default-db)) :steel)
  (poketype-effectiveness (:normal (:poketype-m db/default-db)) :normal)
  (poketype-effectiveness (:fire (:poketype-m db/default-db)) :grass)
  
  (poketype-effectiveness (:normal (:poketype-m db/default-db)) :normal :ghost)
  (poketype-effectiveness (:fire (:poketype-m db/default-db)) :grass :ice)
  (poketype-effectiveness (:fire (:poketype-m db/default-db)) :fire :water)
  )

(defn poketype-labels-by-effectiveness [type-m selected-names lang]
  (if (== (count selected-names) 0)
    []
    (->> type-m
         (map (fn [[name type]]
                (-> (poketype-label [name type] lang)
                    (assoc :effectiveness (->> (concat (list type) selected-names)
                                               (apply poketype-effectiveness))))))
         (filter #(not= (% :effectiveness) 1))
         (group-by :effectiveness)
         (sort-by first)
         (reverse))))

(comment
  (poketype-labels-by-effectiveness (:poketype-m db/default-db) [:fire] :en)
  (poketype-labels-by-effectiveness (:poketype-m db/default-db) [] :en)
  )

(re-frame/reg-sub
 ::poketype-labels-by-effectiveness
 :<- [::poketype-m]
 :<- [::selected-poketype-names]
 :<- [::lang]
 (fn [[poketype-m selected-names lang] _]
   (poketype-labels-by-effectiveness poketype-m selected-names lang)))

(re-frame/reg-sub
 ::active-panel
 (fn [db _]
   (:active-panel db)))
