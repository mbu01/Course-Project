;;
;; This homework is to use uscheme to implement higher functions concerning pattern 
;; matching
;;

(*
 Problem A.(1)
fun compound applies the operator to x N times
 *)

fun compound _     0 x = x
  | compound binop n x = binop (x, compound binop (n - 1) x);


(* 
Problem A.(2)
fun exp define exponential operation
 *)

fun exp _ 0 = 1
  | exp a b = compound op * (b - 1) a;


(* 
Problem B.(1)
fun myrev reverse the list
 *)

fun myrev xs = foldl op :: nil xs;


(*
Problem B.(2)
fun minlist return the smallest in a non-empty list 
*) 

fun minlist (x :: xs) = foldl Int.min x xs
  | minlist _         = 0;

(*
Problem C 
implement myfoldl and myfoldr
*)

fun myfoldl _ zero        [] = zero
  | myfoldl f zero (x :: xs) = myfoldl f (f (x, zero)) xs;

fun myfoldr _ zero        [] = zero
  | myfoldr f zero (x :: xs) = f (x, myfoldr f zero xs);

(*
Problem D
fun flatten produce a new list
*)

fun flatten xs = List.foldr op @ [] xs;

(*
Problem E
fun mynth return the nth element
*)

exception IndexOutOfBounds

fun nth 0 (x :: xs) = x
  | nth n (_ :: xs) = nth (n-1) xs
  | nth _ []        = raise IndexOutOfBounds;

(*
Problem F
fun pairfoldr applies a three-argument function to a pair of lists of equal length 
*)


fun	pairfoldr f cero (nil, _) = cero
  |	pairfoldr f cero (_, nil) = cero
  |	pairfoldr f cero (x :: xs, y :: ys)	= f (x, y, pairfoldr f cero (xs, ys));


(* 
Problem G.(1)
fun evalexp computes the value of the expression by applying the operators.
*)

datatype Exp = VAL of int 
          | BINARY of Op * Exp * Exp 
          | UNARY of Op * Exp
          and Op = PLUS | MINUS | TIMES | DIVIDE;
          
exception Mismatch

fun	evalexp	(VAL(x)) = x
  |	evalexp	(BINARY(PLUS, exp1, exp2))   = (evalexp exp1) + (evalexp exp2)
  |	evalexp	(BINARY(MINUS, exp1, exp2))  = (evalexp exp1) - (evalexp exp2)
  |	evalexp	(BINARY(TIMES, exp1, exp2))  = (evalexp exp1) * (evalexp exp2)
  |	evalexp	(BINARY(DIVIDE, exp1, exp2)) = (evalexp exp1) div (evalexp exp2)
  |	evalexp	(UNARY(PLUS, exp))           = evalexp exp
  |	evalexp	(UNARY(MINUS, exp))          = (~1) * (evalexp exp)
  |	evalexp	_                            = raise Mismatch;


(*
Problem G.(2)
fun checkexp checks to make sure that TIMES and DIVIDE are never used as unary operators
*)

fun	checkexp	(VAL(x))                 = true
  |	checkexp	(BINARY(Op, exp1, exp2)) = (checkexp exp1) andalso (checkexp exp2)
  |	checkexp	(UNARY(Op, exp))         = ((Op = PLUS) orelse (Op = MINUS)) andalso (checkexp exp);

 
(* 
 Problem G.(3)
 fun toscheme converts the expression tree into a Scheme expression
*)

exception InvalidOperator
fun	toscheme	(VAL(x)) = Int.toString(x)
  |	toscheme	(BINARY(PLUS, exp1, exp2))   = "( " ^ " + " ^ " " ^(toscheme exp1) ^ " " ^ (toscheme exp2) ^ ")"
  |	toscheme	(BINARY(MINUS, exp1, exp2))  = "( " ^ " - " ^ " " ^(toscheme exp1) ^ " " ^ (toscheme exp2) ^ ")"
  |	toscheme	(BINARY(TIMES, exp1, exp2))  = "( " ^ " * " ^ " " ^(toscheme exp1) ^ " " ^ (toscheme exp2) ^ ")"
  |	toscheme	(BINARY(DIVIDE, exp1, exp2)) = "( " ^ " / " ^ " " ^(toscheme exp1) ^ " " ^ (toscheme exp2) ^ ")"
  |	toscheme	(UNARY(PLUS, exp))           = (toscheme exp)
  |	toscheme	(UNARY(MINUS, exp))          = "( " ^ " - " ^ " " ^ " 0 " ^ " " ^ (toscheme exp) ^ ")"
  |	toscheme	_                            = raise InvalidOperator;

(*
Problem G.(4)
fun toml converts the expression tree into an ML expression
*)


exception InvalidOperator
fun	toml	(VAL(x)) = Int.toString(x)
  |	toml	(BINARY(PLUS, exp1, exp2))  = (toml exp1) ^ " + " ^ (toml exp2) 
  |	toml	(BINARY(MINUS, exp1, exp2)) = (toml exp1) ^ " - " ^ (toml exp2)
  |	toml	(BINARY(TIMES, (BINARY(Op1,exp1,exp2)), (BINARY(Op2,exp3,exp4))))
            = "(" ^ (toml (BINARY(Op1,exp1,exp2))) ^ " ) " ^ " * " ^ " ( " ^ (toml (BINARY(Op2,exp3,exp4))) ^ ")"
  |	toml	(BINARY(DIVIDE, (BINARY(Op1,exp1,exp2)), (BINARY(Op2,exp3,exp4))))
            = "(" ^ (toml (BINARY(Op1,exp1,exp2))) ^ " ) " ^ " / " ^ " ( " ^ (toml (BINARY(Op2,exp3,exp4))) ^ ")"
  |	toml	(BINARY(TIMES, (BINARY(Op1,exp1,exp2)), exp3))
            = "(" ^ (toml (BINARY(Op1,exp1,exp2))) ^ " ) " ^ " * " ^ (toml exp3)
  |	toml	(BINARY(DIVIDE, (BINARY(Op1,exp1,exp2)), exp3))
            = "(" ^ (toml (BINARY(Op1,exp1,exp2))) ^ " ) " ^ " / " ^ (toml exp3)
  |	toml	(BINARY(TIMES, exp1, (BINARY(Op1,exp2,exp3))))
            = (toml exp1)^ " * " ^ "(" ^ (toml (BINARY(Op1,exp2,exp3)))^ " ) " 
  |	toml	(BINARY(DIVIDE, exp1, (BINARY(Op1,exp2,exp3))))
            = (toml exp1)^ " / " ^ "(" ^ (toml (BINARY(Op1,exp2,exp3)))^ " ) " 
  |	toml	(UNARY(PLUS, exp))   = toml exp
  |	toml	(UNARY(MINUS, exp)) = "( " ^ " - " ^ (toml exp) ^ ")"
  |	toml	_  = raise InvalidOperator;

