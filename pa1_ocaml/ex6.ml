let sigma (n1, n2, f) =
        if n1 > n2 then 0
        else
                let rec iter_fun num res =
                        if num > n2 then res
                        else iter_fun (num + 1) (res + f num)
                 in
                 iter_fun n1 0

let _ =
        let _ = Format.printf "%d\n" (sigma (10, 10, (fun x -> x))) in
        let _ = Format.printf "%d\n" (sigma (11, 10, (fun x -> x))) in
        let _ = Format.printf "%d\n" (sigma (10, 5, (fun x -> x))) in
        let _ = Format.printf "%d\n" (sigma (1, 10, (fun x -> if x mod 2 = 0 then 1 else 0))) in
        let _ = Format.printf "%d\n" (sigma (2, 10, (fun x -> x + 10))) in
        let _ = Format.printf "%d\n" (sigma (0, 100, (fun x -> 0))) in
        Format.printf "%d\n" (sigma (10, 12, (fun x -> 2 * x)))
