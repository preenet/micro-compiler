-- test input 2 from ass-t 1 with multiple syntactic errors.
A + B BEGIN --SOMETHING UNUSUAL
	READ(A1, New_A, D, B);
	C-:= A1 +(New_A - * D) - 75;
	New_C * =((B - (7)+(C+D))) � (3 � + A1); -- STUPID FORMULA
	WRITE (C, A1+-New_C) X;
	-- WHAT ABOUT := B+D;
END
