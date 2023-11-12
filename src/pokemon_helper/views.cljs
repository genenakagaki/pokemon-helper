(ns pokemon-helper.views
  (:require
   [re-frame.core :refer [subscribe dispatch]]
   [pokemon-helper.events :as events]
   [pokemon-helper.routes :as routes]
   [pokemon-helper.subs :as subs]
   ["react-feather" :as icon]
   ))

(defn poketype-select []
  [:div.grid.grid-cols-3.gap-3.py-4
   (for [{:keys [name label selected?]}
         @(subscribe [::subs/poketype-select-options])]
     ^{:key name}
     [:div.bg-black.rounded-2xl
      [:button.w-full.rounded-2xl.py-4.border-b-2
       {:class (str (:color label)
                    " "
                    (if selected?
                      "opacity-100"
                      "opacity-60"))
        :on-click #(dispatch [::events/select-poketype name])}
       [:span.text-white.font-bold.drop-shadow
        (:text label)]]])])

(defn poketype-effectiveness []
  [:div
   (for [[effectiveness labels]
         @(subscribe [::subs/poketype-labels-by-effectiveness])]
     ^{:key effectiveness}
     [:div.rounded-2xl.py-4
      [:p.text-xl
       (let [{:keys [icon-name icon-color]}
             (case effectiveness
               4 {:icon-name icon/ChevronsUp
                  :icon-color "text-green-500"}
               2 {:icon-name icon/ChevronUp
                  :icon-color "text-green-500"}
               0.5 {:icon-name icon/ChevronDown
                    :icon-color "text-red-600"}
               0.25 {:icon-name icon/ChevronsDown
                     :icon-color "text-red-600"}
               0 {:icon-name icon/X
                  :icon-color "text-red-600"}
               :else "")]
         [:> icon-name {:class (str icon-color " inline pb-1")
                        :size "36"}])
       "攻撃力" effectiveness "倍の属性"]
      [:div.flex.flex-wrap.gap-1.pt-2
       (for [{:keys [name label]} labels]
         ^{:key name}
         [:div.rounded-full
          {:class (:color label)}
          [:span.text-white.font-bold.drop-shadow.px-4.py-2
           (:text label)]])]])])

;; home

(defn home-panel []
  [:div.container
   [:h2.text-2xl "どのポケモンでバトルするか"]
   [:p.text-xl.pt-8 "相手はどんな属性？（2つまで）"]
   [:div.grid.gap-0 {:class "grid-cols-1 md:grid-cols-2 md:gap-8"}
    [poketype-select]
    [poketype-effectiveness]]])

(defmethod routes/panels :home-panel [] [home-panel])

;; about

(defn about-panel []
  [:div
   [:h1 "This is the About Page."]

   [:div
    [:a {:on-click #(dispatch [::events/navigate :home])}
     "go to Home Page"]]])

(defmethod routes/panels :about-panel [] [about-panel])

;; main

(defn main-panel []
  (let [active-panel (subscribe [::subs/active-panel])]
    [:<>
     [:header.flex.border-b-8.border-black.rounded-t-full {:class "bg-[#DF0024]"}
      [:div.grow]
      [:h1.text-4xl.text-white.py-16 "ポケモンヘルパー"]
      [:div.grow]]
     [:div.flex
      [:div.grow]
      [:a.relative.rounded-full.ring-8.ring-black.bg-white.p-3
       {:href "https://github.com/genenakagaki/pokemon-helper"
        :target "_blank"
        :class "bottom-[36px] hover:bg-blue-200"}
       [:> icon/GitHub {:size "36"}]]
      [:div.grow]]
     [:main.container.mx-auto.px-4
      (routes/panels @active-panel)]
     [:div.flex
      [:div.grow]
      [:a.relative.rounded-full.ring-8.ring-black.bg-white.p-3
       {:href "https://twitter.com/genakag"
        :target "_blank"
        :class "top-[36px] hover:bg-blue-300"}
       [:> icon/Twitter {:size "36"}]]
      [:div.grow]]
     [:footer.flex.border-t-8.border-x-2.border-b-2.border-black.rounded-b-full
      [:div.grow]
      [:p.py-10 "Created with:"
       [:br]
       "- ClojureScript"
       [:br]
       "- re-frame"
       [:br]
       "- tailwindcss"]
      [:div.grow]]]))
