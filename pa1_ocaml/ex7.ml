let fold3 f a lst1 lst2 lst3 =
        let rec iter_fold a1 ls1 ls2 ls3 =
                match ls1, ls2, ls3 with
                | [], [], [] -> a1
                | var1 :: re_ls1, var2 :: re_ls2, var3 :: re_ls3 -> iter_fold (f a1 var1 var2 var3) re_ls1 re_ls2 re_ls3 
                | _, _, _ -> 0
        in
        iter_fold a lst1 lst2 lst3

let _ = 
        let _ = Format.printf "%d\n" (fold3 (fun a b c d -> a + b + c + d) 10 [33; 67; 12; 33] [10; 23; 84; 57] [11; 55; 23; 58]) in
        let _ = Format.printf "%d\n" (fold3 (fun a b c d -> (-a) + b + c + d) 4 [11; 63; -45; 22] [75; 123; -44; 1] [55; 24; 20; 3]) in
        let _ = Format.printf "%d\n" (fold3 (fun a b c d -> a * b * c * d) 55 [] [] []) in
        let _ = Format.printf "%d\n" (fold3 (fun a b c d -> (a * b * c + d) mod 7 ) 33 [12; 33] [10; 7] [5; 12]) in
        let _ = Format.printf "%d\n" (fold3 (fun a b c d -> if b then a + c else a + d) 34 [true; false; false; true] [12; 3; 4; 77] [11; 23; 6; 100]) in
        Format.printf "%d\n" (fold3 (fun a b c d -> if b then a else c + d) 34 [true; true; false; false; true] [111; 63; 88; 123; 98] [0; 23; 778; 34; 6])


                                
