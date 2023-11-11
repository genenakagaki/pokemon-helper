(ns pokemon-helper.views
  (:require
   [re-frame.core :as re-frame]
   [pokemon-helper.events :as events]
   [pokemon-helper.routes :as routes]
   [pokemon-helper.subs :as subs]
   ))


;; home

(defn home-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:h1
      (str "Hello from " @name ". This is the Home Page.")]

     [:div
      [:a {:on-click #(re-frame/dispatch [::events/navigate :about])}
       "go to About Page"]]
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
