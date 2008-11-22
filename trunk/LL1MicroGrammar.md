<system goal>	->	<program> EofSym
<program>	->	BeginSym <stmt list> EndSym
<stmt list>	->	<statement> <statement tail>
<statement tail>	->	<stmt list>
<statement tail>	->	lambda
<statement>	->	<ident> AssignOp <expression> Semicolon
<statement> ->	ReadSym LPalen <id list> RPalen Semicolon
<statement>	->	WriteSym LPalen <expr list> RPalen Semicolon
<id list>	->	<ident> <id tail>
<id tail>	->	Comma <id list>
<id tail>	->	lambda
<expr list>	->	<expression> <expression tail>
<expression tail>	->	Comma <expr list>
<expression tail>	->	lambda
<expression>	->	<factor> <factor tail>
<factor tail>   ->  <add op> <expression>
<factor tail>   -> lambda
<factor>		->  <primary> <primary tail>
<primary tail>	->	<mul op> <factor>
<primary tail>	-> lambda
<primary>	->	LPalen <expression> RPalen
<primary>	->	<ident>
<primary>	->	IntLiteral
<add op>	->	PlusOp
<add op>	->	MinusOp
<mul op>	->  MultiOp
<ident>		->  Id
