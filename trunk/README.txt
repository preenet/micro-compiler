Pree Thiengburanathum
preenet@gmail.com
UC Assignment 11
due 12/01/08
README.txt
-------------------------

--------------------------------
Directory and File descriptions:
--------------------------------
*.java - Java source file
*.m - Micro source file
*.md - Micro Compiler data file
*.txt - Text file'

uc/
	ExternalFile.java
	MicroCompiler.java
grammar/
	Grammar.java
	Production.java
ll1grammar/
	Grammar.java
	NonTerminal.java
	Production.java
	Symbol.java
	Terminal.java
parser/
	GrammarAnalyzer.java
	LL1PredictGenerator.java
	Parser.java
	TermSet.java
scanner/
	Scanner.java
	TokenRecord.java
	TokenType.java
semantic/
	ExprRecord.java
	ExprType.java
	OpRecord.java
	SemanticRoutine.java
LL1MicroGrammar.md
makefile
program1.m
program2.m
README.txt


-----------------------
How to compile and run:
-----------------------
1) To extract the program type 'unrar x csc5640-a11.rar'
2) To compiler the program type 'make'
3) To run the program type 'java uc.MicroCompiler [micro_program.m]'
4) Make sure the input files LL1MicroGrammar.md and the target source file (*.m)
   are in the same folder as described as above structure.

---------------------------  
Work environment and tools:
---------------------------
Eclipse IDE with jre 1.6.0_07, Vista 32-bits and Ubuntu OS

------------------------------------------
Example of program in running(program1.m):
------------------------------------------
pree@Heroin:~/Public/csc5640-a11$ ls
bin  doc  LL1MicroGrammar.md  makefile  program1.m  program2.m  README.txt  src
pree@Heroin:~/Public/csc5640-a11$ make
mkdir -p bin
javac -classpath src -d bin -Xlint src/uc/ExternalFile.java
javac -classpath src -d bin -Xlint src/uc/MicroCompiler.java
javac -classpath src -d bin -Xlint src/grammar/Grammar.java
javac -classpath src -d bin -Xlint src/grammar/Production.java
javac -classpath src -d bin -Xlint src/parser/GrammarAnalyzer.java
javac -classpath src -d bin -Xlint src/parser/LL1PredictGenerator.java
javac -classpath src -d bin -Xlint src/parser/Parser.java
javac -classpath src -d bin -Xlint src/parser/TermSet.java
javac -classpath src -d bin -Xlint src/scanner/Scanner.java
javac -classpath src -d bin -Xlint src/scanner/TokenRecord.java
javac -classpath src -d bin -Xlint src/scanner/TokenType.java
javac -classpath src -d bin -Xlint src/semantic/ExprRecord.java
javac -classpath src -d bin -Xlint src/semantic/ExprType.java
javac -classpath src -d bin -Xlint src/semantic/OpRecord.java
javac -classpath src -d bin -Xlint src/semantic/SemanticRoutine.java
javac -classpath src -d bin -Xlint src/ll1grammar/Grammar.java
javac -classpath src -d bin -Xlint src/ll1grammar/NonTerminal.java
javac -classpath src -d bin -Xlint src/ll1grammar/Production.java
javac -classpath src -d bin -Xlint src/ll1grammar/Symbol.java
javac -classpath src -d bin -Xlint src/ll1grammar/Terminal.java
Micro Compiler Non Universal has been compiled.
pree@Heroin:~/Public/csc5640-a11$ cp *.md bin
pree@Heroin:~/Public/csc5640-a11$ cp *.m bin
pree@Heroin:~/Public/csc5640-a11$ cd bin
pree@Heroin:~/Public/csc5640-a11/bin$ java uc.MicroCompiler program1.m
Scanning:
Tokens after scanned:
[Id BeginSym Id PlusOp AssignOp Id PlusOp IntLiteral MultiOp Id Id Semicolon 
EndSym EofSym ]
Token Error 0 error(s).
Retreive the first and follow set for the grammar:
First Set:
 [<system goal> [BeginSym]
, <program> [BeginSym]
, <stmt list> [ReadSym, WriteSym, Id]
, <statement> [ReadSym, WriteSym, Id]
, <ident> [Id]
, <assignment> [AssignOp, EqualOp]
, <expression> [LPalen, IntLiteral, Id]
, <id list> [Id]
, <expr list> [LPalen, IntLiteral, Id]
, <factor> [LPalen, IntLiteral, Id]
, <add op> [PlusOp, MinusOp]
, <primary> [LPalen, IntLiteral, Id]
, <mul op> [MultiOp]
]
Follow Set:
 [<system goal> [lambda]
, <program> [EofSym]
, <stmt list> [EndSym]
, <statement> [ReadSym, WriteSym, Id, EndSym]
, <ident> [AssignOp, EqualOp, Comma, RPalen, MultiOp, PlusOp, MinusOp, Semicolon]
, <assignment> [LPalen, IntLiteral, Id]
, <expression> [Semicolon, Comma, RPalen]
, <id list> [RPalen]
, <expr list> [RPalen]
, <factor> [PlusOp, MinusOp, Semicolon, Comma, RPalen]
, <add op> [LPalen, IntLiteral, Id]
, <primary> [MultiOp, PlusOp, MinusOp, Semicolon, Comma, RPalen]
, <mul op> [LPalen, IntLiteral, Id]
]

