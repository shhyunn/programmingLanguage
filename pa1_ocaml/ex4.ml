let merge lst1 lst2 =
        let merge_list = lst1 @ lst2 in
        let up_list = List.sort compare merge_list in
        let rec sort_down lst down_list =
                match lst with
                | [] -> down_list
                | head :: remain -> sort_down remain (head :: down_list)
        in 
        let res = sort_down up_list [] in
        Format.printf "[";
        let rec print_list res =
                match res with
                | [] -> Format.printf "]\n"
                | [one] -> Format.printf "%d]\n" one
                | head :: remain -> Format.printf "%d;" head; print_list remain in
        print_list res


 
let _ =
        let _ = merge [3;2;1] [5;4] in
        let _ = merge [5;3] [5;2] in
        let _ = merge [4;2] [] in
        let _ = merge [] [2;1] in
        let _ = merge [] [] in
        let _ = merge [0;0;0;0] [0;0;0;0] in
        let _ = merge [4;3;-2] [9;7;7] in
        merge [-2;-999] [] 
