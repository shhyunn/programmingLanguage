(*소인수분해 함수*)
let div_list x =
  let rec div x d =
    if x < d then []
    else if x mod d = 0 then d :: (div (x / d) d) (*재귀 호출, 나누어 떨어지면 리스트에 추가, 나누는 수는 변함 없음*)
    else div x (d + 1) (*나누는 숫자 증가 *)
  in
  div x 2 (*2부터 소인수 분해 시작 *)

let rec pow x y =
        if y = 0 then 1
        else x * (pow x (y - 1))
        
let phi n =
        if n = 1 then 0
        else
                let lst = div_list n in
                if List.length lst = 1 then n - 1
                else 
                        let unique_lst = List.sort_uniq compare lst in
                        let filter_lst p = List.filter (fun x -> x = p) lst in
                        let unique_cnt p = List.length (filter_lst p) in
                        let cal_phi k p = k * (pow p ((unique_cnt p) - 1) * (p - 1)) in
                        List.fold_left cal_phi 1 unique_lst
        (*리스트에서 하나씩 뽑아서 원래 lst의 개수만큼 거듭제곱해서 k에다 곱해가는 코드로..*)

let _ =
        let _ = Format.printf "%d\n" (phi 4) in
        let _ = Format.printf "%d\n" (phi 9) in
        let _ = Format.printf "%d\n" (phi 10) in
        let _ = Format.printf "%d\n" (phi 17) in
        Format.printf "%d\n" (phi 30)


