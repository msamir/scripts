(ns fern
  (:import (javax.swing JFrame JLabel)
           (java.awt.image BufferedImage)
           (java.awt Dimension Color)))

(defn transform-one [[x y]]
  [0 (* 0.16 y)])

(defn transform-two [[x y]]
  [(- (* 0.2  x) (* 0.26 y)) 
   (+ (* 0.23 x) (* 0.22 y))])

(defn transform-three [[x y]]
  [(+ (* -0.15 x) (* 0.28 y))  
   (+ (* 0.26  x) (* 0.24 y) 0.44)])

(defn transform-four [[x y]]
  [(+ (* 0.85   x) (* 0.04 y))
   (+ (* -0.004 x) (* 0.85 y) 1.6)])

(defn transform 
  "Transform point accourding to the percentage."
  [target]
  (let  [percentage (rand-int 101)]
    (cond 
     (<= percentage 1) (transform-one target)
     (<= percentage 7) (transform-two target)
     (<= percentage 14) (transform-three target)
     (<= percentage 100) (transform-four target))))

(defn paint-point 
  "Paint point on canvas"
  [width height [x y] graphics]
  (let  [scale (int (/ height 11))
         y (- (- height 25) (* scale y)) 
         x (+ (/ width 2)   (* scale x))]
    (.drawLine graphics x y x y)))

(defn draw-fern [width height max-points graphics]
  (doseq [coord (take max-points (iterate transform [0 1]))]
    (paint-point width height coord graphics)))

(defn draw [width height points]
  (let [image  (BufferedImage. width height BufferedImage/TYPE_INT_RGB)
        canvas (proxy [JLabel] []
                 (paint [g]                       
                   (.drawImage g image 0 0 this)))
        graphics (.createGraphics image)]

    (.setColor graphics Color/green)
    (draw-fern width height points graphics)

    (doto (JFrame.)
      (.add canvas)
      (.setSize (Dimension. width height))
      (.show))))
(draw 800 600 100000)

