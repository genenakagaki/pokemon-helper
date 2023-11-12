(ns pokemon-helper.subs
  (:require
   [cljs.pprint :as pp]
   [pokemon-helper.db :as db]
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 ::poketype-m
 (fn [db]
   (:poketype-m db)))

(defn poketype-label [type-kv language]
  (let [[name type] type-kv]
    {:name name
     :label (-> (:label type)
                (assoc :text (get-in type [:label :text language])))}))

(defn poketype-labels [poketype-m]
  (->> poketype-m
       (map #(poketype-label % :ja))))

(comment
  (poketype-label (first (:poketype-m db/default-db)) :ja)
  (poketype-labels (:poketype-m db/default-db))
  )

(re-frame/reg-sub
 ::poketype-labels
 :<- [::poketype-m]
 (fn [poketype-m _]
   (poketype-labels poketype-m)))


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
  (poketype-select-options (poketype-labels (:poketype-m db/default-db)) [:normal])
  )

(re-frame/reg-sub
 ::poketype-select-options
 :<- [::poketype-labels]
 :<- [::selected-poketype-names]
 (fn [[labels selected-names] _]
   (poketype-select-options labels selected-names)))

(re-frame/reg-sub
 ::active-panel
 (fn [db _]
   (:active-panel db)))

(println (ns-interns 'pokemon-helper.subs))
