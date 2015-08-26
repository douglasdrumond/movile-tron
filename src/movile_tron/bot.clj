(ns movile-tron.bot
  (:use [movile-tron.core :only [battle]])) ; :reload))


;(def state (:pos [12 56]))


(defn move-up [x y] [x (dec y)])
(defn move-right [x y] [(inc x)  y])
(defn move-down [x y] [x  (inc y)])
(defn move-left [x y] [(dec x)  y])

(defn count-neighbours [bot-name x y arena]
  (let [positions [
                   ; upper line
                   [(dec x) (dec y)]
                   [ x (dec y)]
                   [(inc x) (dec y)]

                   ; current line
                   [(dec x)  y]
                   [(inc x)  y]

                   ; lower line
                   [(dec x) (inc y)]
                   [ x (inc y)]
                   [(inc x) (inc y)]
                   ]
        ]
      (reduce (fn [acc val] (if (= (get arena val) bot-name)
                              (inc acc)
                              acc
                              ))
              0 positions)
  )
)

(defn choose-neighbour [bot-name x y arena]
  (let [
          upcount (count-neighbours bot-name x (dec y) arena)
          leftcount (count-neighbours bot-name  (dec x) y arena)
          rightcount (count-neighbours bot-name (inc x) y arena)
          downcount (count-neighbours bot-name x (inc y) arena)

          counts [upcount leftcount rightcount downcount]

          positions [
                     [x (dec y)]
                     [(dec x) y]
                     [(inc x) y]
                     [x (inc y)]
                     ]

          okpositions (filter (fn [[x y]] (not (contains? arena [x y]) )) positions)
          okcounts (map (fn [[xx yy]] (count-neighbours bot-name xx yy arena)) okpositions )

      ]
    (get okpositions (.indexOf counts (apply max okcounts)))
  ( println okpositions)
    )
  )

; {[x y] :wall, [x y] :wall, [x y] :bot}

(defn badass-bot
  [{[x y] :pos} arena]
    {:pos (choose-neighbour :badass x y arena)}
    )

; {:pos  [x y]}

(defn -main []
(battle {:badass badass-bot}))
