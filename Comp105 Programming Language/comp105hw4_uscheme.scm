;;
;; This homework is to use uscheme to implement some higher functions 
;;

;;
;;Problem 10.(b)
;;

(define max* (xs)
   (foldl max (car xs) xs))

;;use foldl to compare each two element from left to right
(check-expect(max* '(1 1 2 3))3) ;;check the case when numbers are different
(check-expect(max* '(1 1))1) ;;check the case when numbers are the same
(check-expect(max* '(-1 0 1))1) ;;check the case when there's negative number and 0

;;
;;Problem 10.(c)
;;

(define gcd* (xs)
   (foldl gcd (car xs) xs))

;;use foldl to find gcd for each two element from left to right
(check-expect(gcd* '(4 8 16))4) ;;check the case when gcd is in the list
(check-expect(gcd* '(4 8 15))1) ;;check the case when gcd is 1
(check-expect(gcd* '(4 6 8))2) ;;check the case then gcd is not 1 and not in the list

;;
;;Problem 10.(d)
;;

(define lcm* (xs)
   (foldl lcm (car xs) xs))

;;use foldl to find lcm for each two element from left to right
(check-expect(lcm* '(2 4))4) ;;check the case when lcm is in the list
(check-expect(lcm* '(2 19))38) ;;check the case when lcm is the result of multiply
(check-expect(lcm* '(2 4 19))76) ;;check the case when there exist to element that the gcd is not 1

;;
;;Problem 10.(e)
;;

(define sum (xs)
   (foldl + 0 xs))

;;use foldl to add element from left to right, start with 0
(check-expect(sum '(1 3 4 8 100))116) ;;check the sum of random number

;;
;;Problem 10.(f)
;;

(define product (xs)
   (foldl * 1 xs))

;;use foldl to multiply element from left to right, start with 1
(check-expect(product '(1 2 3 4))24) ;;check the product of random number

;;
;;Problem 10.(g)
;;

(define append (s1 s2)
   (foldr cons s2 s1))

;;use foldr to add element from s1 to s2 from right to left
(check-expect(append '(1 1 2 9) '(8 3 10 1))'(1 1 2 9 8 3 10 1)) 
;;check the append of random two lists

;;
;;Problem 10.(i)
;;

(define reverse (s1)
   (foldl cons '() s1))

;;use foldl to add the first element to the last digit
(check-expect(reverse '(1 2 3 10))'(10 3 2 1))
;;check the reverse of random list

;;
;;Problem 10.(j)
;;


(define insertion-sort (xs)
   (let*
      ((insert ( lambda (y ys)
          (if (null? ys)
              (list1 y)
              (if (< y (car ys))
                  (cons y ys)
                  (cons (car ys) (insert y (cdr ys))))))))
      (foldl insert '() xs)))

;;define function
;;use lambda to define the insert function
;;use foldl and let to insert the element one by one


;;
;;Problem 11
;;implemet length

(define length (xs)
    (if (null? xs) 0
    (foldl (lambda(a x) (+ 1 x)) 0 xs)))

;;add 1 each time with each element by foldl
(check-expect(length '(1 2 4))3) ;;check random length

;;
;;Problem 11
;;implement map

(define map (f xs)
  (foldr (lambda (a ys) (cons (f a) ys)) '() xs))

;;implement map by adding each element every time using foldr 
(check-expect(map number? '(0 #f))'(#t #f))
;;check the case that determine if it is number
(check-expect(map (lambda(x) (+ x 1)) '(0 1 2))'(1 2 3))
;;check the case when map add 1 function

;;
;;Problem 11
;;implement filter

(define filter (f xs)
   (foldr (lambda (y ys)
          (if (f y)
            (cons y ys)
             ys)) '() xs))

;;define function
;;check element one by one
;;if it's in f, add the element
;;if not, skip

(check-expect(filter number? '(1 3 #f #t a b))'(1 3))
;;check the case when there's different typs
(check-expect(filter (lambda(x) (> x 0)) '(2 4 5 0 -2 4))'(2 4 5 4))
;;check the case when decide if x is greater then 0

;;
;;Problem 11
;;implement exists?

(define exists? (f xs)
   (let*
      ((result (foldr (lambda (a ys) 
          (if (f a) 
             (cons #t (cdr ys))
              ;;#t
              ys)) '(#f) xs)))
      (car result)))

;;define function
;;expect the result of #t or #f, store the result in car result
;;if (f a), the first is #t, then exit

(check-expect(exists? number? '(a 9 2 #f)) #t);; check the case when there exists numebr
(check-expect(exists? number? '(a b c #f)) #f);; check the case when there does not exists number

;;
;;Problem 11
;;implement all?

(define all? (f xs)
  (if (null? xs) #f
    (let*
      ((result (foldr (lambda (a ys)
          (if (not (f a))
            ;;#f
          (cons #f (cdr ys))
          ys)) '(#t) xs)))
      (car result))))

;;similar with exists, the result is stored in result
;;if not (f a), then the first element is set to #f
;;check the first element to see if all satisfy

(check-expect(all? number? '(a 9 2 #f)) #f);;check the case when it's not all number
(check-expect(all? number? '(0 9 2)) #t) ;;check the case when the result is true

;;
;;Problem 16
;;(a) rewrite add-element

(val emptyset (lambda (x) #f))
(define member? (x s) (s x))
(define add-element(x s)
  (if (s x) 
    s 
    (lambda (y) 
      (if (equal? x y) #t
                       (s y)))))
;;if s is a member of x, return s
;;if not, if x is equal to the new element, return #t
;;else, return (s y)

;;rewrite union, x should be member in either s1 or s2
(define union (s1 s2)
    (lambda (x) (or (s1 x) (s2 x))))

;;rewrite inter, x should be member in both s1 and s2
(define inter (s1 s2)
    (lambda (x) (and (s1 x) (s2 x))))

;;rewrite diff, x should be only in s1, not in s2
(define diff (s1 s2)
    (lambda (x) (if (s2 x) #f (s1 x))))

(check-expect((add-element 2 emptyset) 2) #t);check emptyset with the element
(check-expect((add-element 2 emptyset) 3) #f);check emptyset without the element
(check-expect (member? 1 (union emptyset number?)) #t) ;; check union
(check-expect (member? 1 (inter emptyset number?)) #f) ;; check inter
(check-expect (member? 1 (diff number? number?)) #f) ;; check diff
;;
;;Problem 16 
;;(b) combine the functions using polymorphism in piazza

(define mk-set-ops (eqfun)
  (let*
    ((emptyset (lambda (x) #f))
      (member? (lambda(x s) (s x))))
  (list6
    (lambda (x s)(if (s x) s 
                (lambda (y) 
                (if (equal? x y) #t
                                (s y)))));;add-element
    (lambda (s1 s2) (lambda (x) (or (s1 x) (s2 x)))) ;;inter
    (lambda (s1 s2) (lambda (x) (and (s1 x) (s2 x)))) ;;union
    (lambda (s1 s2) (lambda (x) (if (s2 x) #f (s1 x)))) ;;diff
    )))
;;I refer this according to Professor's post on piazza.
;;using list to create different conditions to get the 
;;equality function into a closure
;;to test all the case one more time
(check-expect((add-element 2 emptyset) 2) #t);check emptyset with the element
(check-expect((add-element 2 emptyset) 3) #f);check emptyset without the element
(check-expect (member? 1 (union emptyset number?)) #t) ;; check union
(check-expect (member? 1 (inter emptyset number?)) #f) ;; check inter
(check-expect (member? 1 (diff number? number?)) #f) ;; check diff


;;
;;Problem 23
;;

;;make-all is similar to make-cnf-true
;;make-any is similar to make-disjunction-true
;;we divide into four cases: symbol, not, and, or
;;if symbol, we should use make-f-true, which is similar to make-literal-true
;;if not, we should set one of the value to #f
;;if and, if true, make all, else, make any
;;or is the opposite of and

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



;;
;;Problem A
;;Good functional style
;;we want to use letrec instead of while to define recursive functions


(define f-functional(y)
  (letrec
      ((x e)
        (while_f
          (lambda(x) (if (p x y) x
          (while_f (g x y))))))
      (h (while_f x) y)))

;;in the beginning, we set the value of x to be e
;;while (p x y), we set x to be (g x y)
;;in the last step, we evaluate (h x y), which is (h (while_f x) y)


;;
;;Quicksort
;;

(define qsort (op)
   (lambda (xs)
     (if( <= (length xs) 1) xs
        (let* 
          ((partitionLeft
            (lambda (op zs)
              (if(op 0 1) (filter ((curry >) (car zs)) zs)
                 (filter ((curry <) (car zs)) zs))))
           (partitionRight
            (lambda (op zs) 
              (if(op 0 1) (filter ((curry <) (car zs)) zs)
                (filter ((curry >) (car zs)) zs)))) 
           (left (partitionLeft op xs)) 
           (right (partitionRight op xs)))
          (append ((qsort op) left) (cons (car xs) ((qsort op) right)))))))
;;op stands for operator
;;the idea of quicksort is to divide the list to two parts
;;use filter to select the most smaller one to the left
;;and the larger one to the right
;;continue sorting one by one using let*

(check-expect((qsort <) '(2 4 1 3 5)) '(1 2 3 4 5));;check the qsort of acsending list
(check-expect((qsort >) '(2 4 1 3 5 10)) '(10 5 4 3 2 1)) ;;check the qsort of decsending list

