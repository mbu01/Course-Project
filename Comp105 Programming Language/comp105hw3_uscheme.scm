;;
;; This homework is to use uscheme to implement basic functions 
;;

;;
;;Problem 2(a)
;;this function (count x xs) returns the number of (top-level) elements
;;of xs that are equal to x.

(define count(x xs)
  (if (null? xs) 0 
    (if (atom? (car xs))
     (if (equal? (car xs) x)
         (+ 1 (count x (cdr xs)))
         (+ 0 (count x (cdr xs))))
     (+ 0 (count x (cdr xs))))))
     
;;define function
;;if xs is null, the initial value of count is 0
;;check if x is the same with the first digit of xs
;;if same, count+1
;;if not, count remain the same and check count(x (cdr xs))

(check-expect(count 'a '(1 b a (c a)))1) ;;test the case with all level
(check-expect(count 'a '(1 b a a a))3) ;;test the case with multiple a
(check-expect(count '$ '(1 b a a a))0) ;;test the case with 0 symbol
(check-expect(count 'a '(1 b a (c a) (b a a)))1) ;;test the case with multiple lists

;;Problem 2(b)
;;in this function, function (countall x xs) will return the number of times
;;x occurs wherever in xs
;;it's like the combination of 2a and 2d

(define countall(x xs)
   (if (null? xs) 0 
     (if (pair? (car xs))
         (+ (countall x (cdr xs))(countall x (car xs)))
         (if (equal? x (car xs))
              (+ 1 (countall x (cdr xs)))
              (+ 0 (countall x (cdr xs)))))))
;;define function
;;if xs is null, the result is null
;;check if (car xs) is pair
;;if it's pair, we need to apply function like 2d to make it into flatten lists
;;if it's not pair, check if it's equal to x
;;if it's the same as x, add 1
;;if not, do not change countall

(check-expect(countall 'a '(1 b a (c a)))2) ;;test the case when there are multiple 
;;x in different level
(check-expect(countall 'a '(1 b a a a a))4) ;;test the case when x in one level
(check-expect(countall 'a '(1 b a a a ()))3) ;;test the case when there exist null list

;;Problem 2(c)
;;this function (mirror xs) returns a list 
;;in which every list in xs is recursively mirrored
;;the solution is similar to 2b

(define mirror(xs)
  (if (null? xs) '()
     (if (pair? (car xs))
       (append (mirror (cdr xs)) (list1(mirror (car xs))))
       (append (mirror (cdr xs)) (list1 (car xs))))))

;;define function
;;if xs is null, the result is null
;;check if car(xs) is pair
;;if car(xs) is pair, then we need to recursively check and reverse
;;if not, reverse and put car(cs) in to the last digit

