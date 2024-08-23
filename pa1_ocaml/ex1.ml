let rec gcd n m =
        if m = 0 then n
        else gcd m (n mod m) 


let _ =
        let _ = Format.printf "%d\n" (gcd 10 0) in
        let _ = Format.printf "%d\n" (gcd 9 5) in
        let _ = Format.printf "%d\n" (gcd 13 13) in
        let _ = Format.printf "%d\n" (gcd 37 600) in
        Format.printf "%d\n" (gcd 0 0) 
