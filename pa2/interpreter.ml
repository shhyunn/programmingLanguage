module F = Format

let rec interp_expr (e: Ast.expr) (g: FStore.t) (s: Store.t) : Value.t =
       match e with
       | Num n -> NumV n
       | Add (ex1, ex2) ->
                       let n1 = interp_expr ex1 g s in
                       let n2 = interp_expr ex2 g s in
                       (match n1, n2 with
                       | NumV n1, NumV n2 -> NumV(n1 + n2)
                       )

       | Sub (ex1, ex2) ->
                       let n1 = interp_expr ex1 g s in
                       let n2 = interp_expr ex2 g s in
                       (match n1, n2 with
                       | NumV n1, NumV n2 -> NumV(n1 - n2)
                       )
       | Id str ->
                       if (Store.mem str s) then Store.find str s
                       else failwith (F.sprintf "Free identifier: %s" str) 
       | LetIn (var, ex1, ex2) ->
                       interp_expr ex2 g (Store.add var (interp_expr ex1 g s) s)
                       
       | Call (f, exprs) -> (*exprs : 파라미터 변수*)
                       if (FStore.mem f g) then (*f 함수가 g에 있으면*)
                               let (p, e) = FStore.find f g in (*p는 함수 파라미터, e는 함수 body*)
                               let ex_lst = List.length exprs in
                               let p_lst = List.length p in
                               if ex_lst != p_lst then failwith (F.sprintf "The number of arguments of %s mismatched: Required: %d, Actual: %d" f p_lst ex_lst)
                               else
                                       let rec cal_expr exprs p s = (*exprs는 호출 시 넣어주는 값, p는 함수의 파라미터*)
                                              match exprs, p with
                                              | [], [] -> s (*그대로 s 리턴*)
                                              | ex1 :: rest_expr, p1 :: rest_p ->
                                                             cal_expr rest_expr rest_p (Store.add p1 (interp_expr ex1 g s) s)
                                              | _ -> s
                                       in
                                       interp_expr e g (cal_expr exprs p s)

                       else failwith (F.sprintf "Undefined function: %s" f) 


 (* Implement this function *)

let interp_fundef (d: Ast.fundef) (g: FStore.t) : FStore.t =
       match d with
       | FunDef (n, p, e) -> FStore.add n (p, e) g (*n : function name, p : params list, e : function body*) 
 (* Implement this function *)

let interp (p: Ast.prog) : Value.t =
        let fs = FStore.empty in
        let s = Store.empty in
        match p with
       | Prog (def_list, exprs) ->
                       let rec def_proc def_list fs =
                               match def_list with
                               | [] -> fs
                               | fst :: rest -> def_proc rest (interp_fundef fst fs)
        in
        interp_expr exprs (def_proc def_list fs) s
 (* Implement this function *)

