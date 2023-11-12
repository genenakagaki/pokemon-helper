(ns pokemon-helper.db)

(def normal :normal)
(def fire :fire)
(def water :water)
(def electric :electric)
(def grass :grass)
(def ice :ice)
(def fighting :fighting)
(def poison :poison)
(def ground :ground)
(def flying :flying)
(def psychic :psychic)
(def bug :bug)
(def rock :rock)
(def ghost :ghost)
(def dragon :dragon)
(def dark :dark)
(def steel :steel)
(def fairy :fairy)

(def default-db
  {:poketype-m
   (array-map
    normal {:label {:text {:ja "ノーマル"
                           :en "Normal"}
                    :color "bg-[#AEAEAE]"}
            :effectiveness {:double []
                            :half [rock steel]
                            :none [ghost]}}
    fire {:label {:text {:ja "ほのお"
                         :en "Fire"}
                  :color "bg-[#FFA766]"}
          :effectiveness {:double [grass ice bug steel]
                          :half [fire water rock dragon]
                          :none []}}
    water {:label {:text {:ja "みず"
                          :en "Water"}
                   :color "bg-[#66C5F7]"}
           :effectiveness {:double [fire ground rock]
                           :half [water grass dragon]
                           :none []}}
    electric {:label {:text {:ja "でんき"
                             :en "Electric"}
                      :color "bg-[#E7D400]"}
              :effectiveness {:double [water flying]
                              :half [electric grass dragon]
                              :none [ground]}}
    grass {:label {:text {:ja "くさ"
                          :en "Grass"}
                   :color "bg-[#9AC310]"}
           :effectiveness {:double [water ground rock]
                           :half [fire grass poison flying bug dragon steel]
                           :none []}}
    ice {:label {:text {:ja "こおり"
                        :en "Ice"}
                 :color "bg-[#61E9F6]"}
         :effectiveness {:double [grass ground flying dragon]
                         :half [fire water ice steel]
                         :none []}}
    fighting {:label {:text {:ja "かくとう"
                             :en "Fighting"}
                      :color "bg-[#EF6969]"}
              :effectiveness {:double [normal ice rock dark steel]
                              :half [poison flying psychic bug fairy]
                              :none [ghost]}}
    poison {:label {:text {:ja "どく"
                           :en "Poison"}
                    :color "bg-[#AB7ACA]"}
            :effectiveness {:double [grass fairy]
                            :half [poison ground rock ghost]
                            :none [steel]}}
    ground {:label {:text {:ja "じめん"
                           :en "Ground"}
                    :color "bg-[#C8A841]"}
            :effectiveness {:double [fire electric poison rock steel]
                            :half [grass bug]
                            :none [flying]}}
    flying {:label {:text {:ja "ひこう"
                           :en "Flying"}
                    :color "bg-[#65A7F1]"}
            :effectiveness {:double [grass fighting bug]
                            :half [electric rock steel]
                            :none []}}
    psychic {:label {:text {:ja "エスパー"
                            :en "Psychic"}
                     :color "bg-[#EC7FF4]"}
             :effectiveness {:double [fighting poison]
                             :half [psychic steel]
                             :none [dark]}}
    bug {:label {:text {:ja "むし"
                        :en "Bug"}
                 :color "bg-[#52CB5A]"}
         :effectiveness {:double [grass psychic dark]
                         :half [fire fighting poison flying ghost steel fairy]
                         :none []}}
    rock {:label {:text {:ja "いわ"
                         :en "Rock"}
                  :color "bg-[#FAC727]"}
          :effectiveness {:double [fire ice flying bug]
                          :half [fighting ground steel]
                          :none []}}
    ghost {:label {:text {:ja "ゴースト"
                          :en "Ghost"}
                   :color "bg-[#756EB4]"}
           :effectiveness {:double [psychic ghost]
                           :half [dark]
                           :none [normal]}}
    dragon {:label {:text {:ja "ドラゴン"
                           :en "Dragon"}
                    :color "bg-[#FF885A]"}
            :effectiveness {:double [dragon]
                            :half [steel]
                            :none [fairy]}}
    dark {:label {:text {:ja "あく"
                         :en "Dark"}
                  :color "bg-[#6881D4]"}
          :effectiveness {:double [psychic ghost]
                          :half [fighting dark fairy]
                          :none []}}
    steel {:label {:text {:ja "はがね"
                          :en "Steel"}
                   :color "bg-[#818AA4]"}
           :effectiveness {:double [ice rock fairy]
                           :half [fire water electric steel]
                           :none []}}
    fairy {:label {:text {:ja "フェアリー"
                          :en "Fairy"}
                   :color "bg-[#FC7799]"}
           :effectiveness {:double [fighting dragon dark]
                           :half [fire poison steel]
                           :none []}})   :selected-poketype-list []
   })