Recursive Descent Parsing with Error Recovery:
Check Input:
ValidSet : <program> [BeginSym]
FollowSet: <program> [EofSym]
HeaderSet: <program> [EofSym]
Syntax Error: Id
Union Set: <program> [BeginSym, EofSym]
Skipped token: Id

> BeginSym matched BeginSym
Check Input:
ValidSet : <stmt list> [ReadSym, WriteSym, Id]
FollowSet: <stmt list> [EndSym]
HeaderSet: <stmt list> [EofSym]
Valid token: Id

Check Input:
ValidSet : <statement> [ReadSym, WriteSym, Id]
FollowSet: <statement> [ReadSym, WriteSym, Id, EndSym]
HeaderSet: <statement> [EofSym]
Valid token: Id

Check Input:
ValidSet : <ident> [Id]
FollowSet: <ident> [AssignOp, EqualOp, Comma, RPalen, MultiOp, PlusOp, MinusOp, Semicolon]
HeaderSet: <ident> [EndSym]
Valid token: Id

> Id matched Id
>> checkId  A
Check Input:
ValidSet : <assignment> [AssignOp, EqualOp]
FollowSet: <assignment> [LPalen, IntLiteral, Id]
HeaderSet: <assignment> [EndSym]
Syntax Error: PlusOp
Union Set: <assignment> [AssignOp, EqualOp, LPalen, IntLiteral, Id, EndSym]
Skipped token: PlusOp

Syntax Error: PlusOp
Check Input:
ValidSet : <expression> [LPalen, IntLiteral, Id]
FollowSet: <expression> [Semicolon, Comma, RPalen]
HeaderSet: <expression> [EndSym]
Syntax Error: AssignOp
Union Set: <expression> [LPalen, IntLiteral, Id, Semicolon, Comma, RPalen, EndSym]
Skipped token: AssignOp

Check Input:
ValidSet : <factor> [LPalen, IntLiteral, Id]
FollowSet: <factor> [PlusOp, MinusOp, Semicolon, Comma, RPalen]
HeaderSet: <factor> [EndSym]
Valid token: Id

Check Input:
ValidSet : <primary> [LPalen, IntLiteral, Id]
FollowSet: <primary> [MultiOp, PlusOp, MinusOp, Semicolon, Comma, RPalen]
HeaderSet: <primary> [EndSym]
Valid token: Id

Check Input:
ValidSet : <ident> [Id]
FollowSet: <ident> [AssignOp, EqualOp, Comma, RPalen, MultiOp, PlusOp, MinusOp, Semicolon]
HeaderSet: <ident> [EndSym]
Valid token: Id

> Id matched Id
>> checkId  BB
Check Input:
ValidSet : <add op> [PlusOp, MinusOp]
FollowSet: <add op> [LPalen, IntLiteral, Id]
HeaderSet: <add op> [EndSym]
Valid token: PlusOp

> PlusOp matched PlusOp
Check Input:
ValidSet : <expression> [LPalen, IntLiteral, Id, Semicolon, Comma, RPalen, EndSym]
FollowSet: <expression> [Semicolon, Comma, RPalen]
HeaderSet: <expression> [EndSym]
Valid token: IntLiteral

Check Input:
ValidSet : <factor> [LPalen, IntLiteral, Id]
FollowSet: <factor> [PlusOp, MinusOp, Semicolon, Comma, RPalen]
HeaderSet: <factor> [EndSym]
Valid token: IntLiteral

