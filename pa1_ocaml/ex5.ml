let goldbach_list_limit a b c =
        (*소수인지 확인*)
        let is_prime n =
                let rec iter_div div_num =
                        if div_num * div_num > n then true
                        else if n mod div_num = 0 then false
                        else iter_div (div_num + 1)
                in 
                n > 1 && iter_div 2 
        in
        (*경계 내의 소수 구하는 함수*)
        let find_prime min max = 
                let rec add_prime var prime_lst = 
                        if var > max then List.rev prime_lst
                        else if (is_prime var) then add_prime (var + 1) (var :: prime_lst)
                        else add_prime (var + 1) prime_lst 
                in
                add_prime min [] 
        in
        let add_tuple even primes res_list= 
                let rec find_pair primes =
                        match primes with
                        | [] -> res_list
                        | p :: ps ->
                                        let other = even - p in
                                        if other >= p && List.mem other primes then 
                                                if p >= c then (even, (p, other)) :: res_list
                                                else res_list
                                        else find_pair ps 
               in
               find_pair primes 
        in

        (*리스트에서 소수 뽑아다가 각 짝수에서 소수 빼고 남은 것도 소수인지 확인->소수일시 즉시 res에 튜플 형태로 추가 후 종료*)
        let rec calculate_even even res_list =
                if even > b then res_list
                else
                        let primes = find_prime 2 b in
                        let res_list = add_tuple even primes res_list in
                        calculate_even (even + 2) res_list 
        in
        let to_even n =
                if n mod 2 = 1 then n + 1
                else n 
        in

        List.rev (calculate_even (to_even a) [])

let rec print_list ls = 
        match ls with
        | [] -> ()
        | [(a, (b, c))] -> Format.printf "(%d, (%d, %d))"  a b c
        | (a, (b, c)) :: t -> Format.printf "(%d, (%d, %d)); " a b c;
                              print_list t 



        let _ =
                Format.printf "[";
                print_list (goldbach_list_limit 9 20 5);
                Format.printf "]\n"
        
        let _ = 
                Format.printf "[";
                print_list (goldbach_list_limit 25 70 10);
                Format.printf "]\n"
        
        let _ =
                Format.printf "[";
                print_list (goldbach_list_limit 100 100 100);
                Format.printf "]\n"
        
        let _ =
                Format.printf "[";
                print_list (goldbach_list_limit 100 200 19);
                Format.printf "]\n"
        
        let _ =
                Format.printf "[";
                print_list (goldbach_list_limit 50 500 20);
                Format.printf "]\n"
        
        let _ =
                Format.printf "[";
                print_list (goldbach_list_limit 1 2000 50);
                Format.printf "]\n"
