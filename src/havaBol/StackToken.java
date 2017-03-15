package havaBol;

public class StackToken {

	Token token;
	int iPrecedence = 0;
	int iStackPrecedence = 0;
	int iOperandCnt = 0;
	
	public StackToken(Token token)
	{
		this.token = token;
		switch (token.tokenStr)
		{
		case "(":
			this.iPrecedence = 15;
			this.iStackPrecedence = 2;
			this.iOperandCnt = 0;
			break;
			
		case "^":
			this.iPrecedence = 10;
			this.iStackPrecedence = 11;
			this.iOperandCnt = 2;
			break;
			
		case "*":
		case "/":
			this.iPrecedence = 9;
			this.iStackPrecedence = 9;
			this.iOperandCnt = 2;
			break;
			
		case "+":
		case "-":
			this.iPrecedence = 8;
			this.iStackPrecedence = 8;
			this.iOperandCnt = 2;
			break;
			
		case "#":
			this.iPrecedence = 7;
			this.iStackPrecedence = 7;
			this.iOperandCnt = 2;
			break;
			
		case "<":
		case ">":
		case "<=":
		case ">=":
		case "==":
		case "!=":
		case "in":
		case "notin":
			this.iPrecedence = 6;
			this.iStackPrecedence = 6;
			this.iOperandCnt = 2;
			break;
			
		case "not":
			this.iPrecedence = 5;
			this.iStackPrecedence = 5;
			this.iOperandCnt = 1;
			break;
		case "and":
		case "or":
			this.iPrecedence = 4;
			this.iStackPrecedence = 4;
			this.iOperandCnt = 2;
			break;
			
		case "u-":
			this.iPrecedence = 12;
			this.iStackPrecedence = 12;
			this.iOperandCnt = 1;
			break;
		}
	}
}