(check-expect(mirror '(1 2 3 4 5)) '(5 4 3 2 1));;test when there is a simple list that has one level
(check-expect(mirror '((a (b 5))(c d)e)) '(e (d c)((5 b) a)))
;;test the case when there are multiple levels
(check-expect(mirror '((a b)())) '(()(b a))) ;;test the case when there is empty list

;;Problem 2(d)
;;this function (flatten xs) constructs a list having the same atom as xs
;;in the same order

(define flatten (xs)
  (if (null? xs) '()
     (if (pair? xs)
       (append (flatten(car xs)) (flatten (cdr xs)))
       (list1 xs))))

;;define function
;;if xs is null, the result is null
;;check if xs is pair
;;if x is pair, then add (car xs)
;;if x is not pair, make it a list

(check-expect(flatten '((I Ching)(U Thant)(E Coli)))'(I Ching U Thant E Coli))
;;test the case when xs contains different lists
(check-expect(flatten '(((((a))))))'(a));;test the case when xs contains multiple lsits
(check-expect(flatten '((a b)((c d)e)))'(a b c d e));;test the case when there are different levels
(check-expect(flatten '(()))'());;test the case when the list contains only null list

;;Problem 2(e)
;;this function (contig-sublist? xs ys) determines whether the list xs is a contiguous subsequence
;;of the list ys, it's a boolean function, returns #t/#f
;;we need a helper function

;;helper function, after find the first element that are equal
;;check if all the element in xs is contains in ys in the same contiguous order
(define contig-same? (xs ys)
  (if(null? xs) #t
    (if (equal? (car xs)(car ys))
        (contig-same? (cdr xs)(cdr ys))
        #f)))
        
;;define helper function
;;if xs is null, true
;;check if (car ys) (car xs) is equal
;;if equal, continuous to see the next one
;;if not equal, return false

(define contig-sublist? (xs ys) 
  (if (equal? (car xs) (car ys))
      (contig-same? xs ys)
      (contig-sublist? xs (cdr ys))))
      
;;define function
;;try to find the first element in ys that is same with the first element in xs
;;if equal, then jump to helper function to see if all xs is in ys continiously
;;if not, continuous check (cdr ys)

(check-expect(contig-sublist? '(a b z) '(x a y b z c))#f);;check the case when contain a, b, c but not in coniguous order
(check-expect(contig-sublist? '(a y b) '(x a y b z c))#t);;check the case when contains contiguous lists
(check-expect(contig-sublist? '(x) '(x a y b z c))#t);;check the case when it's contained in ys

;;Problem 2(f)
;;this function is similar with 2e, but returns #t when the list ys contains the elements of xs 
;;in the same order but not required to be contiguous

(define sublist? (xs ys) 
    (if (null? xs) #t
        (if (null? ys) #f
            (if (equal? (car xs) (car ys))
                (sublist? (cdr xs) (cdr ys))
                (sublist? xs (cdr ys))))))
  
;;define function
;;if xs is null, then it's true
;;if ys is null, then it's false
;;check if (car xs) and (car ys) are the same
;;if same, continious to check the next two digit
;;if not, contiinious to find the element in ys that is same with that element in xs

(check-expect(sublist? '(a b c) '(x a y b z c))#t) ;;check the case when it contains a,b,c but not in coniguous order
(check-expect(sublist? '(a y b) '(x a y b z c))#t);;check the case when contains contiguous lists
(check-expect(sublist? '(a z b) '(x a y b z c))#f);;check the case when it does not contain contiguous lists

;;problem 6
;;the takewhile function returns the longest prefix of the list in which every element satisfies the predicate
;;the dropwhile removes the longest prefix and returns whatever is left over
;;p stands for predicate 

(define takewhile(p? xs)
  (if (null? xs) '()
    (if (p? (car xs))
      (cons (car xs) (takewhile p? (cdr xs)))
      '())))

;;define function
;;if xs is null, return null
;;check if car xs satisfy p
;;if yes, then construct car xs and continuous check the next digit
;;if not, then return '()

(check-expect (takewhile null? '(2 4 6 7 8 10 12))'());;test the case when the result should be null
(check-expect (takewhile number? '(2 4 6 7 8 10 12 #t))'(2 4 6 7 8 10 12));;test the case when part of the result is true

(define dropwhile(p? xs)
  (if (null? xs) '()
     (if(p? (car xs))
       (dropwhile p? (cdr xs))
       (append xs '()))))

;;define function
;;if xs is null, return null
;;check if car xs satisfy p
;;if yes, then continue to find the one that does not satisfy
;;if not, then return the left xs


(check-expect (dropwhile null? '(2 4 6 7 8 10 12))'(2 4 6 7 8 10 12))
;;test the case when all result should be included
(check-expect(dropwhile number? '(2 4 6 7 8 10 12 #t))'(#t))
;;test the case when part of the result is true

;;problem15
;;(define vector-length (x y)
;;   (let ((+ *)
;;         (* +))
;;     (sqrt (* (+ x x) (+ y y)))))
;;
;;This function calculate the square root of x^2+y^2
;;The function let changes the meaning of '+' to '*', and changes the meaning of '*' to '+'
;;Thus, by implementing the new rule, the (sqrt (* (+ x x) (+ y y))) is changed to 
;;(sqrt (+ (* x x) (* y y))), which is the third side of the Pythagorean theorem

;;A
;;function (take n xs) returns the longest prefix of xs that contains at most n elements
;;function (drop n xs) removes those in (take n xs)

(define take (n xs)
  (if (> n (length xs)) xs
   (if (!= n 0)
      (if (null? xs) '()
          (cons (car xs)(take (- n 1) (cdr xs))))
    '())))

;;define function
;;if the n is larger than the length of xs, return xs
;;check if n= 0, then stop
;;check if xs is null, if null, add '()
;;if not null, then cons the car xs until n equals to 0
;;if n is 0, then add '()

(check-expect(take 3 '(a b c d e f))'(a b c));;check the case when take random number
(check-expect(take 10 '(a b c d e f))'(a b c d e f));;check the case when n is larger than the length of xs, return xs
(check-expect(take 0 '(a b c d e f))'());;check the case when return null

(define drop (n xs)
  (if (> n (length xs)) '()
    (if (!= n 0)
      (if (null? xs) '()
          (drop (- n 1)(cdr xs)))
      (append xs '()))))
;;define function
;;if n> length of xs, then return null
;;if n not equal to 0
;;if xs is null, '()
;;if not, continue to the n-1 one
;;construct the one after n

(check-expect(drop 3 '(a b c d e f))'(d e f));;check the case when take random number
(check-expect(drop 10 '(a b c d e f))'());;check the case when n is larger than the length of xs, return xs
(check-expect(drop 0 '(a b c d e f))'(a b c d e f));;check the case when return null

;;B
;;function zip converts a pair of lists to an association list
;;unzip converts an association list to a pair of lists
(define zip (xs ys) 
    (if (null? xs) '()
        (cons (list2 (car xs) (car ys)) (zip (cdr xs) (cdr ys)))))

;;define function
;;if xs is null, '()
;;if not, cons the car xs and car ys to one list and continue

(check-expect(zip '(1 2 3)'(a b c))'((1 a) (2 b) (3 c)));;check the case when there are three keys
(check-expect(zip '(1)'(a))'((1 a)));;check the case when there is only one pair

;;we need two helper function for unzip
;;rebuilt-key(xs) rebuilts the first half of xs
;;rebuilt-attribute(xs) rebuilts the second half of xs

(define rebuilt-key(xs) 
    (if(null? xs)'()
        (cons (caar xs) (rebuilt-key(cdr xs)))))

;;rebuilt first half by adding each key 

(define rebuild-attribute(xs)
    (if(null? xs)
        '()
        (cons (cadar xs) (rebuild-attribute(cdr xs)))))
;;rebuilt second half by adding each attribute

(define unzip (xs) 
    (if (null? xs) '()
        (list2 (rebuilt-key xs) (rebuild-attribute xs))))

(check-expect(unzip '((1 a) (2 b) (3 c)))'((1 2 3) (a b c)));;check the case when there are three pairs
(check-expect(unzip '((1 a)))'((1) (a)));;check the case when there is only one pair

;;C
;;the function arg-max has a function f that maps a value in set A to another list
;;the other function check which return value is the largest

 (define arg-max (f a) 
    (if (null? a) 1
        (if (> (f(car a)) (f(arg-max f (cdr a))))
            (car a)
            (arg-max f (cdr a)))))

;;define function
;;if a is null, return 1...
;;restore the result that is the largest

(check-expect(arg-max (lambda (x) (+ 1 x)) '(5 4 3 2))5);;check add function
(check-expect(arg-max (lambda (a) (* a  a)) '(5 4 3 2 1))5);;check multiply function
(check-expect(arg-max (lambda (a) (/ 1000  a)) '(5 4 3 2 1))1);;check divide function

;;D

;;E
;;this function merge expect two lists xs and ys and return a list taken together
(define merge(xs ys) 
    (if(null? xs) ys
        (if(null? ys) xs
            (if( < (car xs) (car ys))
                (cons (car xs) (merge (cdr xs) ys))
                (cons (car ys) (merge (cdr ys) xs))))))

;;define function
;;if xs is null, add ys
;;if ys is null, add xs
;;else, if car xs is larger then car ys, add car xs and vice versa

(check-expect(merge '(1 2 3) '(4 5 6))'(1 2 3 4 5 6));;check the case when its in ascend order
(check-expect(merge '(1 2 7) '(4 5 6))'(1 2 4 5 6 7));;check the case in not randomly order
(check-expect(merge '(1 2 7) '(4 5 6 9 11))'(1 2 4 5 6 7 9 11));;check the case when xs,ys is not in same length
(check-expect(merge '() '(4 5 6 9 11))'(4 5 6 9 11));;check the case when there is a empty list
(check-expect(merge '() '())'());;check the case when there are two empty list

;;F
;;the function interleave implement two lists together in ascend order
(define interleave (xs ys) 
  (if(null? xs) ys
    (if(null? ys) xs
      (cons (car xs) (interleave ys (cdr xs))))))

;;define function
;;if xs is null, then add ys
;;if ys is null, then add xs
;;construct with the smaller one 

(check-expect(interleave '(1 2 3) '(a b c))'(1 a 2 b 3 c));;check the case when xs, ys has the same length
(check-expect(interleave '(1 2 3) '(a b c d e f))'(1 a 2 b 3 c d e f));;check the case when length(ys)>length(xs)
(check-expect(interleave '(1 2 3 4 5 6) '(a b c))'(1 a 2 b 3 c 4 5 6));;check the case when length(xs)>length(ys)
(check-expect(interleave '(1 2 3 4 5 6) '())'(1 2 3 4 5 6));;check the case when there is empty list






