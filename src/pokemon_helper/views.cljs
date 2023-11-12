(ns pokemon-helper.views
  (:require
   [re-frame.core :as re-frame]
   [pokemon-helper.events :as events]
   [pokemon-helper.routes :as routes]
   [pokemon-helper.subs :as subs]
   ))

;; home

(defn home-panel []
  (let [poketype-selection-list @(re-frame/subscribe [::subs/poketype-select-options])]
    [:div.container
     [:h2.text-2xl.pt-4 "どのポケモンでバトルするか"]
     [:p.text-xl.pt-4 "相手はどんな属性？（2つまで）"]
     [:div.grid.grid-cols-3.gap-3.py-4
      (for [{:keys [name label selected?]} poketype-selection-list]
        ^{:key name}
        [:div.bg-black.rounded-2xl
         [:button.w-full.rounded-2xl.py-4.border-b-2
          {:class (str (:color label)
                       " "
                       (if selected?
                         "opacity-100"
                         "opacity-60"))
           :on-click #(re-frame/dispatch [::events/select-poketype name])}
          [:span.text-white.font-bold.drop-shadow
           (:text label)]
          ]])]
     [:div
      (for [[effectiveness labels] @(re-frame/subscribe [::subs/poketype-labels-by-effectiveness])]
        ^{:key effectiveness}
        [:div.rounded-2xl.py-4
         [:p.text-xl "攻撃力" effectiveness "倍の属性"]
         [:div.flex.flex-wrap.gap-1.pt-2
          (for [{:keys [name label]} labels]
            ^{:key name}
            [:div.rounded-full
             {:class (:color label)}
             [:span.text-white.font-bold.drop-shadow.px-4.py-2
              (:text label)]])]
         ])]
     ]))

(defmethod routes/panels :home-panel [] [home-panel])

;; about

(defn about-panel []
  [:div
   [:h1 "This is the About Page."]

   [:div
    [:a {:on-click #(re-frame/dispatch [::events/navigate :home])}
     "go to Home Page"]]])

(defmethod routes/panels :about-panel [] [about-panel])

;; main

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [:<>
     [:header.flex.border-b-8.border-black.rounded-t-full {:class "bg-[#DF0024]"}
      [:div.grow]
      [:h1.text-4xl.text-white.py-16 "ポケモンヘルパー"]
      [:div.grow]]
     [:main.container.mx-auto.px-4
      (routes/panels @active-panel)]
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
