;;
;; This homework is to use uscheme to implement some higher functions 
;;

(define make-formula-true (f fail success)
  (letrec
      ((make-all (lambda (f bool cur fail success) 
       (if (null? f)
          (success cur fail)
          (make-formula (car f) bool cur fail
            (lambda (cur resume)
             (make-all (cdr f) bool cur resume success))))))

      (make-any (lambda (f bool cur fail success) 
       (if (null? f)
           (fail)
           (make-formula (car f) bool cur
            (lambda() (make-any (cdr f) bool cur fail success))
            success))))

      ;;(variable-of (literal)
       ;; (if (symbol? literal)
         ;; literal
          ;;(cadr literal)))

      ;;(binds? (literal alist bool)
        ;;(find-c (variable-of literal) alist (lambda (_) #t) (lambda () #f)))

      ;;(satisfies? (f alist bool)
        ;;(find-c (variable-of f) alist
          ;;  (lambda (b) (= b bool))
            ;;(lambda () #f)))

      ;;(make-f-true (lit bool cur fail success)
        ;;(if (satisfies? lit cur bool)
          ;;  (success cur fail)
            ;;(if (binds? lit cur bool)
              ;;  (fail)
                ;;(success (bind (variable-of lit) bool cur) fail ))))

      (make-formula (lambda (f bool cur fail success)
       ;; (if (symbol? make-f-true (f bool cur fail success))            ;;symbol
       ;;(if (symbol? (make-f-true f bool cur fail success)) 
        (if (symbol? f)
        (if (null? (find f cur))
          (success (bind f bool cur) fail)
          (if (equal? (find f cur) bool)
              (success cur fail)
             (fail)))
        (if (equal? (car f) 'and)   ;;and
          (if (equal? bool #t)
              (make-all (cdr f) bool cur fail success)
              (make-any (cdr f) bool cur fail success))
        (if (equal? (car f) 'or)    ;;or
              (if (equal? bool #t)
                (make-any (cdr f) bool cur fail success)
                (make-all (cdr f) bool cur fail success))
              (if (equal? (car f) 'not)  ;;not
                (make-formula (cadr f) (not bool) cur fail success)
               '(error)
                 )))))))
       (make-formula f #t '() fail success)))
            
(define one-solution (f)
  (make-formula-true f (lambda () 'no-solution)
         (lambda (cur resume) cur)))

(define all-solutions (f)
  (make-formula-true f (lambda () '())
         (lambda (cur resume) (cons cur (resume)))))



;;test case for problem 23
(val f1 '(and (or x y z) (or (not x) (not y) (not z)) (or x y (not z))))
(val f2 '(and (or x (not y)) z))
(val f3 '(and (or (not x) y z) (or x y (not z) (and x (not y) (not z)))))


(check-expect (one-solution f1) '((x #t) (y #f)))
(check-expect (one-solution f2) '((x #t) (z #t)))
(check-expect (one-solution f3) '((x #f) (y #t)))
(check-expect (all-solutions f1) '(((x #t) (y #f)) ((x #t) (y #f) (z #f)) ((x #t) (z #f)) ((x #t) (z #f) (y #t)) ((x #t) (z #f)) ((y #t) (x #f)) ((y #t) (x #f) (z #f)) ((y #t) (z #f) (x #t)) ((y #t) (z #f)) ((y #t) (z #f)) ((z #t) (x #f) (y #t)) ((z #t) (y #f) (x #t))))
(check-expect (all-solutions f2) '(((x #t) (z #t)) ((y #f) (z #t))))
(check-expect (all-solutions f3) '(((x #f) (y #t)) ((x #f) (z #f)) ((y #t) (x #t)) ((y #t)) ((y #t) (z #f)) ((z #t) (x #t)) ((z #t) (y #t))))
