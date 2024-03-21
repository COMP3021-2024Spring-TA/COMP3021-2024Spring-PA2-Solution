package hk.ust.comp3021.misc;

import hk.ust.comp3021.utils.*;
import java.util.ArrayList;


public class ASTEnumOp extends ASTElement {

    /*
     * expr_context = Load | Store | Del
     *
     * boolop = And | Or
     *
     * operator = Add | Sub | Mult | MatMult | Div | Mod | Pow | LShift | RShift |
     * BitOr | BitXor | BitAnd | FloorDiv
     *
     * unaryop = Invert | Not | UAdd | USub
     *
     * cmpop = Eq | NotEq | Lt | LtE | Gt | GtE | Is | IsNot | In | NotIn
     */

    public enum ASTOperator {
        Ctx_Load, Ctx_Store, Ctx_Del,
        OP_And, OP_Or,
        OP_Add, OP_Sub, OP_Mult, OP_MatMult, OP_Div, OP_Mod, OP_Pow, OP_LShift, OP_RShift, OP_BitOr, OP_BitXor,
        OP_BitAnd, OP_FloorDiv,
        OP_Invert, OP_Not, OP_UAdd, OP_USub,
        OP_Eq, OP_NotEq, OP_Lt, OP_LtE, OP_Gt, OP_GtE, OP_Is, OP_IsNot, OP_In, OP_NotIn
    }

    private ASTOperator op;

    public ASTEnumOp(XMLNode node) {
        switch (node.getTagName()) {
        case "Load":
            this.op = ASTOperator.Ctx_Load;
            break;
        case "Store":
            this.op = ASTOperator.Ctx_Store;
            break;
        case "Del":
            this.op = ASTOperator.Ctx_Del;
            break;
        case "And":
            this.op = ASTOperator.OP_And;
            break;
        case "Or":
            this.op = ASTOperator.OP_Or;
            break;
        case "Add":
            this.op = ASTOperator.OP_Add;
            break;
        case "Sub":
            this.op = ASTOperator.OP_Sub;
            break;
        case "Mult":
            this.op = ASTOperator.OP_Mult;
            break;
        case "MatMult":
            this.op = ASTOperator.OP_MatMult;
            break;
        case "Div":
            this.op = ASTOperator.OP_Div;
            break;
        case "Mod":
            this.op = ASTOperator.OP_Mod;
            break;
        case "Pow":
            this.op = ASTOperator.OP_Pow;
            break;
        case "LShift":
            this.op = ASTOperator.OP_LShift;
            break;
        case "RShift":
            this.op = ASTOperator.OP_RShift;
            break;
        case "BitOr":
            this.op = ASTOperator.OP_BitOr;
            break;
        case "BitXor":
            this.op = ASTOperator.OP_BitXor;
            break;
        case "BitAnd":
            this.op = ASTOperator.OP_BitAnd;
            break;
        case "FloorDiv":
            this.op = ASTOperator.OP_FloorDiv;
            break;
        case "Invert":
            this.op = ASTOperator.OP_Invert;
            break;
        case "Not":
            this.op = ASTOperator.OP_Not;
            break;
        case "UAdd":
            this.op = ASTOperator.OP_UAdd;
            break;
        case "USub":
            this.op = ASTOperator.OP_USub;
            break;
        case "Eq":
            this.op = ASTOperator.OP_Eq;
            break;
        case "NotEq":
            this.op = ASTOperator.OP_NotEq;
            break;
        case "Lt":
            this.op = ASTOperator.OP_Lt;
            break;
        case "LtE":
            this.op = ASTOperator.OP_LtE;
            break;
        case "Gt":
            this.op = ASTOperator.OP_Gt;
            break;
        case "GtE":
            this.op = ASTOperator.OP_GtE;
            break;
        case "Is":
            this.op = ASTOperator.OP_Is;
            break;
        case "IsNot":
            this.op = ASTOperator.OP_IsNot;
            break;
        case "In":
            this.op = ASTOperator.OP_In;
            break;
        case "NotIn":
            this.op = ASTOperator.OP_NotIn;
            break;
        default:
            throw new IllegalArgumentException("Unsupported tag name: " + node.getTagName());
        }
    }

    public boolean isOperator() {
        if (this.op == ASTOperator.Ctx_Del || this.op == ASTOperator.Ctx_Load || this.op == ASTOperator.Ctx_Store) {
            return false;
        }
        return true;
    }

    public String getOperatorName() {
        String name = this.op.name();
        if (name.startsWith("OP_")) {
            return name.substring(3);
        }
        return "";
    }

    public ASTOperator getOp() {
        return op;
    }

    @Override
    public ArrayList<ASTElement> getChildren() {
        ArrayList<ASTElement> children = new ArrayList<>();
        return children;
    }
    
    @Override
    public String getNodeType() {
        return this.op.name();
    }

    @Override
    public String toString() {
        switch (this.op) {
            case OP_And:
                return "and";
                
            case OP_Or:
                return "or";
                
            case OP_Add:
                return "+";
                
            case OP_Sub:
                return "-";
                
            case OP_Mult:
                return "*";
                
            case OP_MatMult:
                return "@";
                
            case OP_Div:
                return "/";
                
            case OP_Mod:
                return "%";
                
            case OP_Pow:
                return "**";
                
            case OP_LShift:
                return "<<";
                
            case OP_RShift:
                return ">>";
                
            case OP_BitOr:
                return "|";
                
            case OP_BitXor:
                return "^";
                
            case OP_BitAnd:
                return "&";
                
            case OP_FloorDiv:
                return "//";
                
            case OP_Invert:
                return "~";
                
            case OP_Not:
                return "not";
                
            case OP_UAdd:
                return "+";
                
            case OP_USub:
                return "-";
                
            case OP_Eq:
                return "==";
                
            case OP_NotEq:
                return "!=";
                
            case OP_Lt:
                return "<";
                
            case OP_LtE:
                return "<=";
                
            case OP_Gt:
                return ">";
                
            case OP_GtE:
                return ">=";
                
            case OP_Is:
                return "is";
                
            case OP_IsNot:
                return "is not";
                
            case OP_In:
                return "in";
                
            case OP_NotIn:
                return "not in";
                
            default:
                return "unknown operator";
                
        }
    }

    @Override
    public void printByPos(StringBuilder str) {
        str.append(toString());
    }

    public int getPrecedence() {
        switch (this.op) {
            case OP_And:
                return 3;
            case OP_Or:
                return 2;
            case OP_Add:
                return 11;
            case OP_Sub:
                return 11;
            case OP_Mult:
                return 12;
            case OP_MatMult:
                return 12;
            case OP_Div:
                return 12;
            case OP_Mod:
                return 12;
            case OP_Pow:
                return 14;
            case OP_LShift:
                return 10;
            case OP_RShift:
                return 10;
            case OP_BitOr:
                return 7;
            case OP_BitXor:
                return 8;
            case OP_BitAnd:
                return 9;
            case OP_FloorDiv:
                return 12;
            case OP_Invert:
                return 13;
            case OP_Not:
                return 5;
            case OP_UAdd:
                return 13;
            case OP_USub:
                return 13;
            case OP_Eq:
                return 5;
            case OP_NotEq:
                return 5;
            case OP_Lt:
                return 5;
            case OP_LtE:
                return 5;
            case OP_Gt:
                return 5;
            case OP_GtE:
                return 5;
            case OP_Is:
                return 5;
            case OP_IsNot:
                return 5;
            case OP_In:
                return 5;
            case OP_NotIn:
                return 5;
            default:
                return -1;
        }
    }

}
