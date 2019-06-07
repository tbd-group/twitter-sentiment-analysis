(defproject twitter-sentiment-analysis "0.1.0"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "MIT"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.antlr/antlr4 "4.7.2"]
                 [org.projectlombok/lombok "1.18.8" :scope "provided"]
                 [com.google.code.findbugs/annotations "3.0.1" :scope "provided"]
                 [com.google.code.findbugs/jsr305 "3.0.1" :scope "provided"]]
  ;; :repl-options {:init-ns twitter-sentiment-analysis}
  ;; :source-paths ["src/clj" "target/classes"]
  :java-source-paths ["src/java"]
  :plugins [[lein-exec "0.3.7"]])
