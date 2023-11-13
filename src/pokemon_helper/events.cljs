(ns pokemon-helper.events
  (:require
   [day8.re-frame.tracing :refer-macros [fn-traced]]
   [pokemon-helper.db :as db]
   [pokemon-helper.subs :as subs]
   [re-frame.core :as re-frame]))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
            db/default-db))

(re-frame/reg-event-db
 ::toggle-lang
 (fn-traced [db _]
            (assoc db :lang (if (= :en (:lang db))
                              :ja
                              :en))))

(defn select-poketype [db name]
  (let [{:keys [selected-poketype-names]} db]
    (assoc db :selected-poketype-names
           (if (subs/selected-poketype? name selected-poketype-names)
             (->> selected-poketype-names
                  (filter #(not= % name)))
             (if (< (count selected-poketype-names) 2)
               (->> selected-poketype-names
                    (concat (list name)))
               selected-poketype-names)))))

(comment
  (select-poketype {:selected-poketype-list []} :normal)
  (select-poketype {:selected-poketype-list [:normal]} :normal)
  )

(re-frame/reg-event-db
 ::select-poketype
 (fn-traced [db [_ name]]
            (select-poketype db name)))


(re-frame/reg-event-fx
 ::navigate
 (fn-traced [_ [_ handler]]
            {:navigate handler}))

(re-frame/reg-event-fx
 ::set-active-panel
 (fn-traced [{:keys [db]} [_ active-panel]]
            {:db (assoc db :active-panel active-panel)}))