Check Input:
ValidSet : <primary> [LPalen, IntLiteral, Id]
FollowSet: <primary> [MultiOp, PlusOp, MinusOp, Semicolon, Comma, RPalen]
HeaderSet: <primary> [EndSym]
Valid token: IntLiteral

> IntLiteral matched IntLiteral
Check Input:
ValidSet : <mul op> [MultiOp]
FollowSet: <mul op> [LPalen, IntLiteral, Id]
HeaderSet: <mul op> [EndSym]
Valid token: MultiOp

> MultiOp matched MultiOp
Check Input:
ValidSet : <expression> [LPalen, IntLiteral, Id, Semicolon, Comma, RPalen, EndSym]
FollowSet: <expression> [Semicolon, Comma, RPalen]
HeaderSet: <expression> [EndSym]
Valid token: Id

Check Input:
ValidSet : <factor> [LPalen, IntLiteral, Id]
FollowSet: <factor> [PlusOp, MinusOp, Semicolon, Comma, RPalen]
HeaderSet: <factor> [EndSym]
Valid token: Id

Check Input:
ValidSet : <primary> [LPalen, IntLiteral, Id]
FollowSet: <primary> [MultiOp, PlusOp, MinusOp, Semicolon, Comma, RPalen]
HeaderSet: <primary> [EndSym]
Valid token: Id

Check Input:
ValidSet : <ident> [Id]
FollowSet: <ident> [AssignOp, EqualOp, Comma, RPalen, MultiOp, PlusOp, MinusOp, Semicolon]
HeaderSet: <ident> [EndSym]
Valid token: Id

> Id matched Id
>> checkId  A
>> checkId  Temp&1
>> checkId  Temp&2
> Semicolon matched Id
Syntax Error: Semicolon
Found: Semicolon
> EndSym matched Semicolon
Syntax Error: EndSym
> EofSym matched Semicolon
Syntax Error: EofSym
Skipped: EndSym
Found: EofSym

Demonstrate of the parser:
Grammar for Micro language:
1 <program> -> BeginSym <stmt list> EndSym
2 <stmt list> -> <statement>
3 <stmt list> -> <statement> <stmt list>
4 <statement> -> <ident> <assignment> <expression> SemiColon
5 <statement> -> ReadSym (<id list>)SemiColon
6 <statement> -> WriteSym (<expr list>)SemiColon
7 <id list> -> <ident>
8 <id list> -> <ident> Comma <id list>
9 <id list> -> 
10 <expr list> -> <expression>
11 <expr list> -> <expression>, <expr list>
12 <assignment> -> AssignOp
13 <assignment> -> EqualOp
14 <expression> -> <factor>
15 <expression> -> <factor> <add op> <expression>
16 <factor> -> <primary>
17 <factor> -> <primary> <mul op> <expression>
18 <primary> -> (<expression>)
19 <primary> -> <ident>
20 <primary> -> IntLiteral
21 <ident> -> Id
22 <add op> -> PlusOp
23 <add op> -> MinusOp
24 <mul op> -> MultiOp
25 <system goal> -> <program> EofSym

Grammar rule applied:
25, 1, 2, 4, 21, 15, 16, 19, 21, 22, 14, 17, 20, 24, 14, 16, 19, 21, 

Parsing Derivation:
<system goal>	25—> <program> EofSym
	1—> BeginSym <stmt list> EndSym EofSym
	2—> BeginSym <statement> EndSym EofSym
	4—> BeginSym <ident> <assignment> <expression> SemiColon EndSym EofSym
	21—> BeginSym Id <assignment> <expression> SemiColon EndSym EofSym
	15—> BeginSym Id <assignment> <factor> <add op> <expression> SemiColon EndSym EofSym
	16—> BeginSym Id <assignment> <primary> <add op> <expression> SemiColon EndSym EofSym
	19—> BeginSym Id <assignment> <ident> <add op> <expression> SemiColon EndSym EofSym
	21—> BeginSym Id <assignment> Id <add op> <expression> SemiColon EndSym EofSym
	22—> BeginSym Id <assignment> Id PlusOp <expression> SemiColon EndSym EofSym
	14—> BeginSym Id <assignment> Id PlusOp <factor> SemiColon EndSym EofSym
	17—> BeginSym Id <assignment> Id PlusOp <primary> <mul op> <expression> SemiColon EndSym EofSym
	20—> BeginSym Id <assignment> Id PlusOp IntLiteral <mul op> <expression> SemiColon EndSym EofSym
	24—> BeginSym Id <assignment> Id PlusOp IntLiteral MultiOp <expression> SemiColon EndSym EofSym
	14—> BeginSym Id <assignment> Id PlusOp IntLiteral MultiOp <factor> SemiColon EndSym EofSym
	16—> BeginSym Id <assignment> Id PlusOp IntLiteral MultiOp <primary> SemiColon EndSym EofSym
	19—> BeginSym Id <assignment> Id PlusOp IntLiteral MultiOp <ident> SemiColon EndSym EofSym
	21—> BeginSym Id <assignment> Id PlusOp IntLiteral MultiOp Id SemiColon EndSym EofSym

