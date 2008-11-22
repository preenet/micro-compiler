<system goal>	->	<program> EofSym
<program>	->	BeginSym <stmt list> EndSym
<stmt list>	->	<statement> <statement tail>
<statement tail>	->	<stmt list>
<statement tail>	->	lambda
<statement>	->	<ident> := <expression> ;
<statement> ->	ReadSym ( <id list> ) ;
<statement>	->	WriteSym ( <expr list> ) ;
<id list>	->	<ident> <id tail>
<id tail>	->	, <id list>
<id tail>	->	lambda
<expr list>	->	<expression> <expression tail>
<expression tail>	->	, <expr list>
<expression tail>	->	lambda
<expression>	->	<factor> <factor tail>
<factor tail>   ->  <add op> <expression>
<factor tail>   -> lambda
<factor>		->  <primary> <primary tail>
<primary tail>	->	<mul op> <factor>
<primary tail>	-> lambda
<primary>	->	( <expression> )
<primary>	->	<ident>
<primary>	->	IntLiteral
<add op>	->	PlusOp
<add op>	->	MinusOp
<mul op>	->  MultiOp
<ident>		->  Id
