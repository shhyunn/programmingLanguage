let rec fib i =
        match i with
        | 0 -> 0
        | 1 -> 1
        | n -> fib (n-2) + fib (n-1)

let _ =
        let _ = Format.printf "%d\n" (fib 0) in
        let _ = Format.printf "%d\n" (fib 1) in
        let _ = Format.printf "%d\n" (fib 2) in
        let _ = Format.printf "%d\n" (fib 3) in
        let _ = Format.printf "%d\n" (fib 7) in
        Format.printf "%d\n" (fib 10)