Parsing Steps Trace:
Step Parser Action			Remaining Input				Generated Code
---- -------------			---------------				----------------------
(1)  Call systemGoal()                  x BEGIN A + := BB + 314 * A y ; END $ 
(2)  Call program()                     BEGIN A + := BB + 314 * A y ; END $ 
(3)  Semantic Action: start()           BEGIN A + := BB + 314 * A y ; END $ 
(4)  match(BeginSym)                    BEGIN A + := BB + 314 * A y ; END $ 
(5)  Call statementList()               A + := BB + 314 * A y ; END $ 
(6)  Call statement()                   A + := BB + 314 * A y ; END $ 
(7)  Call ident()                       A + := BB + 314 * A y ; END $ 
(8)  match(Id)                          A + := BB + 314 * A y ; END $ 
(9)  Semantic Action: processId()       + := BB + 314 * A y ; END $ [ Declare A, Integer ] 
(10)  Call assignment()                  := BB + 314 * A y ; END $ 
(11)  Call expression()[1]               BB + 314 * A y ; END $ 
(12)  Call factor()                      BB + 314 * A y ; END $ 
(13)  Call primary()                     BB + 314 * A y ; END $ 
(14)  Call ident()                       BB + 314 * A y ; END $ 
(15)  match(Id)                          BB + 314 * A y ; END $ 
(16)  Semantic Action: processId()       + 314 * A y ; END $ [ Declare BB, Integer ] 
(17)  Call addOp()                       + 314 * A y ; END $ 
(18)  match(PlusOp)                      + 314 * A y ; END $ 
(19)  Semantic Action: processOp()       314 * A y ; END $ 
(20)  Call expression()[2]               314 * A y ; END $ 
(21)  Call factor()                      314 * A y ; END $ 
(22)  Call primary()                     314 * A y ; END $ 
(23)  match(IntLiteral)                  314 * A y ; END $ 
(24)  Semantic Action: processLiteral()  * A y ; END $ 
(25)  Call MulOp()                       * A y ; END $ 
(26)  match(MultiOp)                     * A y ; END $ 
(27)  Semantic Action: processOp()       A y ; END $ 
(28)  Call expression()[3]               A y ; END $ 
(29)  Call factor()                      A y ; END $ 
(30)  Call primary()                     A y ; END $ 
(31)  Call ident()                       A y ; END $ 
(32)  match(Id)                          A y ; END $ 
(33)  Semantic Action: processId()       y ; END $ 
(34)  Semantic Action: genInfix()        y ; END $ [ Declare Temp&1, Integer ] [ MULT 314, A, Temp&1 ] 
(35)  Semantic Action: genInfix()        y ; END $ [ Declare Temp&2, Integer ] [ ADD BB, Temp&1, Temp&2 ] 
(36)  Semantic Action: assign()          y ; END $ [ Store Temp&2, A ] 
(37)  match(Semicolon)                   y ; END $ 
(38)  match(EndSym)                      ; END $ 
(39)  match(EofSym)                      ; END $ 
(40)  Semantic Action: finish()          $ [ Halt ] 


Symbol Table:
A
BB
Temp&1
Temp&2


Micro Generated Code:
[ Declare A, Integer ] 
[ Declare BB, Integer ] 
[ Declare Temp&1, Integer ] [ MULT 314, A, Temp&1 ] 
[ Declare Temp&2, Integer ] [ ADD BB, Temp&1, Temp&2 ] 
[ Store Temp&2, A ] 
[ Halt ] 

Done compilng!